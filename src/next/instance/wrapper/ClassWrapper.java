package next.instance.wrapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import next.annotation.Build;
import next.annotation.ImplementedBy;
import next.build.Builder;
import next.instance.InstancePool;

public class ClassWrapper {
	private Class<?> type;
	private Object instance;

	public ClassWrapper(Class<?> type) {
		instance = newInstance(type);
		this.type = type;
	}

	public ClassWrapper(Class<?> fieldType, String value) {
		this.instance = Builder.get(fieldType, value);
		this.type = fieldType;
	}

	public void buildFields(InstancePool instancePool) {
		Field[] fields = type.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (!fields[i].isAnnotationPresent(Build.class))
				continue;
			setFields(instancePool, fields[i]);
		}
	}

	public Class<?> getImplType(Field field) {
		ImplementedBy impl = field.getAnnotation(ImplementedBy.class);
		String value = impl.value();
		Class<?> type = field.getType();
		if (impl.samePackage())
			value = type.getName().substring(0, type.getName().length() - type.getSimpleName().length()) + value;
		try {
			return Class.forName(value);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	private void setFields(InstancePool instancePool, Field field) {
		field.setAccessible(true);
		try {
			if (field.get(instance) != null)
				return;
			String value = field.getAnnotation(Build.class).value();
			Object build = null;
			Class<?> fieldType = null;
			if (field.isAnnotationPresent(ImplementedBy.class))
				fieldType = getImplType(field);
			else
				fieldType = field.getType();

			if (value.equals(""))
				build = instancePool.getInstance(fieldType);
			else {
				build = instancePool.getInstance(fieldType, value);
			}
			field.set(instance, build);
		} catch (Exception e) {
		}
	}

	public Object getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> type) {
		List<Object> params = new ArrayList<Object>();
		if (type.getConstructors().length == 0)
			return null;
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
