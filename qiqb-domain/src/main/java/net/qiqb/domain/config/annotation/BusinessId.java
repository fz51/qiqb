package net.qiqb.domain.config.annotation;

import java.lang.annotation.*;

/**
 *
 */
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BusinessId {

    String name() default "";
}
