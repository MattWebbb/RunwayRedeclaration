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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
public class EditAirport extends Closer {

  Controller controller;
  Stage editAirportWindow;


  VBox editAirportBox;
  HBox addRunwayBox;
  Button leftRunwayButton;
  Button rightRunwayButton;
  Button centerRunwayButton;
  Button leftRunwayEditButton;
  Button rightRunwayEditButton;
  Button centerRunwayEditButton;

  TextField nameInput;
  TextField codeInput;

  Airport airport;

  boolean dark;
  Scene scene;

  ArrayList<EditRunway> windows = new ArrayList<>();

  @Override
  public void close() {
    editAirportWindow.close();
    for (EditRunway window : windows){
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
    for (EditRunway window : windows){
      try {
        window.setDark(dark);
      } catch (Exception ignored){}
    }
  }
  public EditAirport(Controller controller, boolean dark){

    this.dark = dark;

    this.controller = controller;

    StackPane primaryStackPane = new StackPane();
    scene = new Scene(primaryStackPane, 300, 360);
    if (dark) {
      scene.getRoot().setStyle("-fx-base:black");
    } else {
      scene.getRoot().setStyle("");
    }

    editAirportWindow = new Stage();
    editAirportWindow.setTitle("Edit Airport");
    editAirportWindow.setResizable(false);
    editAirportWindow.setScene(scene);
    editAirportWindow.show();

    Font bold = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 13);
    Font regular = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 13);

    editAirportBox = new VBox(16);
    editAirportBox.setPadding(new Insets(20, 20, 20, 20));
    primaryStackPane.getChildren().add(editAirportBox);
    // 1

    Label titleLabel = new Label("Edit Airport:");
    titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
    editAirportBox.getChildren().add(titleLabel);

    // 2

    Label airportLabel = new Label("Airport:");
    airportLabel.setMinWidth(120);
    airportLabel.setFont(bold);

    ComboBox<String> airportDropdown = new ComboBox<>();
    airportDropdown.setMinWidth(150);
    airportDropdown.getItems().add("");
    airportDropdown.setValue("");

    ArrayList<Airport> airportList = controller.getAirportList();
    for (Airport airport1 : airportList) {
      airportDropdown.getItems().add(airport1.getInfo());
    }

    HBox airportDropdownBox = new HBox(0, airportLabel, airportDropdown);
    editAirportBox.getChildren().add(airportDropdownBox);

    airportDropdown.setOnAction(event -> {
      String selectedItem = airportDropdown.getValue();
      ArrayList<Airport> airportListLookup = controller.getAirportList();
      airport = null;
      for (Airport airport : airportList) {
        if (airport.getInfo().equals(selectedItem)) {
          this.airport = airport;
        }
      }
      resetInputs();
    });

    getAirportInfo();
    resetInputs();
  }

  public void getAirportInfo(){

    Font bold = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 13);
    Font regular = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 13);

    // 2

    Label nameLabel = new Label("Name:");
    nameLabel.setMinWidth(100);
    nameLabel.setFont(bold);

    nameInput = new TextField("");
    nameInput.setMaxWidth(160);

    HBox inputBox2 = new HBox(0, nameLabel, nameInput);
    editAirportBox.getChildren().add(inputBox2);

    // 3

    Label codeLabel = new Label("Code:");
    codeLabel.setMinWidth(100);
    codeLabel.setFont(bold);

    codeInput = new TextField("");
    codeInput.setMaxWidth(160);

    HBox inputBox3 = new HBox(0, codeLabel, codeInput);
    editAirportBox.getChildren().add(inputBox3);

    // 4

    leftRunwayButton = new Button("Add Left Runway");
    leftRunwayButton.setWrapText(true);
    leftRunwayButton.setMinSize(67, 45);
    leftRunwayButton.setOnAction(event -> {
      windows.add(new EditRunway(airport, null, "L", controller, this, dark));
    });

    rightRunwayButton = new Button("Add Right Runway");
    rightRunwayButton.setWrapText(true);
    rightRunwayButton.setMinSize(67, 45);
    rightRunwayButton.setOnAction(event -> {
      windows.add(new EditRunway(airport, null, "R", controller, this, dark));
    });

    centerRunwayButton = new Button("Add Central Runway");
    centerRunwayButton.setWrapText(true);
    centerRunwayButton.setMinSize(67, 45);
    centerRunwayButton.setOnAction(event -> {
      windows.add(new EditRunway(airport, null, "C", controller, this, dark));
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
    editAirportBox.getChildren().add(addRunwayBox);


    // 5

    Button reset = new Button("Reset");
    reset.setMinWidth(260);
    editAirportBox.getChildren().add(reset);

    reset.setOnAction(event -> {
      resetInputs();
    });

    Button delete = new Button("Delete");
    delete.setMinWidth(260);
    editAirportBox.getChildren().add(delete);

    delete.setOnAction(event -> {
      attemptToDelete();
    });

    Button editAirport = new Button("Edit Airport");
    editAirport.setMinWidth(260);
    editAirportBox.getChildren().add(editAirport);

    editAirport.setOnAction(event -> {
      attemptToAdd(codeInput.getText(), nameInput.getText());
    });
  }

  public void resetInputs(){
    nameInput.setText("");
    codeInput.setText("");
    addRunwayBox.getChildren().clear();
    addRunwayBox.getChildren().add(leftRunwayButton);
    addRunwayBox.getChildren().add(centerRunwayButton);
    addRunwayBox.getChildren().add(rightRunwayButton);
    leftRunwayButton.setDisable(true);
    centerRunwayButton.setDisable(true);
    rightRunwayButton.setDisable(true);
    if (airport != null){
      nameInput.setText(airport.getName());
      codeInput.setText(airport.getCode());
      manageRunwayButtons();
    }
  }

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
          windows.add(new EditRunway(airport, runwayList.get(0), null, controller, this, dark));
        });

        centerRunwayButton.setDisable(true);
        rightRunwayButton.setDisable(false);
      } else if (Objects.equals(runwayList.get(0).getPosition(), "R")){
        addRunwayBox.getChildren().add(leftRunwayButton);
        addRunwayBox.getChildren().add(centerRunwayButton);
        addRunwayBox.getChildren().add(rightRunwayEditButton);

        rightRunwayEditButton.setOnAction(event -> {
          windows.add(new EditRunway(airport, runwayList.get(0), null, controller, this, dark));
        });

        leftRunwayButton.setDisable(false);
        centerRunwayButton.setDisable(true);
      } else if (Objects.equals(runwayList.get(0).getPosition(), "C")) {
        addRunwayBox.getChildren().add(leftRunwayButton);
        addRunwayBox.getChildren().add(centerRunwayEditButton);
        addRunwayBox.getChildren().add(rightRunwayButton);

        centerRunwayEditButton.setOnAction(event -> {
          windows.add(new EditRunway(airport, runwayList.get(0), null, controller, this, dark));
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
            windows.add(new EditRunway(airport, runwayList.get(0), null, controller, this, dark));
          });
          centerRunwayEditButton.setOnAction(event -> {
            windows.add(new EditRunway(airport, runwayList.get(1), null, controller, this, dark));
          });

          rightRunwayButton.setDisable(false);
        } else if (Objects.equals(runwayList.get(1).getPosition(), "R")) {
          addRunwayBox.getChildren().add(leftRunwayEditButton);
          addRunwayBox.getChildren().add(centerRunwayButton);
          addRunwayBox.getChildren().add(rightRunwayEditButton);

          leftRunwayEditButton.setOnAction(event -> {
            windows.add(new EditRunway(airport, runwayList.get(0), null, controller, this, dark));
          });
          rightRunwayEditButton.setOnAction(event -> {
            windows.add(new EditRunway(airport, runwayList.get(1), null, controller, this, dark));
          });
          centerRunwayButton.setDisable(false);
        }
      } else if (Objects.equals(runwayList.get(0).getPosition(), "C")){
        addRunwayBox.getChildren().add(leftRunwayButton);
        addRunwayBox.getChildren().add(centerRunwayEditButton);
        addRunwayBox.getChildren().add(rightRunwayEditButton);

        centerRunwayEditButton.setOnAction(event -> {
          windows.add(new EditRunway(airport, runwayList.get(0), null, controller, this, dark));
        });
        rightRunwayEditButton.setOnAction(event -> {
          windows.add(new EditRunway(airport, runwayList.get(1), null, controller, this, dark));
        });

        leftRunwayButton.setDisable(false);
      }
    } else {
      addRunwayBox.getChildren().add(leftRunwayEditButton);
      addRunwayBox.getChildren().add(centerRunwayEditButton);
      addRunwayBox.getChildren().add(rightRunwayEditButton);

      leftRunwayEditButton.setOnAction(event -> {
        windows.add(new EditRunway(airport, runwayList.get(0), null, controller, this, dark));
      });
      centerRunwayEditButton.setOnAction(event -> {
        windows.add(new EditRunway(airport, runwayList.get(1), null, controller, this, dark));
      });
      rightRunwayEditButton.setOnAction(event -> {
        windows.add(new EditRunway(airport, runwayList.get(2), null, controller, this, dark));
      });
    }
  }

  public void attemptToDelete(){
    if(!controller.getAirportList().contains(airport)){
      new AlertHandler("Invalid Airport: Airport no longer exists.");
      return;
    }
    controller.removeAirport(airport);
    CalculationInput.updateAirportDropdown();
    CalculationInput.updateRunwayDropdown("");
    CalculationInput.updateRunwayInformation("");
  }

  public void attemptToAdd (String code, String name){
    if(airport.getRunwayList().isEmpty()){
      new AlertHandler("Invalid Runway List: An airport cannot be created with no runways.");
      return;
    }
    if(!controller.getAirportList().contains(airport)){
      new AlertHandler("Invalid Airport: Airport no longer exists.");
      return;
    }
    Boolean successfulAdd = controller.editAirport(airport, code, name);
    if (successfulAdd){
      Notifications.addNotification("Airport edited successfully: " + name + " (" + code + ")");
      editAirportWindow.close();
      CalculationInput.updateAirportDropdown();
      CalculationInput.updateRunwayDropdown("");
      CalculationInput.updateRunwayInformation("");

    }
  }

}


