package org.mbari.kb.jpa.knowledgebase;

import org.mbari.kb.core.knowledgebase.HistoryDAO;
import org.mbari.kb.core.knowledgebase.History;
import org.mbari.kb.jpa.DAO;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

import javax.persistence.EntityManager;

/**
 * Created by IntelliJ IDEA.
 * User: brian
 * Date: Aug 7, 2009
 * Time: 4:45:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class HistoryDAOImpl extends DAO implements HistoryDAO {

    public HistoryDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
    
    public Set<History> findAll() {
        return new HashSet<History>(findByNamedQuery("History.findAll"));
    }

    public Set<History> findPendingHistories() {
        Map<String, Object> params = new HashMap<String, Object>();
        Set<History> histories = new HashSet<History>();
        histories.addAll(findByNamedQuery("History.findPendingApproval", params));
        return histories;
    }

    public Set<History> findApprovedHistories() {
        Map<String, Object> params = new HashMap<String, Object>();
        Set<History> histories = new HashSet<History>();
        histories.addAll(findByNamedQuery("History.findApproved", params));
        return histories;
    }
}
