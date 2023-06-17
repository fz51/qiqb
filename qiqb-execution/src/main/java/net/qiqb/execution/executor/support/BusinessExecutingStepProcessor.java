package net.qiqb.execution.executor.support;

import net.qiqb.execution.executor.support.handler.HandlerManager;
import net.qiqb.execution.common.step.StepProcessorChain;
import net.qiqb.execution.executor.CommandWrapper;
import net.qiqb.execution.executor.DomainValueSnapshot;
import net.qiqb.execution.executor.config.BusinessExecutor;
import net.qiqb.execution.executor.support.handler.TypeHandlerChain;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class BusinessExecutingStepProcessor extends AbsStepProcessor {


    @Override
    public void doProcess(CommandWrapper cmd, StepProcessorChain chain) {
        log.info("命令开始执行业务处理：{}", cmd.getCommandDefinition().getDefinitionName());
        final HandlerManager handlerManager = getHandlerManager();
        final TypeHandlerChain handlerChain = handlerManager.getHandler(cmd, BusinessExecutor.class);
        if (handlerChain != null) {
            final Object[] handlers = handlerChain.getHandlers();
            for (Object handler : handlers) {
                handlerManager.getHandlerAdapter(handler).handle(cmd, handler);
            }
        }
        cmd.getDomain().snapshot(cmd, DomainValueSnapshot.COMPLETE_BUSINESS_EXECUTION_POINT);
        chain.doProcess(cmd);
    }
}
