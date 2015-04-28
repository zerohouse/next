package next.mapping.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Http {

	String getParameter(String name);

	<T> T getJsonObject(Class<T> cLass, String name);

	<T> T getJsonObject(Class<T> cLass);

	void forword(String path);

	void setContentType(String type);

	void write(String string);

	void addUriVariable(String uriVariable);

	String getUriVariable(int number);

	void setCharacterEncoding(String encording);

	void sendNotFound();

	void setSessionAttribute(String name, Object value);

	void removeSessionAttribute(String name);

	<T> T getSessionAttribute(Class<T> cLass, String name);

	Object getSessionAttribute(String name);

	void sendRedirect(String location);

	void sendError(int errorNo);

	void sendError(int errorNo, String errorMesage);

	void setAttribute(String key, Object value);

	Object getAttribute(String key);

	HttpServletRequest getReq();

	HttpServletResponse getResp();

}
