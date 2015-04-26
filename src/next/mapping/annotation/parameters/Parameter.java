package next.mapping.annotation.parameters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {

	public static final String PARAM_NULL = "필수 파라미터가 빠졌습니다.";

	String value();

	boolean require() default true;

	String errorWhenParamNull() default PARAM_NULL;
}
