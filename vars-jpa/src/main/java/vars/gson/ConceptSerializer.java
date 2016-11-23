package vars.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import vars.knowledgebase.Concept;
import vars.knowledgebase.ConceptMetadata;

import java.lang.reflect.Type;

/**
 * Custom serializer that forces loading of concept metadata (which is normally
 * lazy loaded from the database.
 *
 * @author Brian Schlining
 * @since 2016-11-22T13:16:00
 */
public class ConceptSerializer implements JsonSerializer<Concept> {

    @Override
    public JsonElement serialize(Concept concept, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();
        obj.add("names", jsonSerializationContext.serialize(concept.getConceptNames()));
        obj.addProperty("rankLevel", concept.getRankLevel());
        obj.addProperty("rankName", concept.getRankName());
        obj.addProperty("nodcCode", concept.getNodcCode());
        obj.addProperty("originator", concept.getOriginator());
        ConceptMetadata m = concept.getConceptMetadata();
        if (!m.getLinkRealizations().isEmpty()
                || !m.getLinkTemplates().isEmpty()
                || !m.getMedias().isEmpty()) {
            obj.add("metadata", jsonSerializationContext.serialize(concept.getConceptMetadata()));
        }
        obj.add("children", jsonSerializationContext.serialize(concept.getChildConcepts()));
        return obj;
    }
}
