package org.mbari.kb.jpa.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.mbari.kb.core.knowledgebase.Concept;
import org.mbari.kb.core.knowledgebase.ConceptName;
import org.mbari.kb.core.knowledgebase.Media;
import org.mbari.kb.jpa.knowledgebase.ConceptImpl;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class SquiddleConceptSerializer implements JsonSerializer<ConceptImpl> {

    @Override
    public JsonElement serialize(ConceptImpl c, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        ConceptName name = c.getPrimaryConceptName();
        String primaryName = name == null ? "missing_name_" + c.getPrimaryKey() : name.getName();
        obj.addProperty("name", primaryName);
        obj.addProperty("rank", getRank(c));
        obj.addProperty("id", (Long) c.getPrimaryKey());
        if (c.getParentConcept() != null) {
            obj.addProperty("parent_id", (Long) c.getParentConcept().getPrimaryKey());
        }
        if (c.getConceptNames().size() > 1) {
            List<String> alternateNames = c.getConceptNames()
                    .stream()
                    .map(ConceptName::getName)
                    .collect(Collectors.toList());
            alternateNames.remove(primaryName);

            obj.add("alternate_names", context.serialize(alternateNames));
        }
//        if (!c.getChildConcepts().isEmpty()) {
//            List<ConceptImpl> children = (List<ConceptImpl>)(List<?>) c.getChildConcepts();
//            obj.add("children", context.serialize(children));
//        }
        if (!c.getConceptMetadata().getMedias().isEmpty()) {
            List<String> urls = c.getConceptMetadata()
                    .getMedias()
                    .stream()
                    .map(Media::getUrl)
                    .collect(Collectors.toList());
            obj.add("media", context.serialize(urls));
        }
        return obj;
    }

    public static String getRank(Concept c) {
        String rankLevel = c.getRankLevel();
        String rankName = c.getRankName();
        String rank = null;
        if (rankLevel == null && rankName == null) {
            // Do nothing
        }
        else if (rankLevel == null) {
            rank = rankName;
        }
        else {
            rank = rankLevel + rankName;
        }
        return rank;
    }
}