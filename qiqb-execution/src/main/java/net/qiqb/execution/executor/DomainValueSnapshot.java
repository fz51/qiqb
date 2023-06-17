package net.qiqb.execution.executor;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 领域对象值快照
 */
public class DomainValueSnapshot {
    /**
     * 初始化
     */
    public static final String INIT_POINT = "init_point";
    /**
     * 完成业务时间
     */
    public static final String COMPLETE_BUSINESS_EXECUTION_POINT = "complete_business_execution_point";

    @Getter
    private final String point;
    /**
     * 快照时间
     */
    @Getter
    private final Object value;

    @Getter
    private final LocalDateTime createTime = LocalDateTime.now();


    public DomainValueSnapshot(String point, Object value) {
        this.point = point;
        this.value = value;
    }
}
