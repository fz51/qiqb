package net.qiqb.execution.executor.support.handler;

import net.qiqb.execution.config.annotation.CommandMapping;
import net.qiqb.execution.config.annotation.DomainMapping;
import net.qiqb.execution.config.support.named.GenericNamedFinderHolder;
import net.qiqb.execution.executor.CommandWrapper;
import net.qiqb.execution.executor.config.DomainObjectFactory;

import java.util.List;

public class DomainObjectFactoryHandlerMapping extends AbsTypeHandlerMapping<DomainObjectFactory> {


    public DomainObjectFactoryHandlerMapping(List<DomainObjectFactory> domainObjectFactories) {
        super( domainObjectFactories);
    }

    @Override
    public Object filterHandler(CommandWrapper commandWrapper,Class<?> handlerType, DomainObjectFactory handler) {
        if (!handlerType.equals(DomainObjectFactory.class)){
            return null;
        }
        String commandName = commandWrapper.getCommandDefinition().getDefinitionName();
        String domainName = commandWrapper.getCommandDefinition().getDomainDefinition().getDefinitionName();
        String decorateCommandName;
        String decorateDomainName = null;
        // 比较泛型是否匹配
        final CommandMapping commandMapping = GenericNamedFinderHolder.getMappingAnnotation(handler, CommandMapping.class);
        if (commandMapping == null) {
            decorateCommandName = GenericNamedFinderHolder.findGeneric(handler,DomainObjectFactory.class, 0).getName();
        } else {
            decorateCommandName = commandMapping.value();
        }
        final DomainMapping domainMapping = GenericNamedFinderHolder.getMappingAnnotation(handler, DomainMapping.class);
        if (domainMapping == null) {
            decorateDomainName = GenericNamedFinderHolder.findGeneric(handler, DomainObjectFactory.class,1).getName();
        } else {
            decorateDomainName = domainMapping.value();
        }
        if (commandName.equals(decorateCommandName) && domainName.equals(decorateDomainName)) {
            return handler;
        }
        return null;
    }
}
