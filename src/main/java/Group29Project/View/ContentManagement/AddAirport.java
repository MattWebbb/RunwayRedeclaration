package Group29Project.View.ContentManagement;

import Group29Project.Controller.Controller;
import Group29Project.Models.Airport;
import Group29Project.Models.FileHandling.XMLexport;
import Group29Project.Models.Runway;
import Group29Project.View.AlertHandler;
import Group29Project.View.Closer;
import Group29Project.View.Components.CalculationInput;
import Group29Project.View.Components.Notifications;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
public class AddAirport extends Closer{

  Controller controller;
  Stage addAirportWindow;

  VBox addAirportBox;
  HBox addRunwayBox;
  Button leftRunwayButton;
  Button rightRunwayButton;
  Button centerRunwayButton;
  Button leftRunwayEditButton;
  Button rightRunwayEditButton;
  Button centerRunwayEditButton;

  ArrayList<AddRunway> windows = new ArrayList<>();

  Airport airport;

  boolean dark;
  Scene scene;

  public void close() {
    addAirportWindow.close();
    for (AddRunway window : windows){
      try {
        window.close();
      } catch (Exception ignored){}
    }
  }

  @Override
  public void setDark(boolean dark){
    if (dark) {
      scene.getRoot().setStyle("-fx-base:black");
    } else {
      scene.getRoot().setStyle("");
    }
    for (AddRunway window : windows){
      try {
        window.setDark(dark);
      } catch (Exception ignored){}
    }
  }

  public AddAirport(Controller controller, boolean dark){

    this.dark = dark;

    this.controller = controller;

    this.airport = controller.addAirport();

    StackPane primaryStackPane = new StackPane();
    scene = new Scene(primaryStackPane, 300, 235);
    if (dark) {
      scene.getRoot().setStyle("-fx-base:black");
    } else {
      scene.getRoot().setStyle("");
    }

    addAirportWindow = new Stage();
    addAirportWindow.setTitle("Add Airport");
    addAirportWindow.setResizable(false);
    addAirportWindow.setScene(scene);
    addAirportWindow.show();

    Font bold = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 13);
    Font regular = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 13);

    addAirportBox = new VBox(16);
    addAirportBox.setPadding(new Insets(20,20,20,20));
    primaryStackPane.getChildren().add(addAirportBox);

    // 1

    Label titleLabel = new Label("Add Airport:");
    titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
    addAirportBox.getChildren().add(titleLabel);

    // 2 -- Get relevent info

    Label nameLabel = new Label("Name:");
    nameLabel.setMinWidth(100);
    nameLabel.setFont(bold);

    TextField nameInput = new TextField("");
    nameInput.setMaxWidth(160);

    HBox inputBox2 = new HBox(0, nameLabel, nameInput);
    addAirportBox.getChildren().add(inputBox2);

    // 3

    Label codeLabel = new Label("Code:");
    codeLabel.setMinWidth(100);
    codeLabel.setFont(bold);

    TextField codeInput = new TextField("");
    codeInput.setMaxWidth(160);

    HBox inputBox3 = new HBox(0, codeLabel, codeInput);
    addAirportBox.getChildren().add(inputBox3);

    // 4 -- Create globally accessable runway buttons + edit but theyre not needed yet

    leftRunwayButton = new Button("Add Left Runway");
    leftRunwayButton.setWrapText(true);
    leftRunwayButton.setMinSize(67, 45);
    leftRunwayButton.setOnAction(event -> {
      windows.add(new AddRunway(airport, null, "L", controller, this, dark));
    });

    rightRunwayButton = new Button("Add Right Runway");
    rightRunwayButton.setWrapText(true);
    rightRunwayButton.setMinSize(67, 45);
    rightRunwayButton.setOnAction(event -> {
      windows.add(new AddRunway(airport, null, "R", controller, this, dark));
    });

    centerRunwayButton = new Button("Add Central Runway");
    centerRunwayButton.setWrapText(true);
    centerRunwayButton.setMinSize(67, 45);
    centerRunwayButton.setOnAction(event -> {
      windows.add(new AddRunway(airport, null, "C", controller, this, dark));
    });

    leftRunwayEditButton = new Button("Edit Left Runway");
    leftRunwayEditButton.setWrapText(true);
    leftRunwayEditButton.setMinSize(67, 45);

    rightRunwayEditButton = new Button("Edit Right Runway");
    rightRunwayEditButton.setWrapText(true);
    rightRunwayEditButton.setMinSize(67, 45);

    centerRunwayEditButton = new Button("Edit Central Button");
    centerRunwayEditButton.setWrapText(true);
    centerRunwayEditButton.setMinSize(67, 45);

    addRunwayBox = new HBox(20);
    addAirportBox.getChildren().add(addRunwayBox);
    manageRunwayButtons();

    // 5

    Button addAirport = new Button("Add Airport");
    addAirport.setMinWidth(260);
    addAirportBox.getChildren().add(addAirport);

    addAirport.setOnAction(event -> {
      attemptToAdd(codeInput.getText(), nameInput.getText());
    });
  }

  // Manage which runways can be created and which can be edited based on which exists

  public void manageRunwayButtons(){
    addRunwayBox.getChildren().clear();

    ArrayList<Runway> runwayList = airport.getRunwayList();

    if (runwayList.isEmpty()){
      addRunwayBox.getChildren().add(leftRunwayButton);
      addRunwayBox.getChildren().add(centerRunwayButton);
      addRunwayBox.getChildren().add(rightRunwayButton);

      leftRunwayButton.setDisable(false);
      centerRunwayButton.setDisable(true);
      rightRunwayButton.setDisable(false);
    } else if (runwayList.size() == 1){
      if (Objects.equals(runwayList.get(0).getPosition(), "L")){
        addRunwayBox.getChildren().add(leftRunwayEditButton);
        addRunwayBox.getChildren().add(centerRunwayButton);
        addRunwayBox.getChildren().add(rightRunwayButton);

        leftRunwayEditButton.setOnAction(event -> {
          windows.add(new AddRunway(airport, runwayList.get(0), null, controller, this, dark));
        });

        centerRunwayButton.setDisable(true);
        rightRunwayButton.setDisable(false);
      } else if (Objects.equals(runwayList.get(0).getPosition(), "R")){
        addRunwayBox.getChildren().add(leftRunwayButton);
        addRunwayBox.getChildren().add(centerRunwayButton);
        addRunwayBox.getChildren().add(rightRunwayEditButton);

        rightRunwayEditButton.setOnAction(event -> {
          windows.add(new AddRunway(airport, runwayList.get(0), null, controller, this, dark));
        });

        leftRunwayButton.setDisable(false);
        centerRunwayButton.setDisable(true);
      } else if (Objects.equals(runwayList.get(0).getPosition(), "C")) {
        addRunwayBox.getChildren().add(leftRunwayButton);
        addRunwayBox.getChildren().add(centerRunwayEditButton);
        addRunwayBox.getChildren().add(rightRunwayButton);

        centerRunwayEditButton.setOnAction(event -> {
          windows.add(new AddRunway(airport, runwayList.get(0), null, controller, this, dark));
        });

        leftRunwayButton.setDisable(false);
        rightRunwayButton.setDisable(false);
      }
    } else if (runwayList.size() == 2){
      if (Objects.equals(runwayList.get(0).getPosition(), "L")){
        if (Objects.equals(runwayList.get(1).getPosition(), "C")){
          addRunwayBox.getChildren().add(leftRunwayEditButton);
          addRunwayBox.getChildren().add(centerRunwayEditButton);
          addRunwayBox.getChildren().add(rightRunwayButton);

          leftRunwayEditButton.setOnAction(event -> {
            windows.add(new AddRunway(airport, runwayList.get(0), null, controller, this, dark));
          });
          centerRunwayEditButton.setOnAction(event -> {
            windows.add(new AddRunway(airport, runwayList.get(1), null, controller, this, dark));
          });

          rightRunwayButton.setDisable(false);
        } else if (Objects.equals(runwayList.get(1).getPosition(), "R")) {
          addRunwayBox.getChildren().add(leftRunwayEditButton);
          addRunwayBox.getChildren().add(centerRunwayButton);
          addRunwayBox.getChildren().add(rightRunwayEditButton);

          leftRunwayEditButton.setOnAction(event -> {
            windows.add(new AddRunway(airport, runwayList.get(0), null, controller, this, dark));
          });
          rightRunwayEditButton.setOnAction(event -> {
            windows.add(new AddRunway(airport, runwayList.get(1), null, controller, this, dark));
          });
          centerRunwayButton.setDisable(false);
        }
      } else if (Objects.equals(runwayList.get(0).getPosition(), "C")){
        addRunwayBox.getChildren().add(leftRunwayButton);
        addRunwayBox.getChildren().add(centerRunwayEditButton);
        addRunwayBox.getChildren().add(rightRunwayEditButton);

        centerRunwayEditButton.setOnAction(event -> {
          windows.add(new AddRunway(airport, runwayList.get(0), null, controller, this, dark));
        });
        rightRunwayEditButton.setOnAction(event -> {
          windows.add(new AddRunway(airport, runwayList.get(1), null, controller, this, dark));
        });

        leftRunwayButton.setDisable(false);
      }
    } else {
      addRunwayBox.getChildren().add(leftRunwayEditButton);
      addRunwayBox.getChildren().add(centerRunwayEditButton);
      addRunwayBox.getChildren().add(rightRunwayEditButton);

      leftRunwayEditButton.setOnAction(event -> {
        windows.add(new AddRunway(airport, runwayList.get(0), null, controller, this, dark));
      });
      centerRunwayEditButton.setOnAction(event -> {
        windows.add(new AddRunway(airport, runwayList.get(1), null, controller, this, dark));
      });
      rightRunwayEditButton.setOnAction(event -> {
        windows.add(new AddRunway(airport, runwayList.get(2), null, controller, this, dark));
      });
    }
  }

  // Run basic error checking

  public void attemptToAdd (String code, String name){
    if(airport.getRunwayList().isEmpty()){
      new AlertHandler("Invalid Runway List: An airport cannot be created with no runways.");
      return;
    }

    // Submit and reset selection boxes

    Boolean successfulAdd = controller.editAirport(airport, code, name);
    if (successfulAdd){
      Notifications.addNotification("Airport added successfully: " + name + " (" + code + ")");
      addAirportWindow.close();
      CalculationInput.updateAirportDropdown();
      CalculationInput.updateRunwayDropdown("");
      CalculationInput.updateRunwayInformation("");

    }
  }

}

