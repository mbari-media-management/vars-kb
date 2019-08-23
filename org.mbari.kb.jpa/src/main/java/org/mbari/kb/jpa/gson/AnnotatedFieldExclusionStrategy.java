package org.mbari.kb.jpa.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Usage:
 * {{{
 *     new GsonBuilder().setExclusionStrategies(new AnnotationExclusionStrategy()).create()
 * }}}
 * @author Brian Schlining
 * @since 2016-11-22T10:14:00
 */
public class AnnotatedFieldExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(Exclude.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}