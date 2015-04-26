package next.instance.wrapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import next.mapping.exception.RequiredParamNullException;
import next.mapping.http.Http;
import next.mapping.response.Json;

public class MethodWrapper {

	private Object instance;
	private Method method;

	public MethodWrapper(Object instance, Method method) {
		this.method = method;
		this.instance = instance;
	}

	public Method getMethod() {
		return method;
	}

	public Object execute(Http http) {
		Object returnValue = null;
		ParameterArray paramArray = null;
		try {
			paramArray = new ParameterArray(http, method);
			returnValue = method.invoke(instance, paramArray.getArray());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			return new Json(true, e.getMessage(), null);
		} catch (RequiredParamNullException e) {
			return new Json(true, e.getMessage(), null);
		} finally {
			if (paramArray != null)
				paramArray.close();
		}
		return returnValue;
	}

	@Override
	public String toString() {
		return method.getName();
	}

	public boolean needDAO() {
		return method.getParameterCount() == 2;
	}
}
