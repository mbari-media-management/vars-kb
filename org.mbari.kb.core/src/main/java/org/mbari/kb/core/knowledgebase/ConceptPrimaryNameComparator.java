/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.kb.core.knowledgebase;


import java.util.Comparator;

import org.mbari.kb.core.IgnoreCaseToStringComparator;

/**
 *
 * @author brian
 */
public class ConceptPrimaryNameComparator implements Comparator<Concept> {

    private Comparator<String> c = new IgnoreCaseToStringComparator();

    public int compare(Concept o1, Concept o2) {
        final String s1 = o1.getPrimaryConceptName().getName();
        final String s2 = o2.getPrimaryConceptName().getName();
        return c.compare(s1, s2);
    }

}
