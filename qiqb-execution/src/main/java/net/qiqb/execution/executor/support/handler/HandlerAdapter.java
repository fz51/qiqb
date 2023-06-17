package net.qiqb.execution.executor.support.handler;


import net.qiqb.execution.executor.CommandWrapper;

public interface HandlerAdapter {

    boolean supports(Object handler);

    void handle(CommandWrapper command, Object handler);

}
