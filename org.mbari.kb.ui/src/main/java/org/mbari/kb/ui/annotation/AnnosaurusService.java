package org.mbari.kb.ui.annotation;

import com.typesafe.config.Config;
import org.mbari.vars.core.util.AsyncUtils;
import org.mbari.vars.services.AuthService;
import org.mbari.vars.services.BasicJWTAuthService;
import org.mbari.vars.services.impl.annosaurus.v1.AnnoService;
import org.mbari.vars.services.impl.annosaurus.v1.AnnoWebServiceFactory;
import org.mbari.vars.services.model.Authorization;
import org.mbari.vars.services.model.ConceptCount;
import org.mbari.vars.services.model.ConceptsRenamed;
import org.mbari.kb.ui.Initializer;


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

    private final org.mbari.vars.services.AnnotationService annotationService;

    public AnnosaurusService() {

        Config config = Initializer.getConfig();
        String endpoint = config.getString("annotation.service.url");
        String clientSecret = config.getString("annotation.service.client.secret");
        Duration timeout = config.getDuration("annotation.service.timeout");
        AnnoWebServiceFactory factory = new AnnoWebServiceFactory(endpoint, timeout);
        AuthService authService = new BasicJWTAuthService(factory,
                new Authorization("APIKEY", clientSecret));

        this.annotationService = new AnnoService(new AnnoWebServiceFactory(endpoint, timeout),
                authService);
    }

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
