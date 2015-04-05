package me.auth;

public class Mail {

	private String head;
	private String body;

	public String getHead() {
		return head;
	}

	public String getBody() {
		return body;
	}

	public Mail(String head, String body) {
		this.head = head;
		this.body = body;
	}

}
