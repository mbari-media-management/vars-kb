module org.mbari.kb.shared {

  requires commons.email;
  requires eventbus;
  requires io.reactivex.rxjava2;
  requires java.desktop;
  requires java.prefs;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.graphics;
  requires mbarix4j;
  requires org.mbari.kb.core;
  requires org.mbari.kb.jpa;
  requires slf4j.api;
  requires swing.layout;
  requires swingx.all;
  requires typesafe.config;

  exports org.mbari.kb.shared.awt;
  exports org.mbari.kb.shared.javafx.application;
  exports org.mbari.kb.shared.javafx.scene.control;
  exports org.mbari.kb.shared.javafx.scene.image;
  exports org.mbari.kb.shared.javafx.stage;
  exports org.mbari.kb.shared.preferences;
  exports org.mbari.kb.shared.rx;
  exports org.mbari.kb.shared.rx.messages;
  exports org.mbari.kb.shared.ui;
  exports org.mbari.kb.shared.ui.dialogs;
  exports org.mbari.kb.shared.ui.event;
  exports org.mbari.kb.shared.ui.tree;
  exports org.mbari.kb.shared.util;
}