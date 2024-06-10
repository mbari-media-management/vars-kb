package org.mbari.kb.core;

import java.util.Comparator;


/**
 * A camparator that compares the values returned by the <code>toString</code>
 * methods of Objects. The comparison is case-insensitive.
 *
 * @author brian
 * @version $Id: IgnoreCaseToStringComparator.java 3 2005-10-27 16:20:12Z hohonuuli $
 */
public class IgnoreCaseToStringComparator<T> implements Comparator<T> {

    /**
     *
     */
    public IgnoreCaseToStringComparator() {
    }


    /**
     * @param o1
     * @param o2
     *
     * @return
     */
    public int compare(final T o1, final T o2) {
        String s1 = o1.toString().toLowerCase();
        String s2 = o2.toString().toLowerCase();
        return s1.compareTo(s2);
    }
}
