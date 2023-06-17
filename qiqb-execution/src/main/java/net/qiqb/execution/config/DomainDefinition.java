package net.qiqb.execution.config;

import net.qiqb.execution.common.Definition;
import lombok.Getter;
import lombok.Setter;

/**
 * 聚合定义
 */
public class DomainDefinition implements Definition {

    @Setter
    private String domainName;


    private final String domainClassName;

    @Getter
    private final DomainType type;

    public DomainDefinition(String domainClassName, DomainType type) {
        this.domainClassName = domainClassName;
        this.type = type;
    }

    public DomainDefinition(Class<?> domainType, DomainType type) {
        this(domainType.getName(), type);
    }

    @Override
    public String getDefinitionName() {
        return this.domainName;
    }

    @Override
    public String getDefinitionClassName() {
        return this.domainClassName;
    }

    public boolean isService(){
        return type.equals(DomainType.SERVICE);
    }
}