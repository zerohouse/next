package next.mapping.annotation.parameters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FromDB {
	String keyParameter();
	
	
	boolean require() default true;
}
