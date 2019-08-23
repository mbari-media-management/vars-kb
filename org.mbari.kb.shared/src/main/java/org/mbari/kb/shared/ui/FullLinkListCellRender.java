package org.mbari.kb.shared.ui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import org.mbari.kb.core.ILink;
import org.mbari.kb.core.LinkUtilities;

public class FullLinkListCellRender extends DefaultListCellRenderer {

    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setText(LinkUtilities.formatAsLongString((ILink) value));
        
        return this;
    }

}
