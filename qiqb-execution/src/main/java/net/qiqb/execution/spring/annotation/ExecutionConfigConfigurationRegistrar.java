
package net.qiqb.execution.spring.annotation;


import net.qiqb.execution.spring.ExecutionSpringInitializer;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class ExecutionConfigConfigurationRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        ExecutionSpringInitializer.initialize(registry);

    }
}
