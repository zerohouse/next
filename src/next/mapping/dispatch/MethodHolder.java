package next.mapping.dispatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.database.DAO;
import next.mapping.annotation.parameters.FromDB;
import next.mapping.annotation.parameters.JsonParameter;
import next.mapping.annotation.parameters.Parameter;
import next.mapping.annotation.parameters.SessionAttribute;
import next.mapping.exception.RequiredParamNullException;
import next.mapping.http.Http;
import next.mapping.response.support.Result;

public class MethodHolder {

	private Object instance;
	private Method method;

	public MethodHolder(Object instance, Method method) {
		this.method = method;
		this.instance = instance;
	}

	public Method getMethod() {
		return method;
	}

	public Object execute(Http http, DAO dao) {
		Object returnValue = null;
		try {
			List<Object> parameterArray = makeInsertParameters(http, dao);
			returnValue = method.invoke(instance, parameterArray.toArray());
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			return new Result(true, e.getMessage());
		} catch (RequiredParamNullException e) {
			return new Result(true, e.getMessage());
		}
		return returnValue;
	}

	private List<Object> makeInsertParameters(Http http, DAO dao) throws RequiredParamNullException {
		Class<?>[] types = method.getParameterTypes();
		java.lang.reflect.Parameter[] obj = method.getParameters();
		List<Object> parameterArray = new ArrayList<Object>();
		for (int i = 0; i < obj.length; i++) {
			if (types[i].equals(Http.class)) {
				parameterArray.add(http);
				continue;
			}
			if (types[i].equals(DAO.class)) {
				parameterArray.add(dao);
				continue;
			}
			if (types[i].equals(HttpServletRequest.class)) {
				parameterArray.add(http.getReq());
				continue;
			}
			if (types[i].equals(HttpServletResponse.class)) {
				parameterArray.add(http.getResp());
				continue;
			}
			if (obj[i].isAnnotationPresent(Parameter.class)) {
				Parameter param = obj[i].getAnnotation(Parameter.class);
				String name = param.value();
				String value = http.getParameter(name);
				if (param.require() && value == null)
					throw new RequiredParamNullException("필수 파라미터가 빠졌습니다.");
				parameterArray.add(value);
				continue;
			}
			if (obj[i].isAnnotationPresent(JsonParameter.class)) {
				JsonParameter jparam = obj[i].getAnnotation(JsonParameter.class);
				String name = jparam.value();
				Object value = http.getJsonObject(Object.class, name);
				if (jparam.require() && value == null)
					throw new RequiredParamNullException("필수 파라미터가 빠졌습니다.");
				parameterArray.add(value);
				continue;
			}
			if (obj[i].isAnnotationPresent(SessionAttribute.class)) {
				SessionAttribute session = obj[i].getAnnotation(SessionAttribute.class);
				String name = session.value();
				Object value = http.getSessionAttribute(Object.class, name);
				if (session.require() && value == null)
					throw new RequiredParamNullException("세션이 만료되었거나 잘못된 접근입니다.");
				parameterArray.add(value);
				continue;
			}
			if (obj[i].isAnnotationPresent(FromDB.class)) {
				FromDB param = obj[i].getAnnotation(FromDB.class);
				String name = param.keyParameter();
				String value = http.getParameter(name);
				if (param.require() && value == null)
					throw new RequiredParamNullException("필수 파라미터가 빠졌습니다.");
				Object fromDB = dao.getObject(types[i], value);
				if (param.require() && fromDB == null)
					throw new RequiredParamNullException("해당 레코드가 없습니다.");
				parameterArray.add(fromDB);
				continue;
			}
			parameterArray.add(null);
		}
		return parameterArray;
	}

	@Override
	public String toString() {
		return method.getName();
	}

	public boolean needDAO() {
		return method.getParameterCount() == 2;
	}
}
