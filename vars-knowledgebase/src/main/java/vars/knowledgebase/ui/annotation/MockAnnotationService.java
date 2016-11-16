package vars.knowledgebase.ui.annotation;

import java.util.Collection;

/**
 * @author Brian Schlining
 * @since 2016-11-15T15:00:00
 */
public class MockAnnotationService implements AnnotationService {

    @Override
    public void updateConceptUsedByAnnotations(String newConcept, Collection<String> oldConcepts) {

    }

    @Override
    public void deleteAnnotationsByConcept(String concept) {

    }

    @Override
    public int countByConcepts(Collection<String> concepts) {
        return 0;
    }
}
