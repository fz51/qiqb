package net.qiqb.execution.auto;

import net.qiqb.dao.mybatis.plus.BasicInfoFillMetaObjectHandler;
import net.qiqb.domain.spring.beans.DomainConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DomainConfiguration.class)
public class AutoExecutionConfiguration {


    @Bean
    public BasicInfoFillMetaObjectHandler basicInfoFillMetaObjectHandler() {
        return new BasicInfoFillMetaObjectHandler();
    }
}
