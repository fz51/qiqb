package net.qiqb.execution.common.step;


import net.qiqb.execution.executor.CommandWrapper;

public interface StepHandlerAdapter {

    boolean supports(Object handler);


    void handle(CommandWrapper command, Object handler);

}
