module org.mbari.kb.ui {

    requires com.google.gson;
    requires com.rabbitmq.client;
    requires eventbus;
    requires java.desktop;
    requires javafx.base;
    // requires mbarix4j;
    requires org.mbari.kb.core;
    requires org.mbari.kb.jpa;
    requires org.mbari.kb.shared;
    requires org.mbari.vars.core;
    requires org.mbari.vars.services;
    requires org.slf4j;
    requires swing.layout;
    requires swingx.all;
    requires typesafe.config;

}