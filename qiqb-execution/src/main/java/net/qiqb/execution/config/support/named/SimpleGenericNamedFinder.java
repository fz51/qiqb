package net.qiqb.execution.config.support.named;

import cn.hutool.core.util.TypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

@Slf4j
public class SimpleGenericNamedFinder implements GenericNamedFinder {

    @Override
    public Class<?> findGeneric(Object o, Class<?> interfaceType, int index) {
        final Class<?> clazz = o.getClass();

        if (clazz.getName().contains("Lambda")) {
            return null;
        }
        final Type[] genericInterfaces = clazz.getGenericInterfaces();

        for (Type gi : genericInterfaces) {
            if (gi.getTypeName().startsWith(interfaceType.getName())) {
                final Type typeArgument = TypeUtil.getTypeArgument(gi, index);
                if (typeArgument != null) {
                    return (Class<?>) typeArgument;
                }
            }
        }

        return null;
    }

    @Override
    public <A extends Annotation> A getMappingAnnotation(Object o, Class<A> annotationType) {
        if (o instanceof Field) {
            return AnnotationUtils.getAnnotation((Field) o, annotationType);
        }
        final Class<?> clazz = o.getClass();
        if (clazz.getName().contains("Lambda")) {
            return null;
        }
        return AnnotationUtils.getAnnotation(o.getClass(), annotationType);
    }
}
