package next.mapping.dispatch;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.database.DAO;
import next.database.annotation.testdata.Insert;
import next.database.annotation.testdata.InsertList;
import next.database.annotation.testdata.TestData;
import next.database.maker.TableMaker;
import next.mapping.dispatch.support.DirectoryMaker;
import next.mapping.http.Http;
import next.mapping.http.HttpImpl;
import next.resource.Static;
import next.setting.Setting;

public class Dispatcher extends HttpServlet {

	private static final long serialVersionUID = -2929326068606297558L;
	private Mapper mapper;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Http http = new HttpImpl(req, resp);
		String encording = Setting.get().getMapping().getCharacterEncoding();
		if (encording != null && !"".equals(encording))
			http.setCharacterEncoding(encording);
		mapper.execute(new UriKey(req.getMethod(), req.getRequestURI()), http);
	}

	@Override
	public void init() throws ServletException {
		mapper = new Mapper();
		databseSetting();
		InsertTestData();
		CONTEXT_PATH = getServletContext().getRealPath(java.io.File.separator) + java.io.File.separator;
		ServletRegistration.Dynamic dispatcher = (Dynamic) getServletContext().getServletRegistration("Dispatcher");
		dispatcher.setMultipartConfig(Setting.get().getMapping().getUpload().getMultipartConfig());
		DirectoryMaker.makeDirectories();
	}

	public static String CONTEXT_PATH;

	private void InsertTestData() {
		if (!Setting.get().getDatabase().getCreateOption().getInsertDataOnServerStart())
			return;
		Static.getReflections().getTypesAnnotatedWith(TestData.class).forEach(each -> {
			DAO dao = new DAO();
			try {
				Object obj = each.getConstructor().newInstance();
				Field[] fields = each.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					if (fields[i].isAnnotationPresent(Insert.class)) {
						fields[i].setAccessible(true);
						dao.insert(fields[i].get(obj));
						continue;
					}
					if (fields[i].isAnnotationPresent(InsertList.class)) {
						fields[i].setAccessible(true);
						List<?> list = (List<?>) fields[i].get(obj);
						list.forEach(e -> {
							dao.insert(e);
						});
						continue;
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	private void databseSetting() {
		boolean create = Setting.get().getDatabase().getCreateOption().getCreateTablesOnServerStart();
		boolean reset = Setting.get().getDatabase().getCreateOption().getResetTablesOnServerStart();
		if (!(create || reset))
			return;
		TableMaker.create(reset);
	}

}