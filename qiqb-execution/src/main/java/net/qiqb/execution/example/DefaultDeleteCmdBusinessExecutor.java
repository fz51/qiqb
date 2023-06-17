package net.qiqb.execution.example;

import net.qiqb.execution.executor.config.BusinessExecutor;

/**
 * 当删除的命令没有执行器的时候，默认添加一个执行器给命令
 */
public class DefaultDeleteCmdBusinessExecutor implements BusinessExecutor<DeleteCommand, Object> {

    @Override
    public void doExecute(DeleteCommand cmd, Object object) {
        DeleteDomainHolder.markDelete(object);
    }
}
