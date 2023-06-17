package net.qiqb.execution.spring.beans;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import net.qiqb.execution.config.DomainDefinition;
import net.qiqb.execution.config.support.DomainDefinitionRegistryImpl;
import net.qiqb.execution.config.annotation.Domain;
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

@Slf4j
public class DomainAnnotationPostProcessor extends DomainDefinitionRegistryImpl implements SmartInitializingSingleton, ApplicationContextAware, EnvironmentAware, InitializingBean, Ordered {

    public static final String BEAN_NAME = "domainDefinitionRegistry";

    private ApplicationContext applicationContext;

    protected final Set<String> packagesToScan;

    private Set<String> resolvedPackagesToScan;

    private Environment environment;

    private volatile boolean scanned = false;

    public DomainAnnotationPostProcessor(String... packagesToScan) {
        this(asList(packagesToScan));
    }

    public DomainAnnotationPostProcessor(Collection<String> packagesToScan) {
        this(new LinkedHashSet<>(packagesToScan));
    }

    public DomainAnnotationPostProcessor(Set<String> packagesToScan) {
        this.packagesToScan = packagesToScan;
    }


    @Override
    public void afterPropertiesSet() {
        this.resolvedPackagesToScan = resolvePackagesToScan(packagesToScan);
        if (!scanned) {
            scanDomainBeans(this.resolvedPackagesToScan);
        }
    }

    @Override
    public void setApplicationContext(@lombok.NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        //
        if (!scanned) {
            scanDomainBeans(this.resolvedPackagesToScan);
        }
    }

    private void scanDomainBeans(Set<String> packagesToScan) {
        scanned = true;
        // 通过配置注解
        this.applicationContext.getBeansOfType(DomainDefinition.class).forEach(this::registerDomainDefinition);
        if (CollectionUtils.isEmpty(packagesToScan)) {
            // log.warn("领域定义扫描为空");
            return;
        }

        for (String packageToScan : packagesToScan) {
            final Set<Class<?>> classes = ClassUtil.scanPackage(packageToScan);
            for (Class<?> c : classes) {
                final Domain annotation = AnnotationUtil.getAnnotation(c, Domain.class);
                if (annotation == null) {
                    continue;
                }
                String domainName = annotation.name();
                if (StrUtil.isEmpty(domainName)) {
                    domainName = c.getName();
                }
                final DomainDefinition definition = new DomainDefinition(c.getName(), annotation.type());
                definition.setDomainName(domainName);
                this.registerDomainDefinition(domainName, definition);
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
        return 10;
    }
}
