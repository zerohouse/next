package next.mapping.dispatch.support;

import java.io.File;

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

}
