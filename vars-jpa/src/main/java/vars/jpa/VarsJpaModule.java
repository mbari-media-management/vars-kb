/*
 * @(#)VarsJpaModule.java   2009.09.21 at 09:03:15 PDT
 *
 * Copyright 2009 MBARI
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package vars.jpa;

import vars.VarsUserPreferencesFactory;
import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.prefs.PreferencesFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vars.ExternalDataPersistenceService;
import vars.EXPDPersistenceService;
import vars.MiscDAOFactory;
import vars.MiscFactory;
import vars.PersistenceCacheProvider;
import vars.annotation.AnnotationDAOFactory;
import vars.annotation.AnnotationFactory;
import vars.annotation.AnnotationPersistenceService;
import vars.annotation.AnnotationPersistenceServiceImpl;
import vars.annotation.jpa.AnnotationDAOFactoryImpl;
import vars.annotation.jpa.AnnotationFactoryImpl;
import vars.knowledgebase.KnowledgebasePersistenceService;
import vars.knowledgebase.KnowledgebaseDAOFactory;
import vars.knowledgebase.KnowledgebasePersistenceServiceImpl;
import vars.knowledgebase.KnowledgebaseFactory;
import vars.knowledgebase.jpa.KnowledgebaseDAOFactoryImpl;
import vars.knowledgebase.jpa.KnowledgebaseFactoryImpl;
import vars.query.QueryPersistenceService;
import vars.query.QueryPersistenceServiceImpl;

/**
 * Created by IntelliJ IDEA.
 * User: brian
 * Date: Aug 10, 2009
 * Time: 3:35:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class VarsJpaModule implements Module {

    private final String knowledgebasePersistenceUnit;
    private final String miscPersistenceUnit;
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Constructs ...
     *
     * @param knowledgebasePersistenceUnit
     * @param miscPersistenceUnit
     */
    public VarsJpaModule(String knowledgebasePersistenceUnit,
                         String miscPersistenceUnit) {
        this.knowledgebasePersistenceUnit = knowledgebasePersistenceUnit;
        this.miscPersistenceUnit = miscPersistenceUnit;
    }

    public void configure(Binder binder) {

        /*
         * Binding an iso8601 data format
         */
        final DateFormat dateFormatISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'") {{
            setTimeZone(TimeZone.getTimeZone("UTC"));
        }};
        binder.bind(DateFormat.class).toInstance(dateFormatISO);
        
        // Bind the EntityManagerFactories
        binder.bind(EntityManagerFactory.class).annotatedWith(Names.named("annotationPersistenceUnit")).toInstance(Persistence.createEntityManagerFactory(annotationPersistenceUnit));
        binder.bind(EntityManagerFactory.class).annotatedWith(Names.named("knowledgebasePersistenceUnit")).toInstance(Persistence.createEntityManagerFactory(knowledgebasePersistenceUnit));
        binder.bind(EntityManagerFactory.class).annotatedWith(Names.named("miscPersistenceUnit")).toInstance(Persistence.createEntityManagerFactory(miscPersistenceUnit));

        // Bind annotation object and DAO factories

        binder.bind(KnowledgebaseDAOFactory.class).to(KnowledgebaseDAOFactoryImpl.class).in(Scopes.SINGLETON);
        binder.bind(KnowledgebaseFactory.class).to(KnowledgebaseFactoryImpl.class);
        binder.bind(KnowledgebasePersistenceService.class).to(KnowledgebasePersistenceServiceImpl.class);
        binder.bind(MiscDAOFactory.class).to(MiscDAOFactoryImpl.class).in(Scopes.SINGLETON);
        binder.bind(MiscFactory.class).to(MiscFactoryImpl.class);
        binder.bind(PersistenceCacheProvider.class).to(JPACacheProvider.class);
        binder.bind(VarsUserPreferencesFactory.class).to(VarsUserPreferencesFactoryImpl.class).in(Scopes.SINGLETON);
        binder.bind(PreferencesFactory.class).toProvider(PreferenceFactoryProvider.class);

    }

    /**
     * This allows us to bind a single VarsUserPreferenceFactory object to
     * several different bindings.
     */
    private static class PreferenceFactoryProvider implements Provider<VarsUserPreferencesFactory> {

        private final VarsUserPreferencesFactory preferencesFactory;

        @Inject
        public PreferenceFactoryProvider(VarsUserPreferencesFactory preferencesFactory) {
            this.preferencesFactory = preferencesFactory;
        }

        public VarsUserPreferencesFactory get() {
            return preferencesFactory;
        }

    }
}
