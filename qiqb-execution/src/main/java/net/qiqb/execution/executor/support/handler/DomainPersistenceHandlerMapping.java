package net.qiqb.execution.executor.support.handler;

import net.qiqb.execution.config.support.named.GenericNamedFinderHolder;
import net.qiqb.execution.executor.CommandWrapper;
import net.qiqb.execution.config.annotation.DomainMapping;
import net.qiqb.execution.executor.config.DomainPersistence;

import java.util.List;

public class DomainPersistenceHandlerMapping extends AbsTypeHandlerMapping<DomainPersistence> {


    public DomainPersistenceHandlerMapping(List<DomainPersistence> domainPersistences) {
        super(domainPersistences);
    }

    @Override
    public Object filterHandler(CommandWrapper commandWrapper, Class<?> handlerType, DomainPersistence handler) {
        if (!handlerType.equals(DomainPersistence.class)) {
            return null;
        }
        String domainName = commandWrapper.getCommandDefinition().getDomainDefinition().getDefinitionName();
        String decorateDomainName;
        final DomainMapping domainMapping = GenericNamedFinderHolder.getMappingAnnotation(handler, DomainMapping.class);
        if (domainMapping == null) {
            decorateDomainName = GenericNamedFinderHolder.findGeneric(handler, DomainPersistence.class, 0).getName();
        } else {
            decorateDomainName = domainMapping.value();
        }
        if (domainName.equals(decorateDomainName)) {
            return handler;
        }
        return null;
    }
}
