package me.controllers;

import java.util.ArrayList;
import java.util.List;

import me.exception.JsonAlert;
import me.model.Result;
import me.model.database.Matching;
import me.model.database.User;
import next.database.DAO;
import next.mapping.annotation.Mapping;
import next.mapping.http.Http;
import next.mapping.view.Json;

public class MachedController {

	@Mapping(value = "/api/matched", method = "GET", before = "loginCheck")
	public void user(Http http, DAO dao) throws JsonAlert {
		User user = http.getSessionAttribute(User.class, "user");
		String column = "";
		switch (user.getGender()) {
		case 1:
			column = "Matching_man";
			break;
		case 2:
			column = "Matching_woman";
			break;
		default:
			return;
		}
		List<Matching> matchings = dao.getRecordsByClass(Matching.class, String.format("SELECT * FROM Matching WHERE %s=?", column), user.getEmail());
		List<User> matchedUsers = new ArrayList<User>();
		for (int i = 0; i < matchings.size(); i++) {
			User partner = null;
			switch (user.getGender()) {
			case 1:
				partner = dao.getRecordByClass(User.class, matchings.get(i).getWoman());
				break;
			case 2:
				partner = dao.getRecordByClass(User.class, matchings.get(i).getMan());
				break;
			}
			matchedUsers.add(partner);
		}
		http.setView(new Json(new Result(matchedUsers)));
	}
}
