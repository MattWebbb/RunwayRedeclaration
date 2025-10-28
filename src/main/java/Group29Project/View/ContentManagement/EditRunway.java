package Group29Project.View.ContentManagement;

import Group29Project.Controller.Controller;
import Group29Project.Models.Airport;
import Group29Project.Models.FileHandling.XMLexport;
import Group29Project.Models.Runway;
import Group29Project.View.AlertHandler;
import Group29Project.View.Components.CalculationInput;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class EditRunway {

  Controller controller;

  String logicalRunway1;
  String logicalRunway2;
  TextField lengthInput;
  TextField headingInput;
  TextField TORAInput1;
  TextField TODAInput1;
  TextField ASDAInput1;
  TextField LDAInput1;
  TextField thresholdInput1;
  TextField clearwayInput1;
  TextField stopwayInput1;
  TextField TORAInput2;
  TextField TODAInput2;
  TextField ASDAInput2;
  TextField LDAInput2;
  TextField thresholdInput2;
  TextField clearwayInput2;
  TextField stopwayInput2;

  EditAirport parent;
  String position;
  Stage addRunwayWindow;
  Scene scene;

  public void close(){
    addRunwayWindow.close();
  }

  public void setDark(boolean dark){
    if (dark) {
      scene.getRoot().setStyle("-fx-base:black");
    } else {
      scene.getRoot().setStyle("");
    }
  }

  public EditRunway(Airport airport, Runway runway, String position, Controller controller, EditAirport airportUI, boolean dark){

    this.controller = controller;
    this.parent = airportUI;
    this.position = position;

    if(runway != null){
      logicalRunway1 = runway.getLeftRunway().getDesignation();
      logicalRunway2 = runway.getRightRunway().getDesignation();
    } else {
      if (Objects.equals(position, "L")){
        logicalRunway1 = ("Left Logical Runway");
        logicalRunway2 = ("Right Logical Runway");
      } else if (Objects.equals(position, "R")){
        logicalRunway1 = ("Right Logical Runway");
        logicalRunway2 = ("Left Logical Runway");
      } else {
        logicalRunway1 = ("Central Logical Runway");
        logicalRunway2 = ("Central Logical Runway");
      }
    }

    StackPane primaryStackPane = new StackPane();
    if (runway == null){
      scene = new Scene(primaryStackPane, 460, 545);
    } else {
      scene = new Scene(primaryStackPane, 460, 570);
    }
    if (dark) {
      scene.getRoot().setStyle("-fx-base:black");
    } else {
      scene.getRoot().setStyle("");
    }


    addRunwayWindow = new Stage();
    addRunwayWindow.setTitle("Add Runway");
    addRunwayWindow.setResizable(false);
    addRunwayWindow.setScene(scene);
    addRunwayWindow.show();

    Font bold = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 13);
    Font regular = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 13);

    VBox addRunwayBox = new VBox(16);
    addRunwayBox.setPadding(new Insets(20,20,20,20));
    primaryStackPane.getChildren().add(addRunwayBox);

    // 1
    Label titleLabel;
    if (runway != null){
      titleLabel = new Label("Edit Runway: " + runway.getInfo());
    } else {
      if (Objects.equals(position, "L")){
        titleLabel = new Label("Add Left Runway:");
      } else if (Objects.equals(position, "R")){
        titleLabel = new Label("Add Right Runway:");
      } else {
        titleLabel = new Label("Add Central Runway:");
      }
    }
    titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
    addRunwayBox.getChildren().add(titleLabel);

    // 2

    Label lengthLabel = new Label("Length:");
    lengthLabel.setMinWidth(100);
    lengthLabel.setFont(bold);

    lengthInput = new TextField("");
    lengthInput.setMaxWidth(160);

    HBox inputBox2 = new HBox(0, lengthLabel, lengthInput);
    addRunwayBox.getChildren().add(inputBox2);

    // 3

    Label headingLabel = new Label("Heading:");
    headingLabel.setMinWidth(100);
    headingLabel.setFont(bold);

    headingInput = new TextField("");
    headingInput.setMaxWidth(160);

    HBox inputBox3 = new HBox(0, headingLabel, headingInput);
    addRunwayBox.getChildren().add(inputBox3);

    // 4

    VBox logicalRunway1Box = new VBox(16);

    Label logicalRunway1Label = new Label(logicalRunway1 + ":");
    logicalRunway1Label.setMinWidth(200);
    logicalRunway1Label.setFont(bold);
    logicalRunway1Box.getChildren().add(logicalRunway1Label);

    // 5

    Label TORALabel1 = new Label("TORA (m):");
    TORALabel1.setMinWidth(100);
    TORALabel1.setFont(bold);

    TORAInput1 = new TextField("");

    TORAInput1.setMaxWidth(100);

    HBox inputBox5 = new HBox(TORALabel1, TORAInput1);
    logicalRunway1Box.getChildren().add(inputBox5);

    // 6

    Label TODALabel1 = new Label("TODA (m):");
    TODALabel1.setMinWidth(100);
    TODALabel1.setFont(bold);

    TODAInput1 = new TextField("");
    TODAInput1.setMaxWidth(100);

    HBox inputBox6 = new HBox(TODALabel1, TODAInput1);
    logicalRunway1Box.getChildren().add(inputBox6);

    // 7

    Label ASDALabel1 = new Label("ASDA (m):");
    ASDALabel1.setMinWidth(100);
    ASDALabel1.setFont(bold);

    ASDAInput1 = new TextField("");
    ASDAInput1.setMaxWidth(100);

    HBox inputBox7 = new HBox(ASDALabel1, ASDAInput1);
    logicalRunway1Box.getChildren().add(inputBox7);

    // 8

    Label LDALabel1 = new Label("LDA (m):");
    LDALabel1.setMinWidth(100);
    LDALabel1.setFont(bold);

    LDAInput1 = new TextField("");
    LDAInput1.setMaxWidth(100);

    HBox inputBox8 = new HBox(LDALabel1, LDAInput1);
    logicalRunway1Box.getChildren().add(inputBox8);

    // 9

    Label thresholdLabel1 = new Label("Threshold (m):");
    thresholdLabel1.setMinWidth(100);
    thresholdLabel1.setFont(bold);

    thresholdInput1 = new TextField("");
    thresholdInput1 .setMaxWidth(100);

    HBox inputBox9 = new HBox(thresholdLabel1 , thresholdInput1);
    logicalRunway1Box.getChildren().add(inputBox9);

    // 10

    Label clearwayLabel1 = new Label("Clearway (m):");
    clearwayLabel1.setMinWidth(100);
    clearwayLabel1.setFont(bold);

    clearwayInput1 = new TextField("");
    clearwayInput1.setMaxWidth(100);

    HBox inputBox10 = new HBox(clearwayLabel1, clearwayInput1);
    logicalRunway1Box.getChildren().add(inputBox10);

    // 11

    Label stopwayLabel1 = new Label("Stopway (m):");
    stopwayLabel1 .setMinWidth(100);
    stopwayLabel1 .setFont(bold);

    stopwayInput1 = new TextField("");
    stopwayInput1.setMaxWidth(100);

    HBox inputBox11 = new HBox(stopwayLabel1, stopwayInput1);
    logicalRunway1Box.getChildren().add(inputBox11);

    // Logical runway 2

    VBox logicalRunway2Box = new VBox(16);

    Label logicalRunway2Label = new Label(logicalRunway2 + ":");
    logicalRunway2Label.setMinWidth(200);
    logicalRunway2Label.setFont(bold);
    logicalRunway2Box.getChildren().add(logicalRunway2Label);

    // 12

    Label TORALabel2 = new Label("TORA (m):");
    TORALabel2.setMinWidth(100);
    TORALabel2.setFont(bold);

    TORAInput2 = new TextField("");
    TORAInput2.setMaxWidth(100);

    HBox inputBox12 = new HBox(TORALabel2, TORAInput2);
    logicalRunway2Box.getChildren().add(inputBox12);

    // 13

    Label TODALabel2 = new Label("TODA (m):");
    TODALabel2.setMinWidth(100);
    TODALabel2.setFont(bold);

    TODAInput2 = new TextField("");
    TODAInput2.setMaxWidth(100);

    HBox inputBox13 = new HBox(TODALabel2, TODAInput2);
    logicalRunway2Box.getChildren().add(inputBox13);

    // 14

    Label ASDALabel2 = new Label("ASDA (m):");
    ASDALabel2.setMinWidth(100);
    ASDALabel2.setFont(bold);

    ASDAInput2 = new TextField("");
    ASDAInput2.setMaxWidth(100);

    HBox inputBox14 = new HBox(ASDALabel2, ASDAInput2);
    logicalRunway2Box.getChildren().add(inputBox14);

    // 15

    Label LDALabel2 = new Label("LDA (m):");
    LDALabel2.setMinWidth(100);
    LDALabel2.setFont(bold);

    LDAInput2 = new TextField("");
    LDAInput2.setMaxWidth(100);

    HBox inputBox15 = new HBox(LDALabel2, LDAInput2);
    logicalRunway2Box.getChildren().add(inputBox15);

    // 16

    Label thresholdLabel2 = new Label("Threshold (m):");
    thresholdLabel2.setMinWidth(100);
    thresholdLabel2.setFont(bold);

    thresholdInput2 = new TextField("");
    thresholdInput2 .setMaxWidth(100);

    HBox inputBox16 = new HBox(thresholdLabel2 , thresholdInput2);
    logicalRunway2Box.getChildren().add(inputBox16);

    // 17

    Label clearwayLabel2 = new Label("Clearway (m):");
    clearwayLabel2.setMinWidth(100);
    clearwayLabel2.setFont(bold);

    clearwayInput2 = new TextField("");
    clearwayInput2.setMaxWidth(100);

    HBox inputBox17 = new HBox(clearwayLabel2, clearwayInput2);
    logicalRunway2Box.getChildren().add(inputBox17);

    // 18

    Label stopwayLabel2 = new Label("Stopway (m):");
    stopwayLabel2 .setMinWidth(100);
    stopwayLabel2 .setFont(bold);

    stopwayInput2 = new TextField("");
    stopwayInput2.setMaxWidth(100);

    HBox inputBox18 = new HBox(stopwayLabel2, stopwayInput2);
    logicalRunway2Box.getChildren().add(inputBox18);

    resetRunwayInfo(runway);

    Separator separator = new Separator(Orientation.VERTICAL);
    HBox logicalRunwayBox = new HBox(10, logicalRunway1Box, separator, logicalRunway2Box);
    addRunwayBox.getChildren().add(logicalRunwayBox);

    // 19

    Button reset = new Button("Reset");
    reset.setMinWidth(420);
    reset.setOnAction(event -> {
      resetRunwayInfo(runway);
    });
    addRunwayBox.getChildren().add(reset);

    // 20

    Button delete = new Button("Delete Runway");
    delete.setMinWidth(420);
    delete.setOnAction(event -> {
      controller.removeRunway(airport, runway);
      parent.manageRunwayButtons();
      addRunwayWindow.close();
    });
    if (runway != null){
      addRunwayBox.getChildren().add(delete);
    }

    // 21

    Button submit = new Button("Create New Runway");
    if (runway != null){
      submit.setText("Edit Runway");
    }
    submit.setMinWidth(420);
    submit.setOnAction(event -> {
      attemptToSubmit(controller, airport, runway, lengthInput.getText(), headingInput.getText(), TORAInput1.getText(), TODAInput1.getText(), ASDAInput1.getText(), LDAInput1.getText(), thresholdInput1.getText(), clearwayInput1.getText(), stopwayInput1.getText(), TORAInput2.getText(), TODAInput2.getText(), ASDAInput2.getText(), LDAInput2.getText(), thresholdInput2.getText(), clearwayInput2.getText(), stopwayInput2.getText());
    });
    addRunwayBox.getChildren().add(submit);

  }


  private void resetRunwayInfo(Runway runway){
    if (runway != null){
      lengthInput.setText(runway.getLength().toString());
    }
    if (runway != null){
      headingInput.setText(runway.getDegree().toString());
    }
    if (runway != null){
      TORAInput1.setText(runway.getLeftRunway().getTORA().toString());
    }
    if (runway != null){
      TODAInput1.setText(runway.getLeftRunway().getTODA().toString());
    }
    if (runway != null){
      ASDAInput1.setText(runway.getLeftRunway().getASDA().toString());
    }
    if (runway != null){
      LDAInput1.setText(runway.getLeftRunway().getLDA().toString());
    }
    if (runway != null){
      thresholdInput1.setText(runway.getLeftRunway().getThreshold().toString());
    }
    if (runway != null){
      clearwayInput1.setText(runway.getLeftRunway().getClearway().toString());
    }
    if (runway != null){
      stopwayInput1.setText(runway.getLeftRunway().getStopway().toString());
    }
    if (runway != null){
      TORAInput2.setText(runway.getRightRunway().getTORA().toString());
    }
    if (runway != null){
      TODAInput2.setText(runway.getRightRunway().getTODA().toString());
    }
    if (runway != null){
      ASDAInput2.setText(runway.getRightRunway().getASDA().toString());
    }
    if (runway != null){
      LDAInput2.setText(runway.getRightRunway().getLDA().toString());
    }
    if (runway != null){
      thresholdInput2.setText(runway.getRightRunway().getThreshold().toString());
    }
    if (runway != null){
      clearwayInput2.setText(runway.getRightRunway().getClearway().toString());
    }
    if (runway != null){
      stopwayInput2.setText(runway.getRightRunway().getStopway().toString());
    }
  }

  public void attemptToSubmit(Controller controller, Airport airport, Runway runway, String length, String heading, String TORA1, String TODA1, String ASDA1, String LDA1, String threshold1, String clearway1, String stopway1, String TORA2, String TODA2, String ASDA2, String LDA2, String threshold2, String clearway2, String stopway2){
    Integer lengthInt;
    try {
      lengthInt = Integer.parseInt(length);
    }
    catch (NumberFormatException e) {
      new AlertHandler("Invalid Value For Length: Value must be an integer.");
      return;
    }
    Integer headingInt;
    try {
      headingInt = Integer.parseInt(heading);
    }
    catch (NumberFormatException e) {
      new AlertHandler("Invalid Value For Heading: Value must be an integer.");
      return;
    }
    Integer TORA1Int;
    Integer TORA2Int;
    try {
      TORA1Int = Integer.parseInt(TORA1);
      TORA2Int = Integer.parseInt(TORA2);
    }
    catch (NumberFormatException e) {
      new AlertHandler("Invalid Value For TORA: Value must be an integer.");
      return;
    }
    Integer TODA1Int;
    Integer TODA2Int;
    try {
      TODA1Int = Integer.parseInt(TODA1);
      TODA2Int = Integer.parseInt(TODA2);
    }
    catch (NumberFormatException e) {
      new AlertHandler("Invalid Value For TODA: Value must be an integer.");
      return;
    }
    Integer ASDA1Int;
    Integer ASDA2Int;
    try {
      ASDA1Int = Integer.parseInt(ASDA1);
      ASDA2Int = Integer.parseInt(ASDA2);
    }
    catch (NumberFormatException e) {
      new AlertHandler("Invalid Value For ASDA: Value must be an integer.");
      return;
    }
    Integer LDA1Int;
    Integer LDA2Int;
    try {
      LDA1Int = Integer.parseInt(LDA1);
      LDA2Int = Integer.parseInt(LDA2);
    }
    catch (NumberFormatException e) {
      new AlertHandler("Invalid Value For LDA: Value must be an integer.");
      return;
    }
    Integer threshold1Int;
    Integer threshold2Int;
    try {
      threshold1Int = Integer.parseInt(threshold1);
      threshold2Int = Integer.parseInt(threshold2);
    }
    catch (NumberFormatException e) {
      new AlertHandler("Invalid Value For Threshold: Value must be an integer.");
      return;
    }
    Integer clearway1Int;
    Integer clearway2Int;
    try {
      clearway1Int = Integer.parseInt(clearway1);
      clearway2Int = Integer.parseInt(clearway2);
    }
    catch (NumberFormatException e) {
      new AlertHandler("Invalid Value For Clearway: Value must be an integer.");
      return;
    }
    Integer stopway1Int;
    Integer stopway2Int;
    try {
      stopway1Int = Integer.parseInt(stopway1);
      stopway2Int = Integer.parseInt(stopway2);
    }
    catch (NumberFormatException e) {
      new AlertHandler("Invalid Value For Stopway: Value must be an integer.");
      return;
    }

    if (runway == null){
      ArrayList<Runway> runwayList = airport.getRunwayList();
      for (Runway currentRunway : runwayList){
        if (Objects.equals(currentRunway.getPosition(), this.position)){
          new AlertHandler("Invalid Runway: Runway already exists.");
          return;
        }
      }
      Boolean successfulAddition = controller.createRunway(airport, lengthInt, headingInt, this.position, TORA1Int, TODA1Int, ASDA1Int, LDA1Int, threshold1Int, clearway1Int, stopway1Int, TORA2Int, TODA2Int, ASDA2Int, LDA2Int, threshold2Int, clearway2Int, stopway2Int);
      if (successfulAddition){
        addRunwayWindow.close();
        parent.manageRunwayButtons();
        CalculationInput.updateAirportDropdown();
        CalculationInput.updateRunwayDropdown("");
        CalculationInput.updateRunwayInformation("");
      }
    } else {
      if (!airport.getRunwayList().contains(runway)){
        new AlertHandler("Invalid Runway: Runway no longer exists.");
        return;
      }
      Boolean successfulEdit = controller.editRunway(runway, airport, lengthInt, headingInt, runway.getPosition(), TORA1Int, TODA1Int, ASDA1Int, LDA1Int, threshold1Int, clearway1Int, stopway1Int, TORA2Int, TODA2Int, ASDA2Int, LDA2Int, threshold2Int, clearway2Int, stopway2Int);
      if (successfulEdit) {
        addRunwayWindow.close();
        parent.manageRunwayButtons();
        CalculationInput.updateAirportDropdown();
        CalculationInput.updateRunwayDropdown("");
        CalculationInput.updateRunwayInformation("");
      }
    }
  }
}

