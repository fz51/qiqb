package net.qiqb.execution.executor;

import cn.hutool.core.util.ObjectUtil;
import net.qiqb.execution.config.DomainDefinition;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * 领域对象
 */
@Slf4j
public class DomainWrapper {

    /**
     *
     */
    @Getter
    private final Object id;

    /**
     * 领域对象
     */
    private final Object object;

    private final LinkedHashMap<CommandWrapper, List<DomainValueSnapshot>> snapshots = new LinkedHashMap<>();

    @Getter
    private final DomainDefinition definition;


    public DomainWrapper(DomainDefinition definition, Object value) {
        this.id = UUID.randomUUID().toString();
        this.object = value;
        this.definition = definition;
    }

    public void snapshot(CommandWrapper command, String point) {
        if (this.definition.isService()) {
            // 非聚合根无需设置快照
            log.warn("非聚合领域对象无需设置快照");
            return;
        }
        snapshots.computeIfAbsent(command, k -> new ArrayList<>());
        snapshots.get(command).add(new DomainValueSnapshot(point, ObjectUtil.clone(this.getObject())));
    }

    public List<DomainValueSnapshot> getDomainValueSnapshot(CommandWrapper command) {
        return snapshots.get(command);
    }

    public Object getObject() {
        return this.object;
    }
}
