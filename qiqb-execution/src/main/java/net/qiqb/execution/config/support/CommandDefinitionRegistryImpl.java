package net.qiqb.execution.config.support;

import net.qiqb.execution.config.CommandDefinition;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CommandDefinitionRegistryImpl implements CommandDefinitionRegistry {


    private final Map<String, CommandDefinition> commandDefinitionCache = new ConcurrentHashMap<>();

    @Override
    public void registerCommandDefinition(String commandName, CommandDefinition commandDefinition) {
        commandDefinition.setCommandName(commandName);
        if (containsCommandDefinition(commandName)) {
            log.error("命令名称 {} 已存在。存在的命令定义为：{}", commandName, getCommandDefinition(commandName));
            throw new IllegalStateException("命令定义名称已经存在");
        }
        commandDefinitionCache.put(commandName, commandDefinition);
    }

    @Override
    public void removeCommandDefinition(String commandName) {
        commandDefinitionCache.remove(commandName);
    }

    @Override
    public CommandDefinition getCommandDefinition(String commandName) {

        final CommandDefinition commandDefinition = commandDefinitionCache.get(commandName);
        if (commandDefinition == null) {
            log.info("命令 {} 找不到定义", commandName);
            throw new IllegalStateException("找不到命令定义");
        }
        return commandDefinition;
    }

    @Override
    public CommandDefinition getCommandDefinition(Class<?> commandType) {
        CommandDefinition commandDefinition = null;
        for (CommandDefinition value : this.commandDefinitionCache.values()) {
            if (value.getDefinitionName().equals(commandType)) {
                if (commandDefinition != null) {
                    log.error("根据类型不能确定命令类型.命令类型：{}", commandType.getName());
                    throw new IllegalStateException("命令类型对应多个命令定义");
                }
                commandDefinition = value;
            }
        }
        if (commandDefinition == null) {
            throw new IllegalStateException("找不到命令定义");
        }
        return commandDefinition;
    }

    @Override
    public boolean containsCommandDefinition(String commandName) {
        return commandDefinitionCache.containsKey(commandName);
    }

    @Override
    public String[] getCommandDefinitionNames() {
        return commandDefinitionCache.keySet().toArray(new String[getCommandDefinitionCount()]);
    }

    @Override
    public int getCommandDefinitionCount() {
        return commandDefinitionCache.size();
    }

}
