package net.qiqb.domain.event.domain;

import lombok.Getter;
import net.qiqb.domain.config.annotation.AggretateRoot;
import net.qiqb.domain.config.annotation.EntityId;
import net.qiqb.domain.config.support.EntityIdGeneratorHolder;
import net.qiqb.domain.event.domain.types.DomainEventStatus;
import net.qiqb.domain.event.domain.types.EventProducer;

import java.time.LocalDateTime;
import java.util.EventObject;

/**
 * 域事件
 * 领域事件
 *
 * @author fz51
 * @date 2023/02/20
 */
@AggretateRoot
public class DomainEvent extends EventObject {

    /**
     * 事件id
     */
    @EntityId
    @Getter
    private String id;

    /**
     * 可以通过此值来进行追踪事件的。
     */
    @Getter
    private String traceId;

    /**
     * 产生事件的时间
     */
    @Getter
    private LocalDateTime timestamp = LocalDateTime.now();

    @Getter
    private final EventProducer creator;
    @Getter
    private DomainEventStatus status;

    @Getter
    private final Object content;

    public DomainEvent(Object resource, EventProducer creator, Object content) {
        super(resource);
        // 默认采用雪花id
        this.id = EntityIdGeneratorHolder.get(Long.class).generate().toString();
        this.creator = creator;
        this.status = DomainEventStatus.PRE_SEND;
        this.content = content;
    }

}
