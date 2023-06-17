package net.qiqb.domain.config.annotation;

import java.lang.annotation.*;

/**
 * 值对象
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValueObject {

    String value() default "";

    String label() default "";

}
