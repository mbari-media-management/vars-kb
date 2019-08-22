package vars.shared.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.List;

import vars.ILink;
import vars.jpa.DevelopmentDAOFactory;
import vars.knowledgebase.ConceptCache;
import vars.knowledgebase.jpa.Factories;
import vars.shared.ui.dialogs.StandardDialog;

public class LinkSelectionPanelDemo {
	
	public static void main(String[] args) {
		Factories factories = new Factories(DevelopmentDAOFactory.newEntityManagerFactory());
		ConceptCache conceptCache = factories.getConceptCache();
		List<ILink> links = conceptCache.findLinkTemplatesFor(conceptCache.findRootConcept());
		LinkSelectionPanel panel = new LinkSelectionPanel(conceptCache);
		panel.setLinks(links);
		StandardDialog dialog = new StandardDialog();
		dialog.add(panel, BorderLayout.CENTER);
		ActionListener actionListener = e -> System.exit(0);
		dialog.getOkayButton().addActionListener(actionListener);
		dialog.getCancelButton().addActionListener(actionListener);
		dialog.pack();
		dialog.setVisible(true);
	}

}
