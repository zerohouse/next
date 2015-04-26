package next.mapping.annotation.parameters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SessionAttribute {
	public static final String SESSION_NULL = "세션이 만료되었거나 잘못된 접근입니다.";

	String value();

	boolean require() default true;

	String errorWhenSessionNull() default SESSION_NULL;
}
