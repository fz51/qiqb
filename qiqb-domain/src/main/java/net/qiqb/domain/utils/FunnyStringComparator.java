package net.qiqb.domain.utils;

import org.javers.core.diff.changetype.PropertyChangeMetadata;
import org.javers.core.diff.changetype.container.ContainerElementChange;
import org.javers.core.diff.changetype.container.SetChange;
import org.javers.core.diff.custom.CustomPropertyComparator;
import org.javers.core.metamodel.property.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FunnyStringComparator implements CustomPropertyComparator<String, SetChange> {
    @Override
    public Optional<SetChange> compare(String left, String right, PropertyChangeMetadata metadata, Property property) {
        if (equals(left, right)) {
            return Optional.empty();
        }

        List<ContainerElementChange> changes = new ArrayList<>();

        return Optional.of(new SetChange(metadata, changes));
    }

    @Override
    public boolean equals(String a, String b) {
        return Objects.equals(a, b);
    }

    @Override
    public String toString(String value) {
        return value;
    }
}
