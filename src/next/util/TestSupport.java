package next.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import next.instance.InstancePool;
import next.instance.wrapper.ClassWrapper;
import next.mapping.http.Http;
import next.mapping.test.HttpForTest;
import next.mapping.test.HttpServletRequestForTest;
import next.mapping.test.HttpServletResponseForTest;
import next.mapping.test.HttpSessionForTest;

public class TestSupport {

	private static InstancePool instancePool;

	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> cLass) {
		if (instancePool == null)
			instancePool = new InstancePool();
		ClassWrapper cip = new ClassWrapper(cLass, instancePool);
		return (T) cip.getInstance();
	}
	
	public static Http getHttp(){
		return new HttpForTest();
	}
	public static HttpSession getHttpSession(){
		return new HttpSessionForTest();
	}
	public static HttpServletRequest getHttpServletRequest(){
		return new HttpServletRequestForTest();
	}
	public static HttpServletResponse getHttpServletResponse(){
		return new HttpServletResponseForTest();
	}
}
