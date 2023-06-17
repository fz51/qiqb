package net.qiqb.execution.config.support.named;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class GenericNamedFinderHolder {

    private static List<GenericNamedFinder> genericNamedFinders = new ArrayList<>();

    static {
        genericNamedFinders.add(new SimpleGenericNamedFinder());
    }

    public static void addGenericNamedFinder(GenericNamedFinder genericNamedFinder) {
        genericNamedFinders.add(genericNamedFinder);
    }

    public static Class<?> findGeneric(Object o,Class<?> type, int index) {
        for (GenericNamedFinder namedLookAt : genericNamedFinders) {
            final Class<?> named = namedLookAt.findGeneric(o,type, index);
            if (named != null) {
                return named;
            }
        }
        return null;
    }


    public static <A extends Annotation> A getMappingAnnotation(Object o, Class<A> annotationType) {
        for (GenericNamedFinder namedLookAt : genericNamedFinders) {
            final A mappingAnnotation = namedLookAt.getMappingAnnotation(o, annotationType);
            if (mappingAnnotation != null) {
                return mappingAnnotation;
            }
        }
        return null;
    }
}
