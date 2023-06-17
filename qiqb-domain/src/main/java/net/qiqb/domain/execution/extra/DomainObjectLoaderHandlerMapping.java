package net.qiqb.domain.execution.extra;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import net.qiqb.domain.config.annotation.BusinessIdMapping;
import net.qiqb.domain.persistence.config.AggregateRootLoader;
import net.qiqb.domain.persistence.config.LoadVoucher;
import net.qiqb.execution.config.annotation.DomainMapping;
import net.qiqb.execution.config.support.named.GenericNamedFinderHolder;
import net.qiqb.execution.executor.CommandWrapper;
import net.qiqb.execution.executor.config.DomainObjectFactory;
import net.qiqb.execution.executor.support.handler.AbsTypeHandlerMapping;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 在领域初始化步骤中，如果命令没有获取到相应到 DomainObjectFactory 。尝试通过 AggregateRootLoader 加载。
 */
@Slf4j
public class DomainObjectLoaderHandlerMapping extends AbsTypeHandlerMapping<AggregateRootLoader> {


    public DomainObjectLoaderHandlerMapping(List<AggregateRootLoader> totalHandlers) {
        super(totalHandlers);
    }

    @Override
    public Object filterHandler(CommandWrapper command, Class<?> handlerType, AggregateRootLoader handler) {
        if (!handlerType.equals(DomainObjectFactory.class)) {
            return null;
        }
        String domainName = command.getCommandDefinition().getDomainDefinition().getDefinitionName();
        String decorateDomainName;
        final DomainMapping domainMapping = GenericNamedFinderHolder.getMappingAnnotation(handler, DomainMapping.class);
        if (domainMapping == null) {
            final Class<?> generic = GenericNamedFinderHolder.findGeneric(handler, AggregateRootLoader.class, 1);
            if (generic == null) {
                log.warn("{}对象找不到对应AggregateRootLoader泛型", handler);
                return null;
            }
            decorateDomainName = generic.getName();
        } else {
            decorateDomainName = domainMapping.value();
        }
        // 相同的聚合根
        if (domainName.equals(decorateDomainName)) {
            // 比较加载凭证和聚合根的加载方法是否匹配
            // 获取加载凭证字段
            final Field loadVoucherField = findLoadVoucherField(command.getValue().getClass());
            if (loadVoucherField == null) {
                return null;
            }
            String bidName;
            final BusinessIdMapping businessIdMapping = GenericNamedFinderHolder.getMappingAnnotation(handler, BusinessIdMapping.class);
            if (businessIdMapping != null) {
                bidName = businessIdMapping.value();
            } else {
                // 默认类型名称
                bidName = loadVoucherField.getType().getName();
            }
            String loadVoucherName = AnnotationUtil.getAnnotationValue(loadVoucherField, LoadVoucher.class);
            if (StrUtil.isEmpty(loadVoucherName)) {
                loadVoucherName = loadVoucherField.getType().getName();
            }
            if (bidName.equals(loadVoucherName)) {
                return (DomainObjectFactory) cmdValue -> {
                    final Object fieldValue = ReflectUtil.getFieldValue(cmdValue, loadVoucherField);
                    return handler.loadAggregateRoot(fieldValue);
                };
            }
        }
        return null;
    }


    private Field findLoadVoucherField(Class<?> type) {
        final Field[] declaredFields = ClassUtil.getDeclaredFields(type);
        if (declaredFields == null) {
            return null;
        }
        boolean has = false;
        Field result = null;
        for (Field field : declaredFields) {
            if (hasDeclaredLoadVoucher(field) != null) {
                if (has) {
                    throw new IllegalStateException("LoadVoucher 只能有一个字段修饰");
                }
                has = true;
                result = field;
            }
        }
        return result;
    }

    private LoadVoucher hasDeclaredLoadVoucher(Field field) {
        LoadVoucher annotation = AnnotationUtil.getAnnotation(field, LoadVoucher.class);
        if (annotation == null) {
            annotation = AnnotationUtils.findAnnotation(field.getType(), LoadVoucher.class);
        }
        return annotation;
    }
}
