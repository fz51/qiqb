package net.qiqb.execution.config.annotation;

import net.qiqb.execution.config.DomainType;

import java.lang.annotation.*;

/**
 * 如果某个类表示领域中的一个聚合根，将此注解到类上。
 * 聚合根类上，需要一个字段明确是实体id {@link }<br/>
 *
 * <p>
 * 示例：
 * <pre class="code">
 *  &#64;DomainObject
 *  public User {
 *      &#64;EntityId
 *      private UserId id;
 *      ... 其他属性
 *  }
 * </pre>
 *
 * </p>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Domain {

    String name() default "";

    DomainType type();

}
