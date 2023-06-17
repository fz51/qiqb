package net.qiqb.execution.executor.config;

/**
 * 领域对象工厂
 */
@FunctionalInterface
@HandlerGenericIndex(command = 0, domain = 1)
public interface DomainObjectFactory<C, AR> {
    /**
     * 获取一个聚合根
     *
     * @param cmdValue 命令对象 or 聚合根id
     * @return
     */
    AR getDomainObject(C cmdValue);
}
