/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vars;

import vars.knowledgebase.KnowledgebaseObject;

/**
 *
 * @author brian
 */
public interface PersistenceCacheProvider {

    void clear();


    void evict(KnowledgebaseObject entity);

}
