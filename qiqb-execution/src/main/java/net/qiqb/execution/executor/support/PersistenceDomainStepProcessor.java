package net.qiqb.execution.executor.support;

import lombok.extern.slf4j.Slf4j;
import net.qiqb.execution.common.step.StepProcessorChain;
import net.qiqb.execution.config.DomainType;
import net.qiqb.execution.executor.CommandWrapper;
import net.qiqb.execution.executor.config.DomainPersistence;
import net.qiqb.execution.executor.support.handler.HandlerManager;
import net.qiqb.execution.executor.support.handler.TypeHandlerChain;

/**
 * 领域对象持久
 */
@Slf4j
public class PersistenceDomainStepProcessor extends AbsStepProcessor {


    @Override
    public boolean isSkip(CommandWrapper command) {
        final DomainType type = command.getCommandDefinition().getDomainDefinition().getType();
        // 只有聚合根才会持久化
        return !DomainType.AGGREGATE.equals(type);
    }

    @Override
    public void doProcess(CommandWrapper command, StepProcessorChain chain) {
        // 开始事物

        final HandlerManager handlerManager = getHandlerManager();
        final TypeHandlerChain handlerChain = handlerManager.getHandler(command, DomainPersistence.class);
        if (handlerChain != null) {
            if (!handlerChain.isOnlyOne()) {
                throw new IllegalStateException("命令领域持久化获取处理器，非唯一");
            }
            Object handler = handlerChain.getHandlers()[0];
            handlerManager.getHandlerAdapter(handler).handle(command, handler);
        }
        // 结束事物
        chain.doProcess(command);
    }

}
