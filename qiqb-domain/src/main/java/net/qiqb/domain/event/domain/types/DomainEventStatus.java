package net.qiqb.domain.event.domain.types;

public enum DomainEventStatus {
    // 待分发
    PRE_SEND,
    // 分发中
    SENDING,
    // 待处理
    PRE_PROCESS,
    // 处理中
    PROCESSING,
    // 全部完成
    PROCESSED,
}
