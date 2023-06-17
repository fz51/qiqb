package net.qiqb.execution.spring.beans;

import net.qiqb.execution.CommandDispatcher;
import net.qiqb.execution.CommandProcessorBuilder;
import net.qiqb.execution.common.step.StepProcessor;
import net.qiqb.execution.common.step.StepProcessorConfig;
import net.qiqb.execution.config.support.CommandDefinitionRegistry;
import net.qiqb.execution.example.DefaultDeleteBusinessExecutorHandlerMapping;
import net.qiqb.execution.executor.CommandExecutor;
import net.qiqb.execution.executor.DefaultCommandExecutor;
import net.qiqb.execution.executor.ExecutorInterceptorChain;
import net.qiqb.execution.executor.config.BusinessExecutor;
import net.qiqb.execution.executor.config.DomainObjectFactory;
import net.qiqb.execution.executor.config.DomainPersistence;
import net.qiqb.execution.executor.config.ExecutorInterceptor;
import net.qiqb.execution.executor.support.BusinessExecutingStepProcessor;
import net.qiqb.execution.executor.support.DomainInitStepProcessor;
import net.qiqb.execution.executor.support.PersistenceDomainStepProcessor;
import net.qiqb.execution.executor.support.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class ExecutionConfiguration {


    @Bean
    public HandlerManager handlerManagerBean(List<TypeHandlerMapping> typeHandlerMappings, List<HandlerAdapter> handlerAdapters) {
        final HandlerManager handlerManager = new HandlerManager();
        for (TypeHandlerMapping typeHandlerMapping : typeHandlerMappings) {
            handlerManager.addHandlerMapping(typeHandlerMapping);
        }

        for (HandlerAdapter handlerAdapter : handlerAdapters) {
            handlerManager.addHandlerAdapter(handlerAdapter);
        }
        return handlerManager;
    }


    @Bean
    public DomainObjectFactoryHandlerMapping domainObjectFactoryHandlerMapping(List<DomainObjectFactory> domainObjectFactories) {
        final DomainObjectFactoryHandlerMapping handlerMapping = new DomainObjectFactoryHandlerMapping(domainObjectFactories);
        return handlerMapping;
    }

    @Order(10)
    @Bean
    public BusinessExecutorHandlerMapping businessExecutorHandlerMapping(List<BusinessExecutor<?, ?>> businessExecutors) {
        final BusinessExecutorHandlerMapping handlerMapping = new BusinessExecutorHandlerMapping(businessExecutors);

        return handlerMapping;
    }

    /**
     * 需要比 BusinessExecutorHandlerMapping 优先级低
     *
     * @return
     */
    @Order(100)
    @Bean
    public DefaultDeleteBusinessExecutorHandlerMapping defaultDeleteBusinessExecutorHandlerMapping() {

        return new DefaultDeleteBusinessExecutorHandlerMapping();
    }


    @Bean
    public DomainPersistenceHandlerMapping domainPersistenceHandlerMapping(List<DomainPersistence> domainPersistences) {
        final DomainPersistenceHandlerMapping handlerMapping = new DomainPersistenceHandlerMapping(domainPersistences);

        return handlerMapping;
    }


    @Bean
    public DomainObjectFactoryHandler domainObjectFactoryHandler() {
        return new DomainObjectFactoryHandler();
    }

    @Bean
    public BusinessExecutorHandler businessExecutorHandler() {
        return new BusinessExecutorHandler();
    }

    @Bean
    public DomainPersistenceHandler domainPersistenceHandler() {
        return new DomainPersistenceHandler();
    }


    @Bean
    @Order(10)
    public DomainInitStepProcessor domainInitStepProcessor(HandlerManager handlerManager) {
        final DomainInitStepProcessor domainInitStepProcessor = new DomainInitStepProcessor();
        domainInitStepProcessor.setHandlerManager(handlerManager);
        return domainInitStepProcessor;
    }


    @Bean
    @Order(20)
    public BusinessExecutingStepProcessor businessExecutingStepProcessor(HandlerManager handlerManager) {
        final BusinessExecutingStepProcessor businessExecutingStepProcessor = new BusinessExecutingStepProcessor();
        businessExecutingStepProcessor.setHandlerManager(handlerManager);
        return businessExecutingStepProcessor;
    }


    @Bean
    @Order(30)
    public PersistenceDomainStepProcessor persistenceDomainStepProcessor(HandlerManager handlerManager) {
        final PersistenceDomainStepProcessor persistenceDomainStepProcessor = new PersistenceDomainStepProcessor();
        persistenceDomainStepProcessor.setHandlerManager(handlerManager);
        return persistenceDomainStepProcessor;
    }

    @Bean
    public CommandExecutor commandExecutor(List<ExecutorInterceptor> executorInterceptors, CommandProcessorBuilder commandProcessorBuilder) {
        final ExecutorInterceptorChain interceptorChain = new ExecutorInterceptorChain();
        for (ExecutorInterceptor value : executorInterceptors) {
            interceptorChain.addInterceptors(value);
        }
        return new DefaultCommandExecutor(interceptorChain, commandProcessorBuilder);
    }


    @Bean
    public CommandDispatcher commandDispatcher(CommandDefinitionRegistry commandDefinitionRegistry, CommandExecutor commandExecutor) {
        return new CommandDispatcher(commandDefinitionRegistry, commandExecutor);
    }


    @Bean
    public CommandProcessorBuilder commandProcessorBuilder(List<StepProcessor> stepProcessors) {
        final CommandProcessorBuilder commandProcessorBuilder = new CommandProcessorBuilder();
        if (!stepProcessors.isEmpty()) {
            for (StepProcessor stepProcessor : stepProcessors) {
                commandProcessorBuilder.addStepProcessorConfigs(new StepProcessorConfig(stepProcessor));
            }
        }
        return commandProcessorBuilder;
    }

}
