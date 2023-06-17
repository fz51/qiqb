package net.qiqb.execution.config.support;

import net.qiqb.execution.config.DomainDefinition;

public interface DomainDefinitionRegistry {

    void registerDomainDefinition(String domainName, DomainDefinition domainDefinition);


    void removeDomainDefinition(String domainName);


    DomainDefinition getDomainDefinition(String domainName);

    DomainDefinition getDomainDefinition(Class<?> domainType);

    boolean containsDomainDefinition(String domainName);


    String[] getDomainDefinitionNames();


    int getDomainDefinitionCount();
}
