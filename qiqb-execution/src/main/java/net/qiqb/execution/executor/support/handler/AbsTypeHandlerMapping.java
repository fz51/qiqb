package net.qiqb.execution.executor.support.handler;

import cn.hutool.core.collection.CollUtil;
import net.qiqb.execution.executor.CommandWrapper;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsTypeHandlerMapping<T> implements TypeHandlerMapping {

    protected final List<T> totalHandlers = new ArrayList<>();

    public AbsTypeHandlerMapping(List<T> totalHandlers) {

        this.totalHandlers.addAll(totalHandlers);
    }

    public AbsTypeHandlerMapping() {
    }

    @Override
    public TypeHandlerChain getHandler(CommandWrapper commandWrapper, Class<?> handlerType) {

        final List<Object> collect = new ArrayList<>();
        for (T h : this.totalHandlers) {
            Object o = filterHandler(commandWrapper, handlerType, h);
            if (o != null) {
                collect.add(o);
            }
        }
        if (CollUtil.isEmpty(collect)) {
            return null;
        }
        return new TypeHandlerChain(collect.toArray());
    }

    /**
     * 返回成
     *
     * @param commandWrapper
     * @param handler
     * @return
     */
    public abstract Object filterHandler(CommandWrapper commandWrapper, Class<?> handlerType, T handler);

}
