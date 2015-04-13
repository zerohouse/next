package me.controllers;

import java.io.IOException;

import javax.servlet.http.Part;

import me.exception.JsonAlert;
import me.model.Result;
import me.model.database.User;
import next.database.DAO;
import next.mapping.annotation.Mapping;
import next.mapping.http.Http;
import next.mapping.view.Json;
import next.setting.Setting;

public class FileController {
	private static final String PATH = "http://%s/uploads/%s";
	
	@Mapping(value = "/api/upload", method = "POST", before = "loginCheck")
	public void insert(Http http, DAO dao) throws JsonAlert {
		User user = http.getSessionAttribute(User.class, "user");
		String fileName = user.getEmail().replace('@', 'a');
		Part filePart = http.getPart("file");
		user.setProfileUrl(String.format(PATH, Setting.get("url"), fileName));
		dao.update(user);
		try {
			filePart.write(fileName);
			http.setView(new Json(new Result(user.getProfileUrl())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
