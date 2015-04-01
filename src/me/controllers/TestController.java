package me.controllers;

import me.exception.JsonAlert;
import me.model.Result;
import me.model.database.Test;
import me.model.database.User;
import next.database.DAO;
import next.mapping.annotation.Mapping;
import next.mapping.http.Http;
import next.mapping.view.Json;

public class TestController {

	@Mapping(value = "/api/test", method = "POST", before = "loginCheck")
	public void insertTest(Http http, DAO dao) throws JsonAlert {
		Test test = http.getJsonObject(Test.class, "test");
		test.setUserId(http.getSessionAttribute(User.class, "user").getId());
		if (!dao.execute("INSERT INTO Test values(?,?,?) ON DUPLICATE KEY UPDATE Test_result=?", test.getUserId(), test.getName(), test.getResult(),
				test.getResult()))
			throw new JsonAlert("DB입력 중 오류가 발생했습니다.");
		http.setView(new Json(new Result(test)));
	}
}
