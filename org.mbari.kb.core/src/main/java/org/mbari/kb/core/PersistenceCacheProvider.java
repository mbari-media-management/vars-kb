/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.kb.core;

import org.mbari.kb.core.knowledgebase.KnowledgebaseObject;

/**
 *
 * @author brian
 */
public interface PersistenceCacheProvider {

    void clear();


    void evict(KnowledgebaseObject entity);

}
