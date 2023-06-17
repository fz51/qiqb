package net.qiqb.domain.persistence.config;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 加载凭证
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface LoadVoucher {

    /**
     * 业务id 名称
     *
     * @return
     */
    String value() default "";

}
