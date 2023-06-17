package net.qiqb.execution.spring.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(ExecutionConfigConfigurationRegistrar.class)
public @interface EnableExecutionConfig {
}
