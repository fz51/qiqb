package net.qiqb.execution.config.support.named;

import java.lang.annotation.Annotation;

public interface GenericNamedFinder {
    /**
     * 查找泛型
     *
     * @param o     对象
     * @param type  对象继承的接口
     * @param index 接口中泛型索引，从0 开始
     * @return
     */
    Class<?> findGeneric(Object o, Class<?> type, int index);

    /**
     * 获取
     *
     * @param o
     * @param annotationType
     * @param <A>
     * @return
     */
    <A extends Annotation> A getMappingAnnotation(Object o, Class<A> annotationType);
}
