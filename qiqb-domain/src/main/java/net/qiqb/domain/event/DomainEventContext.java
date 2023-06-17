package net.qiqb.domain.event;

import net.qiqb.domain.event.domain.DomainEvent;
import net.qiqb.domain.event.domain.types.EventProducer;
import net.qiqb.execution.executor.ExecutingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 领域事件容器
 */
public class DomainEventContext {

    private final static Map<String, List<DomainEvent>> EVENTS_MAP = new ConcurrentHashMap<>();

    private static String getCommandId() {
        return ExecutingContext.get().getCommand().getId();
    }

    public static void addEvent(Object... events) {

        if (events != null) {
            String commandId = getCommandId();
            EVENTS_MAP.computeIfAbsent(commandId, k -> new ArrayList<>());
            final List<DomainEvent> domainEvents = EVENTS_MAP.get(commandId);
            for (Object e : events) {
                if (!domainEvents.contains(e)) {
                    domainEvents.add(new DomainEvent("", EventProducer.nameless(), e));
                }
            }
        }
    }

    public void clear() {
        String commandId = getCommandId();
        EVENTS_MAP.remove(commandId);

    }

    /**
     * 获取当前命令下的产生的命令
     *
     * @return
     */
    public List<DomainEvent> getEvents() {
        String commandId = getCommandId();
        return EVENTS_MAP.get(commandId);
    }
}
