package me.controllers;

import me.auth.AuthKeyMaker;
import me.auth.AuthSender;
import me.auth.Mail;
import me.exception.JsonAlert;
import me.model.Result;
import me.model.database.EmailAuth;
import me.model.database.User;
import next.database.DAO;
import next.mapping.annotation.HttpMethod;
import next.mapping.annotation.Mapping;
import next.mapping.http.Http;
import next.mapping.view.Json;

public class UserController {

	@Mapping(value = "/api/user", method = "POST")
	public void register(Http http, DAO dao) throws JsonAlert {
		User user = http.getJsonObject(User.class, "user");
		if (user == null)
			return;
		if (!dao.insert(user))
			throw new JsonAlert("DB입력 중 오류가 발생했습니다.");
		EmailAuth auth = new EmailAuth();
		auth.setEmail(user.getEmail());
		auth.setKey(AuthKeyMaker.getKey(15));
		if (!dao.insertIfExistUpdate(auth))
			throw new JsonAlert("DB입력 중 오류가 발생했습니다.");
		AuthSender.sendMail(
				user.getEmail(),
				new Mail("Uss 가입 인증 메일입니다.", String.format(
						"<h1>Uss에 가입하신 것을 환영합니다.</h1><p><h3><a href='%s'>email 인증하기</a></h3>링크를 누르면 회원님의 메일이 인증됩니다.</p>", auth.getLink())));
		http.setSessionAttribute("user", user);
		user.removePassword();
		http.setView(new Json(new Result(user)));
	}

	@Mapping(value = "/api/user/mailRequest", method = "POST")
	public void remail(Http http, DAO dao) throws JsonAlert {
		EmailAuth auth = new EmailAuth();
		auth.setEmail(http.getParameter("email"));
		auth.setKey(AuthKeyMaker.getKey(15));
		if (!dao.insertIfExistUpdate(auth))
			throw new JsonAlert("DB입력 중 오류가 발생했습니다.");
		AuthSender.sendMail(
				http.getParameter("email"),
				new Mail("Uss 가입 인증 메일입니다.", String.format(
						"<h1>Uss에 가입하신 것을 환영합니다.</h1><p><h3><a href='%s'>email 인증하기</a></h3>링크를 누르면 회원님의 메일이 인증됩니다.</p>", auth.getLink())));
		http.setView(new Json(new Result()));
	}

	@Mapping(value = "/api/user/update", method = "POST", before = "loginCheck")
	public void update(Http http, DAO dao) throws JsonAlert {
		User user = http.getJsonObject(User.class, "user");
		if (user == null)
			return;
		if (!user.getEmail().equals(http.getSessionAttribute(User.class, "user").getEmail()))
			return;
		if (!dao.update(user))
			throw new JsonAlert("DB입력 중 오류가 발생했습니다.");
		http.setSessionAttribute("user", user);
		user.removePassword();
		http.setView(new Json(new Result(user)));
	}

	@Mapping(value = "/api/user", method = "GET", before = "loginCheck")
	public void user(Http http, DAO dao) throws JsonAlert {
		User user = http.getSessionAttribute(User.class, "user");
		user = dao.getRecordByClass(User.class, user.getEmail());
		user.defineFactors(dao);
		user.removePassword();
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
		fromDB.defineFactors(dao);
		http.setSessionAttribute("user", fromDB);
		fromDB.removePassword();
		http.setView(new Json(new Result(fromDB)));
	}

	@Mapping(value = "/api/user/logout", method = "GET")
	public void logout(Http http) throws JsonAlert {
		http.removeSessionAttribute("user");
		http.setView(new Json(new Result(true)));
	}

	@HttpMethod
	public void loginCheck(Http http, DAO dao) throws JsonAlert {
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null)
			throw new JsonAlert("로그인이 필요합니다.");
	}

}
