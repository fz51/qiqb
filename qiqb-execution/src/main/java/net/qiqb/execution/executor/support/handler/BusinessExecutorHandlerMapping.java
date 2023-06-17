package net.qiqb.execution.executor.support.handler;

import net.qiqb.execution.config.annotation.CommandMapping;
import net.qiqb.execution.config.annotation.DomainMapping;
import net.qiqb.execution.config.support.named.GenericNamedFinderHolder;
import net.qiqb.execution.executor.CommandWrapper;
import net.qiqb.execution.executor.config.BusinessExecutor;

import java.util.List;

public class BusinessExecutorHandlerMapping extends AbsTypeHandlerMapping<BusinessExecutor<?, ?>> {


    public BusinessExecutorHandlerMapping(List<BusinessExecutor<?, ?>> businessExecutors) {
        super(businessExecutors);
    }

    @Override
    public Object filterHandler(CommandWrapper commandWrapper, Class<?> handlerType, BusinessExecutor<?, ?> handler) {
        if (!handlerType.equals(BusinessExecutor.class)) {
            return null;
        }
        String commandName = commandWrapper.getCommandDefinition().getDefinitionName();
        String domainName = commandWrapper.getCommandDefinition().getDomainDefinition().getDefinitionName();
        String decorateCommandName;
        String decorateDomainName;
        // 比较泛型是否匹配
        final CommandMapping commandMapping = GenericNamedFinderHolder.getMappingAnnotation(handler, CommandMapping.class);
        if (commandMapping == null) {
            decorateCommandName = GenericNamedFinderHolder.findGeneric(handler, BusinessExecutor.class, 0).getName();
        } else {
            decorateCommandName = commandMapping.value();
        }
        final DomainMapping domainMapping = GenericNamedFinderHolder.getMappingAnnotation(handler, DomainMapping.class);
        if (domainMapping == null) {
            decorateDomainName = GenericNamedFinderHolder.findGeneric(handler, BusinessExecutor.class, 1).getName();
        } else {
            decorateDomainName = domainMapping.value();
        }
        if (commandName.equals(decorateCommandName) && domainName.equals(decorateDomainName)) {
            return handler;
        }
        return null;
    }


}
