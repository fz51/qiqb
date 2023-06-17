package net.qiqb.domain.utils;

import lombok.extern.slf4j.Slf4j;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;

import java.util.List;

@Slf4j
public class AggregateDiffHelper {

    private static final Javers javers = JaversBuilder.javers().build();

    /**
     * 是否修改
     *
     * @param o1
     * @param o2
     * @return
     */
    public static boolean hasChange(Object o1, Object o2) {
        final Diff compare = javers.compare(o1, o2);
        log.info(compare.prettyPrint());
        return compare.hasChanges();
    }

    public static List<ChangeField> getChanges(Object o1, Object o2) {
        final Diff compare = javers.compare(o1, o2);
        log.info(compare.prettyPrint());
        final Changes changes = compare.getChanges();
        return null;
    }


}
