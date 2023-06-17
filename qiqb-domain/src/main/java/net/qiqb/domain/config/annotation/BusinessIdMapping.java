package net.qiqb.domain.config.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface BusinessIdMapping {

    String value();
}
