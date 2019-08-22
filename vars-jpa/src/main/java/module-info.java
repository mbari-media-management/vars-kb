module vars.jpa {
  requires gson.javatime.serialisers;
  requires gson;
  requires jasypt;
  requires java.persistence;
  requires java.prefs;
  requires java.sql;
  requires mbarix4j;
  requires slf4j.api;
  requires typesafe.config;
  requires vars.core;

  exports vars.gson;
  exports vars.jpa;
  exports vars.knowledgebase.jpa;

}