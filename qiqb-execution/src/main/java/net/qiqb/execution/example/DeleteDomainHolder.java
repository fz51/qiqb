package net.qiqb.execution.example;

import cn.hutool.core.collection.CollUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DeleteDomainHolder {

    private final static Map<Class<?>, Set<Object>> preDelete = new ConcurrentHashMap<>();


    /**
     * 标记删除
     */
    public static void markDelete(Object domain) {
        Set<Object> ids = preDelete.get(domain.getClass());
        if (CollUtil.isEmpty(ids)) {
            ids = new HashSet<>();
            preDelete.put(domain.getClass(), ids);
        }
        ids.add(domain);
    }

    public static boolean isDelete(Object domain) {
        final Set<Object> ids = preDelete.get(domain.getClass());
        if (CollUtil.isEmpty(ids)) {
            return false;
        }
        return ids.contains(domain);
    }

    public static void deleted(Object domain) {
        final Set<Object> ids = preDelete.get(domain.getClass());
        if (CollUtil.isNotEmpty(ids)) {
            ids.remove(domain);
        }
    }
}
