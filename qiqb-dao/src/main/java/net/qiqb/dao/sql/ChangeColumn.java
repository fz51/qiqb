package net.qiqb.dao.sql;

import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;
import lombok.Getter;

import java.lang.reflect.Field;

public class ChangeColumn<T> {

    private final SingleTableChanges<T> singleTableChanges;

    /**
     * 表列名
     */
    @Getter
    private String columnName;
    @Getter
    private Object newVal;

    /**
     *
     */
    private Object oldVal;


    private Field columnField;


    public ChangeColumn(SingleTableChanges<T> singleTableChanges) {
        this.singleTableChanges = singleTableChanges;
    }

    public ChangeColumn<T> setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    private String getName(Func1<T, ?> sFunction) {
        return LambdaUtil.getFieldName(sFunction);
    }


    public ChangeColumn<T> setColumnName(Func1<T, ?> sFunction) {
        this.columnName = getName(sFunction);
        return this;
    }

    public ChangeColumn<T> setNewVal(Object newVal) {
        this.newVal = newVal;
        return this;
    }

    public ChangeColumn<T> setOldVal(Object oldVal) {
        this.oldVal = oldVal;
        return this;
    }

    public ChangeColumn<T> setColumnField(Field columnField) {
        this.columnField = columnField;
        return this;
    }

    public SingleTableChanges<T> end() {
        return singleTableChanges;
    }
}