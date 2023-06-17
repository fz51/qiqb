package net.qiqb.execution;

import java.util.ArrayList;
import java.util.List;

/**
 * 合并命令
 */
public class MergedCommand {

    /**
     * 领头羊
     */

    private final Object bellwether;
    /**
     * 追随的命令
     */
    private final List<Object> followValues = new ArrayList<>();


    public MergedCommand(Object bellwether) {
        this.bellwether = bellwether;
    }

    public void addFollow(Object value) {
        followValues.add(value);
    }

    public List<Object> getCommandValues() {
        List<Object> result = new ArrayList<>();
        result.add(this.bellwether);
        result.addAll(this.followValues);
        return result;
    }
}
