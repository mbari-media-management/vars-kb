package org.mbari.kb.core.knowledgebase;

import org.mbari.kb.core.DAO;

import java.util.Collection;

/**
 * @author brian
 */
public interface ArtifactDAO  extends DAO {

    Collection<Artifact> find(String concept, String group, String key);

    Collection<Artifact> find(String concept, String group, String key, String version);

    Collection<Artifact> find(String concept, String group, String key, String version, String classifier);

    Collection<Artifact> findByReference(String reference);

}
