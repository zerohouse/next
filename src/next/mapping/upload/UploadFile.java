package next.mapping.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.http.Part;

import next.mapping.dispatch.Dispatcher;
import next.setting.Setting;

public class UploadFile implements Part {

	Part part;

	public UploadFile(Part part) {
		this.part = part;
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
		part.write(Dispatcher.CONTEXT_PATH + Setting.get().getMapping().getUploadSetting().getLocation() + fileName);
	}

	public void writeWithExtention(String name) throws IOException {
		String fileName = part.getSubmittedFileName().replaceFirst("[\\w\\.]+(\\.\\w+)$", name + "$1");
		part.write(Dispatcher.CONTEXT_PATH + Setting.get().getMapping().getUploadSetting().getLocation() + fileName);
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
