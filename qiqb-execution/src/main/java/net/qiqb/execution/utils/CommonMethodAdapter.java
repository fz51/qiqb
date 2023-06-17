package net.qiqb.execution.utils;

import cn.hutool.core.exceptions.ExceptionUtil;
import net.qiqb.execution.config.CommandDefinition;
import lombok.Getter;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommonMethodAdapter {

    @Getter
    private final Object targetBean;
    @Getter
    private final Method targetMethod;

    @Getter
    private final CommandDefinition commandDefinition;

    public CommonMethodAdapter(CommandDefinition commandDefinition, Object targetBean, Method targetMethod) {
        this.targetBean = targetBean;
        this.targetMethod = targetMethod;
        this.commandDefinition = commandDefinition;
    }

    @SneakyThrows
    public Object invoke(Object... params) {
        final Object returnObject;
        try {
            returnObject = this.targetMethod.invoke(targetBean, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw ExceptionUtil.getRootCause(e);
        }
        return returnObject;
    }

}
