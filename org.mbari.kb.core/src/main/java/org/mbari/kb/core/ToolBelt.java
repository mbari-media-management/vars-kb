package org.mbari.kb.core;

/*
 * @(#)Toolbelt.java   2009.11.15 at 07:47:13 PST
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



import org.mbari.kb.core.knowledgebase.KnowledgebaseDAOFactory;
import org.mbari.kb.core.knowledgebase.ConceptCache;
import org.mbari.kb.core.knowledgebase.KnowledgebaseFactory;

/**
 *
 */
public class ToolBelt {

    private final KnowledgebaseDAOFactory knowledgebaseDAOFactory;
    private final KnowledgebaseFactory knowledgebaseFactory;
    private final MiscDAOFactory miscDAOFactory;
    private final MiscFactory miscFactory;
    private final PersistenceCache persistenceCache;
    private final ConceptCache conceptCache;

    /**
     * Constructs ...
     *
     * @param conceptCache
     * @param knowledgebaseDAOFactory
     * @param knowledgebaseFactory
     * @param miscDAOFactory
     * @param miscFactory
     * @param persistenceCacheProvider
     */
    public ToolBelt(
            KnowledgebaseDAOFactory knowledgebaseDAOFactory,
            KnowledgebaseFactory knowledgebaseFactory,
            MiscDAOFactory miscDAOFactory, MiscFactory miscFactory,
            PersistenceCacheProvider persistenceCacheProvider, 
            ConceptCache conceptCache) {
        this.conceptCache = conceptCache;
        this.knowledgebaseDAOFactory = knowledgebaseDAOFactory;
        this.knowledgebaseFactory = knowledgebaseFactory;
        this.miscDAOFactory = miscDAOFactory;
        this.miscFactory = miscFactory;
        this.persistenceCache = new PersistenceCache(persistenceCacheProvider);
    }

    /**
     * @return
     */
    public KnowledgebaseDAOFactory getKnowledgebaseDAOFactory() {
        return knowledgebaseDAOFactory;
    }

    /**
     * @return
     */
    public KnowledgebaseFactory getKnowledgebaseFactory() {
        return knowledgebaseFactory;
    }

    /**
     * @return
     */
    public MiscDAOFactory getMiscDAOFactory() {
        return miscDAOFactory;
    }

    /**
     * @return
     */
    public MiscFactory getMiscFactory() {
        return miscFactory;
    }

    /**
     * @return
     */
    public PersistenceCache getPersistenceCache() {
        return persistenceCache;
    }


    /**
     * @return
     */
    public ConceptCache getConceptCache() {
        return conceptCache;
    }


}

