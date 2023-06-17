package net.qiqb.core.diff;

import lombok.Getter;

/**
 * 用户实体
 */
public class UserName {

    @Getter
    private final String val;

    public UserName(String val) {
        this.val = val;
    }
}
