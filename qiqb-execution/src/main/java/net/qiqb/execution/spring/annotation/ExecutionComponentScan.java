package net.qiqb.execution.spring.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ExecutionComponentScanRegistrar.class)
public @interface ExecutionComponentScan {

    String[] value() default {};

    String[] basePackages() default {};


    Class<?>[] basePackageClasses() default {};
}
