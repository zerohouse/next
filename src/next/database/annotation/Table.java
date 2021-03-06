package next.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 테이블 설정을 지정합니다.<br>
 * neverDrop을 true로 설정하면, 리셋 설정이 되어 있어도 테이블을 드롭하지 않습니다.
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

	boolean neverDrop() default false;
	
	String value() default "";

	String table_suffix() default "";

	String createQuery() default "";

}
