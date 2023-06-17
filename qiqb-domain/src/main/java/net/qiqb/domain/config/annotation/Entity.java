package net.qiqb.domain.config.annotation;

import java.lang.annotation.*;

/**
 * 如果某个类表示领域中的一个实体，将此注解到类上。
 * 实体类上，需要一个字段明确是实体id {@link EntityId}<br/>
 *
 * <p>
 * 示例：
 * <pre class="code">
 *  &#64;Entity
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
@Documented
public @interface Entity {

    String value() default "";

    String label() default "";

    String name() default "";

}
