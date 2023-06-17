package net.qiqb.domain.spring.beans;

import net.qiqb.domain.config.EntityIdGenerator;
import net.qiqb.domain.utils.EntityIdGeneratorAdvisor;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.Serializable;
import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * 对象改造
 */
public class DomainInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        final Instrumentation install = ByteBuddyAgent.install();
        new AgentBuilder.Default()
                .type(ElementMatchers.isSubTypeOf(EntityIdGenerator.class))
                .transform((builder, typeDescription, classLoader, module, protectionDomain) -> {
                    return builder
                            // 默认添加
                            .implement(Serializable.class)
                            .method(named("generate"))
                            .intercept(MethodDelegation.to(EntityIdGeneratorAdvisor.class));

                })
                .installOn(install);


    }
}
