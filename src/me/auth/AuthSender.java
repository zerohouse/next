package me.auth;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AuthSender {

	public static void sendMail(String to, String keyLink) {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "587");
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("ussauth@gmail.com", "uss123456");
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Uss 가입 인증 메일입니다.");
			message.setContent(
					String.format("<h1>Uss에 가입하신 것을 환영합니다.</h1><p><h3><a href='%s'>email 인증하기</a></h3>링크를 누르면 회원님의 메일이 인증됩니다.</p>", keyLink),
					"text/html; charset=UTF-8");
			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
