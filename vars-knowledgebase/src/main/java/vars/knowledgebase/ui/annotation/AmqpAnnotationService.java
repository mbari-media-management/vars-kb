package vars.knowledgebase.ui.annotation;

import java.util.Collection;

/**
 * @author Brian Schlining
 * @since 2018-01-17T17:03:00
 */
public class AmqpAnnotationService implements AnnotationService {

    @Override
    public void updateConceptUsedByAnnotations(String newConcept, Collection<String> oldConcepts) {

    }


    @Override
    public int countByConcepts(Collection<String> concepts) {
        return 0;
    }
}
