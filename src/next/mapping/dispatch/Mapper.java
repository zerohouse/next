package next.mapping.dispatch;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import next.database.DAO;
import next.mapping.annotation.After;
import next.mapping.annotation.Before;
import next.mapping.annotation.HttpMethod;
import next.mapping.annotation.Mapping;
import next.mapping.dispatch.support.ClassFinder;
import next.mapping.http.Http;
import next.setting.Setting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mapper {

	private static final Logger logger = LoggerFactory.getLogger(Mapper.class);
	private Map<UriKey, MethodHolder> methodMap = new HashMap<UriKey, MethodHolder>();
	private UriMap uriMap = new UriMap();
	private List<MethodHolder> beforeList = new ArrayList<MethodHolder>();
	private List<MethodHolder> afterList = new ArrayList<MethodHolder>();

	Mapper() {
		ClassFinder cf = new ClassFinder();
		String path = Setting.get("controllerPath");
		cf.find(path).forEach(cLass -> {
			makeMethodMap(cLass);
		});
		cf.find(path).forEach(cLass -> {
			makeUriMap(cLass);
		});
	}

	public void execute(UriKey url, Http http) {
		List<MethodHolder> methods = uriMap.get(url, http);
		if (methods == null) {
			http.sendError(404);
			return;
		}
		DAO dao = null;

		Queue<MethodHolder> todo = new LinkedList<MethodHolder>();

		todo.addAll(beforeList);
		todo.addAll(methods);
		todo.addAll(afterList);

		logger.debug(String.format("Uri:%s -> %s", url, todo.toString()));

		while (!todo.isEmpty()) {
			MethodHolder mh = todo.poll();
			if (mh == null)
				continue;
			if (mh.needDAO() && dao == null)
				dao = new DAO();
			if (!mh.execute(http, dao))
				break;
		}

		if (dao != null)
			dao.commitAndClose();

		http.render();
	}

	private void makeMethodMap(Class<?> eachClass) {
		Method methods[] = eachClass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].isAnnotationPresent(Mapping.class)) {
				Mapping mapping = methods[i].getAnnotation(Mapping.class);
				UriKey key = new UriKey(mapping.method()[0], mapping.value()[0]);
				methodMap.put(key, new MethodHolder(methods[i]));
			}
			if (methods[i].isAnnotationPresent(HttpMethod.class)) {
				HttpMethod method = methods[i].getAnnotation(HttpMethod.class);
				String methodName = method.value();
				if (methodName.equals(""))
					methodName = methods[i].getName();
				methodMap.put(new UriKey(UriKey.METHOD, methodName), new MethodHolder(methods[i]));
			}
			if (methods[i].isAnnotationPresent(Before.class)) {
				beforeList.add(new MethodHolder(methods[i]));
			}
			if (methods[i].isAnnotationPresent(After.class)) {
				afterList.add(new MethodHolder(methods[i]));
			}
		}

	}

	private void makeUriMap(Class<?> eachClass) {
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
						UriKey urikey = new UriKey(mapping.method()[j], mapping.value()[k]);
						uriMap.put(urikey, methodList);
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
