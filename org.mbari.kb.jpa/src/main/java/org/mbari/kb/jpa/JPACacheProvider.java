/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.kb.jpa;

import org.mbari.kb.core.PersistenceCacheProvider;

import javax.persistence.Cache;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mbari.kb.core.VARSObject;
import org.mbari.kb.core.knowledgebase.KnowledgebaseObject;

/**
 * Provides a method to clear the 2nd level cache used by JPA.
 *
 * @author brian
 */
public class JPACacheProvider implements PersistenceCacheProvider {

    private final EntityManagerFactory kbEmf;
    private final EntityManagerFactory miscEmf;
    private final Logger log = LoggerFactory.getLogger(getClass());



    public JPACacheProvider(EntityManagerFactory kbEmf,
            EntityManagerFactory miscEmf) {

        this.kbEmf = kbEmf;
        this.miscEmf = miscEmf;
    }

    /**
     * Clear the second level cache
     */
    public void clear() {
        
        Cache cache = kbEmf.getCache();
        cache.evictAll();

        cache = miscEmf.getCache();
        cache.evictAll();
        
    }


    public void evict(KnowledgebaseObject entity) {
        evict(kbEmf.getCache(), entity);
    }

    private void evict(Cache cache, VARSObject entity) {
        try {
            cache.evict(entity.getClass(), entity.getPrimaryKey());
        }
        catch (Exception e) {
            log.info("Failed to evict " + entity + " from cache", e);
        }
    }

}
