package net.qiqb.domain.event.infrastructure.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.qiqb.domain.event.domain.types.DomainEventStatus;

import java.time.LocalDateTime;

@Table(name = "qiqb_domain_event")
@Data
@Entity
@Builder
@AllArgsConstructor
public class DomainEventDO {

    @Id
    private String id;

    private String traceId;

    private LocalDateTime createTime;

    private String creatorId;

    private String creatorName;

    private DomainEventStatus status;

    private String sourceDescription;

    private String content;

    public DomainEventDO() {

    }
}
