package next.mapping.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Http {

	String getParameter(String name);

	<T> T getJsonObject(Class<T> cLass, String name);

	<T> T getJsonObject(Class<T> cLass);

	void forword(String path) throws ServletException, IOException;

	void setContentType(String type);

	void write(String string);

	void addUriVariable(String uriVariable);

	String getUriVariable(int number);

	void setCharacterEncoding(String encording) throws UnsupportedEncodingException;

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
