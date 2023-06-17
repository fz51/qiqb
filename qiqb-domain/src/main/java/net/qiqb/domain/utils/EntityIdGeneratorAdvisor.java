package net.qiqb.domain.utils;

import net.qiqb.domain.config.EntityIdGenerator;
import net.qiqb.domain.persistence.PersistenceHelper;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.Super;

/**
 * 聚合根拦截
 */
@Slf4j
public class EntityIdGeneratorAdvisor {


    public static Object generate(@Super EntityIdGenerator<Object> callable) throws Exception {
        final Object call = callable.generate();
        PersistenceHelper.addNonCommit(call);
        return call;
    }
}
