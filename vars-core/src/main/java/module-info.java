module vars.core {
    requires java.desktop;
    requires java.persistence;
    requires java.prefs;
    requires mbarix4j;

    exports vars;
    exports vars.knowledgebase;
}