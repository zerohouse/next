package me.mail;


import me.auth.AuthSender;
import me.auth.Mail;

import org.junit.Test;

public class MailSenderTest {

	@Test
	public void test() {
		AuthSender.sendMail("parksungho86@gmail.com", new Mail("head", "body"));
	}

}
