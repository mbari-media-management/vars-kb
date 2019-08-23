/*
 * @(#)LinkTemplateCrudTest.java   2009.11.09 at 04:41:28 PST
 *
 * Copyright 2009 MBARI
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mbari.kb.jpa.knowledgebase;

import java.util.Collection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mbari.kb.jpa.EntityUtilities;
import org.mbari.kb.core.knowledgebase.Concept;
import org.mbari.kb.core.knowledgebase.ConceptDAO;
import org.mbari.kb.core.knowledgebase.ConceptMetadata;
import org.mbari.kb.core.knowledgebase.KnowledgebaseDAOFactory;
import org.mbari.kb.core.knowledgebase.KnowledgebaseFactory;
import org.mbari.kb.core.knowledgebase.LinkTemplate;
import org.mbari.kb.core.testing.KnowledgebaseTestObjectFactory;

/**
 *
 * @author brian
 */
@TestInstance(Lifecycle.PER_CLASS)
public class LinkTemplateCrudTest {

    public final Logger log = LoggerFactory.getLogger(getClass());
    KnowledgebaseDAOFactory daoFactory;
    EntityUtilities entityUtilities;
    KnowledgebaseFactory kbFactory;
    KnowledgebaseTestObjectFactory testObjectFactory;

    @Test
    public void crudTest01() {

        log.info("---------- TEST: crudTest01 ----------");
        ConceptDAO dao = daoFactory.newConceptDAO();
        Concept concept = testObjectFactory.makeObjectGraph("crudTest01", 1);
        dao.startTransaction();
        dao.persist(concept);
        dao.endTransaction();
        // log.info("INITIAL KNOWLEDGEBASE TREE:\n" +
        // entityUtilities.buildTextTree(concept));

        // Insert
        LinkTemplate linkTemplate = testObjectFactory.makeLinkTemplate();
        dao.startTransaction();
        ConceptMetadata conceptMetadata = dao.merge(concept.getConceptMetadata());
        conceptMetadata.addLinkTemplate(linkTemplate);
        dao.persist(linkTemplate);
        dao.endTransaction();
        // log.info("INSERTED " + linkTemplate + " in KNOWLEDGEBASE TREE:\n" +
        // entityUtilities.buildTextTree(concept));

        // Update
        dao.startTransaction();
        linkTemplate = dao.merge(linkTemplate);
        linkTemplate.setLinkName("WOOOGA WOOGA WOOOGA");
        dao.endTransaction();
        // log.info("UPDATED " + linkTemplate + " in KNOWLEDGEBASE TREE:\n" +
        // entityUtilities.buildTextTree(concept));

        // Delete
        dao.startTransaction();
        linkTemplate = dao.merge(linkTemplate);
        linkTemplate.getConceptMetadata().removeLinkTemplate(linkTemplate);
        dao.endTransaction();
        // log.info("DELETED " + linkTemplate + " in KNOWLEDGEBASE TREE:\n" +
        // entityUtilities.buildTextTree(concept));

        dao.cascadeRemove(concept);

    }

    @BeforeAll
    public void setup() {
        Factories factories = new Factories(DerbyTestDAOFactory.newEntityManagerFactory());
        kbFactory = factories.getKnowledgebaseFactory();
        testObjectFactory = new KnowledgebaseTestObjectFactory(kbFactory);
        daoFactory = factories.getKnowledgebaseDAOFactory();
        entityUtilities = new EntityUtilities();

        ConceptDAO dao = daoFactory.newConceptDAO();
        dao.startTransaction();
        Concept concept = dao.findRoot();
        dao.endTransaction();
        if (concept != null) {
            dao.cascadeRemove(concept);
        }
        dao.close();
    }

    @AfterAll
    public void cleanup() {
        ConceptDAO dao = daoFactory.newConceptDAO();
        dao.startTransaction();
        Concept concept = dao.findRoot();
        dao.endTransaction();
        if (concept != null) {
            dao.cascadeRemove(concept);
        }
        dao.close();

        Collection<Concept> badData = daoFactory.newConceptDAO().findAll();

        if (badData.size() > 0) {

            // String s = "Concepts that shouldn't still be in the database:\n";
            // for (Concept c : badData) {
            // s += "\n" + entityUtilities.buildTextTree(c) + "\n";
            // }
            // log.info(s);
            log.info("There are " + badData.size()
                    + " concepts that are still in the knowledgebase that shouldn't be there.");
        }
    }
}
