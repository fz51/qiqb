package net.qiqb.core.utils;

import org.springframework.context.ApplicationContext;

/**
 * 简单的通过静态方法获取spring ApplicationContext 对象
 */
public class SpringAppCtxHolder {

    private static ApplicationContext applicationContext;

    public static ApplicationContext get() {
        return applicationContext;
    }

    public static void set(ApplicationContext applicationContext) {
        SpringAppCtxHolder.applicationContext = applicationContext;
    }
}
