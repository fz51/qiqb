package net.qiqb.execution.executor;

/**
 * 执行器
 */
public interface CommandExecutor {

    void init();

    void execute(CommandWrapper cmd);

    void destroy();
}
