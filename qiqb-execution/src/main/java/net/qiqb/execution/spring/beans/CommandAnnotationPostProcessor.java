package net.qiqb.execution.spring.beans;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import net.qiqb.execution.config.CommandDefinition;
import net.qiqb.execution.config.DomainDefinition;
import net.qiqb.execution.config.support.CommandDefinitionRegistryImpl;
import net.qiqb.execution.config.support.DomainDefinitionRegistry;
import net.qiqb.execution.config.annotation.Command;
import net.qiqb.execution.config.annotation.DomainMapping;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * 处理 command 注解类
 */
@Slf4j
public class CommandAnnotationPostProcessor extends CommandDefinitionRegistryImpl implements SmartInitializingSingleton, ApplicationContextAware, EnvironmentAware, InitializingBean, Ordered {

    private ApplicationContext applicationContext;

    protected final Set<String> packagesToScan;

    private Set<String> resolvedPackagesToScan;

    private Environment environment;

    private volatile boolean scanned = false;

    public CommandAnnotationPostProcessor(String... packagesToScan) {
        this(asList(packagesToScan));
    }

    public CommandAnnotationPostProcessor(Collection<String> packagesToScan) {
        this(new LinkedHashSet<>(packagesToScan));
    }

    public CommandAnnotationPostProcessor(Set<String> packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    @Override
    public void afterPropertiesSet() {
        this.resolvedPackagesToScan = resolvePackagesToScan(packagesToScan);
        if (!scanned) {
            scanCommandBeans(this.resolvedPackagesToScan);
        }
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        //
        if (!scanned) {
            scanCommandBeans(this.resolvedPackagesToScan);
        }
    }

    private void scanCommandBeans(Set<String> packagesToScan) {
        scanned = true;
        // 通过配置注解
        this.applicationContext.getBeansOfType(CommandDefinition.class).forEach(this::registerCommandDefinition);
        if (CollectionUtils.isEmpty(packagesToScan)) {
            //log.warn("命令定义扫描为空");
            return;
        }
        DomainDefinitionRegistry domainDefinitionRegistry = this.applicationContext.getBean(DomainDefinitionRegistry.class);
        for (String packageToScan : packagesToScan) {
            final Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(packageToScan, Command.class);
            for (Class<?> c : classes) {
                final Command annotation = AnnotationUtil.getAnnotation(c, Command.class);
                String commandName = annotation.name();
                if (StrUtil.isEmpty(commandName)) {
                    commandName = c.getName();
                }
                String domainName = null;
                final DomainMapping executingMapping = AnnotationUtil.getAnnotation(c, DomainMapping.class);
                if (executingMapping != null) {
                    domainName = executingMapping.value();
                }
                if (StrUtil.isEmpty(domainName)) {
                    domainName = annotation.value().getName();
                }
                final DomainDefinition domainDefinition = domainDefinitionRegistry.getDomainDefinition(domainName);
                if (domainDefinition == null) {
                    throw new IllegalStateException("创建命令定义找不到领域定义");
                }
                final CommandDefinition commandDefinition = new CommandDefinition(c.getName(), domainDefinition);

                this.registerCommandDefinition(commandName, commandDefinition);
            }
        }

    }

    private Set<String> resolvePackagesToScan(Set<String> packagesToScan) {
        Set<String> resolvedPackagesToScan = new LinkedHashSet<>(packagesToScan.size());
        for (String packageToScan : packagesToScan) {
            if (StringUtils.hasText(packageToScan)) {
                String resolvedPackageToScan = environment.resolvePlaceholders(packageToScan.trim());
                resolvedPackagesToScan.add(resolvedPackageToScan);
            }
        }
        return resolvedPackagesToScan;
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }

    @Override
    public int getOrder() {
        return 20;
    }
}
