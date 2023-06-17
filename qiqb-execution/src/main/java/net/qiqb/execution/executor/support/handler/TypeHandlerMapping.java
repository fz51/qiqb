package net.qiqb.execution.executor.support.handler;

import net.qiqb.execution.executor.CommandWrapper;

public interface TypeHandlerMapping {

    /**
     * 通过定义和类型找到合适的处理对象
     *
     * @param commandWrapper
     * @param handlerType
     * @return
     */
     TypeHandlerChain getHandler(CommandWrapper commandWrapper, Class<?> handlerType);

}
