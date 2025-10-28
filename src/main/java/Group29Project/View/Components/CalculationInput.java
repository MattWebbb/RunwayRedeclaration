package Group29Project.View.Components;

import Group29Project.App;
import Group29Project.Controller.Controller;
import Group29Project.Models.Airport;
import Group29Project.Models.Obstacle;
import Group29Project.Models.Runway;
import Group29Project.View.AlertHandler;
import java.util.ArrayList;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class CalculationInput {

  static Controller controller;
  App app;
  static Airport selectedAirportGlobal;
  static Runway selectedRunwayGlobal;
  static Obstacle selectedObstacleGlobal;
  static ComboBox<String> airportDropdown;
  static ComboBox<String> runwayDropdown;
  static ComboBox<String> obstacleDropdown;

  static Label lengthInfoLabel;
  static Label runwaysInfoLabel1;
  static Label runwaysInfoLabel2;
  static Label thresholdInfoLabel1;
  static Label thresholdInfoLabel2;
  static Label clearwayInfoLabel1;
  static Label clearwayInfoLabel2;
  static Label stopwayInfoLabel1;
  static Label stopwayInfoLabel2;
  static Label heightInfoLabel;
  static Label widthInfoLabel;
  RadioButton left;
  RadioButton right;

  public CalculationInput(Controller controller, App app){
    this.controller = controller;
    this.app = app;
  }

  // Draw all input and info boxes

  public VBox generateCalculationInput(){
    Font bold = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 13);
    Font regular = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 13);

    VBox inputBox = new VBox(16);
    inputBox.setStyle("-fx-border-color: transparent black transparent transparent; -fx-border-width: 0 2px 0 0");
    inputBox.setPadding(new Insets(20,20,20,20));

    inputBox.setMinSize(310,775);
    inputBox.setMaxSize(310,775);

    // 1
    Label airportLabel = new Label("Airport:");
    airportLabel.setMinWidth(120);
    airportLabel.setFont(bold);

    airportDropdown = new ComboBox<>();
    airportDropdown.getItems().add("");
    airportDropdown.setValue("");
    airportDropdown.setMinWidth(150);
    updateAirportDropdown();

    airportDropdown.setOnAction(event -> {
      String selectedItem = airportDropdown.getValue();
      if (!selectedItem.equals("")) {
        Notifications.addNotification("New airport selected: " + selectedItem);
      }
      updateRunwayDropdown(selectedItem);
    });

    HBox inputBox1 = new HBox(0, airportLabel, airportDropdown);
    inputBox.getChildren().add(inputBox1);

    // 2
    Label blastProtectionLabel = new Label("Blast Protection:");
    blastProtectionLabel.setMinWidth(120);
    blastProtectionLabel.setFont(bold);

    Label blastProtectionInfoLabel = new Label("300m");
    blastProtectionInfoLabel.setFont(regular);

    HBox inputBox2 = new HBox(0, blastProtectionLabel, blastProtectionInfoLabel);
    inputBox.getChildren().add(inputBox2);

    // 3
    Label stripEndLabel = new Label("Strip End:");
    stripEndLabel.setMinWidth(120);
    stripEndLabel.setFont(bold);

    Label  stripEndInfoLabel = new Label("60m");
    stripEndInfoLabel.setFont(regular);

    HBox inputBox3 = new HBox(0,  stripEndLabel,  stripEndInfoLabel);
    inputBox.getChildren().add(inputBox3);

    // 4
    Label slopeLabel = new Label("Slope:");
    slopeLabel.setMinWidth(120);
    slopeLabel.setFont(bold);

    Label slopeInfoLabel = new Label("1:50");
    slopeInfoLabel.setFont(regular);

    HBox inputBox4 = new HBox(0, slopeLabel, slopeInfoLabel);
    inputBox.getChildren().add(inputBox4);

    // 5
    Label empty = new Label("");
    inputBox.getChildren().add(empty);

    // 6
    Label runwayLabel = new Label("Runway:");
    runwayLabel.setMinWidth(120);
    runwayLabel.setFont(bold);

    runwayDropdown = new ComboBox<>();
    runwayDropdown.setMinWidth(150);
    runwayDropdown.getItems().add("");

    runwayDropdown.setValue("");

    runwayDropdown.setOnAction(event -> {
      String selectedItem = runwayDropdown.getValue();
      if(selectedItem != null){
        if (!selectedItem.equals("")) {
          Notifications.addNotification("New runway selected: " + selectedItem);
        }
        updateRunwayInformation(selectedItem);
      }
    });

    HBox inputBox6 = new HBox(0, runwayLabel, runwayDropdown);
    inputBox.getChildren().add(inputBox6);

    // 7
    Label lengthLabel = new Label("Length:");
    lengthLabel.setMinWidth(120);
    lengthLabel.setFont(bold);

    lengthInfoLabel = new Label("0m");
    lengthInfoLabel.setFont(regular);

    HBox inputBox7 = new HBox(0, lengthLabel, lengthInfoLabel);
    inputBox.getChildren().add(inputBox7);

    // 8
    Label runwaysLabel = new Label("Designator:");
    runwaysLabel.setMinWidth(120);
    runwaysLabel.setFont(bold);

    runwaysInfoLabel1 = new Label("N/A");
    runwaysInfoLabel1.setMinWidth(75);
    runwaysInfoLabel1.setFont(bold);

    runwaysInfoLabel2 = new Label("N/A");
    runwaysInfoLabel2.setFont(bold);

    HBox inputBox8 = new HBox(0, runwaysLabel,runwaysInfoLabel1, runwaysInfoLabel2);
    inputBox.getChildren().add(inputBox8);

    // 9

    Label thresholdLabel = new Label("Threshold:");
    thresholdLabel.setMinWidth(120);
    thresholdLabel.setFont(bold);

    thresholdInfoLabel1 = new Label("0m");
    thresholdInfoLabel1.setMinWidth(75);
    thresholdInfoLabel1.setFont(regular);

    thresholdInfoLabel2 = new Label("0m");
    thresholdInfoLabel2.setFont(regular);

    HBox inputBox9 = new HBox(0, thresholdLabel,thresholdInfoLabel1, thresholdInfoLabel2);
    inputBox.getChildren().add(inputBox9);

    // 10

    Label clearwayLabel = new Label("Clearway:");
    clearwayLabel.setMinWidth(120);
    clearwayLabel.setFont(bold);

    clearwayInfoLabel1 = new Label("0m");
    clearwayInfoLabel1.setMinWidth(75);
    clearwayInfoLabel1.setFont(regular);

    clearwayInfoLabel2 = new Label("0m");
    clearwayInfoLabel2.setFont(regular);

    HBox inputBox10 = new HBox(0, clearwayLabel,clearwayInfoLabel1, clearwayInfoLabel2);
    inputBox.getChildren().add(inputBox10);

    // 11

    Label stopwayLabel = new Label("Stopway:");
    stopwayLabel.setMinWidth(120);
    stopwayLabel.setFont(bold);

    stopwayInfoLabel1 = new Label("0m");
    stopwayInfoLabel1.setMinWidth(75);
    stopwayInfoLabel1.setFont(regular);

    stopwayInfoLabel2 = new Label("0m");
    stopwayInfoLabel2.setFont(regular);

    HBox inputBox11 = new HBox(0, stopwayLabel,stopwayInfoLabel1, stopwayInfoLabel2);
    inputBox.getChildren().add(inputBox11);

    // 12

    Label empty2 = new Label("");
    inputBox.getChildren().add(empty2);

    // 13

    Label obstacleLabel = new Label("Obstacle:");
    obstacleLabel.setMinWidth(120);
    obstacleLabel.setFont(bold);

    obstacleDropdown = new ComboBox<>();
    obstacleDropdown.setMinWidth(150);
    obstacleDropdown.getItems().add("");
    obstacleDropdown.setValue("");
    updateObstacleDropdown();

    obstacleDropdown.setOnAction(event -> {
      String selectedItem = obstacleDropdown.getValue();
      if (!selectedItem.equals("")) {
        Notifications.addNotification("New obstacle selected: " + selectedItem);
      }
      updateObstacleInformation(selectedItem);
    });

    HBox inputBox13 = new HBox(0, obstacleLabel, obstacleDropdown);
    inputBox.getChildren().add(inputBox13);

    // 14

    Label heightLabel = new Label("Height:");
    heightLabel.setMinWidth(120);
    heightLabel.setFont(bold);

    heightInfoLabel = new Label("0m");
    heightInfoLabel.setFont(regular);

    HBox inputBox14 = new HBox(0, heightLabel, heightInfoLabel);
    inputBox.getChildren().add(inputBox14);

    // 15

    Label widthLabel = new Label("Width:");
    widthLabel.setMinWidth(120);
    widthLabel.setFont(bold);

    widthInfoLabel = new Label("0m");
    widthInfoLabel.setFont(regular);

    HBox inputBox15 = new HBox(0, widthLabel, widthInfoLabel);
    inputBox.getChildren().add(inputBox15);

    // 16

    Label leftOrRightLabel = new Label("(Position) Centerline:");
    leftOrRightLabel.setMinWidth(120);
    leftOrRightLabel.setFont(bold);

    ToggleGroup leftOrRight = new ToggleGroup();
    left = new RadioButton("Left");
    left.setToggleGroup(leftOrRight);
    right = new RadioButton("right");
    right.setToggleGroup(leftOrRight);

    HBox inputBox16 = new HBox(10, leftOrRightLabel, left, right);
    inputBox.getChildren().add(inputBox16);

    // 17

    Label distanceCenterlineLabel = new Label("(Distance) Centerline: (m)");
    distanceCenterlineLabel.setMinWidth(200);
    distanceCenterlineLabel.setFont(bold);

    TextField distanceCenterLineInput = new TextField("");
    distanceCenterLineInput.setMaxWidth(70);

    HBox inputBox17 = new HBox(0, distanceCenterlineLabel, distanceCenterLineInput);
    inputBox.getChildren().add(inputBox17);

    // 18

    Label distanceLeftThresholdLabel = new Label("(Distance) Left Threshold: (m)");
    distanceLeftThresholdLabel.setMinWidth(200);
    distanceLeftThresholdLabel.setFont(bold);

    TextField distanceLeftThresholdInput = new TextField("");
    distanceLeftThresholdInput.setMaxWidth(70);

    HBox inputBox18 = new HBox(0, distanceLeftThresholdLabel, distanceLeftThresholdInput);
    inputBox.getChildren().add(inputBox18);

    // 19

    Label distanceRightThresholdLabel = new Label("(Distance) Right Threshold: (m)");
    distanceRightThresholdLabel.setMinWidth(200);
    distanceRightThresholdLabel.setFont(bold);

    TextField distanceRightThresholdInput = new TextField("");
    distanceRightThresholdInput.setMaxWidth(70);

    HBox inputBox19 = new HBox(0, distanceRightThresholdLabel, distanceRightThresholdInput);
    inputBox.getChildren().add(inputBox19);

    // 20

    Button calculate = new Button("Calculate");
    calculate.setMinWidth(270);
    inputBox.getChildren().add(calculate);

    calculate.setOnAction(event -> {
      attemptToSubmit((RadioButton) leftOrRight.getSelectedToggle(), distanceCenterLineInput.getText(), distanceLeftThresholdInput.getText(), distanceRightThresholdInput.getText());
    });

    return inputBox;
  }

  // On adding a new airport

  public static void updateAirportDropdown(){
    airportDropdown.getItems().retainAll("");
    ArrayList<Airport> airportList = controller.getAirportList();
    for (Airport airport : airportList) {
      airportDropdown.getItems().add(airport.getInfo());
    }
  }

  // On adding a new obstacle

  public static void updateObstacleDropdown(){
    obstacleDropdown.getItems().retainAll("");
    ArrayList<Obstacle> obstacleList = controller.getObstacleList();
    for (Obstacle obstacle : obstacleList) {
      obstacleDropdown.getItems().add(obstacle.getName());
    }
  }

  // On adding a new airport, runway, or selecting an airport

  public static void updateRunwayDropdown(String airportString){
    runwayDropdown.getItems().retainAll("");
    ArrayList<Airport> airportList = controller.getAirportList();
    Airport selectedAirport = null;
    for (Airport airport : airportList) {
      if (airport.getInfo().equals(airportString)){
        selectedAirport = airport;
        selectedAirportGlobal = airport;
      }
    }
    if (selectedAirport != null){
      ArrayList<Runway> runwayList = selectedAirport.getRunwayList();
      for (Runway runway : runwayList){
        runwayDropdown.getItems().add(runway.getInfo());
      }
    }
  }

  // On selecting a runway

  public static void updateRunwayInformation(String runwayString){
    Runway selectedRunway = null;
    selectedRunwayGlobal = null;
    if(selectedAirportGlobal != null) {
      ArrayList<Runway> runwayList = selectedAirportGlobal.getRunwayList();
      for (Runway runway: runwayList) {
        if (runway.getInfo().equals(runwayString)){
          selectedRunway = runway;
          selectedRunwayGlobal = runway;
        }
      }
    }

    if (selectedRunway != null){
      lengthInfoLabel.setText(selectedRunway.getLength() + "m");
      runwaysInfoLabel1.setText(selectedRunway.getLeftRunway().getDesignation());
      runwaysInfoLabel2.setText(selectedRunway.getRightRunway().getDesignation());
      thresholdInfoLabel1.setText(selectedRunway.getLeftRunway().getThreshold() + "m");
      thresholdInfoLabel2.setText(selectedRunway.getRightRunway().getThreshold() + "m");
      clearwayInfoLabel1.setText(selectedRunway.getLeftRunway().getClearway() + "m");
      clearwayInfoLabel2.setText(selectedRunway.getRightRunway().getClearway() + "m");
      stopwayInfoLabel1.setText(selectedRunway.getLeftRunway().getStopway() + "m");
      stopwayInfoLabel2.setText(selectedRunway.getRightRunway().getStopway() + "m");
    } else {
      lengthInfoLabel.setText("0m");
      runwaysInfoLabel1.setText("N/A");
      runwaysInfoLabel2.setText("N/A");
      thresholdInfoLabel1.setText("0m");
      thresholdInfoLabel2.setText("0m");
      clearwayInfoLabel1.setText("0m");
      clearwayInfoLabel2.setText("0m");
      stopwayInfoLabel1.setText("0m");
      stopwayInfoLabel2.setText("0m");
    }
  }

  // On selecting an obstacle

  public static void updateObstacleInformation(String obstacleString){
    ArrayList<Obstacle> obstacleList = controller.getObstacleList();
    selectedObstacleGlobal = null;
    Obstacle selectedObstacle = null;
    for (Obstacle obstacle : obstacleList) {
      if (obstacle.getName().equals(obstacleString)){
        selectedObstacle = obstacle;
        selectedObstacleGlobal = obstacle;
      }
    }

    if(selectedObstacle != null){
      heightInfoLabel.setText(selectedObstacle.getHeight() + "m");
      widthInfoLabel.setText(selectedObstacle.getWidth() + "m");
    } else {
      heightInfoLabel.setText("0m");
      widthInfoLabel.setText("0m");
    }
  }


  // Basic error checking after submit clicked
  public void attemptToSubmit(RadioButton toggle, String center, String lThreshold, String rThreshold){


    if (this.selectedRunwayGlobal == null){
      new AlertHandler("Invalid Value For Runway: No Value Selected.");
      return;
    }

    if (selectedObstacleGlobal == null){
      new AlertHandler("Invalid Value For Obstacle: No Value Selected.");
      return;
    }

    Integer centerInt;
    try {
      centerInt = Integer.parseInt(center);
    }
    catch (NumberFormatException e) {
      new AlertHandler("Invalid Value For (Distance) Center: Value must be an integer.");
      return;
    }
    Integer lThresholdInt;
    try {
      lThresholdInt = Integer.parseInt(lThreshold);
    }
    catch (NumberFormatException e) {
      new AlertHandler("Invalid Value For (Distance) Left Threshold: Value must be an integer.");
      return;
    }
    Integer rThresholdInt;
    try {
      rThresholdInt = Integer.parseInt(rThreshold);
    }
    catch (NumberFormatException e) {
      new AlertHandler("Invalid Value For (Distance) Right Threshold: Value must be an integer.");
      return;
    }

    if (toggle == left){
      centerInt = -centerInt;
    }

    Boolean successfulCalculations = controller.runCalculations(selectedRunwayGlobal, selectedObstacleGlobal, centerInt, lThresholdInt, rThresholdInt);
    if (successfulCalculations){
      Notifications.addNotification("Successful re-declaration performed.");
      app.calculationsCompleted(selectedRunwayGlobal);
    }
  }



}
