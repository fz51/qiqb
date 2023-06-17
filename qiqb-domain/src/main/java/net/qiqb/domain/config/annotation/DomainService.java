package net.qiqb.domain.config.annotation;

import net.qiqb.execution.config.DomainType;
import net.qiqb.execution.config.annotation.Domain;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Domain(type = DomainType.SERVICE)
public @interface DomainService {
    @AliasFor(annotation = Domain.class, attribute = "name")
    String name() default "";
}
