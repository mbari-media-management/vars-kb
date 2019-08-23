package org.mbari.kb.jpa.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.mbari.kb.jpa.knowledgebase.ConceptImpl;

/**
 * @author Brian Schlining
 * @since 2016-11-22T13:54:00
 */
public class Constants {

    /**
     * A Gson object configured to serialize the VARS knowledgebase.
     */
    public static Gson GSON = new GsonBuilder()
            .setExclusionStrategies(new UnderscoreFieldExclusionStrategy(),
                    new AnnotatedFieldExclusionStrategy())
            .registerTypeAdapter(ConceptImpl.class, new ConceptSerializer())
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .create();
}
