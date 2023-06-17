package net.qiqb.execution.config;

import net.qiqb.execution.common.Definition;
import lombok.Getter;
import lombok.Setter;

/**
 * 命令定义
 */
public class CommandDefinition implements Definition {


    @Setter
    private String commandName;


    private final String commandClassName;


    @Getter
    private final DomainDefinition domainDefinition;


    public CommandDefinition(String commandClassName, DomainDefinition domainDefinition) {

        this.commandClassName = commandClassName;
        this.domainDefinition = domainDefinition;
    }

    public CommandDefinition(Class<?> commandType, DomainDefinition domainDefinition) {
        this(commandType.getName(), domainDefinition);
    }

    @Override
    public String getDefinitionName() {
        return this.commandName;
    }

    @Override
    public String getDefinitionClassName() {
        return this.commandClassName;
    }
}