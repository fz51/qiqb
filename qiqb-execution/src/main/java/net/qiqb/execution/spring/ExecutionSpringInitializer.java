package net.qiqb.execution.spring;

import net.qiqb.execution.spring.beans.ExecutionConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ExecutionSpringInitializer {

    private static Map<BeanDefinitionRegistry, ExecutionSpringInitContext> contextMap = new ConcurrentHashMap<>();


    public static void initialize(BeanDefinitionRegistry registry) {
        if (contextMap.putIfAbsent(registry, new ExecutionSpringInitContext()) != null) {
            return;
        }
        // prepare context and do customize
        ExecutionSpringInitContext context = contextMap.get(registry);

        // find beanFactory
        ConfigurableListableBeanFactory beanFactory = findBeanFactory(registry);

        initContext(context, registry, beanFactory);
    }


    private static void initContext(ExecutionSpringInitContext context, BeanDefinitionRegistry registry,
                                    ConfigurableListableBeanFactory beanFactory) {
        context.setRegistry(registry);
        context.setBeanFactory(beanFactory);
        // 将context 注册到容器中
        beanFactory.registerSingleton(context.getClass().getName(), context);

        // register common beans
        registerCommonBeans(registry);
    }

    static void registerCommonBeans(BeanDefinitionRegistry registry) {

        registerInfrastructureBean(registry, ExecutionConfiguration.class.getName(), ExecutionConfiguration.class);

    }

    private static ConfigurableListableBeanFactory findBeanFactory(BeanDefinitionRegistry registry) {
        ConfigurableListableBeanFactory beanFactory;
        if (registry instanceof ConfigurableListableBeanFactory) {
            beanFactory = (ConfigurableListableBeanFactory) registry;
        } else if (registry instanceof GenericApplicationContext genericApplicationContext) {
            beanFactory = genericApplicationContext.getBeanFactory();
        } else {
            throw new IllegalStateException("Can not find Spring BeanFactory from registry: " + registry.getClass().getName());
        }
        return beanFactory;
    }

    static boolean registerInfrastructureBean(BeanDefinitionRegistry beanDefinitionRegistry,
                                              String beanName,
                                              Class<?> beanType) {

        boolean registered = false;

        if (!beanDefinitionRegistry.containsBeanDefinition(beanName)) {
            RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
            beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
            registered = true;

            if (log.isDebugEnabled()) {
                log.debug("The Infrastructure bean definition [" + beanDefinition
                        + "with name [" + beanName + "] has been registered.");
            }
        }

        return registered;
    }
}
