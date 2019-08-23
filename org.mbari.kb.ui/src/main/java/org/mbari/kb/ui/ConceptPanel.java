/*
 * ConceptPanel.java
 *
 * Created on May 23, 2006, 9:16 AM
 */

package org.mbari.kb.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.mbari.kb.core.knowledgebase.Concept;
import org.mbari.kb.core.UserAccount;
import org.mbari.kb.shared.ui.FancyButton;


/**
 *
 * @author  brian
 */
public class ConceptPanel extends JPanel {


    private Concept concept;
    private UserAccount userAccount; 
    private JPanel topPanel = null;
    private JPanel centerPanel = null;
    private JLabel jLabel = null;
    private JLabel jLabel1 = null;
    private JLabel jLabel2 = null;
    private JLabel jLabel3 = null;
    private JLabel nameLabel = null;
    private JLabel userLabel = null;
    private JButton lockButton = null;
    private JLabel originatorLabel = null;
    private JLabel authorLabel = null;
    private JLabel rankLabel = null;
    private JScrollPane jScrollPane = null;
    private JTextArea referenceArea = null;
    private Icon lockedIcon;
    private Icon unlockedIcon;
    private boolean locked = true;
    
    /** Creates new form ConceptPanel */
    public ConceptPanel() {
        lockedIcon = new ImageIcon(getClass().getResource("/org/mbari/kb/ui/images/lock.png"));
        unlockedIcon = new ImageIcon(
                getClass().getResource("/org/mbari/kb/ui/images/lock_open.png"));
        initialize();
        setConcept(null);
        setUserAccount(null);
    }

    /**
     * @return  the concept
     */
    public Concept getConcept() {
        return concept;
    }

    /**
     * @param concept  the concept to set
     */
    public void setConcept(Concept concept) {
        if (concept == null) {
            nameLabel.setText("");
            authorLabel.setText(" ");
            rankLabel.setText(" ");
            referenceArea.setText(" ");
            originatorLabel.setText(" ");
        }
        else {
            String author = concept.getPrimaryConceptName().getAuthor();
            if (author == null) {
                author = " ";
            }

            String rankLevel = concept.getRankLevel();
            if (rankLevel == null) {
                rankLevel = " ";
            }

            String rankName = concept.getRankName();
            if (rankName == null) {
                rankName = " ";
            }

            String rank = rankLevel + rankName;

            String reference = concept.getReference();
            if (reference == null) {
                reference = " ";
            }

            String originator = concept.getOriginator();
            if (originator == null) {
                originator = " ";
            }

            nameLabel.setText(concept.getPrimaryConceptName().getName());
            authorLabel.setText(author);
            rankLabel.setText(rank);
            referenceArea.setText(reference);
            originatorLabel.setText(originator);
        }
        //this.repaint();
        this.concept = concept;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initialize() {
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(306, 327));
        this.add(getCenterPanel(), BorderLayout.CENTER);

        this.add(getTopPanel(), BorderLayout.NORTH);
    }

    /**
     * This method initializes topPanel	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getTopPanel() {
        if (topPanel == null) {
            topPanel = new JPanel();
            topPanel.setLayout(new BoxLayout(getTopPanel(), BoxLayout.X_AXIS));
            topPanel.add(Box.createHorizontalStrut(20));
            topPanel.add(getNameLabel(), null);
            topPanel.add(Box.createHorizontalGlue());
            topPanel.add(getUserLabel());
            topPanel.add(Box.createHorizontalStrut(4));
            topPanel.add(getLockButton(), null);
            topPanel.add(Box.createHorizontalStrut(20));
        }
        return topPanel;
    }

    /**
     * This method initializes centerPanel	
     * 	
     * @return javax.swing.JPanel	
     */
    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
            gridBagConstraints10.fill = GridBagConstraints.BOTH;
            gridBagConstraints10.gridy = 3;
            gridBagConstraints10.weightx = 1.0;
            gridBagConstraints10.weighty = 1.0;
            gridBagConstraints10.insets = new Insets(0, 4, 10, 20);
            gridBagConstraints10.gridx = 1;
            GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
            gridBagConstraints9.gridx = 1;
            gridBagConstraints9.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints9.insets = new Insets(0, 4, 4, 20);
            gridBagConstraints9.gridy = 2;
            rankLabel = new JLabel();
            rankLabel.setText("");
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.gridx = 1;
            gridBagConstraints8.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints8.insets = new Insets(0, 4, 4, 20);
            gridBagConstraints8.gridy = 1;
            authorLabel = new JLabel();
            authorLabel.setText("");
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.gridx = 1;
            gridBagConstraints7.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints7.insets = new Insets(10, 4, 4, 20);
            gridBagConstraints7.gridy = 0;
            originatorLabel = new JLabel();
            originatorLabel.setText("");
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.gridx = 0;
            gridBagConstraints3.anchor = GridBagConstraints.NORTHWEST;
            gridBagConstraints3.insets = new Insets(0, 20, 10, 0);
            gridBagConstraints3.gridy = 3;
            jLabel3 = new JLabel();
            jLabel3.setText("Reference:");
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.anchor = GridBagConstraints.WEST;
            gridBagConstraints2.insets = new Insets(0, 20, 4, 0);
            gridBagConstraints2.gridy = 2;
            jLabel2 = new JLabel();
            jLabel2.setText("Rank:");
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.insets = new Insets(0, 20, 4, 0);
            gridBagConstraints1.anchor = GridBagConstraints.WEST;
            gridBagConstraints1.gridy = 1;
            jLabel1 = new JLabel();
            jLabel1.setText("Author:");
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.anchor = GridBagConstraints.WEST;
            gridBagConstraints.insets = new Insets(10, 20, 4, 0);
            gridBagConstraints.gridy = 0;
            jLabel = new JLabel();
            jLabel.setText("Originator:");
            centerPanel = new JPanel();
            centerPanel.setLayout(new GridBagLayout());
            centerPanel.add(jLabel, gridBagConstraints);
            centerPanel.add(jLabel1, gridBagConstraints1);
            centerPanel.add(jLabel2, gridBagConstraints2);
            centerPanel.add(jLabel3, gridBagConstraints3);
            centerPanel.add(originatorLabel, gridBagConstraints7);
            centerPanel.add(authorLabel, gridBagConstraints8);
            centerPanel.add(rankLabel, gridBagConstraints9);
            centerPanel.add(getJScrollPane(), gridBagConstraints10);
        }
        return centerPanel;
    }

    /**
     * This method initializes nameLabel	
     * 	
     * @return javax.swing.JLabel	
     */
    private JLabel getNameLabel() {
        if (nameLabel == null) {
            nameLabel = new JLabel();
            nameLabel.setText("");
            nameLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
        }
        return nameLabel;
    }
    
    public JLabel getUserLabel() {
        if (userLabel == null) {
            userLabel = new JLabel();
            userLabel.setText("");
        }
        return userLabel;
    }

    /**
     * This method initializes lockButton	
     * 	
     * @return javax.swing.JButton	
     */
    public JButton getLockButton() {
        if (lockButton == null) {
            lockButton = new FancyButton();
            lockButton.setIcon(new ImageIcon(getClass().getResource("/org/mbari/kb/ui/images/lock.png")));
        }
        return lockButton;
    }

    /**
     * This method initializes jScrollPane	
     * 	
     * @return javax.swing.JScrollPane	
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getReferenceArea());
            jScrollPane.setPreferredSize(new Dimension(190, 120));
        }
        return jScrollPane;
    }

    /**
     * This method initializes referenceArea	
     * 	
     * @return javax.swing.JTextArea	
     */
    private JTextArea getReferenceArea() {
        if (referenceArea == null) {
            referenceArea = new JTextArea();
            referenceArea.setEditable(false);
        }
        return referenceArea;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        locked = userAccount == null || userAccount.isReadOnly();
        
        /*
         * Toggle the icon as appropriate
         */
        Icon lockIcon = locked ? lockedIcon : unlockedIcon;
        getLockButton().setIcon(lockIcon);
        
        String userText = "Not logged in";
        String userName = "";
        if (userAccount != null) {
            userName = userAccount.getUserName();
            userText = userName + " [" + userAccount.getRole() + "]";
        }
        getLockButton().setToolTipText(userText);
        getUserLabel().setText(userName);
    }


}  //  @jve:decl-index=0:visual-constraint="10,10"
