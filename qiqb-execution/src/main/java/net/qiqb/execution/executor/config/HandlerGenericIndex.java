package net.qiqb.execution.executor.config;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HandlerGenericIndex {

    int command() default -1;

    int domain() default -1;
}
