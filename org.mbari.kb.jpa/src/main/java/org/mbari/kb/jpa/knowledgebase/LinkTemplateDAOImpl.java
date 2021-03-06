/*
 * @(#)LinkTemplateDAOImpl.java   2010.01.26 at 02:12:12 PST
 *
 * Copyright 2009 MBARI
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package org.mbari.kb.jpa.knowledgebase;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.mbari.kb.core.ILink;
import org.mbari.kb.core.LinkComparator;
import org.mbari.kb.core.VARSException;
import org.mbari.kb.jpa.DAO;
import org.mbari.kb.core.knowledgebase.Concept;
import org.mbari.kb.core.knowledgebase.ConceptDAO;
import org.mbari.kb.core.knowledgebase.LinkTemplate;
import org.mbari.kb.core.knowledgebase.LinkTemplateDAO;

/**
 * Created by IntelliJ IDEA.
 * User: brian
 * Date: Aug 7, 2009
 * Time: 4:47:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class LinkTemplateDAOImpl extends DAO implements LinkTemplateDAO {

    private static final Comparator<ILink> linkComparator = new LinkComparator();

    private final ConceptDAO conceptDAO;

    /**
     * Constructs ...
     *
     * @param entityManager
     */
    public LinkTemplateDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.conceptDAO = new ConceptDAOImpl(entityManager);
    }



    /**
     * Call this inside a transaction
     *
     * @param concept
     * @return
     */
    public Collection<LinkTemplate> findAllApplicableToConcept(Concept concept) {

        List<LinkTemplate> linkTemplates = new ArrayList<LinkTemplate>();
        while (concept != null) {
            linkTemplates.addAll(concept.getConceptMetadata().getLinkTemplates());
            concept = concept.getParentConcept();
        }

        linkTemplates.sort(linkComparator);
        return linkTemplates;
    }

    /**
     *
     * @param linkName
     * @param toConcept
     * @param linkValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public Collection<LinkTemplate> findAllByLinkFields(String linkName, String toConcept, String linkValue) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("linkName", linkName);
        params.put("toConcept", toConcept);
        params.put("linkValue", linkValue);
        return (Collection<LinkTemplate>) findByNamedQuery("LinkTemplate.findByFields", params);
    }

    /**
     *
     * @param linkName
     * @return
     */
    @SuppressWarnings("unchecked")
    public Collection<LinkTemplate> findAllByLinkName(String linkName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("linkName", linkName);
        return (Collection<LinkTemplate>) findByNamedQuery("LinkTemplate.findByLinkName", params);
    }

    /**
     * Find {@link LinkTemplate}s containing 'linkName' that are applicable to the
     * provided concept. You should call this within a transaction
     *
     * @param linkName
     * @param concept
     * @return
     */
    public Collection<LinkTemplate> findAllByLinkName(final String linkName, Concept concept) {

        Collection<LinkTemplate> linkTemplates = findAllApplicableToConcept(concept);
        return  linkTemplates.stream()
                .filter(linkTemplate -> linkTemplate.getLinkName().equals(linkName))
                .collect(Collectors.toList());

    }

    @Override
    public Collection<LinkTemplate> findAll() {
        return (Collection<LinkTemplate>) findByNamedQuery("LinkTemplate.findAll");
    }

    @Override
    public void updateToConcepts(String newToConcept, Collection<String> oldToConcepts) {


        /*
         * Update the Observation table
         */
        String sql1 = "UPDATE Observation SET ConceptName = ? WHERE ConceptName = ?";
        String sql2 = "UPDATE Association SET ToConcept = ? WHERE ToConcept = ?";

        try {
            EntityManager em = getEntityManager();
            final EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Connection connection = em.unwrap(Connection.class);
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            for (String oldName: oldToConcepts) {
                preparedStatement1.setString(1, newToConcept);
                preparedStatement1.setString(2, oldName);
                preparedStatement1.addBatch();
                preparedStatement2.setString(1, newToConcept);
                preparedStatement2.setString(2, oldName);
                preparedStatement2.addBatch();
            }
            preparedStatement1.executeBatch();
            preparedStatement2.executeBatch();
            preparedStatement1.close();
            preparedStatement2.close();
            transaction.commit();
        }
        catch (Exception e) {
            throw new VARSException("Failed to update concept-names used by annotations", e);
        }
    }

    /**
     *
     * @param object
     */
    public void validateName(LinkTemplate object) {
        Concept concept = conceptDAO.findByName(object.getToConcept());
        if (concept != null) {
            object.setToConcept(concept.getPrimaryConceptName().getName());
        }
        else {
            log.warn(object + " contains a 'conceptName', " + object.getToConcept() +
                     " that was not found in the knowlegebase");
        }
    }
}
