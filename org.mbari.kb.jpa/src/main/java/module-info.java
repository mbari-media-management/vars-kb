module org.mbari.kb.jpa {
  requires gson.javatime.serialisers;
  requires com.google.gson;
  requires jasypt;
  requires java.desktop;
  requires java.instrument;
  requires java.persistence;
  requires java.prefs;
  requires java.sql;
  requires mbarix4j;
  requires org.mbari.kb.core;
  requires org.slf4j;
  requires typesafe.config;

  opens org.mbari.kb.jpa to org.eclipse.persistence.core;
  opens org.mbari.kb.jpa.knowledgebase to org.eclipse.persistence.core;
  exports org.mbari.kb.jpa.gson;
  exports org.mbari.kb.jpa.knowledgebase;
  exports org.mbari.kb.jpa;

}