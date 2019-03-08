package vars.knowledgebase.ui.annotation;

import org.mbari.m3.corelib.Initializer;
import org.mbari.m3.corelib.model.ConceptCount;
import org.mbari.m3.corelib.model.ConceptsRenamed;
import org.mbari.m3.corelib.util.AsyncUtils;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author Brian Schlining
 * @since 2019-03-08T14:40:00
 */
public class AnnosaurusService implements AnnotationService {

    private org.mbari.m3.corelib.services.AnnotationService annotationService =
            Initializer.getInjector()
                    .getInstance(org.mbari.m3.corelib.services.AnnotationService.class);

    @Override
    public void updateConceptUsedByAnnotations(String newConcept, Collection<String> oldConcepts) {

        Function<String, CompletableFuture<ConceptsRenamed>> fn = (oldName) ->
                annotationService.renameConcepts(oldName, newConcept);

        AsyncUtils.await(AsyncUtils.collectAll(oldConcepts, fn),
                Duration.ofSeconds(10 * oldConcepts.size()));

    }

    @Override
    public int countByConcepts(Collection<String> concepts) {
        Optional<Collection<ConceptCount>> counts = AsyncUtils.await(
                AsyncUtils.collectAll(concepts, annotationService::countObservationsByConcept),
                Duration.ofSeconds(60));

        Optional<Integer> totalObservations = counts.map(conceptCounts -> conceptCounts.stream()
                .mapToInt(ConceptCount::getCount)
                .sum());

        return totalObservations.orElse(0);
    }
}
