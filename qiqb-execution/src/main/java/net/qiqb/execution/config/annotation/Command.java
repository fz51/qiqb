package net.qiqb.execution.config.annotation;

import java.lang.annotation.*;

/**
 * 被标记的类标识为一个命令
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Command {

    /**
     * 命令需要操作的领域对象。
     * 领域对象包括聚合根或者领域服务
     *
     * @return
     */
    Class<?> value();

    String name() default "";


}
