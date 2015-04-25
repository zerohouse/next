package next.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class ClassUtil {

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> cLass) {
		List<Object> params = new ArrayList<Object>();
		Class<?>[] paramTypes = cLass.getConstructors()[0].getParameterTypes();
		if (paramTypes.length == 0)
			try {
				return cLass.getConstructor().newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e1) {
				return null;
			}
		Object obj = null;
		for (int i = 0; i < paramTypes.length; i++) {
			obj = getDefaultValue(paramTypes[i]);
			params.add(obj);
		}
		try {
			return (T) cLass.getConstructors()[0].newInstance(params.toArray());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			return null;
		}
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
