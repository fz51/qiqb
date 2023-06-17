package net.qiqb.execution.executor.support;

import net.qiqb.execution.common.step.StepProcessor;
import net.qiqb.execution.common.step.StepProcessorChain;
import net.qiqb.execution.executor.support.handler.HandlerManager;
import net.qiqb.execution.executor.CommandWrapper;

public abstract class AbsStepProcessor implements StepProcessor {

    private HandlerManager handlerManager;

    @Override
    public void doProcess(CommandWrapper command, StepProcessorChain chain) {

    }

    public HandlerManager getHandlerManager() {
        return handlerManager;
    }

    public void setHandlerManager(HandlerManager handlerManager) {
        this.handlerManager = handlerManager;
    }
}
