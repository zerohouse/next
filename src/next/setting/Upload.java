package next.setting;

import javax.servlet.MultipartConfigElement;

public class Upload {
	private String tempLocation = "webapp/uploads";
	private Long maxFileSize = 1024 * 1024 * 5L;
	private Long maxRequestSize = 1024 * 1024 * 5 * 5L;
	private Integer fileSizeThreshold = 1024 * 1024;

	public String getTempLocation() {
		return tempLocation;
	}

	public Long getMaxFileSize() {
		return maxFileSize;
	}

	public Long getMaxRequestSize() {
		return maxRequestSize;
	}

	public Integer getFileSizeThreshold() {
		return fileSizeThreshold;
	}

	public MultipartConfigElement getMultipartConfig() {
		return new MultipartConfigElement(tempLocation, maxFileSize, maxRequestSize, fileSizeThreshold);
	}

}
