package next.mapping.annotation.parameters;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * keyParameter를 꺼내 DB의 해당 오브젝트를 찾습니다. <br>
 * require일 경우, 해당 오브젝트가 없으면 에러를 발생시킵니다.
 * 
 * @see RequiredParamNullException
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface FromDB {

	public static final String PARAM_NULL = "필수 파라미터가 빠졌습니다.";
	public static final String NOT_EXIST = "없는 레코드 입니다.";

	String keyParameter();

	boolean require() default true;

	String errorWhenKeyParamNull() default PARAM_NULL;

	String errorWhenNotExist() default NOT_EXIST;
}
