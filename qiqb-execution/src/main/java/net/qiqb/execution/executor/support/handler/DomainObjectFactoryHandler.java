package net.qiqb.execution.executor.support.handler;

import net.qiqb.execution.common.Definition;
import net.qiqb.execution.config.DomainDefinition;
import net.qiqb.execution.executor.CommandWrapper;
import net.qiqb.execution.executor.DomainWrapper;
import net.qiqb.execution.executor.config.DomainObjectFactory;

public class DomainObjectFactoryHandler implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof DomainObjectFactory;
    }

    @Override
    public void handle(CommandWrapper command, Object handler) {
        final DomainObjectFactory<Object, Object> domainObjectFactory = (DomainObjectFactory) handler;
        final Object domainObject = domainObjectFactory.getDomainObject(command.getValue());
        final Definition domainDefinition = command.getCommandDefinition().getDomainDefinition();
        final DomainWrapper domain = new DomainWrapper((DomainDefinition) domainDefinition, domainObject);
        command.initDomain(domain);
    }
}
