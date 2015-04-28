package next.mapping.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 클래스를 Uri와 매핑합니다.<br>
 * before, after을 지정하면,<br>
 * 지정된 메소드들을 먼저, 혹은 나중에 실행합니다.<br>
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface Mapping {
	String[] value() default "";

	String[] before() default "";

	String[] after() default "";

	String[] method() default "GET";
}
