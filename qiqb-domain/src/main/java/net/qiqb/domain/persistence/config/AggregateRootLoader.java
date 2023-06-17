package net.qiqb.domain.persistence.config;

@FunctionalInterface
public interface AggregateRootLoader<BID, AR> {

    AR loadAggregateRoot(BID bid);
}
