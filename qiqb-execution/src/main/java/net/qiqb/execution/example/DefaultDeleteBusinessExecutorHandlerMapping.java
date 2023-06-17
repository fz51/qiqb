package net.qiqb.execution.example;

import net.qiqb.execution.executor.CommandWrapper;
import net.qiqb.execution.executor.config.BusinessExecutor;
import net.qiqb.execution.executor.support.handler.AbsTypeHandlerMapping;

public class DefaultDeleteBusinessExecutorHandlerMapping extends AbsTypeHandlerMapping<BusinessExecutor<?, ?>> {

    public DefaultDeleteBusinessExecutorHandlerMapping() {
        this.totalHandlers.add(new DefaultDeleteCmdBusinessExecutor());
    }

    @Override
    public Object filterHandler(CommandWrapper commandWrapper, Class<?> handlerType, BusinessExecutor<?, ?> handler) {
        if (!handlerType.equals(BusinessExecutor.class)) {
            return null;
        }
        if (commandWrapper.getValue() instanceof DeleteCommand) {
            return handler;
        }
        return null;
    }


}
