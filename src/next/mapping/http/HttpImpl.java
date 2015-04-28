package next.mapping.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.setting.Setting;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class HttpImpl implements Http {

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private Map<String, String> uriVariables;

	@Override
	public String getParameter(String name) {
		return req.getParameter(name);
	}

	@Override
	public HttpServletRequest getReq() {
		return req;
	}

	@Override
	public HttpServletResponse getResp() {
		return resp;
	}

	@Override
	public <T> T getJsonObject(Class<T> cLass, String name) {
		Gson gson = Setting.getGson();
		try {
			return gson.fromJson(req.getParameter(name), cLass);
		} catch (JsonSyntaxException e) {
			return null;
		}
	}

	@Override
	public <T> T getJsonObject(Class<T> cLass) {
		Gson gson = Setting.getGson();
		try {
			return gson.fromJson(gson.toJson(req.getParameterMap()), cLass);
		} catch (JsonSyntaxException e) {
			return null;
		}
	}

	public HttpImpl(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	@Override
	public void forword(String path) {
		if (path.equals(""))
			sendError(508);
		RequestDispatcher rd = req.getRequestDispatcher(path);
		try {
			rd.forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setContentType(String type) {
		resp.setContentType(type);
	}

	@Override
	public void write(String string) {
		try {
			resp.getWriter().write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void putUriVariable(String key, String uriVariable) {
		if (uriVariables == null)
			uriVariables = new HashMap<String, String>();
		uriVariables.put(key, uriVariable);
	}

	@Override
	public String getUriVariable(String key) {
		if (uriVariables == null)
			return null;
		return uriVariables.get(key);
	}

	@Override
	public void setCharacterEncoding(String encording) {
		try {
			req.setCharacterEncoding(encording);
			resp.setCharacterEncoding(encording);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendNotFound() {
		try {
			resp.sendError(404, req.getRequestURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setSessionAttribute(String name, Object value) {
		req.getSession().setAttribute(name, value);
	}

	@Override
	public void removeSessionAttribute(String name) {
		req.getSession().removeAttribute(name);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getSessionAttribute(Class<T> cLass, String name) {
		return (T) req.getSession().getAttribute(name);
	}

	@Override
	public Object getSessionAttribute(String name) {
		return req.getSession().getAttribute(name);
	}

	@Override
	public void sendRedirect(String location) {
		if (location.equals(""))
			sendError(508);
		try {
			resp.sendRedirect(location);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendError(int errorNo) {
		try {
			resp.sendError(errorNo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendError(int errorNo, String errorMesage) {
		try {
			resp.sendError(errorNo, errorMesage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setAttribute(String key, Object value) {
		req.setAttribute(key, value);
	}

	@Override
	public Object getAttribute(String key) {
		return req.getAttribute(key);
	}

	@Override
	public int getUriVariableSize() {
		return uriVariables.size();
	}

}
