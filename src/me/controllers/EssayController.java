package me.controllers;

import java.util.List;

import me.exception.JsonAlert;
import me.model.Result;
import me.model.database.Essay;
import me.model.database.User;
import next.database.DAO;
import next.mapping.annotation.Mapping;
import next.mapping.http.Http;
import next.mapping.view.Json;

public class EssayController {

	@Mapping(value = "/api/essay", method = "GET", before = "loginCheck")
	public void read(Http http, DAO dao) throws JsonAlert {
		User user = http.getSessionAttribute(User.class, "user");
		String key = http.getParameter("key");
		if (key == null)
			key = user.getEmail();
		Integer start = 0;
		Integer size = 5;
		try {
			start = Integer.parseInt(http.getParameter("start"));
			size = Integer.parseInt(http.getParameter("size"));
		} catch (Exception e) {
		}
		List<Essay> essays = dao.getRecordsByClass(Essay.class, "SELECT * FROM Essay Where Essay_key=? ORDER BY Essay_id DESC LIMIT ?,?", key, start, size);
    		http.setView(new Json(new Result(essays)));
	}

	@Mapping(value = "/api/essay", method = "POST", before = "loginCheck")
	public void update(Http http, DAO dao) throws JsonAlert {
		User user = http.getSessionAttribute(User.class, "user");
		Essay essay = http.getJsonObject(Essay.class, "essay");
		if (!user.getEmail().equals(essay.getEmail()))
			throw new JsonAlert("권한이 없습니다.");
		if (essay.getId() == null) {
			essay.setBody("");
			if (!dao.insert(essay))
				throw new JsonAlert("DB입력 중 오류가 발생했습니다.");
			essay.setId(dao.getLastKey().intValue());
			http.setView(new Json(new Result(essay)));
			return;
		}
		Essay fromDb = dao.getRecordByClass(Essay.class, essay.getId());
		if (!fromDb.getEmail().equals(essay.getEmail()))
			throw new JsonAlert("권한이 없습니다.");
		if (!dao.update(essay))
			throw new JsonAlert("DB입력 중 오류가 발생했습니다.");
		http.setView(new Json(new Result(true)));
	}

	@Mapping(value = "/api/essay/delete", method = "POST", before = "loginCheck")
	public void delete(Http http, DAO dao) throws JsonAlert {
		User user = http.getSessionAttribute(User.class, "user");
		Essay essay = http.getJsonObject(Essay.class, "essay");
		if (!user.getEmail().equals(essay.getEmail()))
			throw new JsonAlert("권한이 없습니다.");
		Essay fromDb = dao.getRecordByClass(Essay.class, essay.getId());
		if (!fromDb.getEmail().equals(essay.getEmail()))
			throw new JsonAlert("권한이 없습니다.");
		essay.setEmail("");
		essay.setKey("");
		if (!dao.update(essay))
			throw new JsonAlert("DB입력 중 오류가 발생했습니다.");
		http.setView(new Json(new Result(true)));
	}
}
