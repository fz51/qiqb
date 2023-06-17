package net.qiqb.domain.persistence;

import lombok.extern.slf4j.Slf4j;
import net.qiqb.domain.utils.AggregateRootUtils;
import net.qiqb.execution.example.DeleteDomainHolder;
import net.qiqb.execution.executor.config.DomainPersistence;
import net.qiqb.execution.executor.config.PersistenceWrapper;

import java.util.Optional;

/**
 * 抽象的领域持久化对象
 *
 * @param <D>
 */
@Slf4j
public abstract class AbstractDomainPersistence<D> implements DomainPersistence<D> {
    /**
     * 领域持久化
     *
     * @param domainObject 待持久化待聚合根对象
     */
    @Override
    public void persisted(PersistenceWrapper<D> domainObject) {
        D aggregateRoot = domainObject.get();
        final Object id = getId(aggregateRoot);
        if (DeleteDomainHolder.isDelete(aggregateRoot)) {
            try {
                doRemove(domainObject);
            } finally {
                DeleteDomainHolder.deleted(aggregateRoot);
            }
            return;
        }
        if (PersistenceHelper.nonCommit(id)) {
            try {
                doAdd(domainObject);
            } finally {
                PersistenceHelper.commit(id);
            }
            return;
        }
        // 更新
        doModify(domainObject);
    }

    protected Object getId(D domainObject) {
        // 获取领域对象的id
        final Optional<Object> id = AggregateRootUtils.lookupAggregateRootId(domainObject);
        if (id.isPresent()) {
            return id.get();
        }
        log.error("找不到聚合根ID:{}", domainObject.getClass());
        throw new IllegalStateException("找不到聚合根ID");
    }

    /**
     * 执行聚合根新增操作
     *
     * @param domainObject 待新增的领域聚合根对象
     */
    protected abstract void doAdd(PersistenceWrapper<D> domainObject);

    /**
     * 根据聚合根执行领域对象更新操作
     *
     * @param domainObject 待更新的领域聚合根对象
     */
    protected abstract void doModify(PersistenceWrapper<D> domainObject);

    /**
     * 根据聚合根执行领域对象删除操作
     *
     * @param domainObject 待新增的领域聚合根对象
     */
    protected abstract void doRemove(PersistenceWrapper<D> domainObject);

}
