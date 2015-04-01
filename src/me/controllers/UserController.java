package me.controllers;

import me.exception.JsonAlert;
import me.model.Result;
import me.model.database.User;
import next.database.DAO;
import next.mapping.annotation.Mapping;
import next.mapping.http.Http;
import next.mapping.view.Json;

public class UserController {

	@Mapping(value = "/api/user", method = "POST")
	public void register(Http http, DAO dao) throws JsonAlert {
		User user = http.getJsonObject(User.class, "user");
		if (!dao.insert(user))
			throw new JsonAlert("DB입력 중 오류가 발생했습니다.");
		http.setSessionAttribute("user", user);
		http.setView(new Json(new Result(user)));
	}

	@Mapping(value = "/api/user/existId", method = "POST")
	public void checkId(Http http, DAO dao) throws JsonAlert {
		String id = http.getParameter("id");
		User user = dao.getRecordByClass(User.class, id);
		if (user == null) {
			http.setView(new Json(false));
			return;
		}
		http.setView(new Json(true));
	}

	@Mapping(value = "/api/user/login", method = "POST")
	public void login(Http http, DAO dao) throws JsonAlert {
		User user = http.getJsonObject(User.class, "user");
		User fromDB = dao.getRecordByClass(User.class, user.getId());
		if (fromDB == null)
			throw new JsonAlert("없는 아이디입니다.");
		if (!user.getPassword().equals(fromDB.getPassword()))
			throw new JsonAlert("패스워드가 다릅니다.");
		http.setSessionAttribute("user", fromDB);
		http.setView(new Json(new Result(fromDB)));
	}

}
