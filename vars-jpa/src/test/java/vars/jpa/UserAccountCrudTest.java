package vars.jpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vars.DAO;
import vars.MiscDAOFactory;
import vars.MiscFactory;
import vars.UserAccount;
import vars.UserAccountRoles;
import vars.knowledgebase.jpa.DerbyTestDAOFactory;
import vars.knowledgebase.jpa.Factories;

/**
 * Created by IntelliJ IDEA. User: brian Date: Aug 19, 2009 Time: 3:37:47 PM To
 * change this template use File | Settings | File Templates.
 */
@TestInstance(Lifecycle.PER_CLASS)
public class UserAccountCrudTest {

    MiscFactory miscFactory;
    MiscDAOFactory daoFactory;

    public final Logger log = LoggerFactory.getLogger(getClass());

    @BeforeAll
    public void setup() {
        Factories factories = new Factories(DerbyTestDAOFactory.newEntityManagerFactory());

        miscFactory = factories.getMiscFactory();
        daoFactory = factories.getMiscDAOFactory();
    }

    @Test
    public void basicCrud() {
        log.info("---------- TEST: basicCrud ----------");
        String testString = "test";
        UserAccount userAccount = miscFactory.newUserAccount();
        userAccount.setPassword(testString);
        log.info("Password '" + testString + "' encrypted as '" + userAccount.getPassword() + "'");
        userAccount.setUserName(testString);
        userAccount.setRole(UserAccountRoles.ADMINISTRATOR.getRoleName());
        DAO dao = daoFactory.newUserAccountDAO();
        dao.startTransaction();
        dao.persist(userAccount);
        dao.endTransaction();
        Assertions.assertNotNull(((JPAEntity) userAccount).getId());

        dao.startTransaction();
        userAccount = dao.findByPrimaryKey(UserAccountImpl.class, ((JPAEntity) userAccount).getId());
        log.info("Password stored in database as '" + userAccount.getPassword() + "'");
        Assertions.assertEquals(testString, userAccount.getUserName(), "UserName wasn't stored correctly");
        Assertions.assertEquals(UserAccountRoles.ADMINISTRATOR.getRoleName(), userAccount.getRole(),
                "Role wasn't stored correctly");
        Assertions.assertTrue(userAccount.authenticate(testString), "Couldn't authenticate");

        dao.remove(userAccount);
        dao.endTransaction();

        Assertions.assertNull(((JPAEntity) userAccount).getId(), "Primary key wasn't reset on delete");
    }
}
