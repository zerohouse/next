package me.controllers;

import java.util.List;

import me.exception.JsonAlert;
import me.model.Result;
import me.model.database.Letter;
import me.model.database.User;
import next.database.DAO;
import next.mapping.annotation.Mapping;
import next.mapping.http.Http;
import next.mapping.view.Json;

public class LetterController {

	@Mapping(value = "/api/letter", method = "GET", before = "loginCheck")
	public void read(Http http, DAO dao) throws JsonAlert {
		User user = http.getSessionAttribute(User.class, "user");
		Integer start = 0;
		Integer size = 5;
		try {
			start = Integer.parseInt(http.getParameter("start"));
			size = Integer.parseInt(http.getParameter("size"));
		} catch (Exception e) {
		}
		List<Letter> letters = dao.getRecordsByClass(Letter.class, "SELECT * FROM Letter Where Letter_key=? ORDER BY Letter_id DESC LIMIT ?,?",
				user.getEmail(), start, size);
		http.setView(new Json(new Result(letters)));
	}

	@Mapping(value = "/api/sendletter", method = "GET", before = "loginCheck")
	public void readSendLetter(Http http, DAO dao) throws JsonAlert {
		User user = http.getSessionAttribute(User.class, "user");
		Integer start = 0;
		Integer size = 5;
		try {
			start = Integer.parseInt(http.getParameter("start"));
			size = Integer.parseInt(http.getParameter("size"));
		} catch (Exception e) {
		}
		List<Letter> letters = dao.getRecordsByClass(Letter.class, "SELECT * FROM Letter Where Letter_email=? ORDER BY Letter_id DESC LIMIT ?,?",
				user.getEmail(), start, size);
		http.setView(new Json(new Result(letters)));
	}

	@Mapping(value = "/api/letter", method = "POST", before = "loginCheck")
	public void update(Http http, DAO dao) throws JsonAlert {
		User user = http.getSessionAttribute(User.class, "user");
		Letter letter = http.getJsonObject(Letter.class, "letter");
		if (!user.getEmail().equals(letter.getEmail()))
			throw new JsonAlert("잘못된 접근입니다.");
		int count = Integer.parseInt(dao
				.getRecord("SELECT COUNT(Letter_id) from Letter WHERE Letter_key=? and Letter_email=?", 1, letter.getKey(), letter.getEmail()).get(0)
				.toString());
		if (count > 3)
			throw new JsonAlert("편지는 한명에게 두통까지만 작성 가능합니다.");
		if (letter.getId() == null) {
			if (letter.getBody() == null)
				letter.setBody("");
			if (!dao.insert(letter))
				throw new JsonAlert("DB입력 중 오류가 발생했습니다.");
			letter.setId(dao.getLastKey().intValue());
			http.setView(new Json(new Result(letter)));
			return;
		}
		Letter fromDb = dao.getRecordByClass(Letter.class, letter.getId());
		if (!fromDb.getEmail().equals(letter.getEmail()))
			throw new JsonAlert("권한이 없습니다.");
		if (!dao.update(letter))
			throw new JsonAlert("DB입력 중 오류가 발생했습니다.");
		http.setView(new Json(new Result(letter)));
	}

	@Mapping(value = "/api/letter/delete", method = "POST", before = "loginCheck")
	public void delete(Http http, DAO dao) throws JsonAlert {
		User user = http.getSessionAttribute(User.class, "user");
		Letter letter = http.getJsonObject(Letter.class, "letter");
		Letter fromDb = dao.getRecordByClass(Letter.class, letter.getId());
		if (!(fromDb.getKey().equals(user.getEmail()) || user.getEmail().equals(fromDb.getEmail())))
			throw new JsonAlert("권한이 없습니다.");
		letter.setEmail("");
		letter.setKey("");
		if (!dao.update(letter))
			throw new JsonAlert("DB입력 중 오류가 발생했습니다.");
		http.setView(new Json(new Result(true)));
	}
}
