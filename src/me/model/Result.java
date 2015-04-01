package me.model;

public class Result {

	private boolean error;
	private String errorMessage;
	private Object obj;


	@Override
	public String toString() {
		return "Result [error=" + error + ", errorMessage=" + errorMessage + ", obj=" + obj + "]";
	}

	public Result(Object obj) {
		this.obj = obj;
	}

	public Result(boolean error, String errorMessage) {
		this.error = error;
		this.errorMessage = errorMessage;
	}

}
