package vars.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.mbari.net.URLUtilities;
import vars.knowledgebase.Concept;
import vars.knowledgebase.ConceptName;
import vars.knowledgebase.jpa.ConceptImpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Brian Schlining
 * @since 2017-02-23T11:13:00
 */
public class SquiddleExport {
    private static Long lastId = 1L;

    private static Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(ConceptImpl.class, new SquiddleConceptSerializer())
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .create();

    public static void main(String[] args) throws Exception {
        URL url = SquiddleExport.class.getResource("/kb/kb-dump.json.zip");
        File file = URLUtilities.toFile(url);
        final Concept concept = InitializeKnowledgebaseApp.read(file);
        addPrimaryKey(concept);
        List<ConceptImpl> concepts = new ArrayList<>();
        flatten((ConceptImpl) concept, concepts);
        final String s = GSON.toJson(concepts);
        File out = new File("kb-for-squiddle.json");
        try (BufferedWriter fs = new BufferedWriter(new FileWriter(out))) {
            fs.write(s);
        }
    }

    public static void addPrimaryKey(Concept concept) {
        lastId = lastId + 1;
        ((ConceptImpl) concept).setId(lastId);
        concept.getChildConcepts()
                .forEach(SquiddleExport::addPrimaryKey);
    }


    public static void flatten(ConceptImpl concept, List<ConceptImpl> accum) {
        accum.add(concept);
        concept.getChildConcepts()
                .stream()
                .map(c -> (ConceptImpl) c)
                .forEach(c -> flatten(c, accum));
    }


}




