package net.qiqb.execution.common.step;

import net.qiqb.execution.executor.CommandWrapper;

public interface StepProcessorChain {

    void doProcess(CommandWrapper command);
}
