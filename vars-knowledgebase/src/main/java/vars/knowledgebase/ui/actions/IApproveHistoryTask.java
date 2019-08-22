package vars.knowledgebase.ui.actions;

import org.mbari.kb.core.knowledgebase.History;
import org.mbari.kb.core.DAO;
import org.mbari.kb.core.UserAccount;


public interface IApproveHistoryTask {
    
	/**
	 * Approve method should be called within a DAO transaction
	 * @param userAccount
	 * @param history
	 * @param dao
	 */
    void approve(UserAccount userAccount, History history, DAO dao);

}
