package net.qiqb.execution.spring.util;

import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotatedTargetMethodWrapper {

    @Getter
    private final Object targetBean;

    @Getter
    private final Method targetMethod;

    private final Class<? extends Annotation> interestedAnnotation;

    public AnnotatedTargetMethodWrapper(Class<? extends Annotation> interestedAnnotation, Object targetBean, Method targetMethod) {
        this.targetBean = targetBean;
        this.targetMethod = targetMethod;
        this.interestedAnnotation = interestedAnnotation;
    }

    /**
     * 判断方法是否含有注解
     *
     * @param annotationClazz
     * @return
     */
    public boolean hasMethodAnnotated(Class<? extends Annotation> annotationClazz) {
        return false;
    }

    /**
     * 获取方法的参数类型
     *
     * @return
     */
    public Class<?>[] getMethodParameterTypes() {
        return targetMethod.getParameterTypes();
    }

    /**
     * 获取方法返回值
     *
     * @return
     */
    public Class<?> getMethodReturnType() {
        return targetMethod.getReturnType();
    }
}
