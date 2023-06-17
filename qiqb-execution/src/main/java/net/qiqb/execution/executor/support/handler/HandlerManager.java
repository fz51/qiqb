package net.qiqb.execution.executor.support.handler;

import net.qiqb.execution.executor.CommandWrapper;

import java.util.ArrayList;
import java.util.List;

public class HandlerManager {

    private final List<TypeHandlerMapping> handlerMappings = new ArrayList<>();
    private final List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    public TypeHandlerChain getHandler(CommandWrapper commandWrapper, Class<?> handlerType) {

        for (TypeHandlerMapping handlerMapping : handlerMappings) {
            TypeHandlerChain stepExecutionChain = handlerMapping.getHandler(commandWrapper, handlerType);
            if (stepExecutionChain != null) {
                return stepExecutionChain;
            }
        }
        return null;
    }

    public HandlerAdapter getHandlerAdapter(Object handler) {

        for (HandlerAdapter adapter : this.handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalStateException("找不到处理");
    }

    public void addHandlerMapping(TypeHandlerMapping handlerMapping) {
        this.handlerMappings.add(handlerMapping);
    }

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        this.handlerAdapters.add(handlerAdapter);
    }
}
