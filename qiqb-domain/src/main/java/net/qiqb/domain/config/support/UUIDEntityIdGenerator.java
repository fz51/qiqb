package net.qiqb.domain.config.support;

import cn.hutool.core.util.ReflectUtil;
import net.qiqb.domain.config.EntityIdGenerator;

import java.lang.reflect.Constructor;
import java.util.UUID;

/**
 * 按照UUID 算法生成id。
 */
public class UUIDEntityIdGenerator<T> implements EntityIdGenerator<T> {

    private final Class<T> idClazz;

    public UUIDEntityIdGenerator(Class<T> idClazz) {
        this.idClazz = idClazz;
    }


    @Override
    public T generate() {
        final String idVal = UUID.randomUUID().toString();
        if (idClazz.equals(String.class)) {
            return (T) idVal;
        }
        final Constructor<T> constructor = ReflectUtil.getConstructor(idClazz, String.class);
        if (constructor == null) {
            throw new IllegalStateException();
        }
        return ReflectUtil.newInstance(idClazz, idVal);
    }

}
