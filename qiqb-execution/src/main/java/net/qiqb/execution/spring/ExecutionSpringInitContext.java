
package net.qiqb.execution.spring;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;

public class ExecutionSpringInitContext {

    private BeanDefinitionRegistry registry;

    private ConfigurableListableBeanFactory beanFactory;

    private ApplicationContext applicationContext;


    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    void setRegistry(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    void setBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}
