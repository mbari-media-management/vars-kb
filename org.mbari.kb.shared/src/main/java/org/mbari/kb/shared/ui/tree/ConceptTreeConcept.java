/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.kb.shared.ui.tree;


import org.mbari.kb.core.knowledgebase.ConceptNameTypes;
import org.mbari.kb.core.knowledgebase.SimpleConceptBean;
import org.mbari.kb.core.knowledgebase.SimpleConceptNameBean;

/**
 *
 * @author brian
 */
public class ConceptTreeConcept extends SimpleConceptBean {

    public ConceptTreeConcept(String primaryConceptName) {
        addConceptName(new SimpleConceptNameBean(primaryConceptName, ConceptNameTypes.PRIMARY.getName()));
    }

}
