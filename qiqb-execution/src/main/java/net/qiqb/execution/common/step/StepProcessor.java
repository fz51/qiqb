package net.qiqb.execution.common.step;


import net.qiqb.execution.executor.CommandWrapper;

public interface StepProcessor {
    default void init(StepProcessorConfig stepProcessorConfig) {
    }

    default boolean isSkip(CommandWrapper command) {
        return false;
    }

    /**
     * @param command
     * @param chain
     */
    void doProcess(CommandWrapper command, StepProcessorChain chain);

    default void destroy() {
    }
}
