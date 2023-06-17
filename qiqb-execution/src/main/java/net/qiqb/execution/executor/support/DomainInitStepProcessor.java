package net.qiqb.execution.executor.support;

import net.qiqb.execution.common.step.StepProcessorChain;
import net.qiqb.execution.common.step.StepProcessorConfig;
import net.qiqb.execution.config.DomainDefinition;
import net.qiqb.execution.config.DomainType;
import net.qiqb.execution.executor.config.DomainObjectFactory;
import net.qiqb.execution.executor.support.handler.HandlerAdapter;
import net.qiqb.execution.executor.support.handler.HandlerManager;
import net.qiqb.execution.executor.support.handler.TypeHandlerChain;
import net.qiqb.execution.executor.CommandWrapper;
import net.qiqb.execution.executor.DomainValueSnapshot;
import net.qiqb.execution.executor.DomainWrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 领域对象初始化
 */
@Slf4j
public class DomainInitStepProcessor extends AbsStepProcessor {

    @Override
    public boolean isSkip(CommandWrapper command) {
        return command.getDomain() != null;
    }

    @Override
    public void init(StepProcessorConfig stepProcessorConfig) {
        super.init(stepProcessorConfig);
    }

    @Override
    public void doProcess(CommandWrapper cmd, StepProcessorChain chain) {
        log.info("命令领域初始化：{}", cmd.getCommandDefinition().getDefinitionName());
        final DomainDefinition domainDefinition = cmd.getCommandDefinition().getDomainDefinition();
        //
        final DomainType type = domainDefinition.getType();
        if (DomainType.AGGREGATE.equals(type) || DomainType.SERVICE.equals(type)) {
            HandlerAdapter handlerAdapter = null;
            Object hander = null;
            final HandlerManager handlerManager = getHandlerManager();
            final TypeHandlerChain handlerChain = handlerManager.getHandler(cmd, DomainObjectFactory.class);
            if (handlerChain != null) {
                if (!handlerChain.isOnlyOne()) {
                    throw new IllegalStateException("初始化命令获取处理器，非唯一");
                }
                hander = handlerChain.getHandlers()[0];
                handlerAdapter = handlerManager.getHandlerAdapter(hander);
            }
            // 执行
            if (handlerAdapter != null) {
                handlerAdapter.handle(cmd, hander);
            }
        } else {
            throw new IllegalStateException("不支持其他领域类型");
        }
        final DomainWrapper domain = cmd.getDomain();
        if (domain == null) {
            throw new IllegalStateException("领域对象不能为空");
        }
        domain.snapshot(cmd, DomainValueSnapshot.INIT_POINT);
        chain.doProcess(cmd);
    }
}
