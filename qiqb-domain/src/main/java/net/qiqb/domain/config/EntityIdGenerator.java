package net.qiqb.domain.config;

/**
 * 实体类ID 生成器。
 */
public interface EntityIdGenerator<ID> {

    ID generate();

}