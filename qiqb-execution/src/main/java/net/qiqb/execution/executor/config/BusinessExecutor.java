package net.qiqb.execution.executor.config;

/**
 * 命令业务执行器
 *
 * @param <C>
 * @param <AR>
 */
@FunctionalInterface
@HandlerGenericIndex(command = 0, domain = 1)
public interface BusinessExecutor<C, AR> {

    void doExecute(C cmd, AR ar);
}
