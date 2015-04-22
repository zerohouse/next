package next.mapping.response.support;

public class Result {

	private boolean error;
	private String errorMessage;
	private Object response;
	
	@Override
	public String toString() {
		return "Result [error=" + error + ", errorMessage=" + errorMessage + ", response=" + response + "]";
	}

	public Result(boolean error, String errorMessage) {
		this.error = error;
		this.errorMessage = errorMessage;
	}
	
	public Result(Object response) {
		this.response = response;
	}
}
