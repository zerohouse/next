package next.mapping.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {

	String[] value();
	
	String[] before() default "";
	
	String[] after() default "";
	
	String[] method() default "GET";
}
