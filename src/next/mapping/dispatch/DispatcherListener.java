package next.mapping.dispatch;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import next.setting.Setting;

@WebListener
public class DispatcherListener implements ServletContextListener {

	
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
