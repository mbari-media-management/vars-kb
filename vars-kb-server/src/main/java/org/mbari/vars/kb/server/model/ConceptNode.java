package org.mbari.vars.kb.server.model;

import java.util.Optional;
import java.util.Set;

/**
 * @author Brian Schlining
 * @since 2016-11-11T16:30:00
 */
public class ConceptNode {
    private transient final ConceptNode parent;
    private final Set<ConceptNode> children;
    private final String name;
    private String rank;

    public ConceptNode(String name, ConceptNode parent, Set<ConceptNode> children) {
        this(name, parent, children, null);
    }

    public ConceptNode(String name, ConceptNode parent, Set<ConceptNode> children, String rank) {
        this.children = children;
        this.parent = parent;
        this.name = name;
        this.rank = rank;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if ((o == null)
                || (getClass() != o.getClass())) {
            return false;
        }

        ConceptNode that = (ConceptNode) o;

        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public Set<ConceptNode> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public Optional<ConceptNode> getParent() {
        return Optional.ofNullable(parent);
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}

