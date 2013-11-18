package fmi.uni.grading.server.grader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark different methods of the classes provided by
 * the various applications of the application server (typically methods of the
 * {@link Grader} instances) that are not supported for the particular type of
 * grader.
 * 
 * @author Martin Toshev
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface NotSupported {
}
