package net.qiqb.execution.common.step;

import net.qiqb.execution.executor.support.handler.TypeHandlerChain;
import net.qiqb.execution.common.Definition;

public interface StepHandlerMapping {

    TypeHandlerChain getHandler(Definition definition);
}
