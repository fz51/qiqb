package net.qiqb.dao.sql;

import cn.hutool.core.lang.func.Func1;

import java.util.HashMap;
import java.util.Map;

public class LambdaDynamicUpdateCondition<T> implements DynamicUpdateCondition<LambdaDynamicUpdateCondition<T>, Func1<T, ?>> {

    private Map<Func1<T, ?>, Object> sql = new HashMap<>();

    @Override
    public LambdaDynamicUpdateCondition<T> eq(Func1<T, ?> column) {
        sql.put(column, null);
        return this;
    }

    @Override
    public LambdaDynamicUpdateCondition<T> eq(Func1<T, ?> column, Object value) {
        sql.put(column, value);
        return this;
    }

    @Override
    public Map<Func1<T, ?>, Object> getConditions() {
        return new HashMap<>(sql);
    }

    public static <R> LambdaDynamicUpdateCondition<R> of(Func1<R, ?> column) {
        final LambdaDynamicUpdateCondition<R> object = new LambdaDynamicUpdateCondition<>();
        object.eq(column);
        return object;
    }
}
