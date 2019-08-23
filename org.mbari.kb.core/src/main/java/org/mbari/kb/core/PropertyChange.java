/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.kb.core;

import java.beans.PropertyChangeListener;


/**
 * Interface defining classes that have property change listeners
 */
public interface PropertyChange {

    void addPropertyChangeListener(String string, PropertyChangeListener listener);

    void removePropertyChangeListener(String string, PropertyChangeListener listener);

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);

    PropertyChangeListener[] getPropertyChangeListeners();

    PropertyChangeListener[] getPropertyChangeListeners(String string);


}
