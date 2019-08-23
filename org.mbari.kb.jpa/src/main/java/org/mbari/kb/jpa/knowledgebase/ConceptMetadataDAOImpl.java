package org.mbari.kb.jpa.knowledgebase;

import org.mbari.kb.core.knowledgebase.ConceptMetadataDAO;
import org.mbari.kb.jpa.DAO;
import javax.persistence.EntityManager;

/**
 * Created by IntelliJ IDEA.
 * User: brian
 * Date: Aug 7, 2009
 * Time: 4:44:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConceptMetadataDAOImpl extends DAO implements ConceptMetadataDAO {

    public ConceptMetadataDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
    
}
