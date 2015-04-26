package next.mapping.annotation.parameters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FromDB {

	public static final String PARAM_NULL = "필수 파라미터가 빠졌습니다.";
	public static final String NOT_EXIST = "없는 레코드 입니다.";

	String keyParameter();

	boolean require() default true;

	String errorWhenKeyParamNull() default PARAM_NULL;

	String errorWhenNotExist() default NOT_EXIST;
}
