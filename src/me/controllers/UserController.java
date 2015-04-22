package me.controllers;

import javax.servlet.http.HttpServletRequest;

import me.model.User;
import next.database.DAO;
import next.mapping.annotation.Controller;
import next.mapping.annotation.HttpMethod;
import next.mapping.annotation.Mapping;
import next.mapping.annotation.parameters.FromDB;
import next.mapping.annotation.parameters.Parameter;
import next.mapping.constant.Method;
import next.mapping.response.Json;
import next.mapping.response.Response;
import next.mapping.response.support.Result;

@Controller
@Mapping("/api/user")
public class UserController {

	@Mapping(method = Method.GET)
	public Response getUser(@FromDB(keyParameter = "userId") User user, @Parameter("userId") String userId, DAO dao) {
		System.out.println(user);
		return new Json("abc");
	}

	@Mapping(value = "/user", method = Method.GET)
	@HttpMethod(value = "loginCheck")
	public Result loginCheck(HttpServletRequest req) {
		if (req.getSession().getAttribute("user") == null)
			return new Result("로그인이 필요한 서비스입니다.");
		return null;
	}

}
