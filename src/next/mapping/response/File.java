package next.mapping.response;

import java.io.IOException;

import javax.servlet.ServletException;

import next.mapping.http.Http;

public class File implements Response {

	private String path;

	public File(String path) {
		this.path = path;
	}

	@Override
	public void render(Http http) {
		try {
			http.forword(path);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
