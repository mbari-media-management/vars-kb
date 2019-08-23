package org.mbari.kb.jpa.knowledgebase;

import org.mbari.kb.jpa.DAO;
import org.mbari.kb.core.knowledgebase.LinkRealizationDAO;
import org.mbari.kb.core.knowledgebase.LinkRealization;
import org.mbari.kb.core.knowledgebase.ConceptDAO;
import org.mbari.kb.core.knowledgebase.Concept;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

import javax.persistence.EntityManager;

/**
 * Created by IntelliJ IDEA.
 * User: brian
 * Date: Aug 7, 2009
 * Time: 4:46:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class LinkRealizationDAOImpl extends DAO implements LinkRealizationDAO {

    private final ConceptDAO conceptDAO;

    public LinkRealizationDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.conceptDAO = new ConceptDAOImpl(entityManager);
    }

    public Collection<LinkRealization> findAllByLinkName(String linkName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("linkName", linkName);
        return findByNamedQuery("LinkRealization.findByLinkName", params);
    }

    public void validateName(LinkRealization object) {
        Concept concept = conceptDAO.findByName(object.getToConcept());
        if (concept != null) {
            object.setToConcept(concept.getPrimaryConceptName().getName());
        }
        else {
            log.warn(object + " contains a 'conceptName', " + object.getToConcept() +
                    " that was not found in the knowlegebase");
        }
    }
    
}
