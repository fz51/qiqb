package net.qiqb.execution.config.support;

import net.qiqb.execution.config.CommandDefinition;

public interface CommandDefinitionRegistry {

    void registerCommandDefinition(String commandName, CommandDefinition commandDefinition);


    void removeCommandDefinition(String commandName);


    CommandDefinition getCommandDefinition(String commandName);

    CommandDefinition getCommandDefinition(Class<?> commandType);

    boolean containsCommandDefinition(String commandName);


    String[] getCommandDefinitionNames();


    int getCommandDefinitionCount();
}
