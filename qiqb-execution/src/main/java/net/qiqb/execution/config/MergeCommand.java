package net.qiqb.execution.config;

public interface MergeCommand<D> {

    default boolean canMerge(D d) {
        return false;
    }

}
