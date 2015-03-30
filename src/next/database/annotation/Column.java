package next.database.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

	String value() default "";

	boolean NULL() default false;

	boolean hasDefaultValue() default true;

	String DEFAULT() default "";

	String DATA_TYPE() default "";
	
	String[] function() default "";

}
