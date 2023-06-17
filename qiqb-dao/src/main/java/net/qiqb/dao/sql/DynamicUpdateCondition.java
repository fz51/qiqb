package net.qiqb.dao.sql;

import java.io.Serializable;
import java.util.Map;

/**
 * 查询条件
 *
 * @param <Children>
 * @param <R>
 */
public interface DynamicUpdateCondition<Children, R> extends Serializable {

    Children eq(R column);


    Children eq(R column, Object value);

    Map<R, Object> getConditions();
    /*
     *//**
     * ignore
     *//*
    Children isNull(R column);

    Children isNotNull(R column);

    Children in(R column, Collection<?> coll);

    *//**
     * ignore
     *//*
    Children in(R column, Object... values);


    Children notIn(R column, Collection<?> coll);

    Children notIn(R column, Object... values);*/


}