package org.mbari.kb.jpa.knowledgebase;

import org.mbari.kb.core.knowledgebase.ConceptMetadata;
import org.mbari.kb.core.knowledgebase.Media;

public class NamedMediaImpl implements Media {

    Long id;
    String conceptName;
    String caption;
    String credit;
    String type;
    String url;

    public NamedMediaImpl() {
    }

    public NamedMediaImpl(Long id, String conceptName, String caption, String credit, String type, String url) {
        this.id = id;
        this.conceptName = conceptName;
        this.caption = caption;
        this.credit = credit;
        this.type = type;
        this.url = url;
    }

    @Override
    public Object getPrimaryKey() {
        return id;
    }

    @Override
    public ConceptMetadata getConceptMetadata() {
        return null;
    }


    @Override
    public Boolean isPrimary() {
        return false;
    }

    @Override
    public void setPrimary(Boolean primary) {
        // do nothing
    }


    @Override
    public String stringValue() {
        return url;
    }

    public String getConceptName() {
        return conceptName;
    }

    public void setConceptName(String conceptName) {
        this.conceptName = conceptName;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public String getCredit() {
        return credit;
    }

    @Override
    public void setCredit(String credit) {
        this.credit = credit;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
