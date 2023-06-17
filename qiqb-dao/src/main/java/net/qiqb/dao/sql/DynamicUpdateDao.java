package net.qiqb.dao.sql;

import java.util.List;

/**
 * 基本的数据库表操作
 *
 * @param <PO>
 */
public interface DynamicUpdateDao<PO> {

    /**
     * 动态更新数据库。此方法会比较新值和旧值，对比后只会更新差异化的字段。如果没有差异，则不会进行调用持久化操作
     *
     * @param newPO 更新后的新值
     * @param oldPO 更新前的旧值
     * @return
     */
    boolean dynamicUpdate(PO newPO, PO oldPO, LambdaDynamicUpdateCondition<PO> updateCondition);

}
