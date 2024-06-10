module org.mbari.kb.shared {

  requires commons.email;
  requires eventbus;
  requires io.reactivex.rxjava2;
  requires java.desktop;
  requires java.prefs;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.graphics;
  // requires mbarix4j;
//  requires org.apache.commons.mail;
  requires org.mbari.kb.core;
  requires org.mbari.kb.jpa;
  requires org.slf4j;
  requires swing.layout;
  requires swingx.all;
  requires typesafe.config;

  exports mbarix4j.awt;
  exports mbarix4j.awt.event;
  exports mbarix4j.awt.image;
  exports mbarix4j.awt.layout;
//  exports mbarix4j.concurrent;
//  exports mbarix4j.geometry;
//  exports mbarix4j.gis.util;
  exports mbarix4j.io;
  exports mbarix4j.math;
//  exports mbarix4j.model.solar;
//  exports mbarix4j.model.tidal;
//  exports mbarix4j.nativelib;
  exports mbarix4j.net;
//  exports mbarix4j.ocean;
//  exports mbarix4j.solar;
//  exports mbarix4j.sql;
  exports mbarix4j.swing;
  exports mbarix4j.swing.actions;
  exports mbarix4j.swing.table;
  exports mbarix4j.swing.text;
  exports mbarix4j.swingworker;
  exports mbarix4j.text;
  exports mbarix4j.util;
  exports mbarix4j.util.prefs;
  exports mbarix4j.util.stream;

//  opens mbarix4j.images;
//  opens mbarix4j.js;

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