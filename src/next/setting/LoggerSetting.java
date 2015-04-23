package next.setting;

public class LoggerSetting {

	private String level = "ALL";
	private String logFilePath = "/log/";
	private String pattern = "%level [%thread] %msg - %logger{10} : %file:%line %date%n";

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLogFilePath() {
		return logFilePath;
	}

	public void setLogFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
