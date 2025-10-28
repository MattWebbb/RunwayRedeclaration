package Group29Project.View.Components;

import Group29Project.Models.Runway;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class CalculationOutput {

  public CalculationOutput(){}

  public VBox getCalculationOutput(Runway runway){

    Font bold = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 13);
    Font regular = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 13);

    VBox outputBox = new VBox(16);
    outputBox.setStyle("-fx-border-color: transparent transparent transparent black; -fx-border-width: 0 0 0 2px");
    outputBox.setPadding(new Insets(20,20,20,20));

    outputBox.setMinSize(290,775);
    outputBox.setMaxSize(290,775);

    // 1

    Label runwayLabel = new Label("Runway");
    runwayLabel.setFont(bold);
    runwayLabel.setMinWidth(70);

    Label TORALabel = new Label("TORA");
    TORALabel.setFont(bold);
    TORALabel.setMinWidth(50);

    Label TODALabel = new Label("TODA");
    TODALabel.setFont(bold);
    TODALabel.setMinWidth(50);

    Label ASDALabel = new Label("ASDA");
    ASDALabel.setFont(bold);
    ASDALabel.setMinWidth(50);

    Label LDALabel = new Label("LDA");
    LDALabel.setFont(bold);
    LDALabel.setMinWidth(50);

    HBox outputBox1 = new HBox(0, runwayLabel, TORALabel, TODALabel, ASDALabel, LDALabel);
    outputBox.getChildren().add(outputBox1);

    // 2

    Label originalValues = new Label("-------------- Original Values --------------");
    originalValues.setFont(bold);
    originalValues.setMinWidth(270);
    originalValues.setAlignment(Pos.CENTER);
    outputBox.getChildren().add(originalValues);

    // 3 -- Display original values

    Label runwayLabel1 = new Label("N/A");
    runwayLabel1.setFont(bold);
    runwayLabel1.setMinWidth(70);

    Label TORALabel1 = new Label("0m");
    TORALabel1.setFont(regular);
    TORALabel1.setMinWidth(50);

    Label TODALabel1 = new Label("0m");
    TODALabel1.setFont(regular);
    TODALabel1.setMinWidth(50);

    Label ASDALabel1 = new Label("0m");
    ASDALabel1.setFont(regular);
    ASDALabel1.setMinWidth(50);

    Label LDALabel1 = new Label("0m");
    LDALabel1.setFont(regular);
    LDALabel1.setMinWidth(50);

    if(runway != null){
      runwayLabel1.setText(runway.getLeftRunway().getDesignation());
      TORALabel1.setText(runway.getLeftRunway().getTORA() + "m");
      TODALabel1.setText(runway.getLeftRunway().getTODA() + "m");
      ASDALabel1.setText(runway.getLeftRunway().getASDA() + "m");
      LDALabel1.setText(runway.getLeftRunway().getLDA() + "m");
    }

    HBox outputBox3 = new HBox(0, runwayLabel1, TORALabel1, TODALabel1, ASDALabel1, LDALabel1);
    outputBox.getChildren().add(outputBox3);

    // 4

    Label runwayLabel2 = new Label("N/A");
    runwayLabel2.setFont(bold);
    runwayLabel2.setMinWidth(70);

    Label TORALabel2 = new Label("0m");
    TORALabel2.setFont(regular);
    TORALabel2.setMinWidth(50);

    Label TODALabel2 = new Label("0m");
    TODALabel2.setFont(regular);
    TODALabel2.setMinWidth(50);

    Label ASDALabel2 = new Label("0m");
    ASDALabel2.setFont(regular);
    ASDALabel2.setMinWidth(50);

    Label LDALabel2 = new Label("0m");
    LDALabel2.setFont(regular);
    LDALabel2.setMinWidth(50);

    if(runway != null){
      runwayLabel2.setText(runway.getRightRunway().getDesignation());
      TORALabel2.setText(runway.getRightRunway().getTORA() + "m");
      TODALabel2.setText(runway.getRightRunway().getTODA() + "m");
      ASDALabel2.setText(runway.getRightRunway().getASDA() + "m");
      LDALabel2.setText(runway.getRightRunway().getLDA() + "m");
    }

    HBox outputBox4 = new HBox(0, runwayLabel2, TORALabel2, TODALabel2, ASDALabel2, LDALabel2);
    outputBox.getChildren().add(outputBox4);

    // 5

    Label recalculatedValues = new Label("------------ Recalculated Values ------------");
    recalculatedValues.setFont(bold);
    recalculatedValues.setMinWidth(270);
    recalculatedValues.setAlignment(Pos.CENTER);
    outputBox.getChildren().add(recalculatedValues);

    // 6 -- Display new values

    Label runwayLabel3 = new Label("N/A");
    runwayLabel3.setFont(bold);
    runwayLabel3.setMinWidth(70);

    Label TORALabel3 = new Label("0m");
    TORALabel3.setFont(regular);
    TORALabel3.setMinWidth(50);

    Label TODALabel3 = new Label("0m");
    TODALabel3.setFont(regular);
    TODALabel3.setMinWidth(50);

    Label ASDALabel3 = new Label("0m");
    ASDALabel3.setFont(regular);
    ASDALabel3.setMinWidth(50);

    Label LDALabel3 = new Label("0m");
    LDALabel3.setFont(regular);
    LDALabel3.setMinWidth(50);

    if(runway != null){
      runwayLabel3.setText(runway.getLeftRunway().getDesignation());
      TORALabel3.setText(runway.getLeftRunway().getRecalculatedTORA() + "m");
      TODALabel3.setText(runway.getLeftRunway().getRecalculatedTODA() + "m");
      ASDALabel3.setText(runway.getLeftRunway().getRecalculatedASDA() + "m");
      LDALabel3.setText(runway.getLeftRunway().getRecalculatedLDA() + "m");
    }

    HBox outputBox6 = new HBox(0, runwayLabel3, TORALabel3, TODALabel3, ASDALabel3, LDALabel3);
    outputBox.getChildren().add(outputBox6);

    // 7

    Label runwayLabel4 = new Label("N/A");
    runwayLabel4.setFont(bold);
    runwayLabel4.setMinWidth(70);

    Label TORALabel4 = new Label("0m");
    TORALabel4.setFont(regular);
    TORALabel4.setMinWidth(50);

    Label TODALabel4 = new Label("0m");
    TODALabel4.setFont(regular);
    TODALabel4.setMinWidth(50);

    Label ASDALabel4 = new Label("0m");
    ASDALabel4.setFont(regular);
    ASDALabel4.setMinWidth(50);

    Label LDALabel4 = new Label("0m");
    LDALabel4.setFont(regular);
    LDALabel4.setMinWidth(50);

    if(runway != null){
      runwayLabel4.setText(runway.getRightRunway().getDesignation());
      TORALabel4.setText(runway.getRightRunway().getRecalculatedTORA() + "m");
      TODALabel4.setText(runway.getRightRunway().getRecalculatedTODA() + "m");
      ASDALabel4.setText(runway.getRightRunway().getRecalculatedASDA() + "m");
      LDALabel4.setText(runway.getRightRunway().getRecalculatedLDA() + "m");
    }

    HBox outputBox7 = new HBox(0, runwayLabel4, TORALabel4, TODALabel4, ASDALabel4, LDALabel4);
    outputBox.getChildren().add(outputBox7);

    // 8

    //Label empty = new Label("");
    //outputBox.getChildren().add(empty);

    // 9

    Label calculationBreakdown = new Label("----------- Calculation Breakdown -----------");
    calculationBreakdown.setFont(bold);
    calculationBreakdown.setMinWidth(270);
    calculationBreakdown.setAlignment(Pos.CENTER);
    outputBox.getChildren().add(calculationBreakdown);

    // 10 -- Breakdowns, 1 per tab

    Tab TORATab = new Tab("TORA");
    TORATab.setClosable(false);
    Label TORATabContent = new Label("");
    TORATabContent.setWrapText(true);
    if (runway != null) {
      ArrayList<String> TORABreakdown = runway.getTORAbreakdown();
      for (String line : TORABreakdown) {
        if ((line.contains("(Take Off Away, Landing Over)") || line.contains("(Take Off Towards, Landing Towards)")) && !TORATabContent.getText().isEmpty()) {
          TORATabContent.setText(TORATabContent.getText() + "---------------------------------------------------" + "\n" + "" + "\n");
        }
        TORATabContent.setText(TORATabContent.getText() + line + "\n");
      }
    }
    TORATab.setContent(TORATabContent);

    Tab TODATab = new Tab("TODA");
    TODATab.setClosable(false);
    Label TODATabContent = new Label("");
    TODATabContent.setWrapText(true);
    if (runway != null) {
      ArrayList<String> TODABreakdown = runway.getTODAbreakdown();
      for (String line : TODABreakdown) {
        if ((line.contains("(Take Off Away, Landing Over)") || line.contains("(Take Off Towards, Landing Towards)")) && !TODATabContent.getText().isEmpty()) {
          TODATabContent.setText(TODATabContent.getText() + "---------------------------------------------------" + "\n" + "" + "\n");
        }
        TODATabContent.setText(TODATabContent.getText() + line + "\n");
      }
    }
    TODATab.setContent(TODATabContent);

    Tab ASDATab = new Tab("ASDA");
    ASDATab.setClosable(false);
    Label ASDATabContent = new Label("");
    ASDATabContent.setWrapText(true);
    if (runway != null) {
      ArrayList<String> ASDABreakdown = runway.getASDAbreakdown();
      for (String line : ASDABreakdown) {
        if ((line.contains("(Take Off Away, Landing Over)") || line.contains("(Take Off Towards, Landing Towards)")) && !ASDATabContent.getText().isEmpty()) {
          ASDATabContent.setText(ASDATabContent.getText() + "---------------------------------------------------" + "\n" + "" + "\n");
        }
        ASDATabContent.setText(ASDATabContent.getText() + line + "\n");
      }
    }
    ASDATab.setContent(ASDATabContent);

    Tab LDATab = new Tab("LDA");
    LDATab.setClosable(false);
    Label LDATabContent = new Label("");
    LDATabContent.setWrapText(true);
    LDATabContent.setMinHeight(250);
    LDATabContent.setAlignment(Pos.TOP_LEFT);
    if (runway != null) {
      ArrayList<String> LDABreakdown = runway.getLDAbreakdown();
      for (String line : LDABreakdown) {
        if ((line.contains("(Take Off Away, Landing Over)") || line.contains("(Take Off Towards, Landing Towards)")) && !LDATabContent.getText().isEmpty()) {
          LDATabContent.setText(LDATabContent.getText() + "---------------------------------------------------" + "\n" + "" + "\n");
        }
        LDATabContent.setText(LDATabContent.getText() + line + "\n");
      }
    }

    LDATab.setContent(LDATabContent);

    TabPane calculationBreakdownTab = new TabPane(TORATab, TODATab, ASDATab, LDATab);
    calculationBreakdownTab.setMaxWidth(250);
    calculationBreakdownTab.setMinHeight(550);
    calculationBreakdownTab.setMaxHeight(550);
    outputBox.getChildren().add(calculationBreakdownTab);

    return outputBox;
  }
}
