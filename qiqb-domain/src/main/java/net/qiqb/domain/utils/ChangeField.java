package net.qiqb.domain.utils;

import lombok.Getter;

public class ChangeField {
    @Getter
    private final String fieldName;
    @Getter
    private final Object leftValue;
    @Getter
    private final Object rightValue;


    public ChangeField(String fieldName, Object leftValue, Object rightValue) {
        this.fieldName = fieldName;
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }
}
