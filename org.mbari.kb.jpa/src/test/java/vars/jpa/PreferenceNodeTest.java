/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vars.jpa;

import org.mbari.kb.core.VarsUserPreferencesFactory;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mbari.kb.core.MiscDAOFactory;
import org.mbari.kb.core.MiscFactory;
import org.mbari.kb.core.PersistenceCache;
import vars.knowledgebase.jpa.DerbyTestDAOFactory;
import org.mbari.kb.jpa.knowledgebase.Factories;

/**
 *
 * @author brian
 */
@TestInstance(Lifecycle.PER_CLASS)
public class PreferenceNodeTest {

    MiscFactory miscFactory;
    MiscDAOFactory daoFactory;
    VarsUserPreferencesFactory prefsFactory;
    PersistenceCache cache;

    public final Logger log = LoggerFactory.getLogger(getClass());

    @BeforeAll
    public void setup() {
        Factories factories = new Factories(DerbyTestDAOFactory.newEntityManagerFactory());
        miscFactory = factories.getMiscFactory();
        daoFactory = factories.getMiscDAOFactory();
        prefsFactory = factories.getVarsUserPreferencesFactory();
        cache = factories.getPersistenceCache();
    }

    @Test
    public void test01() {

        int testOrder = 0;
        String testName = "test-button";

        // Create nodes
        Preferences root = prefsFactory.userRoot("test");
        log.info("Absolutepath is " + root.absolutePath());
        Preferences test01 = root.node("test01");
        Preferences buttonNode = test01.node("abutton");
        buttonNode.putInt("buttonOrder", testOrder);
        buttonNode.put("buttonName", testName);
        Preferences buttonNode2 = test01.node("bbutton");
        buttonNode2.putInt("buttonOrder", testOrder + 1);
        buttonNode2.put("buttonName", testName + "-not");

        // Clear cache
        cache.clear();

        // Read nodes
        root = prefsFactory.userRoot("test");
        test01 = root.node("test01");
        buttonNode = test01.node("abutton");
        int buttonOrder = buttonNode.getInt("buttonOrder", 1000);
        String buttonName = buttonNode.get("buttonName", "boom");
        try {
            // Clean up
            root.removeNode();
        } catch (BackingStoreException ex) {
            Assertions.fail(ex.getMessage());
        }

        Assertions.assertEquals(testName, buttonName);
        Assertions.assertTrue(testOrder == buttonOrder);

    }

}
