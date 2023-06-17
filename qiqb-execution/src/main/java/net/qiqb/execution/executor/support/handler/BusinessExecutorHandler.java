package net.qiqb.execution.executor.support.handler;

import net.qiqb.execution.executor.CommandWrapper;
import net.qiqb.execution.executor.DomainWrapper;
import net.qiqb.execution.executor.config.BusinessExecutor;

public class BusinessExecutorHandler implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof BusinessExecutor;
    }

    @Override
    public void handle(CommandWrapper command, Object handler) {
        BusinessExecutor<Object, Object> businessExecutor = (BusinessExecutor) handler;
        Object domain = command.getDomain();
        if (domain instanceof DomainWrapper) {
            domain = ((DomainWrapper) domain).getObject();
        }
        if (domain == null) {
            throw new IllegalStateException("找不到领域对象");
        }
        businessExecutor.doExecute(command.getValue(), domain);
    }
}
