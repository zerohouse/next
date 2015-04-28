package next.database.annotation.testdata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 해당 클래스의 필드 중 Insert, InsertList어노테이션이 있는 필드들을 DB에 삽입합니다.
 * 
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TestData {

}
