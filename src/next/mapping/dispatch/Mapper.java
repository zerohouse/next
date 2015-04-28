package next.mapping.dispatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import next.instance.InstancePool;
import next.instance.wrapper.MethodWrapper;
import next.mapping.annotation.HttpMethod;
import next.mapping.annotation.Mapping;
import next.mapping.http.Http;
import next.mapping.response.Json;
import next.mapping.response.Response;
import next.resource.Static;
import next.util.LoggerUtil;

import org.slf4j.Logger;

public class Mapper {

	private static final Logger logger = LoggerUtil.getLogger(Mapper.class);

	private Map<String, MethodWrapper> methodMap;
	private UriMap uriMap;

	private InstancePool instancePool;

	Mapper() {
		instancePool = new InstancePool();
		methodMap = new HashMap<String, MethodWrapper>();
		uriMap = new UriMap();
		makeMethodMap();
		makeUriMap();
	}

	private void makeMethodMap() {
		Static.getReflections().getMethodsAnnotatedWith(HttpMethod.class).forEach(m -> {
			Object instance = instancePool.getInstance(m.getDeclaringClass());
			HttpMethod method = m.getAnnotation(HttpMethod.class);
			String methodName = method.value();
			if (methodName.equals(""))
				methodName = m.getName();
			methodMap.put(methodName, new MethodWrapper(instance, m));
		});
		logger.info(methodMap.toString());
	}

	private void makeUriMap() {
		Static.getReflections().getMethodsAnnotatedWith(Mapping.class).forEach(m -> {
			Class<?> declaringClass = m.getDeclaringClass();
			String[] prefix;
			List<MethodWrapper> methodList = new ArrayList<MethodWrapper>();
			String[] beforeClass = null;
			String[] afterClass = null;
			if (declaringClass.isAnnotationPresent(Mapping.class)) {
				Mapping mapping = declaringClass.getAnnotation(Mapping.class);
				prefix = mapping.value();
				beforeClass = mapping.before();
				afterClass = mapping.after();
			} else {
				prefix = new String[] { "" };
			}
			Mapping mapping = m.getAnnotation(Mapping.class);
			String[] before = mapping.before();
			String[] after = mapping.after();
			addAll(methodList, beforeClass);
			addAll(methodList, before);
			methodList.add(new MethodWrapper(instancePool.getInstance(declaringClass), m));
			addAll(methodList, after);
			addAll(methodList, afterClass);
			for (int i = 0; i < prefix.length; i++)
				for (int j = 0; j < mapping.method().length; j++)
					for (int k = 0; k < mapping.value().length; k++) {
						String method = mapping.method()[j];
						String uri = prefix[i] + mapping.value()[k];
						UriKey urikey = new UriKey(method, uri);
						uriMap.put(urikey, methodList);
						logger.info(String.format("Mapping : %s -> %s", urikey.toString(), methodList.toString()));
					}
		});
	}

	private void addAll(List<MethodWrapper> methodList, String[] stringArray) {
		if (stringArray == null)
			return;
		for (int i = 0; i < stringArray.length; i++) {
			if (stringArray[i].equals(""))
				continue;
			MethodWrapper method;
			if (stringArray[i].charAt(0) == '!') {
				method = methodMap.get(stringArray[i].substring(1));
				if (method == null){
					logger.warn(String.format("없는 Method [%s]를 제외하려고 했습니다.", stringArray[i]));
					continue;
					}		
						
				if(!methodList.contains(method)) {
					logger.warn(String.format("실행할 메소드리스트에 추가되지 않은 Method [%s]를 제외하려고 했습니다.", stringArray[i]));
					continue;
				}
				methodList.remove(method);
			}
			method = methodMap.get(stringArray[i]);
			if (method == null) {
				logger.warn(String.format("없는 Method [%s]를 매핑하려고 했습니다.", stringArray[i]));
				continue;
			}
			methodList.add(methodMap.get(stringArray[i]));
		}
	}

	public void execute(UriKey url, Http http) {
		List<MethodWrapper> methods = uriMap.get(url, http);
		if (methods == null) {
			http.sendError(404);
			return;
		}
		logger.debug(String.format("%s -> %s", url, methods.toString()));
		for (int i = 0; i < methods.size(); i++) {
			Object returned = methods.get(i).execute(http);
			if (returned == null)
				continue;
			if (returned.getClass().getInterfaces().length != 0)
				if (returned.getClass().getInterfaces()[0].equals(Response.class)) {
					((Response) returned).render(http);
					return;
				}
			if (returned.getClass().equals(String.class)) {
				String res = returned.toString();
				if (res.startsWith("redirect:")) {
					http.sendRedirect(res.substring(9));
					return;
				}
				http.forword(res);
				return;
			}
			new Json(returned).render(http);
			return;
		}
		new Json().render(http);
	}

}
