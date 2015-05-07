package next.mapping.dispatch.support;

import java.io.File;

import next.mapping.dispatch.Dispatcher;
import next.setting.Setting;
import next.util.LoggerUtil;

import org.slf4j.Logger;

public class DirectoryMaker {

	private static final Logger logger = LoggerUtil.getLogger(DirectoryMaker.class);

	public static void mkDir(String string) {
		File theDir = new File(string);
		if (!theDir.exists()) {
			logger.info("creating directory: " + theDir.getAbsolutePath());
			try {
				theDir.mkdir();
				return;
			} catch (SecurityException se) {
				logger.warn("디렉토리 만들기 실패 : 권한이 없습니다.");
			}
		}
		logger.debug(theDir.getAbsolutePath());
	}

	public static void makeDirectories() {
		mkDir(Dispatcher.CONTEXT_PATH + Setting.get().getMapping().getUpload().getTempSaveLocation());
		mkDir(Dispatcher.CONTEXT_PATH + Setting.get().getMapping().getUpload().getLocation());
		if (Setting.get().getMapping().getUpload().getNeedDirectories() == null)
			return;
		Setting.get().getMapping().getUpload().getNeedDirectories().forEach(dir -> {
			mkDir(Dispatcher.CONTEXT_PATH + dir);
		});
	}

}
