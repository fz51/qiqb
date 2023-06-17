package net.qiqb.execution.spring;

import net.qiqb.execution.utils.AggregateRootAdvisor;
import net.qiqb.execution.config.annotation.Domain;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatchers;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.any;

/**
 * 对象改造
 */
public class ExecutionInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        final Instrumentation install = ByteBuddyAgent.install();
        new AgentBuilder.Default()
                .type(ElementMatchers.isAnnotatedWith(Domain.class))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) -> {
                    return builder.method(any()).intercept(MethodDelegation.to(AggregateRootAdvisor.class)
                            .andThen(SuperMethodCall.INSTANCE));

                })
                .installOn(install);
        /*new AgentBuilder.Default()
                .type(ElementMatchers.isSubTypeOf(EntityIdGenerator.class))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) -> {
                    return builder.method(named("generate")).intercept(MethodDelegation.to(EntityIdGeneratorAdvisor.class));

                })
                .installOn(install);*/

    }
}
