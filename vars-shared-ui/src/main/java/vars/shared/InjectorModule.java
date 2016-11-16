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



package vars.shared;

import com.google.inject.Binder;
import com.google.inject.Module;
import java.util.Locale;
import java.util.ResourceBundle;

import com.typesafe.config.ConfigFactory;
import vars.VARSException;
import vars.jpa.VarsJpaModule;
import vars.knowledgebase.jpa.DevelopmentDAOFactory;
import vars.knowledgebase.jpa.ProductionDAOFactory;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author brian
 */
public class InjectorModule implements Module {


    private final String environment;

    public InjectorModule() {
        environment = ConfigFactory.load().getString("database.environment");
    }

    /**
     *
     * @param binder
     */
    public void configure(Binder binder) {
        EntityManagerFactory entityManagerFactory =  environment.equalsIgnoreCase("production") ?
                ProductionDAOFactory.newEntityManagerFactory() :
                DevelopmentDAOFactory.newEntityManagerFactory();

        try {
            binder.install(new VarsJpaModule(entityManagerFactory));
        }
        catch (Exception ex) {
            throw new VARSException("Failed to intialize dependency injection", ex);
        }

    }


}
