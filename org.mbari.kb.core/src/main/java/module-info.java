module org.mbari.kb.core {
    requires java.desktop;
    requires java.persistence;
    requires java.prefs;
    requires mbarix4j;

    exports org.mbari.kb.core;
    exports org.mbari.kb.core.knowledgebase;
}