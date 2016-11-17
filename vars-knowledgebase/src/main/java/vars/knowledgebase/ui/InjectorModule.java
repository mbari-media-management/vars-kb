package vars.knowledgebase.ui;

import com.google.inject.Binder;
import com.google.inject.Module;
import vars.knowledgebase.ui.annotation.AnnotationService;
import vars.knowledgebase.ui.annotation.MockAnnotationService;

/**
 * @author Brian Schlining
 * @since 2016-11-16T15:58:00
 */
public class InjectorModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.install(new vars.jpa.InjectorModule());
        binder.bind(AnnotationService.class).to(MockAnnotationService.class);
    }
}
