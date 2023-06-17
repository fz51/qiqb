package net.qiqb.execution.config;

import java.util.Optional;

/**
 * 适合命令执行过程中，需要返回一些结果的场景，定义的命令继承即可。
 *
 * @param <T>
 */
public interface CommandResult<T> {

    Optional<T> getCommandResult();

    void setCommandResult(T t);
}
