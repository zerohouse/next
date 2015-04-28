package next.database.exception;

/**
 * 
 * @RequiredRegex 의 조건을 만족하지 않는 필드를 insert, update하려고 할때 발생.
 * 
 */

public class RegexNotMatchedException extends RuntimeException {

	private static final long serialVersionUID = 5327993556995210476L;

}
