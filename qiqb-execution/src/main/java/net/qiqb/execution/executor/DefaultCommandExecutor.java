package net.qiqb.execution.executor;

import net.qiqb.execution.common.step.StepProcessorChainImpl;
import net.qiqb.execution.common.step.StepProcessorConfig;
import net.qiqb.execution.CommandProcessorBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultCommandExecutor implements CommandExecutor {

    //private final ExecuteBuilder executeBuilder;

    private final ExecutorInterceptorChain globalInterceptorChain;

    public DefaultCommandExecutor(ExecutorInterceptorChain interceptorChain, CommandProcessorBuilder commandProcessorBuilder) {
        //this.executeBuilder = executeBuilder;
        this.globalInterceptorChain = interceptorChain;
        this.commandProcessorBuilder = commandProcessorBuilder;
    }

    private CommandProcessorBuilder commandProcessorBuilder;

    @Override
    public void init() {

        log.info("初始化命令执行器");
    }

    @Override
    public void execute(CommandWrapper cmd) {
        ExecutorInterceptorChain interceptorChain = getInterceptorChain(cmd);
        try {
            ExecutingContext.reset(cmd);

            if (!interceptorChain.applyPreExecute(cmd)) {
                return;
            }
            StepProcessorChainImpl stepProcessorChain = new StepProcessorChainImpl();
            for (StepProcessorConfig stepProcessorConfig : commandProcessorBuilder.getStepProcessorConfigs()) {
                stepProcessorChain.addStepProcessor(stepProcessorConfig);
            }
            stepProcessorChain.doProcess(cmd);
            interceptorChain.applyPostExecute(cmd);
        } finally {
            interceptorChain.triggerAfterCompletion(cmd);
            ExecutingContext.remove();
        }

    }

    private ExecutorInterceptorChain getInterceptorChain(CommandWrapper cmd) {
        return new ExecutorInterceptorChain(this.globalInterceptorChain.getInterceptors());
    }

    @Override
    public void destroy() {
        log.info("DefaultCommandExecutor 销毁");
    }

}
