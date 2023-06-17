package net.qiqb.domain.event.infrastructure.dao;

import java.util.Collection;

public interface DomainEventDao {

    void saveAll(Collection<DomainEventDO> domainEvents);

    Collection<DomainEventDO> listByCommandId(String commandId);
}
