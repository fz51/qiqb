package net.qiqb.domain.execution.extra;

import net.qiqb.execution.executor.DomainWrapper;

import java.util.Optional;

public interface DomainRepository{

    Optional<DomainWrapper> load(Object domainId);

    void save(DomainWrapper domain);
}
