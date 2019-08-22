package vars.knowledgebase.ui.actions;

import org.bushe.swing.event.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.mbari.kb.core.DAO;
import org.mbari.kb.core.UserAccount;
import org.mbari.kb.core.knowledgebase.ConceptMetadata;
import org.mbari.kb.core.knowledgebase.History;
import vars.knowledgebase.ui.StateLookup;

public abstract class AbstractHistoryTask {

    final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Do the task on the history then
     * @param userAccount
     * @param history
     * @return
     */
    public abstract void doTask(final UserAccount userAccount, final History history);

    
    /**
     * Called when an error occurs where the history is void. Gets rid of the 
     * offending history
     * @param h
     * @param msg
     */
    protected void dropHistory(History h, final String msg, DAO dao) {
        EventBus.publish(StateLookup.TOPIC_NONFATAL_ERROR, msg);
        h = dao.find(h);
        final ConceptMetadata conceptMetadata = h.getConceptMetadata();
        conceptMetadata.removeHistory(h);
        dao.remove(h);
    }
    
    protected static boolean canDo(final UserAccount userAccount, final History history) {
        return userAccount != null && userAccount.isAdministrator() && history != null;
    }

}
