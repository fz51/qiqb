package net.qiqb.dao.mybatis.plus;

import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.qiqb.dao.sql.DynamicUpdateDao;
import net.qiqb.dao.sql.LambdaDynamicUpdateCondition;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.PropertyChange;
import org.javers.core.diff.changetype.ValueChange;

import java.util.Map;

import static org.javers.core.diff.ListCompareAlgorithm.LEVENSHTEIN_DISTANCE;

/**
 * mybatis plus 基础拓展实现
 *
 * @param <M>
 * @param <PO>
 */
@Slf4j
public class BasicDaoImpl<M extends BaseMapper<PO>, PO> extends ServiceImpl<M, PO> implements DynamicUpdateDao<PO> {

    private final Javers javers = JaversBuilder.javers()
            .withListCompareAlgorithm(LEVENSHTEIN_DISTANCE)
            .build();


    @Override
    public boolean dynamicUpdate(PO newPO, PO oldPO, LambdaDynamicUpdateCondition<PO> updateCondition) {
        if (newPO == null) {
            throw new IllegalStateException("准备更新的对象不能为空");
        }
        if (oldPO == null) {
            throw new IllegalStateException("对比的对象不能为空");
        }
        if (updateCondition == null || updateCondition.getConditions().isEmpty()) {
            throw new IllegalStateException("更新条件不能为空");
        }
        final Diff compare = javers.compare(oldPO, newPO);
        UpdateWrapper<PO> updateWrapper = new UpdateWrapper<>();
        if (compare.hasChanges()) {
            final Map<String, ColumnCache> columnMap = LambdaUtils.getColumnMap(newPO.getClass());
            boolean getChangeColumn = false;
            for (Change change : compare.getChanges()) {
                String fieldName = null;
                if (change instanceof PropertyChange<?>) {
                    fieldName = ((ValueChange) change).getPropertyName();
                }
                if (fieldName == null) {
                    continue;
                }
                final ColumnCache columnCache = columnMap.get(fieldName.toUpperCase());
                if (columnCache == null) {
                    log.warn("找不到对应的列名");
                    continue;
                }
                final Object fieldValue = ReflectUtil.getFieldValue(newPO, fieldName);
                getChangeColumn = true;
                updateWrapper.set(columnCache.getColumn(), fieldValue);
            }
            final Map<Func1<PO, ?>, Object> conditions = updateCondition.getConditions();
            for (Map.Entry<Func1<PO, ?>, Object> condition : conditions.entrySet()) {
                final String fieldName = LambdaUtil.getFieldName(condition.getKey());
                final ColumnCache columnCache = columnMap.get(fieldName.toUpperCase());
                if (columnCache == null) {
                    throw new IllegalStateException("条件失效");
                }
                updateWrapper.eq(columnCache.getColumn(), condition.getValue() == null ? ReflectUtil.getFieldValue(newPO, fieldName) : condition.getValue());
            }
            if (getChangeColumn) {
                return super.update(updateWrapper);
            }

        } else {
            log.info("没有变更字段，无需更新");
        }

        return false;
    }


}
