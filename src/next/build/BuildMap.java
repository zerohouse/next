package next.build;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import next.annotation.Build;
import next.annotation.ImplementedBy;
import next.build.jobject.JArray;
import next.build.jobject.JMap;
import next.resource.Static;
import next.setting.Setting;
import next.util.LoggerUtil;
import next.util.Parser;

import org.slf4j.Logger;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class BuildMap {

	private final static Logger logger = LoggerUtil.getLogger(BuildMap.class);

	private static BuildMap instance = new BuildMap();

	public static Object get(Class<?> type, String value) {
		if (!instance.idMap.get(value).getClass().equals(type)) {
			logger.warn(String.format("id:%s %s클래스인데, %s클래스로 Build를 시도하였습니다.", value, instance.idMap.get(value).getClass(), type));
			return null;
		}
		return instance.idMap.get(value);
	}

	private Map<String, Object> idMap;
	private JMap jmap;
	Set<Field> fields;

	private BuildMap() {
		fields = Static.getReflections().getFieldsAnnotatedWith(Build.class);
		idMap = new HashMap<String, Object>();
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(Setting.class.getResource("/build.json").getFile()));
			jmap = new JMap(reader);
			makeInstances();
			buildDependenciesAgain();
		} catch (IOException e) {
			logger.warn("세팅이 잘못되었습니다.");
			e.printStackTrace();
		}
	}

	private void buildDependenciesAgain() {
		idMap.forEach((k, v) -> {
			buildDependencies(v);
		});
	}

	public void makeInstances() {
		fields.forEach(field -> {
			getIfNotExistBuild(field);
		});
		logger.debug(idMap.toString());
	}

	private Object getIfNotExistBuild(Field field) {
		Build annotation = field.getAnnotation(Build.class);
		String id = annotation.value();
		Class<?> type = field.getType();
		if (field.isAnnotationPresent(ImplementedBy.class))
			type = field.getAnnotation(ImplementedBy.class).value();
		if (id.equals("")) {
			id = type.getName();
		}
		Object obj = idMap.get(id);
		if (obj != null) {
			if (!obj.getClass().equals(type)) {
				logger.debug(String.format("id:%s 이미 클래스 %s 빌드되었습니다. %s 빌드를 시도하였습니다.", id, obj.getClass(), type));
				return null;
			}
			return obj;
		}
		if (jmap.get(id) == null)
			return buildEmpty(type, id);
		return buildJson(type, id);
	}

	private Object buildJson(Class<?> type, String id) {
		if (jmap.get(id).getClass().equals(type)) {
			logger.debug(String.format("id:%s-> %s 빌드되었습니다.", id, type));
			idMap.put(id, jmap.get(id));
			buildDependencies(jmap.get(id));
			return jmap.get(id);
		}
		if (jmap.get(id).getClass().equals(JArray.class))
			return buildJArrayObj(type, id);
		if (jmap.get(id).getClass().equals(JMap.class))
			return buildJMapObj(type, id);
		return buildEmpty(type, id);
	}

	private Object buildJMapObj(Class<?> type, String id) {
		JMap jm = (JMap) jmap.get(id);
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<Object, Object> idmap = new HashMap<Object, Object>();
		jm.forEach((k, v) -> {
			if (v.toString().startsWith("#")) {
				idmap.put(k, v.toString().substring(1));
				return;
			}
			map.put(k, v);
		});
		Gson gson = Setting.getGson();
		Object obj = gson.fromJson(gson.toJson(map), type);
		idMap.put(id, obj);
		buildDependencies(obj);
		if (Map.class.isAssignableFrom(type)) {
			try {
				Method m = type.getMethod("put", Object.class, Object.class);
				idmap.forEach((k, v) -> {
					Object param = idMap.get(v.toString());
					if (param == null)
						param = getPrimitiveById(v.toString());
					if (param != null)
						try {
							m.invoke(obj, k, param);
						} catch (Exception e) {
							e.printStackTrace();
						}
				});
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	private Object buildJArrayObj(Class<?> type, String id) {
		JArray jm = (JArray) jmap.get(id);
		List<Object> obList = new ArrayList<Object>();
		List<Object> idList = new ArrayList<Object>();
		jm.forEach(each -> {
			if (each.toString().startsWith("#")) {
				idList.add(each.toString().substring(1));
				return;
			}
			obList.add(each);
		});
		Gson gson = Setting.getGson();
		Object obj = gson.fromJson(gson.toJson(obList), type);
		idMap.put(id, obj);
		buildDependencies(obj);
		if (List.class.isAssignableFrom(type)) {
			try {
				Method m = type.getMethod("add", Object.class);
				idList.forEach(v -> {
					Object param = idMap.get(v.toString());
					if (param == null)
						param = getPrimitiveById(v.toString());
					if (param != null)
						try {
							m.invoke(obj, param);
						} catch (Exception e) {
							e.printStackTrace();
						}
				});
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	private Object buildEmpty(Class<?> type, String id) {
		Object obj = Parser.newInstance(type);
		idMap.put(id, obj);
		buildDependencies(obj);
		logger.debug(String.format("id:%s-> %s 빌드되었습니다.", id, type));
		return obj;
	}

	private void buildDependencies(Object obj) {
		logger.debug(String.format("%s Dependency를 빌드합니다.", obj.getClass()));
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (!fields[i].isAnnotationPresent(Build.class))
				continue;
			fields[i].setAccessible(true);
			try {
				fields[i].set(obj, getIfNotExistBuild(fields[i]));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

	}

	private Object getPrimitiveById(String id) {
		if (jmap.get(id) == null)
			return null;
		if (jmap.get(id).getClass().equals(JArray.class))
			return null;
		if (jmap.get(id).getClass().equals(JMap.class))
			return null;
		return jmap.get(id);
	}

	public String toString() {
		return "BuildMap [idmap=" + idMap + ",\n jmap=" + jmap + ",\n fields=" + fields + "]";
	}

	public static BuildMap getInstance() {
		return instance;
	}

}
