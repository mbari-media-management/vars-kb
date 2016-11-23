/*
 * @(#)InjectorModule.java   2010.05.03 at 01:40:46 PDT
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



package vars.jpa;

import com.google.inject.Binder;
import com.google.inject.Module;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import vars.VARSException;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author brian
 */
public class InjectorModule implements Module {


    private final String environment;

    public InjectorModule() {
        Config config = ConfigFactory.load();
        environment = config.getString("database.environment");
    }

    /**
     *
     * @param binder
     */
    public void configure(Binder binder) {
        String nodeName = environment.equalsIgnoreCase("production") ?
                "org.mbari.vars.knowledgebase.database.production" :
                "org.mbari.vars.knowledgebase.database.development";

        EntityManagerFactory entityManagerFactory =  EntityManagerFactories.newEntityManagerFactory(nodeName);

        try {
            binder.install(new VarsJpaModule(entityManagerFactory));
        }
        catch (Exception ex) {
            throw new VARSException("Failed to initialize dependency injection", ex);
        }

    }


}
