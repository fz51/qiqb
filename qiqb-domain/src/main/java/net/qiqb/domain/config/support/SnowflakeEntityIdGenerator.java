package net.qiqb.domain.config.support;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReflectUtil;
import net.qiqb.domain.config.EntityIdGenerator;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;

/**
 * 按照雪花算法生成id。
 */
@Slf4j
public class SnowflakeEntityIdGenerator<T> implements EntityIdGenerator<T> {

    private final Class<T> idClazz;

    public SnowflakeEntityIdGenerator(Class<T> idClazz) {
        this.idClazz = idClazz;
    }


    @Override
    public T generate() {
        final long idVal = IdUtil.getSnowflake().nextId();
        if (idClazz.equals(Long.class)) {
            return (T) Long.valueOf(idVal);
        }
        if (idClazz.equals(String.class)) {
            return (T) String.valueOf(idVal);
        }
        Constructor<T> constructor = ReflectUtil.getConstructor(idClazz, Long.class);
        if (constructor != null) {
            return ReflectUtil.newInstance(idClazz, idVal);
        }
        constructor = ReflectUtil.getConstructor(idClazz, String.class);
        if (constructor != null) {
            return ReflectUtil.newInstance(idClazz, String.valueOf(idVal));
        }
        log.error("");
        throw new IllegalStateException("请默认给一个构造器");
    }

}
