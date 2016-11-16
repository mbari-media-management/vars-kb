package vars.shared.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import vars.ILink;
import vars.jpa.VarsJpaModule;
import vars.knowledgebase.ConceptCache;
import vars.shared.ui.dialogs.StandardDialog;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class LinkSelectionPanelDemo {
	
	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new VarsJpaModule("vars-jpa-knowledgebase", "vars-jpa-misc"));
		ConceptCache conceptCache = injector.getInstance(ConceptCache.class);
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
