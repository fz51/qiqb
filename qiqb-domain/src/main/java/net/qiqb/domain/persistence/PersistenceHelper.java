package net.qiqb.domain.persistence;

import cn.hutool.core.collection.CollUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PersistenceHelper {

    private final static Map<Class<?>, Set<Object>> nonCommitIds = new ConcurrentHashMap<>();

    private final static Map<Class<?>, Set<Object>> removeIds = new ConcurrentHashMap<>();

    public static void addRemoveId(Object id) {
        Set<Object> ids = removeIds.get(id.getClass());
        if (CollUtil.isEmpty(ids)) {
            ids = new HashSet<>();
            removeIds.put(id.getClass(), ids);
        }
        ids.add(id);
    }

    public static boolean preRemove(Object id) {
        final Set<Object> ids = removeIds.get(id.getClass());
        if (CollUtil.isEmpty(ids)) {
            return false;
        }
        return ids.contains(id);
    }

    public static void removed(Object id) {
        final Set<Object> ids = removeIds.get(id.getClass());
        if (CollUtil.isNotEmpty(ids)) {
            ids.remove(id);
        }
    }


    public static void addNonCommit(Object id) {
        Set<Object> ids = nonCommitIds.get(id.getClass());
        if (CollUtil.isEmpty(ids)) {
            ids = new HashSet<>();
            nonCommitIds.put(id.getClass(), ids);
        }
        ids.add(id);
    }

    public static boolean nonCommit(Object id) {
        final Set<Object> ids = nonCommitIds.get(id.getClass());
        if (CollUtil.isEmpty(ids)) {
            return false;
        }
        return ids.contains(id);
    }


    public static void commit(Object id) {
        final Set<Object> ids = nonCommitIds.get(id.getClass());
        if (CollUtil.isNotEmpty(ids)) {
            ids.remove(id);
        }
    }
}
