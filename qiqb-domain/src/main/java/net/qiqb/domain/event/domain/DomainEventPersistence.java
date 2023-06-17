package net.qiqb.domain.event.domain;

import java.util.Collection;
import java.util.List;

public interface DomainEventPersistence {

    /**
     * 保存领域业务处理的过程中产生的事件
     *
     * @param events
     * @return
     */
    void save(Collection<DomainEvent> events);

    /**
     * 根据命令id 获取响应的领域事件
     *
     * @param commandId
     * @return
     */
    List<DomainEvent> getDomainEventsByCommandId(String commandId);


}
