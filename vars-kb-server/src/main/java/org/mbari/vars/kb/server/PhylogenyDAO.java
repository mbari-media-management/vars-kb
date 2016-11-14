package org.mbari.vars.kb.server;

import org.mbari.vars.kb.server.model.ConceptNode;

import java.util.Optional;

/**
 * @author Brian Schlining
 * @since 2016-11-11T16:29:00
 */
public interface PhylogenyDAO {
    Optional<ConceptNode> getDown(String name);

    Optional<ConceptNode> getUp(String name);
}
