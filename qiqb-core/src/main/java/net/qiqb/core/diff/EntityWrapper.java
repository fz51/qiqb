package net.qiqb.core.diff;

import lombok.Getter;
import org.javers.core.metamodel.annotation.Id;

public class EntityWrapper {

    @Id
    @Getter
    private final String id;


    @Getter
    private final Object body;

    public EntityWrapper(String id, Object body) {
        this.id = id;
        this.body = body;
    }
}
