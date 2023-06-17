package net.qiqb.execution.executor;

import net.qiqb.execution.config.CommandDefinition;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * 命令
 */
@Slf4j
public final class CommandWrapper {

    @Getter
    private final String id;

    /**
     * 命令的定义
     */
    @Getter
    private final CommandDefinition commandDefinition;


    @Getter
    private final Object value;

    /**
     * 命令操作领域对象或者聚合根
     */
    @Getter
    private DomainWrapper domain;



    public CommandWrapper(CommandDefinition commandDefinition, Object value) {
        // 命令id唯一
        this.id = UUID.randomUUID().toString();

        this.commandDefinition = commandDefinition;
        if (!commandDefinition.getDefinitionClassName().equals(value.getClass().getName())) {
            throw new IllegalStateException("命令值和定义不匹配");
        }
        this.value = value;
    }

    public void initDomain(DomainWrapper domain) {
        this.domain = domain;

    }
}