package org.mbari.kb.core.knowledgebase;

import org.mbari.kb.core.DAO;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: brian
 * Date: Aug 7, 2009
 * Time: 3:12:37 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MediaDAO extends DAO {

    List<NamedMedia> findRepresentativeMedia(String conceptName);
        
}
