module COMP2211 {
  requires javafx.controls;
  requires itextpdf;
  requires javafx.swing;
  requires io;
  requires layout;
  requires jakarta.xml.bind;
  requires java.sql;
  requires org.json;
  opens Group29Project.Models to jakarta.xml.bind;
  opens Group29Project.Models.FileHandling.XML to com.sun.xml.bind;
  exports Group29Project.Models.FileHandling.XML to com.sun.xml.bind.core;
  exports Group29Project;
  exports Group29Project.View;
  exports Group29Project.View.User;
}
