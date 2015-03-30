package next.database.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

	String value() default "";

	String table_suffix() default "";

	String createQuery() default "";

}
