package next.mapping.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.http.Part;

import next.mapping.dispatch.Dispatcher;
import next.setting.Setting;

public class UploadFile implements Part {

	Part part;

	private String fileName;
	private String extention;
	private String path;

	public UploadFile(Part part) {
		this.part = part;
		extention = part.getSubmittedFileName().replaceFirst("\\S+\\.(\\w+)", "$1");
		fileName = part.getSubmittedFileName().replaceFirst("(\\S+)\\.\\w+", "$1");
		path = Setting.get().getMapping().getUpload().getLocation();
		pathEndCheck();
	}

	private void pathEndCheck() {
		if (path.charAt(path.length() - 1) != '/')
			path = path + '/';
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public String getPath() {
		return path;
	}

	public String getFullPath() {
		return Dispatcher.CONTEXT_PATH + path + getFullName();
	}

	public String getFullName() {
		return fileName + DOT + extention;
	}

	private static final char DOT = '.';

	public String getUriPath() {
		return Setting.get().getMapping().getUrl() + path + getFullName();
	}

	public void save() throws IOException {
		part.write(getFullPath());
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return part.getInputStream();
	}

	@Override
	public String getContentType() {
		return part.getContentType();
	}

	@Override
	public String getName() {
		return part.getName();
	}

	@Override
	public String getSubmittedFileName() {
		return part.getSubmittedFileName();
	}

	@Override
	public long getSize() {
		return part.getSize();
	}

	@Override
	public void write(String fileName) throws IOException {
		part.write(Dispatcher.CONTEXT_PATH + Setting.get().getMapping().getUpload().getTempSaveLocation() + fileName);
	}

	@Override
	public void delete() throws IOException {
		part.delete();
	}

	@Override
	public String getHeader(String name) {
		return part.getHeader(name);
	}

	@Override
	public Collection<String> getHeaders(String name) {
		return part.getHeaders(name);
	}

	@Override
	public Collection<String> getHeaderNames() {
		return part.getHeaderNames();
	}

}
