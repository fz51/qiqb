package net.qiqb.domain.config.support;

import net.qiqb.domain.config.EntityIdGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class EntityIdGeneratorHolder {

    private static final Map<Class<?>, EntityIdGenerator<?>> MAP = new HashMap<>();

    @Deprecated
    public static <ID> EntityIdGenerator<ID> getEntityIdGenerator(Class<ID> idClazz) {

        return get(idClazz);
    }

    public static <ID> EntityIdGenerator<ID> get(Class<ID> idClazz) {
        EntityIdGenerator<?> generator = MAP.get(idClazz);
        if (generator == null) {
            generator = new SnowflakeEntityIdGenerator<>(idClazz);
            MAP.put(idClazz, generator);
        }
        return (EntityIdGenerator<ID>) generator;
    }

}
