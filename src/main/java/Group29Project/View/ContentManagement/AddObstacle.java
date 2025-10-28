package Group29Project.View.ContentManagement;

import Group29Project.Controller.Controller;
import Group29Project.Models.FileHandling.XMLexport;
import Group29Project.View.AlertHandler;
import Group29Project.View.Closer;
import Group29Project.View.Components.CalculationInput;
import Group29Project.View.Components.Notifications;
import java.io.File;
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

public class AddObstacle extends Closer {

  Controller controller;
  Stage addObstacleWindow;
  Scene scene;

  @Override
  public void close() {
    addObstacleWindow.close();
  }

  @Override
  public void setDark(boolean dark){
    if (dark) {
      scene.getRoot().setStyle("-fx-base:black");
    } else {
      scene.getRoot().setStyle("");
    }
  }

  public AddObstacle(Controller controller, boolean dark){

    this.controller = controller;

    StackPane primaryStackPane = new StackPane();
    var scene = new Scene(primaryStackPane, 300, 220);
    if (dark) {
      scene.getRoot().setStyle("-fx-base:black");
    } else {
      scene.getRoot().setStyle("");
    }

    addObstacleWindow = new Stage();
    addObstacleWindow.setTitle("Add Obstacle");
    addObstacleWindow.setResizable(false);
    addObstacleWindow.setScene(scene);
    addObstacleWindow.show();

    Font bold = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 13);
    Font regular = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 13);

    VBox addObstacleBox = new VBox(16);
    addObstacleBox.setPadding(new Insets(20,20,20,20));
    primaryStackPane.getChildren().add(addObstacleBox);

    // 1

    Label titleLabel = new Label("Add Obstacle");
    titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
    addObstacleBox.getChildren().add(titleLabel);

    // 2 -- Get needed inputs

    Label nameLabel = new Label("Name:");
    nameLabel.setMinWidth(100);
    nameLabel.setFont(bold);

    TextField nameInput = new TextField("");
    nameInput.setMaxWidth(160);

    HBox inputBox2 = new HBox(0, nameLabel, nameInput);
    addObstacleBox.getChildren().add(inputBox2);

    // 3

    Label heightLabel = new Label("Height (m):");
    heightLabel.setMinWidth(100);
    heightLabel.setFont(bold);

    TextField heightInput = new TextField("");
    heightInput.setMaxWidth(160);

    HBox inputBox3 = new HBox(0, heightLabel, heightInput);
    addObstacleBox.getChildren().add(inputBox3);

    // 4

    Label widthLabel = new Label("Width (m):");
    widthLabel.setMinWidth(100);
    widthLabel.setFont(bold);

    TextField widthInput = new TextField("");
    widthInput.setMaxWidth(160);

    HBox inputBox4 = new HBox(0, widthLabel, widthInput);
    addObstacleBox.getChildren().add(inputBox4);

    // 5

    Button addObstacle = new Button("Add Obstacle");
    addObstacle.setMinWidth(260);
    addObstacleBox.getChildren().add(addObstacle);

    addObstacle.setOnAction(event -> {
      attemptToAdd(nameInput.getText(), heightInput.getText(), widthInput.getText());
    });
  }


  // Run basic error checking
  public void attemptToAdd (String name, String height, String width){
    Integer heightInt;
    try {
      heightInt = Integer.parseInt(height);
    }
    catch (NumberFormatException e) {
      new AlertHandler("Invalid Value For Height: Value must be an integer.");
      return;
    }
    Integer widthInt;
    try {
      widthInt = Integer.parseInt(width);
    }
    catch (NumberFormatException e) {
      new AlertHandler("Invalid Value For Width: Value must be an integer.");
      return;
    }

    Boolean successfulAddition = controller.addObstacle(name, heightInt, widthInt);

    // Add obstacle and reset selection boxes

    if (successfulAddition){
      Notifications.addNotification("Obstacle added successfully: " + name);
      addObstacleWindow.close();
      CalculationInput.updateObstacleInformation("");
      CalculationInput.updateObstacleDropdown();

    }

  }

}
