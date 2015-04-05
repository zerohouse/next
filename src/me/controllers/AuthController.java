package me.controllers;

import me.auth.AuthKeyMaker;
import me.auth.AuthSender;
import me.auth.Mail;
import me.exception.JsonAlert;
import me.model.Result;
import me.model.database.EmailAuth;
import me.model.database.PasswordRedefine;
import me.model.database.User;
import next.database.DAO;
import next.mapping.annotation.Mapping;
import next.mapping.http.Http;
import next.mapping.view.Json;
import next.mapping.view.Jsp;

public class AuthController {

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
		user.setAuthEmail(true);
		if (!dao.update(user)) {
			jsp.put("result", "이메일 인증이 실패하였습니다.");
			return;
		}
		jsp.put("result", "이메일 인증이 성공하였습니다.");
	}

	@Mapping(value = "/api/passwordRedefine", method = "GET")
	public void redefineRequest(Http http, DAO dao) throws JsonAlert {
		String email = http.getParameter("email");
		PasswordRedefine auth = new PasswordRedefine();
		auth.setEmail(email);
		auth.setKey(AuthKeyMaker.getKey(15));
		if (!dao.insertIfExistUpdate(auth))
			throw new JsonAlert("DB입력 중 오류가 발생했습니다.");
		AuthSender.sendMail(email, new Mail("Uss 패스워드 변경 메일입니다.", String.format("<form action='%s' method='post'>"
				+ "변경할 패스워드 : <input maxlength='12' minlength='4' name='password' type='password' required>" + "<button type='submit'>변경</button></form>", auth.getLink())));
		http.setView(new Json(new Result()));
	}

	@Mapping(value = "/api/passwordRedefine/{}/{}", method = "POST")
	public void passwordRedefine(Http http, DAO dao) throws JsonAlert {
		PasswordRedefine auth = dao.getRecordByClass(PasswordRedefine.class, http.getUriVariable(0));
		Jsp jsp = new Jsp("auth.jsp");
		http.setView(jsp);
		if (auth == null) {
			jsp.put("result", "실패하였습니다.");
			return;
		}

		if (!auth.getKey().equals(http.getUriVariable(1))) {
			jsp.put("result", "실패하였습니다.");
			return;
		}
		User user = new User();
		user.setEmail(auth.getEmail());
		String password = http.getParameter("password");
		if (password == null) {
			jsp.put("result", "실패하였습니다.");
			return;
		}
		auth.setKey("");
		user.setPassword(password);
		if (!dao.insertIfExistUpdate(auth) || !dao.update(user)) {
			jsp.put("result", "실패하였습니다.");
			return;
		}
		jsp.put("result", "패스워드 변경이 성공하였습니다.");
	}
}
