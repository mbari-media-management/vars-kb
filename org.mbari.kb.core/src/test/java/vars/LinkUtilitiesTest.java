package vars;

import java.util.Collection;
import java.util.List;


import org.mbari.kb.core.ILink;
import org.mbari.kb.core.LinkBean;
import org.mbari.kb.core.LinkComparator;
import org.mbari.kb.core.LinkUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LinkUtilitiesTest {

    @Test
    public void testFindLinkIn() {

        LinkComparator comparator = new LinkComparator();

        ILink link = new LinkBean("test", ILink.VALUE_SELF, ILink.VALUE_NIL);

        Collection<ILink> links = List.of(new LinkBean("test", ILink.VALUE_NIL, ILink.VALUE_NIL),
                new LinkBean("test-02", ILink.VALUE_SELF, ILink.VALUE_SELF),
                new LinkBean("test-03", ILink.VALUE_SELF, ILink.VALUE_NIL),
                new LinkBean("test-04", ILink.VALUE_SELF, ILink.VALUE_NIL),
                new LinkBean("test-05", ILink.VALUE_SELF, ILink.VALUE_NIL),
                new LinkBean("test", ILink.VALUE_SELF, ILink.VALUE_NIL), link);

        Collection<ILink> matchingLinks = LinkUtilities.findMatchingLinksIn(links, link);
        Assertions.assertTrue(matchingLinks.size() == 2, "No match was found");

    }

}
