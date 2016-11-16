/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vars.jpa;

import vars.knowledgebase.Concept;
import vars.knowledgebase.ConceptMetadata;
import vars.knowledgebase.ConceptName;
import vars.knowledgebase.History;
import vars.knowledgebase.LinkRealization;
import vars.knowledgebase.LinkTemplate;
import vars.knowledgebase.Media;

/**
 *
 * @author brian
 */
public class EntityUtilities {

    public String buildTextTree(Concept concept) {
        return buildTextTree(concept, 0);
    }

    private String buildTextTree(Concept concept, int depth) {
        final StringBuilder sb = new StringBuilder();
        String a = "";
        for (int i = 0; i < depth; i++) {
            a += "    ";
        }

        sb.append(a).append(">-- ").append(concept).append("\n");
        for (ConceptName conceptName : concept.getConceptNames()) {
            sb.append(a).append("    |-- ").append(conceptName).append("\n");
        }

        final ConceptMetadata conceptMetadata = concept.getConceptMetadata();
        sb.append(a).append("    `-- ").append(conceptMetadata).append("\n");

        for (Media media : conceptMetadata.getMedias()) {
            sb.append(a).append("        |-- ").append(media).append("\n");
        }

        for (History obj : conceptMetadata.getHistories()) {
            sb.append(a).append("        |-- ").append(obj).append("\n");
        }

        for (LinkRealization obj : conceptMetadata.getLinkRealizations()) {
            sb.append(a).append("        |-- ").append(obj).append("\n");
        }

        for (LinkTemplate obj : conceptMetadata.getLinkTemplates()) {
            sb.append(a).append("        |-- ").append(obj).append("\n");
        }

        depth++;
        for (Concept child : concept.getChildConcepts()) {
            sb.append(buildTextTree(child, depth));
        }

        return sb.toString();
    }
}

