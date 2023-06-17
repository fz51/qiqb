package net.qiqb.execution;

import net.qiqb.execution.config.CommandDefinition;
import net.qiqb.execution.config.support.CommandDefinitionRegistry;
import net.qiqb.execution.executor.CommandExecutor;
import net.qiqb.execution.executor.CommandWrapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommandDispatcher {


    private final CommandDefinitionRegistry commandDefinitionRegistry;

    private final CommandExecutor commandExecutor;

    public CommandDispatcher(CommandDefinitionRegistry commandDefinitionRegistry, CommandExecutor commandExecutor) {

        this.commandDefinitionRegistry = commandDefinitionRegistry;
        this.commandExecutor = commandExecutor;
    }

    /**
     * 命令分发器，分发命令进行执行。
     *
     * @param cmdValues
     */
    public void dispatch(Object... cmdValues) {
        if (cmdValues == null) {
            log.warn("命令不能为空");
            return;
        }
        Object firstCmd = cmdValues[0];
        if (cmdValues.length == 1) {
            commandExecutor.execute(generateCommand(firstCmd.getClass().getName(), firstCmd));
            return;
        }
        MergedCommand mergedCommand = new MergedCommand(firstCmd);
        for (int i = 1; i < cmdValues.length; i++) {
            mergedCommand.addFollow(cmdValues[i]);
        }
        commandExecutor.execute(generateCommand(firstCmd.getClass().getName(), firstCmd));
    }


    /**
     * 生成一个命令
     *
     * @param commandName
     * @param cmdValue
     * @return
     */
    public CommandWrapper generateCommand(String commandName, Object cmdValue) {
        CommandDefinition commandDefinition;
        CommandWrapper command;
        if (cmdValue instanceof CommandWrapper) {
            command = (CommandWrapper) cmdValue;
        } else {
            commandDefinition = commandDefinitionRegistry.getCommandDefinition(commandName);
            command = new CommandWrapper(commandDefinition, cmdValue);
        }
        return command;
    }

}
