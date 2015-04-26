package next.setting;

public class MappingSetting {
	private Object mappings;
	private String characterEncoding = "UTF-8";;
	private String dateFormat = "yyyy-MM-dd HH:mm:ss";
	private String url;
	private String jspPath;

	public Object getMappings() {
		return mappings;
	}

	public void setMappings(Object mappings) {
		this.mappings = mappings;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getCharacterEncoding() {
		return characterEncoding;
	}

	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getJspPath() {
		return jspPath;
	}

	public void setJspPath(String jspPath) {
		this.jspPath = jspPath;
	}

}
