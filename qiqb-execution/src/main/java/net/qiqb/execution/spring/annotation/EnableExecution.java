package net.qiqb.execution.spring.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@ExecutionComponentScan
public @interface EnableExecution {

    @AliasFor(annotation = ExecutionComponentScan.class, attribute = "basePackages")
    String[] basePackages() default {};

    @AliasFor(annotation = ExecutionComponentScan.class, attribute = "basePackageClasses")
    Class<?>[] scanBasePackageClasses() default {};

}
