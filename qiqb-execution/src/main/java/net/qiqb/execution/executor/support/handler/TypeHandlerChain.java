package net.qiqb.execution.executor.support.handler;

public class TypeHandlerChain {

    private final Object[] handlers;

    public TypeHandlerChain(Object... handler) {
        this.handlers = new Object[handler.length];
        System.arraycopy(handler, 0, this.handlers, 0, handler.length);
    }

    public Object[] getHandlers() {
        return this.handlers;
    }

    public boolean isOnlyOne() {
        return handlers.length == 1;
    }

    public boolean isEmpty() {
        return this.handlers.length == 0;
    }
}
