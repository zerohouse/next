package me.controllers;

import me.auth.AuthKeyMaker;
import me.auth.AuthSender;
import me.exception.JsonAlert;
import me.model.Result;
import me.model.database.EmailAuth;
import me.model.database.User;
import next.database.DAO;
import next.mapping.annotation.HttpMethod;
import next.mapping.annotation.Mapping;
import next.mapping.http.Http;
import next.mapping.view.Json;
import next.mapping.view.Jsp;

public class UserController {

	@Mapping(value = "/api/getAuth/{}/{}", method = "GET")
	public void getAuth(Http http, DAO dao) throws JsonAlert {
		EmailAuth auth = dao.getRecordByClass(EmailAuth.class, http.getUriVariable(0));
		Jsp jsp = new Jsp("auth.jsp");
		http.setView(jsp);
		if (auth == null) {
			jsp.put("result", "이메일 인증이 실패하였습니다.");
			return;
		}
		if (!auth.getKey().equals(http.getUriVariable(1))) {
			jsp.put("result", "이메일 인증이 실패하였습니다.");
			return;
		}
		User user = new User();
		user.setEmail(auth.getEmail());
		user.setAuthEmail(1);
		if (dao.update(user)) {
			jsp.put("result", "이메일 인증이 실패하였습니다.");
			return;
		}
		jsp.put("result", "이메일 인증이 성공하였습니다.");
	}

	@Mapping(value = "/api/user", method = "POST")
	public void register(Http http, DAO dao) throws JsonAlert {
		User user = http.getJsonObject(User.class, "user");
		if (!dao.insert(user))
			throw new JsonAlert("DB입력 중 오류가 발생했습니다.");
		EmailAuth auth = AuthKeyMaker.getAuth(user.getEmail());
		AuthSender.sendMail(user.getEmail(), auth.getLink());
		if (!dao.insert(auth))
			throw new JsonAlert("DB입력 중 오류가 발생했습니다.");
		http.setSessionAttribute("user", user);
		http.setView(new Json(new Result(user)));
	}

	@Mapping(value = "/api/user/registeredEmail", method = "POST")
	public void checkId(Http http, DAO dao) throws JsonAlert {
		String email = http.getParameter("email");
		User user = dao.getRecordByClass(User.class, email);
		if (user == null) {
			http.setView(new Json(false));
			return;
		}
		http.setView(new Json(email));
	}

	@Mapping(value = "/api/user/login", method = "POST")
	public void login(Http http, DAO dao) throws JsonAlert {
		User user = http.getJsonObject(User.class, "user");
		User fromDB = dao.getRecordByClass(User.class, user.getEmail());
		if (fromDB == null)
			throw new JsonAlert("없는 아이디입니다.");
		if (!user.getPassword().equals(fromDB.getPassword()))
			throw new JsonAlert("패스워드가 다릅니다.");
		http.setSessionAttribute("user", fromDB);
		http.setView(new Json(new Result(fromDB)));
	}

	@HttpMethod
	public void loginCheck(Http http, DAO dao) throws JsonAlert {
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null)
			throw new JsonAlert("로그인이 필요합니다.");
	}

}
