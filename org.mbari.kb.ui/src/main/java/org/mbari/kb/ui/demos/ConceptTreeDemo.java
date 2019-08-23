/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.kb.ui.demos;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import org.jdesktop.swingx.JXTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mbari.kb.ui.Initializer;
import org.mbari.kb.ui.ToolBelt;
import org.mbari.kb.shared.ui.tree.ConceptTreeCellRenderer;
import org.mbari.kb.shared.ui.tree.ConceptTreeModel;

/**
 *
 * @author brian
 */
public class ConceptTreeDemo {

    private static final Logger log = LoggerFactory.getLogger(ConceptTreeDemo.class);

    public ConceptTreeDemo() {
    }

    public static void main(String[] args) {
        ToolBelt toolBelt = Initializer.getToolBelt();
        JFrame frame = new JFrame();
        TreeModel treeModel = new ConceptTreeModel(toolBelt.getKnowledgebaseDAOFactory());
        JXTree tree = new JXTree(treeModel);
        tree.setCellRenderer(new ConceptTreeCellRenderer());
        frame.add(tree);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



}
