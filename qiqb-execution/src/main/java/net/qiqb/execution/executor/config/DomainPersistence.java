package net.qiqb.execution.executor.config;

/**
 * 领域持久化
 *
 * @param <AR>
 */
@FunctionalInterface
@HandlerGenericIndex(domain = 0)
public interface DomainPersistence<AR> {

    void persisted(PersistenceWrapper<AR> ar);
}
