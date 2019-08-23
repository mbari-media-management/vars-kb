/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.kb.core.knowledgebase;

/**
 *
 * @author brian
 */
public interface ConceptNameValidator<T> {

    void validateName(T object);

}