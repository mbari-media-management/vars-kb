package org.mbari.kb.core.knowledgebase;

public interface NamedMedia extends Media {

    String getConceptName();
    void setConceptName(String conceptName);
}
