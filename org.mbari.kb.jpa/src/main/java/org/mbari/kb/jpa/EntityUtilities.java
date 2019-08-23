/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.kb.jpa;

import org.mbari.kb.core.knowledgebase.Concept;
import org.mbari.kb.core.knowledgebase.ConceptMetadata;
import org.mbari.kb.core.knowledgebase.ConceptName;
import org.mbari.kb.core.knowledgebase.History;
import org.mbari.kb.core.knowledgebase.LinkRealization;
import org.mbari.kb.core.knowledgebase.LinkTemplate;
import org.mbari.kb.core.knowledgebase.Media;

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

