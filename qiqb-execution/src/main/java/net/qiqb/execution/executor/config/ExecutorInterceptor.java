package net.qiqb.execution.executor.config;

import net.qiqb.execution.executor.CommandWrapper;

/**
 * 命令执行拦截器
 */
public interface ExecutorInterceptor {
    /**
     * 命令执行前拦截，如果返回false，将不执行命令
     *
     * @param cmd 命令
     * @return true：执行后续流程。false：中断命令执行
     */
    default boolean preExecute(CommandWrapper cmd) {

        return true;
    }

    /**
     * 命令处理后执行，在此方法里报错，将不会回滚聚合根持久化和撤回领域事件发布
     * ⚠️：执行此方法的时机，命令执行过程中已经执行完。聚合根持久化和领域事件发布都已经执行。
     *
     * @param cmd 命令
     */
    default void postExecute(CommandWrapper cmd) {
    }

    /**
     * 命令执行过程中，不管执行过程中有没有异常中断，都将会调用此方法。
     *
     * @param cmd
     */
    default void afterCompletion(CommandWrapper cmd) {
    }
}
