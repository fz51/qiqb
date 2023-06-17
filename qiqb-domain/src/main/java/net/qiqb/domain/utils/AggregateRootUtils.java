package net.qiqb.domain.utils;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import net.qiqb.domain.config.annotation.EntityId;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

public class AggregateRootUtils {
    /**
     * 默认聚合根默认id
     *
     * @param ar
     * @return
     */
    public static Optional<Object> lookupAggregateRootId(Object ar) {
        final Map<String, Field> fieldMap = ReflectUtil.getFieldMap(ar.getClass());
        Field idField = null;
        for (Field value : fieldMap.values()) {
            if (AnnotationUtil.hasAnnotation(value, EntityId.class)) {
                if (idField != null) {
                    throw new IllegalStateException("一个聚合根中不能又多个id");
                }
                idField = value;
            }
        }
        if (idField != null) {
            return Optional.ofNullable(ReflectUtil.getFieldValue(ar, idField));
        }
        return Optional.empty();
    }
}
