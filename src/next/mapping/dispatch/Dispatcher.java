package next.mapping.dispatch;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import next.mapping.http.HttpImpl;
import next.setting.Setting;

@MultipartConfig(location = "webapp/uploads", maxFileSize = 1024 * 1024 * 10, fileSizeThreshold = 1024 * 1024, maxRequestSize = 1024 * 1024 * 20)
public class Dispatcher extends HttpServlet {

	private static final long serialVersionUID = -2929326068606297558L;
	private Mapper mapper = new Mapper();

	@Override
	public void init() throws ServletException {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		Level lv;
		switch (Setting.get("LogLevel")) {
		case "OFF":
			lv = Level.OFF;
			break;
		case "INFO":
			lv = Level.INFO;
			break;
		case "DEBUG":
			lv = Level.DEBUG;
			break;
		case "ERROR":
			lv = Level.ERROR;
			break;
		case "TRACE":
			lv = Level.TRACE;
			break;
		default:
			lv = Level.ALL;
			break;
		}
		root.setLevel(lv);
	};

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		mapper.execute(new UriKey(req.getMethod(), req.getRequestURI()), new HttpImpl(req, resp));
	}

}