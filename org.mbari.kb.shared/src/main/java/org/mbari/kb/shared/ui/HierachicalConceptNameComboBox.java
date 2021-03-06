/*
 * @(#)HierachicalConceptNameComboBox.java   2009.12.17 at 04:30:13 PST
 *
 * Copyright 2009 MBARI
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package org.mbari.kb.shared.ui;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Vector;
import javax.swing.*;

import mbarix4j.swing.SortedComboBoxModel;
import mbarix4j.swing.SpinningDialWaitIndicator;
import mbarix4j.swing.WaitIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.mbari.kb.core.knowledgebase.Concept;
import org.mbari.kb.core.knowledgebase.ConceptCache;

/**
 * <p>Displays the Concept and all it's children in the drop-down list. Names are stored internally as Strings</p>
 *
 * @author <a href="http://www.mbari.org">MBARI</a>
 * @version $Id: HierachicalConceptNameComboBox.java 265 2006-06-20 05:30:09Z hohonuuli $
 */
public class HierachicalConceptNameComboBox extends ConceptNameComboBox {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ConceptCache conceptCache;
    private Concept concept;

    /**
     * Constructs ...
     *
     *
     * @param conceptCache
     */
    public HierachicalConceptNameComboBox(ConceptCache conceptCache) {
        super();
        this.conceptCache = conceptCache;
        initialize();
    }

    /**
     *
     *
     * @param concept
     * @param conceptCache
     */
    public HierachicalConceptNameComboBox(Concept concept, ConceptCache conceptCache) {

        // WARNING!! getDescendentNames can be very slow the first time it is called.
        super();
        this.conceptCache = conceptCache;
        initialize();
        setConcept(concept);
    }

    /**
     * @return
     */
    public Concept getConcept() {
        return concept;
    }

    protected void initialize() {
        setEditable(true);
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (isPopupVisible()) {
                    return;
                }

                showPopup();
            }

        });
        setMaximumRowCount(12);

        // When focused all characters should be selected so that
        // a user can just start typing.
        addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent evt) {
                getEditor().selectAll();
            }
        });
    }

    /**
     *
     * @param concept
     */
    @SuppressWarnings("unchecked")
    public void setConcept(final Concept concept) {

        this.concept = concept;
        removeAllItems();

        if (concept == null) {
            // Do nothing
        }
        else {

            final String primaryName = concept.getPrimaryConceptName().getName();

            final SortedComboBoxModel<String> model =  (SortedComboBoxModel<String>) getModel();
            model.addElement(primaryName); // Gets removed at setItems call, but makes the comboBox look prettier in the interm


                final WaitIndicator waitIndicator = new SpinningDialWaitIndicator( HierachicalConceptNameComboBox.this);

                SwingWorker worker = new SwingWorker<Collection<String>, Void>() {

                    @Override
                    protected Collection<String> doInBackground() throws Exception {
                        return conceptCache.findDescendantNamesFor(concept);
                    }

                    @Override
                    protected void done() {
                        try {
                            model.setItems(new Vector<>(get()));
                        }
                        catch (Exception e) {
                            log.warn("Failed to lookup " + concept, e);
                        }
                        finally {
                            waitIndicator.dispose();
                        }

                        if (!model.contains(primaryName)) {
                            model.addElement(primaryName);
                        }
                    }
                };

                worker.execute();


            
        }

    }

}
