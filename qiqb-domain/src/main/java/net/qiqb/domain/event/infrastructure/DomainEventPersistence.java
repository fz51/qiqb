package net.qiqb.domain.event.infrastructure;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import net.qiqb.domain.event.domain.DomainEvent;
import net.qiqb.domain.event.domain.types.EventProducer;
import net.qiqb.domain.event.infrastructure.dao.DomainEventDO;
import net.qiqb.domain.event.infrastructure.dao.DomainEventDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
public class DomainEventPersistence implements net.qiqb.domain.event.domain.DomainEventPersistence {

    private final DomainEventDao domainEventDao;


    public DomainEventPersistence(DomainEventDao domainEventDao) {
        this.domainEventDao = domainEventDao;
    }


    @Override
    public void save(Collection<DomainEvent> events) {
        List<DomainEventDO> eventDOList = new ArrayList<>();
        for (DomainEvent event : events) {
            DomainEventDO eventDO = DomainEventDO.builder()
                    .id(event.getId())
                    .createTime(event.getTimestamp())
                    .content(JSONUtil.toJsonStr(event.getContent()))
                    .build();
            eventDOList.add(eventDO);
        }
        domainEventDao.saveAll(eventDOList);
    }

    @Override
    public List<DomainEvent> getDomainEventsByCommandId(String commandId) {
        final Collection<DomainEventDO> domainEvents = domainEventDao.listByCommandId(commandId);
        final List<DomainEvent> collect = domainEvents.stream().map(i ->
                new DomainEvent("", new EventProducer("", ""), i.getContent())).collect(Collectors.toList());
        return collect;
    }
}
