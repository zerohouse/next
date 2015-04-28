package next.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 빌드할 필드가 인터페이스일 경우 implement클래스를 지정해줍니다.<br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ImplementedBy {

	Class<?> value();

}
