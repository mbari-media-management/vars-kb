module vars.shared.ui {
  requires eventbus;
  requires java.desktop;
  requires java.prefs;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.graphics;
  requires slf4j.api;
  requires vars.core;
  requires vars.jpa;

  exports vars.shared.awt;
  exports vars.shared.javafx.application;
  exports vars.shared.javafx.scene.control;
  exports vars.shared.javafx.scene.image;
  exports vars.shared.javafx.stage;
  exports vars.shared.preferences;
  exports vars.shared.rx.messages;
  exports vars.shared.rx;
  exports vars.shared.ui.dialogs;
  exports vars.shared.ui.event;
  exports vars.shared.ui.tree;
  exports vars.shared.ui;
  exports vars.shared.util;
}