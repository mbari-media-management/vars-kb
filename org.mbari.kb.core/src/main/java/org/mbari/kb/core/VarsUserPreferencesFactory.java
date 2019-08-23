/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.kb.core;

import java.util.prefs.Preferences;
import java.util.prefs.PreferencesFactory;

/**
 *
 * @author brian
 */
public interface VarsUserPreferencesFactory extends PreferencesFactory {

    Preferences userRoot(String userName);

}
