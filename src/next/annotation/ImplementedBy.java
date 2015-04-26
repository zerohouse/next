package next.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ImplementedBy {

	String value();

	boolean samePackage() default true;

}
