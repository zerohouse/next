package next.mapping.dispatch;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.database.ConnectionPool;
import next.database.DAO;
import next.database.MySql;
import next.database.annotation.testdata.Insert;
import next.database.annotation.testdata.InsertList;
import next.database.annotation.testdata.TestData;
import next.database.maker.PackageCreator;
import next.mapping.http.Http;
import next.mapping.http.HttpImpl;
import next.setting.Setting;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

//@MultipartConfig(location = "webapp/uploads", maxFileSize = 1024 * 1024 * 10, fileSizeThreshold = 1024 * 1024, maxRequestSize = 1024 * 1024 * 20)
public class Dispatcher extends HttpServlet {

	private static final long serialVersionUID = -2929326068606297558L;
	private Mapper mapper;
	private ConnectionPool pool;

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
		pool = new ConnectionPool();
		mapper = new Mapper(pool);
		databseSetting();
		InsertTestData();
	}

	private void InsertTestData() {
		if (!Setting.get().getDatabase().getCreateOption().getInsertDataOnServerStart())
			return;
		Reflections ref = new Reflections(Setting.get().getDatabase().getTestDataPackage(), new SubTypesScanner(), new TypeAnnotationsScanner());
		ref.getTypesAnnotatedWith(TestData.class).forEach(each -> {
			DAO dao = new MySql(pool.getConnection(true));
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
			} finally {
				dao.close();
			}
		});

	}

	private void databseSetting() {
		boolean create = Setting.get().getDatabase().getCreateOption().getCreateTablesOnServerStart();
		boolean reset = Setting.get().getDatabase().getCreateOption().getResetTablesOnServerStart();
		if (!(create || reset))
			return;

		PackageCreator.createTable(reset, Setting.get().getDatabase().getModelPackage());
	}

}