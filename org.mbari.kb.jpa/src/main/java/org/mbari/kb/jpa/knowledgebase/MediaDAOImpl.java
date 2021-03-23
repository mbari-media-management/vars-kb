package org.mbari.kb.jpa.knowledgebase;

import org.mbari.kb.core.knowledgebase.MediaDAO;
import org.mbari.kb.core.knowledgebase.NamedMedia;
import org.mbari.kb.jpa.DAO;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: brian
 * Date: Aug 7, 2009
 * Time: 4:48:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class MediaDAOImpl extends DAO implements MediaDAO {

    public MediaDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }


    @Override
    public List<NamedMedia> findRepresentativeMedia(String conceptName) {
//        String sql = "SELE"
        return null;
    }
}
