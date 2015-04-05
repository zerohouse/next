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

	public static void sendMail(String to, Mail mail) {
		Runnable sendMail = new Runnable() {
			@Override
			public void run() {
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
					message.setSubject(mail.getHead());
					message.setContent(mail.getBody(), "text/html; charset=UTF-8");
					Transport.send(message);
				} catch (MessagingException mex) {
					mex.printStackTrace();
				}
			}
		};
		sendMail.run();

	}
}
