package next.mapping.dispatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import next.database.ConnectionPool;
import next.database.DAO;
import next.database.MySql;
import next.mapping.annotation.After;
import next.mapping.annotation.Before;
import next.mapping.annotation.Controller;
import next.mapping.annotation.HttpMethod;
import next.mapping.annotation.Mapping;
import next.mapping.http.Http;
import next.mapping.response.Json;
import next.mapping.response.Response;
import next.setting.Setting;
import next.util.LoggerUtil;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.slf4j.Logger;

public class Mapper {

	private static final Logger logger = LoggerUtil.getLogger(Mapper.class);

	private Map<UriKey, MethodHolder> methodMap;
	private UriMap uriMap;
	private List<MethodHolder> beforeList;
	private List<MethodHolder> afterList;
	private ConnectionPool pool;

	Mapper(ConnectionPool pool) {
		this.pool = pool;
		methodMap = new HashMap<UriKey, MethodHolder>();
		uriMap = new UriMap();
		beforeList = new ArrayList<MethodHolder>();
		afterList = new ArrayList<MethodHolder>();
		Reflections ref = new Reflections(Setting.get().getMapping().getControllerPackage(), new SubTypesScanner(), new TypeAnnotationsScanner());
		ref.getTypesAnnotatedWith(Controller.class).forEach(cLass -> {
			try {
				makeMethodMap(cLass);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		ref.getTypesAnnotatedWith(Controller.class).forEach(cLass -> {
			makeUriMap(cLass);
		});
	}

	public void execute(UriKey url, Http http) {
		List<MethodHolder> methods = uriMap.get(url, http);
		if (methods == null) {
			http.sendError(404);
			return;
		}
		DAO dao = new MySql(pool.getConnection(false));

		Queue<MethodHolder> todo = new LinkedList<MethodHolder>();

		todo.addAll(beforeList);
		todo.addAll(methods);
		todo.addAll(afterList);

		logger.debug(String.format("%s -> %s", url, todo.toString()));

		while (!todo.isEmpty()) {
			MethodHolder mh = todo.poll();
			Object returned = mh.execute(http, dao);
			if (returned == null)
				continue;
			dao.close();
			if (returned.getClass().getInterfaces().length == 0) {
				new Json(returned).render(http);
				break;
			}
			if (returned.getClass().getInterfaces()[0].equals(Response.class)) {
				((Response) returned).render(http);
				break;
			}
		}
	}

	private void makeMethodMap(Class<?> eachClass) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		Object instance = eachClass.getConstructor().newInstance();
		Method methods[] = eachClass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].isAnnotationPresent(Mapping.class)) {
				Mapping mapping = methods[i].getAnnotation(Mapping.class);
				UriKey key = new UriKey(mapping.method()[0], mapping.value()[0]);
				methodMap.put(key, new MethodHolder(instance, methods[i]));
			}
			if (methods[i].isAnnotationPresent(HttpMethod.class)) {
				HttpMethod method = methods[i].getAnnotation(HttpMethod.class);
				String methodName = method.value();
				if (methodName.equals(""))
					methodName = methods[i].getName();
				methodMap.put(new UriKey(UriKey.METHOD, methodName), new MethodHolder(instance, methods[i]));
			}
			if (methods[i].isAnnotationPresent(Before.class)) {
				beforeList.add(new MethodHolder(instance, methods[i]));
			}
			if (methods[i].isAnnotationPresent(After.class)) {
				afterList.add(new MethodHolder(instance, methods[i]));
			}
		}
	}

	private void makeUriMap(Class<?> eachClass) {
		String prefix = "";
		if (eachClass.isAnnotationPresent(Mapping.class))
			prefix = eachClass.getAnnotation(Mapping.class).value()[0];
		Method methods[] = eachClass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].isAnnotationPresent(Mapping.class)) {
				Mapping mapping = methods[i].getAnnotation(Mapping.class);
				List<MethodHolder> methodList = new ArrayList<MethodHolder>();
				String[] before = mapping.before();
				String[] after = mapping.after();
				UriKey key = new UriKey(mapping.method()[0], mapping.value()[0]);
				addAll(methodList, before);
				methodList.add(methodMap.get(key));
				addAll(methodList, after);

				for (int j = 0; j < mapping.method().length; j++)
					for (int k = 0; k < mapping.value().length; k++) {
						String method = mapping.method()[j];
						String uri = prefix + mapping.value()[k];
						UriKey urikey = new UriKey(method, uri);
						uriMap.put(urikey, methodList);
						logger.info(String.format("Mapping : %s -> %s", urikey.toString(), methodList.toString()));
					}

			}
		}
	}

	private void addAll(List<MethodHolder> methodList, String[] stringArray) {
		for (int j = 0; j < stringArray.length; j++) {
			if (stringArray[j].equals(""))
				continue;
			methodList.add(methodMap.get(new UriKey(UriKey.METHOD, stringArray[j])));
		}
	}

}
