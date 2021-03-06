/*
 * @(#)KnowledgebaseDAOFactory.java   2009.11.23 at 04:18:44 PST
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



package org.mbari.kb.core.knowledgebase;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.mbari.kb.core.DAO;

/**
 * Created by IntelliJ IDEA.
 * User: brian
 * Date: Aug 11, 2009
 * Time: 9:28:24 AM
 * To change this template use File | Settings | File Templates.
 */
public interface KnowledgebaseDAOFactory {

    ConceptDAO newConceptDAO();

    ConceptDAO newConceptDAO(EntityManager entitManager);

    ConceptMetadataDAO newConceptMetadataDAO();

    ConceptMetadataDAO newConceptMetadataDAO(EntityManager entitManager);

    ConceptNameDAO newConceptNameDAO();

    ConceptNameDAO newConceptNameDAO(EntityManager entitManager);

    DAO newDAO();

    DAO newDAO(EntityManager entitManager);

    HistoryDAO newHistoryDAO();

    HistoryDAO newHistoryDAO(EntityManager entitManager);

    LinkRealizationDAO newLinkRealizationDAO();

    LinkRealizationDAO newLinkRealizationDAO(EntityManager entitManager);

    LinkTemplateDAO newLinkTemplateDAO();

    LinkTemplateDAO newLinkTemplateDAO(EntityManager entitManager);

    MediaDAO newMediaDAO();

    MediaDAO newMediaDAO(EntityManager entitManager);

    EntityManagerFactory getEntityManagerFactory();
}
