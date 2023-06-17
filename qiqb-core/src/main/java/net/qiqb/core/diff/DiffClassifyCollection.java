package net.qiqb.core.diff;

import cn.hutool.core.collection.CollUtil;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.*;

import java.util.*;
import java.util.function.Function;

/**
 * 比较分类两个集合(A：B)
 *
 */
public class DiffClassifyCollection<T> {

    private final Collection<T> leftCollection;

    private final Collection<T> rightCollection;

    private final Diff leftToRightCompare;

    private final Diff rightToLeftCompare;

    private Class<T> type;

    public DiffClassifyCollection(Collection<T> leftCollection, Collection<T> rightCollection, Function<T, String> keyFun) {
        this.leftCollection = leftCollection;
        this.rightCollection = rightCollection;
        if (CollUtil.isNotEmpty(leftCollection)) {
            final Optional<T> first = leftCollection.stream().findFirst();
            first.ifPresent(t -> type = (Class<T>) t.getClass());
        } else {
            final Optional<T> first = rightCollection.stream().findFirst();
            first.ifPresent(t -> type = (Class<T>) t.getClass());
        }

        Javers javers;
        if (keyFun != null) {
            final List<EntityWrapper> leftWrapper = leftCollection == null ? null : leftCollection.stream().map(i -> new EntityWrapper(keyFun.apply(i), i)).toList();
            final List<EntityWrapper> rightWrapper = rightCollection == null ? null : rightCollection.stream().map(i -> new EntityWrapper(keyFun.apply(i), i)).toList();
            javers = JaversBuilder.javers()
                    .registerEntity(EntityWrapper.class)
                    .registerValue(type)
                    .build();
            leftToRightCompare = javers.compareCollections(leftWrapper, rightWrapper, EntityWrapper.class);
            rightToLeftCompare = javers.compareCollections(rightWrapper, leftWrapper, EntityWrapper.class);

        } else {
            javers = JaversBuilder.javers()
                    .registerEntity(EntityWrapper.class)
                    .build();
            leftToRightCompare = javers.compareCollections(leftCollection, rightCollection, type);
            rightToLeftCompare = javers.compareCollections(rightCollection, leftCollection, type);
        }
    }

    public Collection<T> getAdditionalFromLeft() {
        return getAdditional(leftToRightCompare);
    }

    private Collection<T> getAdditional(Diff compare) {
        final Changes changes = compare.getChanges();
        if (changes.isEmpty()) {
            return null;
        }
        Collection<T> result = new ArrayList<>();
        changes.forEach(change -> {
            if (change instanceof NewObject) {
                final Optional<Object> affectedObject = change.getAffectedObject();
                if (affectedObject.isPresent()) {
                    if (affectedObject.get() instanceof EntityWrapper) {
                        result.add((T) ((EntityWrapper) affectedObject.get()).getBody());
                    } else {
                        result.add((T) affectedObject.get());
                    }

                }

            }
        });
        return result;
    }

    public Collection<T> getAdditionalFromRight() {
        return getAdditional(rightToLeftCompare);
    }


    private Collection<T> getDelete(Diff compare) {
        final Changes changes = compare.getChanges();
        if (changes.isEmpty()) {
            return null;
        }
        Collection<T> result = new ArrayList<>();
        changes.forEach(change -> {
            if (change instanceof ObjectRemoved) {
                final Optional<Object> affectedObject = change.getAffectedObject();
                if (affectedObject.isPresent()) {
                    if (affectedObject.get() instanceof EntityWrapper) {
                        result.add((T) ((EntityWrapper) affectedObject.get()).getBody());
                    } else {
                        result.add((T) affectedObject.get());
                    }
                }

            }
        });
        return result;
    }

    public Collection<T> getDeletedFromLeft() {
        return getDelete(leftToRightCompare);
    }

    public Collection<T> getDeletedFromRight() {
        return getDelete(rightToLeftCompare);
    }

    /**
     * key 左侧对象，value 右侧对象
     *
     * @param compare
     * @return
     */
    private Map<T, T> getModified(Diff compare) {
        final Changes changes = compare.getChanges();
        if (changes.isEmpty()) {
            return null;
        }
        Javers valueJavers = JaversBuilder.javers()
                //.registerValue(type)
                .build();
        Map<T, T> result = new HashMap<>();
        for (Change change : changes) {
            if (change instanceof NewObject || change instanceof ObjectRemoved) {
                continue;
            }
            if (change instanceof ValueChange) {
                if (change instanceof InitialValueChange || change instanceof TerminalValueChange) {
                    continue;
                }
                final Optional<Object> affectedObject = change.getAffectedObject();
                if (affectedObject.isPresent()) {
                    final T left = (T) ((ValueChange) change).getLeft();
                    final T right = (T) ((ValueChange) change).getRight();
                    final Diff valueCompare = valueJavers.compare(left, right);
                    if (valueCompare.hasChanges()) {
                        result.put(left, right);
                    }
                }
            }

        }
        return result;
    }

    public Map<T, T> getModifiedFromLeft() {
        return getModified(leftToRightCompare);
    }

    public Map<T, T> getModifiedFromRight() {
        return getModified(rightToLeftCompare);
    }

    /**
     * 判断两个集合是否有改变
     *
     * @return
     */
    public boolean hasChange() {
        return leftToRightCompare.hasChanges();
    }
}
