package next.instance.wrapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.database.DAO;
import next.database.GDAO;
import next.database.Transaction;
import next.mapping.annotation.parameters.FromDB;
import next.mapping.annotation.parameters.JsonParameter;
import next.mapping.annotation.parameters.Parameter;
import next.mapping.annotation.parameters.SessionAttribute;
import next.mapping.annotation.parameters.UriVariable;
import next.mapping.exception.RequiredParamNullException;
import next.mapping.http.Http;
import next.mapping.response.Json;
import next.resource.Static;

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

	public Object execute(Http http, Transaction transaction) {
		Object returnValue = null;
		try {
			returnValue = method.invoke(instance, getParamArray(http, method, transaction));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			return new Json(true, e.getMessage(), null);
		} catch (RequiredParamNullException e) {
			return new Json(true, e.getMessage(), null);
		}
		return returnValue;
	}

	@Override
	public String toString() {
		return method.getName();
	}

	public Object[] getParamArray(Http http, Method method, Transaction transaction) throws RequiredParamNullException {
		Class<?>[] types = method.getParameterTypes();
		java.lang.reflect.Parameter[] obj = method.getParameters();
		List<Object> parameters = new ArrayList<Object>();
		for (int i = 0; i < obj.length; i++) {
			if (types[i].equals(Http.class)) {
				parameters.add(http);
				continue;
			}
			if (types[i].equals(DAO.class)) {
				parameters.add(new DAO(transaction));
				continue;
			}
			if (types[i].equals(GDAO.class)) {
				ParameterizedType param = (ParameterizedType) obj[i].getParameterizedType();
				@SuppressWarnings({ "unchecked", "rawtypes" })
				GDAO dao = new GDAO((Class<?>) param.getActualTypeArguments()[0], transaction);
				parameters.add(dao);
				continue;
			}
			if (types[i].equals(HttpServletRequest.class)) {
				parameters.add(http.getReq());
				continue;
			}
			if (types[i].equals(HttpServletResponse.class)) {
				parameters.add(http.getResp());
				continue;
			}
			if (obj[i].isAnnotationPresent(Parameter.class)) {
				Parameter param = obj[i].getAnnotation(Parameter.class);
				String name = param.value();
				String value = http.getParameter(name);
				if (param.require() && value == null)
					throw new RequiredParamNullException(param.errorWhenParamNull());
				parameters.add(value);
				continue;
			}
			if (obj[i].isAnnotationPresent(JsonParameter.class)) {
				JsonParameter jparam = obj[i].getAnnotation(JsonParameter.class);
				String name = jparam.value();
				Object value = http.getJsonObject(types[i], name);
				if (jparam.require() && value == null)
					throw new RequiredParamNullException(jparam.errorWhenParamNull());
				parameters.add(value);
				continue;
			}
			if (obj[i].isAnnotationPresent(SessionAttribute.class)) {
				SessionAttribute session = obj[i].getAnnotation(SessionAttribute.class);
				String name = session.value();
				Object value = http.getSessionAttribute(Object.class, name);
				if (session.require() && value == null)
					throw new RequiredParamNullException(session.errorWhenSessionNull());
				parameters.add(value);
				continue;
			}
			if (obj[i].isAnnotationPresent(FromDB.class)) {
				FromDB param = obj[i].getAnnotation(FromDB.class);
				String name = param.keyParameter();
				String value = http.getParameter(name);
				if (param.require() && value == null)
					throw new RequiredParamNullException(param.errorWhenKeyParamNull());
				Object fromDB = Static.getDAO().find(types[i], value);
				if (param.require() && fromDB == null)
					throw new RequiredParamNullException(param.errorWhenNotExist());
				parameters.add(fromDB);
				continue;
			}
			if (obj[i].isAnnotationPresent(UriVariable.class)) {
				UriVariable uri = obj[i].getAnnotation(UriVariable.class);
				parameters.add(http.getUriVariable(uri.value()));
				continue;
			}
			parameters.add(null);
		}
		return parameters.toArray();
	}

}
