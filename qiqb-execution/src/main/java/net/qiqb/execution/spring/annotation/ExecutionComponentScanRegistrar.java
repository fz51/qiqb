package net.qiqb.execution.spring.annotation;

import net.qiqb.execution.spring.beans.CommandAnnotationPostProcessor;
import net.qiqb.execution.spring.beans.DomainAnnotationPostProcessor;
import net.qiqb.execution.spring.ExecutionSpringInitializer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

public class ExecutionComponentScanRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Set<String> packagesToScan = getPackagesToScan(importingClassMetadata);
        registerCommandAnnotationPostProcessor(packagesToScan, registry);
        registerDomainAnnotationPostProcessor(packagesToScan, registry);
        ExecutionSpringInitializer.initialize(registry);
    }

    private final String commandAnnotationPostProcessorBeanName = "commandAnnotationPostProcessor";

    private final String domainAnnotationPostProcessorBeanName = "domainAnnotationPostProcessor";

    private void registerCommandAnnotationPostProcessor(Set<String> packagesToScan, BeanDefinitionRegistry registry) {
        if (registry.containsBeanDefinition(commandAnnotationPostProcessorBeanName)){
            final BeanDefinition beanDefinition = registry.getBeanDefinition(commandAnnotationPostProcessorBeanName);
            final ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
            final ConstructorArgumentValues.ValueHolder argumentValue = constructorArgumentValues.getArgumentValue(0, Set.class);
            final Set<String> value = (Set<String>) argumentValue.getValue();
            value.addAll(packagesToScan);
            return;
        }
        BeanDefinitionBuilder builder = rootBeanDefinition(CommandAnnotationPostProcessor.class);
        builder.addConstructorArgValue(packagesToScan);
        builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();

        registry.registerBeanDefinition(commandAnnotationPostProcessorBeanName,beanDefinition);


    }

    private void registerDomainAnnotationPostProcessor(Set<String> packagesToScan, BeanDefinitionRegistry registry) {
        if (registry.containsBeanDefinition(domainAnnotationPostProcessorBeanName)){
            final BeanDefinition beanDefinition = registry.getBeanDefinition(domainAnnotationPostProcessorBeanName);
            final ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
            final ConstructorArgumentValues.ValueHolder argumentValue = constructorArgumentValues.getArgumentValue(0, Set.class);
            final Set<String> value = (Set<String>) argumentValue.getValue();
            value.addAll(packagesToScan);
            return;
        }
        BeanDefinitionBuilder builder = rootBeanDefinition(DomainAnnotationPostProcessor.class);
        builder.addConstructorArgValue(packagesToScan);
        builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        registry.registerBeanDefinition(domainAnnotationPostProcessorBeanName,beanDefinition);


    }

    private Set<String> getPackagesToScan(AnnotationMetadata metadata) {

        Set<String> packagesToScan = getPackagesToScan0(metadata, ExecutionComponentScan.class, "basePackages", "basePackageClasses");

        if (packagesToScan.isEmpty()) {
            return Collections.singleton(ClassUtils.getPackageName(metadata.getClassName()));
        }
        return packagesToScan;
    }

    private Set<String> getPackagesToScan0(AnnotationMetadata metadata, Class annotationClass, String basePackagesName, String basePackageClassesName) {

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                metadata.getAnnotationAttributes(annotationClass.getName()));
        if (attributes == null) {
            return Collections.emptySet();
        }

        Set<String> packagesToScan = new LinkedHashSet<>();
        // basePackages
        String[] basePackages = attributes.getStringArray(basePackagesName);
        packagesToScan.addAll(Arrays.asList(basePackages));

        Class<?>[] basePackageClasses = attributes.getClassArray(basePackageClassesName);
        for (Class<?> basePackageClass : basePackageClasses) {
            packagesToScan.add(ClassUtils.getPackageName(basePackageClass));
        }
        // value
        if (attributes.containsKey("value")) {
            String[] value = attributes.getStringArray("value");
            packagesToScan.addAll(Arrays.asList(value));
        }
        return packagesToScan;
    }

}