package org.mbari.kb.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.mbari.kb.core.MiscDAOFactory;
import org.mbari.kb.core.UserAccountDAO;

/**
 * Created by IntelliJ IDEA.
 * User: brian
 * Date: Aug 19, 2009
 * Time: 3:29:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class MiscDAOFactoryImpl implements MiscDAOFactory, EntityManagerFactoryAspect {

    private final EntityManagerFactory entityManagerFactory;

    public MiscDAOFactoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public UserAccountDAO newUserAccountDAO() {
        return new UserAccountDAOImpl(entityManagerFactory.createEntityManager());
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public UserAccountDAO newUserAccountDAO(EntityManager entityManager) {
        return new UserAccountDAOImpl(entityManager);
    }

}
