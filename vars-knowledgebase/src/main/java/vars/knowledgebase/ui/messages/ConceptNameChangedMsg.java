package vars.knowledgebase.ui.messages;

import java.util.List;

/**
 * @author Brian Schlining
 * @since 2018-01-17T16:49:00
 */
public class ConceptNameChangedMsg implements Message {
    private final String newName;
    private final List<String> oldNames;

    public ConceptNameChangedMsg(String newName, List<String> oldNames) {
        this.newName = newName;
        this.oldNames = oldNames;
    }

    public String getNewName() {
        return newName;
    }

    public List<String> getOldNames() {
        return oldNames;
    }
}
