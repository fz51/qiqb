package net.qiqb.execution.executor;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 命令上下文
 */
@Slf4j
public class ExecutingContext {

    private static ThreadLocal<ExecutingContext> ctx = new ThreadLocal<>();

    @Getter
    private CommandWrapper command;

    public void clear() {
        this.command = null;
    }

    public static synchronized ExecutingContext get() {
        if (ctx.get() == null) {
            ctx.set(new ExecutingContext());
        }
        return ctx.get();
    }

    public static void reset(CommandWrapper command) {
        if (get() != null) {
            get().clear();
        }
        final ExecutingContext commandContext = get();
        commandContext.command = command;
    }

    public static void remove() {
        ctx.remove();
    }

}
