/*
 * @(#)ToolBelt.java   2009.12.03 at 11:24:13 PST
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



package vars.knowledgebase.ui;

import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mbari.kb.core.MiscDAOFactory;
import org.mbari.kb.core.MiscFactory;
import org.mbari.kb.core.PersistenceCacheProvider;
import org.mbari.kb.core.knowledgebase.ConceptCache;
import org.mbari.kb.core.knowledgebase.HistoryFactory;
import org.mbari.kb.core.knowledgebase.KnowledgebaseDAOFactory;
import org.mbari.kb.core.knowledgebase.KnowledgebaseFactory;
import vars.knowledgebase.ui.actions.ApproveHistoryTask;
import vars.knowledgebase.ui.actions.RejectHistoryTask;
import vars.knowledgebase.ui.annotation.AnnotationService;
import vars.shared.rx.EventBus;

import java.nio.file.Path;

/**
 * Container that holds on to a ton of shared objects that need to be widely
 * used across this application
 */
public class ToolBelt extends org.mbari.kb.core.ToolBelt {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ApproveHistoryTask approveHistoryTask;
    private final HistoryFactory historyFactory;
    private final RejectHistoryTask rejectHistoryTask;
    private final AnnotationService annotationService;
    private final EventBus eventBus;
    private Config config;
    private Path settingsDirectory;

    /**
     * Constructs ...
     *
     * @param knowledgebaseDAOFactory
     * @param knowledgebaseFactory
     * @param miscDAOFactory
     * @param miscFactory
     * @param persistenceCacheProvider
     * @param conceptCache
     */
    public ToolBelt(KnowledgebaseDAOFactory knowledgebaseDAOFactory, KnowledgebaseFactory knowledgebaseFactory,
                    MiscDAOFactory miscDAOFactory, MiscFactory miscFactory,
                    PersistenceCacheProvider persistenceCacheProvider,
                    ConceptCache conceptCache,
                    AnnotationService annotationService) {
        super(knowledgebaseDAOFactory, knowledgebaseFactory, miscDAOFactory,
              miscFactory, persistenceCacheProvider, conceptCache);
        historyFactory = new HistoryFactory(knowledgebaseFactory);
        approveHistoryTask = new ApproveHistoryTask(this);
        rejectHistoryTask = new RejectHistoryTask(this);
        this.annotationService = annotationService;
        this.eventBus = new EventBus();
        config = Initializer.getConfig();
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    /**
     * @return
     */
    public ApproveHistoryTask getApproveHistoryTask() {
        return approveHistoryTask;
    }

    /**
     * @return
     */
    public HistoryFactory getHistoryFactory() {
        return historyFactory;
    }

    /**
     * @return
     */
    public RejectHistoryTask getRejectHistoryTask() {
        return rejectHistoryTask;
    }

    public AnnotationService getAnnotationService() {
        return annotationService;
    }

    /**
     * First looks for the file `~/.vars/vars-annotation.conf` and, if found,
     * loads that file. Otherwise used the usual `reference.conf`/`application.conf`
     * combination for typesafe's config library.
     * @return
     */
    public Config getConfig() {
        return config;
    }

}
