package net.qiqb.execution.config.support;

import net.qiqb.execution.config.DomainDefinition;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DomainDefinitionRegistryImpl implements DomainDefinitionRegistry {

    private final Map<String, DomainDefinition> DomainDefinitionCache = new ConcurrentHashMap<>();

    @Override
    public void registerDomainDefinition(String domainName, DomainDefinition domainDefinition) {
        domainDefinition.setDomainName(domainName);
        if (containsDomainDefinition(domainName)) {
            log.error("命令名称 {} 已存在。存在的命令定义为：{}", domainName, getDomainDefinition(domainName));
            throw new IllegalStateException("命令定义名称已经存在");
        }
        DomainDefinitionCache.put(domainName, domainDefinition);
    }

    @Override
    public void removeDomainDefinition(String domainName) {
        DomainDefinitionCache.remove(domainName);
    }

    @Override
    public DomainDefinition getDomainDefinition(String domainName) {

        final DomainDefinition DomainDefinition = DomainDefinitionCache.get(domainName);
        if (DomainDefinition == null) {
            log.info("命令 {} 找不到定义", domainName);
            throw new IllegalStateException("找不到命令定义");
        }
        return DomainDefinition;
    }

    @Override
    public DomainDefinition getDomainDefinition(Class<?> domainType) {
        DomainDefinition DomainDefinition = null;
        for (DomainDefinition value : this.DomainDefinitionCache.values()) {
            if (value.getDefinitionName().equals(domainType)) {
                if (DomainDefinition != null) {
                    log.error("根据类型不能确定命令类型.命令类型：{}", domainType.getName());
                    throw new IllegalStateException("命令类型对应多个命令定义");
                }
                DomainDefinition = value;
            }
        }
        if (DomainDefinition == null) {
            throw new IllegalStateException("找不到命令定义");
        }
        return DomainDefinition;
    }

    @Override
    public boolean containsDomainDefinition(String domainName) {
        return DomainDefinitionCache.containsKey(domainName);
    }

    @Override
    public String[] getDomainDefinitionNames() {
        return DomainDefinitionCache.keySet().toArray(new String[getDomainDefinitionCount()]);
    }

    @Override
    public int getDomainDefinitionCount() {
        return DomainDefinitionCache.size();
    }

}
