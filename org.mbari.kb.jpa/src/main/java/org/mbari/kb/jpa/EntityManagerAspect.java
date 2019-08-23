/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.kb.jpa;

import javax.persistence.EntityManager;

/**
 *
 * @author brian
 */
public interface EntityManagerAspect {

    EntityManager getEntityManager();

}
