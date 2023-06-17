package net.qiqb.domain.spring.beans;

import net.qiqb.domain.execution.extra.DomainObjectLoaderHandlerMapping;
import net.qiqb.domain.persistence.config.AggregateRootLoader;
import net.qiqb.execution.spring.beans.SpringGenericNamedFinderBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DomainConfiguration {

    @Bean
    public DomainObjectLoaderHandlerMapping domainObjectLoaderHandlerMapping(List<AggregateRootLoader> aggregateRootLoaders) {
        return new DomainObjectLoaderHandlerMapping(aggregateRootLoaders);
    }

    @Bean
    public SpringGenericNamedFinderBean springGenericNamedFinderBean() {
        final SpringGenericNamedFinderBean springGenericNamedFinderBean = new SpringGenericNamedFinderBean();
        springGenericNamedFinderBean.addHandlerType(AggregateRootLoader.class);
        return springGenericNamedFinderBean;
    }
}
