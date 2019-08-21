module vars.jpa {
  requires java.persistence;
  requires gson;
  requires gson.javatime.serialisers;
  requires vars.core;
  requires slf4j.api;
  requires typesafe.config;
  requires java.sql;
  requires jasypt;
  requires java.prefs;
  requires mbarix4j;
}