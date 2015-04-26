package next.instance.classwrapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import next.instance.InstanceMap;
import next.mapping.annotation.Autowired;

public class ClassWrapper {
	private Class<?> type;
	private Object instance;

	public ClassWrapper(Class<?> type) {
		instance = newInstance(type);
		this.type = type;
	}

	public void injectAutoWired(InstanceMap instanceMap) {
		Field[] fields = type.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (!fields[i].isAnnotationPresent(Autowired.class))
				continue;
			setFields(instanceMap, fields[i]);
		}
	}

	private void setFields(InstanceMap instanceMap, Field field) {
		field.setAccessible(true);
		try {
			if (field.get(instance) != null)
				return;
			String value = field.getAnnotation(Autowired.class).value();
			Object wired = null;
			Class<?> fieldType = field.getType();
			if (value.equals(""))
				wired = instanceMap.get(fieldType);
			else {
				String name = fieldType.getName().substring(0, fieldType.getName().length() - fieldType.getSimpleName().length()) + value;
				Class<?> type = Class.forName(name);
				wired = instanceMap.get(type);
				wired = type.cast(wired);
			}
			field.set(instance, wired);
		} catch (Exception e) {
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> type) {
		List<Object> params = new ArrayList<Object>();
		Class<?>[] paramTypes = type.getConstructors()[0].getParameterTypes();
		if (paramTypes.length == 0)
			try {
				return type.getConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		Object obj = null;
		for (int i = 0; i < paramTypes.length; i++) {
			obj = getDefaultValue(paramTypes[i]);
			params.add(obj);
		}
		try {
			return (T) type.getConstructors()[0].newInstance(params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Object getDefaultValue(Class<?> paramType) {
		if (paramType.equals(byte.class)) {
			return 0;
		}
		if (paramType.equals(byte.class)) {
			return 0;
		}
		if (paramType.equals(short.class)) {
			return 0;
		}
		if (paramType.equals(int.class)) {
			return 0;
		}
		if (paramType.equals(long.class)) {
			return 0L;
		}
		if (paramType.equals(float.class)) {
			return 0.0f;
		}
		if (paramType.equals(double.class)) {
			return 0.0d;
		}
		if (paramType.equals(char.class)) {
			return '\u0000';
		}
		if (paramType.equals(boolean.class)) {
			return false;
		}
		return null;
	}

}
