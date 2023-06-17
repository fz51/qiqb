package net.qiqb.execution.utils;

import net.qiqb.execution.executor.CommandWrapper;
import net.qiqb.execution.executor.ExecutingContext;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * 聚合根拦截
 */
@Slf4j
public class AggregateRootAdvisor {


    @RuntimeType
    public static void intercept(@AllArguments Object[] args, @Origin Method method, @SuperCall Callable<?> callable) throws Exception {
        if (method.getName().equals("equals")) {
            return;
        }
        final CommandWrapper command = ExecutingContext.get().getCommand();
        if (command != null) {
            // throw new IllegalStateException("非业务处理阶段更改聚合根对象");
        }
    }
}
