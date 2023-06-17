package net.qiqb.domain.persistence;


import net.qiqb.domain.execution.extra.DomainRepository;
import net.qiqb.execution.executor.DomainWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 本地持久化
 */
public class LocalDomainRepository implements DomainRepository {

    private final Map<Object, DomainWrapper> repository = new HashMap<>(64);

    @Override
    public Optional<DomainWrapper> load(Object domainId) {

        return Optional.of(repository.get(domainId));
    }

    @Override
    public void save(DomainWrapper domain) {
        repository.put(domain.getId(), domain);
    }
}
