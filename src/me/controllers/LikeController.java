package me.controllers;

import me.exception.JsonAlert;
import me.model.Result;
import me.model.database.UserLike;
import me.model.database.User;
import next.database.DAO;
import next.mapping.annotation.Mapping;
import next.mapping.http.Http;
import next.mapping.view.Json;

public class LikeController {

	@Mapping(value = "/api/like", method = "POST", before = "loginCheck")
	public void insert(Http http, DAO dao) throws JsonAlert {
		User user = http.getSessionAttribute(User.class, "user");
		UserLike like = http.getJsonObject(UserLike.class, "like");
		int count = Integer.parseInt(dao.getRecord("SELECT COUNT(UserLike_id) from UserLike WHERE UserLike_email=?", 1, user.getEmail()).get(0).toString());
		if (count > 1)
			throw new JsonAlert("관심사는 2개까지 지정할 수 있습니다.");
		like.setEmail(user.getEmail());
		if (!dao.insert(like))
			throw new JsonAlert("DB입력 중 오류가 발생했습니다.");
		http.setView(new Json(new Result(true)));
	}

	@Mapping(value = "/api/like/delete", method = "POST", before = "loginCheck")
	public void delete(Http http, DAO dao) throws JsonAlert {
		User user = http.getSessionAttribute(User.class, "user");
		UserLike like = http.getJsonObject(UserLike.class, "like");
		if (!user.getEmail().equals(like.getEmail()))
			throw new JsonAlert("권한이 없습니다.");
		if (!dao.delete(like))
			throw new JsonAlert("DB입력 중 오류가 발생했습니다.");
		http.setView(new Json(new Result(true)));
	}

}
