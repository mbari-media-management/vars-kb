package vars.knowledgebase.ui;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vars.knowledgebase.ui.annotation.AnnotationService;
import vars.knowledgebase.ui.annotation.MockAnnotationService;
import vars.knowledgebase.ui.annotation.RabbitMQAnnotationService;

import java.util.Arrays;

/**
 * @author Brian Schlining
 * @since 2016-11-16T15:58:00
 */
public class InjectorModule implements Module {
    private final Config config;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public InjectorModule() {
        this.config = Initializer.getConfig();
    }

    @Override
    public void configure(Binder binder) {
        binder.install(new vars.jpa.InjectorModule());
        bindAnnotationService(binder);
    }

    private void bindAnnotationService(Binder binder) {
        String annoServiceName = config.getString("app.annotation.service");
        AnnotationService service;
        try {
            Class clazz = Class.forName(annoServiceName);
            service = (AnnotationService) clazz.newInstance();
        }
        catch (Exception e) {
            log.warn("Failed to initialize AnnotationService using " +
                annoServiceName + ". Using a Noop service.", e);
            service = new MockAnnotationService();
        }
        binder.bind(AnnotationService.class).toInstance(service);
    }

}
