package net.qiqb.domain.event.domain.types;

import lombok.Getter;

import java.io.Serializable;

/**
 * 领域事件生产者
 */
public class EventProducer implements Serializable {

    @Getter
    private String id;

    @Getter
    private String name;


    public EventProducer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static EventProducer nameless(){
        return new EventProducer("nameless","佚名");
    }
}
