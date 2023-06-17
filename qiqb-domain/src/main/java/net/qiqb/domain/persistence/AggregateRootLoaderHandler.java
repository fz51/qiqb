package net.qiqb.domain.persistence;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import net.qiqb.domain.persistence.config.AggregateRootLoader;
import net.qiqb.domain.persistence.config.LoadVoucher;
import net.qiqb.execution.common.Definition;
import net.qiqb.execution.config.DomainDefinition;
import net.qiqb.execution.executor.CommandWrapper;
import net.qiqb.execution.executor.DomainWrapper;
import net.qiqb.execution.executor.support.handler.HandlerAdapter;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;

public class AggregateRootLoaderHandler implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof AggregateRootLoader<?, ?>;
    }

    @Override
    public void handle(CommandWrapper command, Object handler) {
        if (handler instanceof AggregateRootLoader) {
            final Field loadVoucherField = findLoadVoucherField(command.getValue().getClass());
            if (loadVoucherField == null) {
                return;
            }
            // 匹配command 的加载命令是否和 handler 一致
            final Object fieldValue = ReflectUtil.getFieldValue(command.getValue(), loadVoucherField);
            final Object domainObject = ((AggregateRootLoader) handler).loadAggregateRoot(fieldValue);
            final Definition domainDefinition = command.getCommandDefinition().getDomainDefinition();
            final DomainWrapper domain = new DomainWrapper((DomainDefinition) domainDefinition, domainObject);
            command.initDomain(domain);
        }
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
