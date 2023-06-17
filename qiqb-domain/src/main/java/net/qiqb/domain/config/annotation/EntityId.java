package net.qiqb.domain.config.annotation;

import java.lang.annotation.*;

/**
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@BusinessId
public @interface EntityId {

    String name() default "";

    String value() default "";

    String label() default "";
}
