package next.setting;

import java.util.List;

import javax.servlet.MultipartConfigElement;

import next.mapping.dispatch.Dispatcher;

public class Upload {

	private String location = "uploads/";
	private String tempSaveLocation = "uploads/temp/";
	private Long maxFileSize = 1024 * 1024 * 5L;
	private Long maxRequestSize = 1024 * 1024 * 5 * 5L;
	private Integer fileSizeThreshold = 1024 * 1024;
	private List<String> needDirectories;

	public List<String> getNeedDirectories() {
		return needDirectories;
	}

	public String getLocation() {
		return location;
	}

	public String getTempSaveLocation() {
		return tempSaveLocation;
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
		return new MultipartConfigElement(Dispatcher.CONTEXT_PATH + tempSaveLocation, maxFileSize, maxRequestSize, fileSizeThreshold);
	}

}
