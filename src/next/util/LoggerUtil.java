package next.util;

import next.setting.Setting;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;

public class LoggerUtil {

	private static Level lv;

	static {
		switch (Setting.get().getLogger().getLevel()) {
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
	}

	public static org.slf4j.Logger getLogger(Class<?> cLass) {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		PatternLayoutEncoder ple = new PatternLayoutEncoder();

		ple.setPattern(Setting.get().getLogger().getPattern());
		ple.setContext(lc);
		ple.start();
		FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();

		String name = cLass.getSimpleName();
		fileAppender.setFile(Setting.get().getLogger().getLogFilePath() + "/" + name + ".log");
		fileAppender.setEncoder(ple);
		fileAppender.setContext(lc);
		fileAppender.start();

		ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<ILoggingEvent>();
		consoleAppender.setEncoder(ple);
		consoleAppender.setContext(lc);
		consoleAppender.start();

		Logger logger = (Logger) LoggerFactory.getLogger(cLass);
		logger.addAppender(fileAppender);
		logger.addAppender(consoleAppender);
		logger.setLevel(lv);
		logger.setAdditive(false); /* set to true if root should log too */

		return logger;
	}

}
