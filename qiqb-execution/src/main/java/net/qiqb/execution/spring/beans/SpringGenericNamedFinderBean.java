package net.qiqb.execution.spring.beans;

import cn.hutool.core.util.ClassLoaderUtil;
import net.qiqb.execution.config.support.named.GenericNamedFinder;
import net.qiqb.execution.config.support.named.GenericNamedFinderHolder;
import net.qiqb.execution.executor.config.BusinessExecutor;
import net.qiqb.execution.executor.config.DomainObjectFactory;
import net.qiqb.execution.executor.config.DomainPersistence;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.ResolvableType;
import org.springframework.lang.NonNull;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class SpringGenericNamedFinderBean implements GenericNamedFinder, ApplicationContextAware, SmartInitializingSingleton, BeanFactoryPostProcessor {

    private ConfigurableListableBeanFactory beanFactory;

    private final List<Class<?>> handlerType = new ArrayList<>();

    public SpringGenericNamedFinderBean() {
        this.handlerType.add(DomainObjectFactory.class);
        this.handlerType.add(BusinessExecutor.class);
        this.handlerType.add(DomainPersistence.class);
    }

    public void addHandlerType(Class<?> type) {
        this.handlerType.add(type);
    }


    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    private ApplicationContext applicationContext;

    private Map<Object, BeanDefinition> objectBeanDefinitionMap = new LinkedHashMap<>();


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        // 将感兴趣的类都加上
        for (Class<?> type : handlerType) {
            final Map<String, ?> aggregateRootFactoryMap = this.applicationContext.getBeansOfType(type);
            aggregateRootFactoryMap.forEach((k, v) -> {
                final BeanDefinition beanDefinition = beanFactory.getBeanDefinition(k);
                objectBeanDefinitionMap.put(v, beanDefinition);
            });
        }
        GenericNamedFinderHolder.addGenericNamedFinder(this);
    }


   /* public boolean isType(Object h, Class<?> handlerType, NameIndex commandNameIndex, NameIndex domainNameIndex) {
        final BeanDefinition beanDefinition = objectBeanDefinitionMap.get(h);
        if (beanDefinition == null) {
            return super.isType(h, handlerType, commandNameIndex, domainNameIndex);
        }
        String commandName = null;

        String domainName = null;

        boolean isType = false;

        String implTypeClassName = beanDefinition.getBeanClassName();

        if (!ObjectUtils.isEmpty(implTypeClassName)) {
            if (handlerType.isAssignableFrom(h.getClass())) {
                isType = true;

            }
            if (isType) {
                final ExecutingMapping executingMapping = AnnotationUtil.getAnnotation(h.getClass(), ExecutingMapping.class);
                if (executingMapping != null) {
                    commandName = executingMapping.commandName();
                    domainName = executingMapping.domainName();
                } else {
                    final ResolvableType type = ResolvableType.forClass(h.getClass());
                    final ResolvableType[] interfaces = type.getInterfaces();
                    for (ResolvableType anInterface : interfaces) {
                        if (handlerType.equals(anInterface.getRawClass())) {
                            final ResolvableType[] generics = anInterface.getGenerics();
                            // 两边的泛型必须一直
                            if (commandNameIndex != null && generics.length > commandNameIndex.getIndex()) {
                                final ResolvableType generic = generics[commandNameIndex.getIndex()];
                                commandName = Objects.requireNonNull(generic.getRawClass()).getName();
                            }
                            if (domainNameIndex != null && generics.length > domainNameIndex.getIndex()) {
                                final ResolvableType generic = generics[domainNameIndex.getIndex()];
                                domainName = Objects.requireNonNull(generic.getRawClass()).getName();
                            }
                        }
                    }
                }

            }
        } else {
            if (beanDefinition instanceof AnnotatedBeanDefinition) {
                // @Bean 加载
                final MethodMetadata factoryMethodMetadata = ((AnnotatedBeanDefinition) beanDefinition).getFactoryMethodMetadata();
                if (factoryMethodMetadata != null) {

                    final boolean sameHandlerType = factoryMethodMetadata.getReturnTypeName().equals(handlerType.getName());
                    if (sameHandlerType) {
                        isType = true;
                        // 比较泛型
                        final String methodName = factoryMethodMetadata.getMethodName();
                        final String declaringClassName = factoryMethodMetadata.getDeclaringClassName();
                        final Class<Object> configClass = ClassUtil.loadClass(declaringClassName);
                        Method method = null;
                        for (Method m : configClass.getMethods()) {
                            if (m.getName().equals(methodName)) {
                                method = m;
                                break;
                            }
                        }
                        if (method != null) {
                            // 是否又
                            final ExecutingMapping executingMapping = AnnotationUtil.getAnnotation(method, ExecutingMapping.class);
                            if (executingMapping != null) {
                                commandName = executingMapping.commandName();
                                domainName = executingMapping.domainName();
                            } else {
                                final ResolvableType type = ResolvableType.forMethodReturnType(method);
                                final ResolvableType[] generics = type.getGenerics();
                                // 两边的泛型必须一直
                                if (commandNameIndex != null && generics.length > commandNameIndex.getIndex()) {
                                    final ResolvableType generic = generics[commandNameIndex.getIndex()];
                                    commandName = Objects.requireNonNull(generic.getRawClass()).getName();
                                }
                                if (domainNameIndex != null && generics.length > domainNameIndex.getIndex()) {
                                    final ResolvableType generic = generics[domainNameIndex.getIndex()];
                                    domainName = Objects.requireNonNull(generic.getRawClass()).getName();
                                }
                            }

                        }
                    }

                }

            }
        }
        if (isType) {
            if (StrUtil.isNotEmpty(commandName) && (commandNameIndex == null || !commandNameIndex.getName().equals(commandName))) {
                isType = false;
            }

            if (StrUtil.isNotEmpty(domainName) && (domainNameIndex == null || !domainNameIndex.getName().equals(domainName))) {
                isType = false;
            }
        }
        return isType;
    }*/


    @Override
    public Class<?> findGeneric(Object o, Class<?> type, int index) {
        final BeanDefinition beanDefinition = objectBeanDefinitionMap.get(o);
        if (beanDefinition == null) {
            return null;
        }

        String implTypeClassName = beanDefinition.getBeanClassName();

        if (!ObjectUtils.isEmpty(implTypeClassName)) {
            final ResolvableType resolvableType = ResolvableType.forClass(o.getClass());
            return getResolvableType(resolvableType, type, index);
        } else {
            if (beanDefinition instanceof AnnotatedBeanDefinition) {
                final String factoryBeanName = beanDefinition.getFactoryBeanName();
                // 配置的bean
                final Object configBean = applicationContext.getBean(factoryBeanName);

                final BeanDefinition factoryBeanDefinition = beanFactory.getBeanDefinition(factoryBeanName);
                if (factoryBeanDefinition instanceof ScannedGenericBeanDefinition) {
                    final String className = ((ScannedGenericBeanDefinition) factoryBeanDefinition).getMetadata().getClassName();
                    final Class<?> aClass = ClassLoaderUtil.loadClass(className);
                    try {
                        final Method method = aClass.getMethod(beanDefinition.getFactoryMethodName());
                        // 是否又
                        final ResolvableType methodReturnType = ResolvableType.forMethodReturnType(method);
                        final ResolvableType[] generics = methodReturnType.getGenerics();
                        if (generics.length > index) {
                            return generics[index].getRawClass();
                        }
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取某个接口的泛型信息，如果接口没有，则
     *
     * @param resolvableType
     * @param type
     * @return
     */
    private ResolvableType[] findResolvableType(ResolvableType resolvableType, Class<?> type) {

        List<ResolvableType> result = new ArrayList<>();
        if (resolvableType != null && ResolvableType.NONE != resolvableType) {
            // 所有接口
            final ResolvableType[] interfaces = resolvableType.getInterfaces();
            for (ResolvableType anInterface : resolvableType.getInterfaces()) {
                // 接口上定义接口
                final ResolvableType[] superResolvableType = anInterface.getInterfaces();

                if (type.equals(anInterface.getRawClass())) {

                }
            }
            final ResolvableType superType = resolvableType.getSuperType();
            if (superType != ResolvableType.NONE) {
                return null;
            }
        }

        return null;
    }

    private Class<?> getResolvableType(ResolvableType resolvableType, Class<?> type, int index) {
        if (resolvableType == null || ResolvableType.NONE == resolvableType) {
            return null;
        }
        if (type.equals(resolvableType.getRawClass())) {
            final ResolvableType[] generics = resolvableType.getGenerics();
            if (generics.length > index) {
                return generics[index].resolve();
            }
        }
        // 接口类型
        for (ResolvableType anInterface : resolvableType.getInterfaces()) {
            // 接口上定义接口
            for (ResolvableType superResType : anInterface.getInterfaces()) {
                Class<?> result = getResolvableType(superResType, type, index);
                if (result != null) {
                    return result;
                }
            }
            //
            Class<?> result = getResolvableType(anInterface, type, index);
            if (result != null) {
                return result;
            }
        }
        // 父类
        return getResolvableType(resolvableType.getSuperType(), type, index);
    }

    @Override
    public <A extends Annotation> A getMappingAnnotation(Object o, Class<A> annotationType) {
        return null;
    }
}