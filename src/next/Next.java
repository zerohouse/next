package next;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import next.setting.Setting;

/**
 * 디스패쳐를 등록하는 리스너 클래스입니다.<br>
 * 매핑의 세팅을 읽어와 매핑된 URL들을 등록합니다.
 */
@WebListener
public class Next implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		Object mappings = Setting.get().getMapping().getMappings();
		ServletRegistration.Dynamic dispatcher = sc.addServlet("Dispatcher", "next.mapping.dispatch.Dispatcher");
		dispatcher.setLoadOnStartup(1);
		if (mappings.getClass().equals(String.class)) {
			dispatcher.addMapping(mappings.toString());
			return;
		}
		if (mappings.getClass().equals(ArrayList.class)) {
			@SuppressWarnings("unchecked")
			List<String> array = (List<String>) mappings;
			array.forEach(each -> {
				dispatcher.addMapping(each.toString());
			});
			return;
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}