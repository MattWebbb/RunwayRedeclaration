package Group29Project.View.ContentManagement;

import Group29Project.Controller.Controller;
import Group29Project.Models.FileHandling.XMLexport;
import Group29Project.Models.Obstacle;
import Group29Project.View.AlertHandler;
import Group29Project.View.Closer;
import Group29Project.View.Components.CalculationInput;
import Group29Project.View.Components.Notifications;
import java.io.File;
import java.util.ArrayList;
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

public class EditObstacle extends Closer {

  Controller controller;
  Stage editObstacleWindow;
  VBox editObstacleBox;
  Obstacle selectedObstacle;

  HBox obstacleDropdownBox;

  Label titleLabel;

  TextField nameInput;
  TextField heightInput;
  TextField widthInput;
  Scene scene;

  @Override
  public void close() {
    editObstacleWindow.close();
  }

  @Override
  public void setDark(boolean dark){
    if (dark) {
      scene.getRoot().setStyle("-fx-base:black");
    } else {
      scene.getRoot().setStyle("");
    }
  }


  public EditObstacle(Controller controller, boolean dark) {

    this.controller = controller;

    StackPane primaryStackPane = new StackPane();
    scene = new Scene(primaryStackPane, 300, 330);
    if (dark) {
      scene.getRoot().setStyle("-fx-base:black");
    } else {
      scene.getRoot().setStyle("");
    }

    editObstacleWindow = new Stage();
    editObstacleWindow.setTitle("Edit Obstacle");
    editObstacleWindow.setResizable(false);
    editObstacleWindow.setScene(scene);
    editObstacleWindow.show();

    Font bold = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 13);
    Font regular = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 13);

    editObstacleBox = new VBox(16);
    editObstacleBox.setPadding(new Insets(20, 20, 20, 20));
    primaryStackPane.getChildren().add(editObstacleBox);

    // 1

    titleLabel = new Label("Edit Obstacle");
    titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
    editObstacleBox.getChildren().add(titleLabel);

    Label obstacleLabel = new Label("Obstacle:");
    obstacleLabel.setMinWidth(120);
    obstacleLabel.setFont(bold);

    ComboBox<String> obstacleDropdown = new ComboBox<>();
    obstacleDropdown.setMinWidth(150);
    obstacleDropdown.getItems().add("");
    obstacleDropdown.setValue("");

    ArrayList<Obstacle> obstacleList = controller.getObstacleList();
    for (Obstacle obstacle : obstacleList) {
      obstacleDropdown.getItems().add(obstacle.getName());
    }

    obstacleDropdownBox = new HBox(0, obstacleLabel, obstacleDropdown);
    editObstacleBox.getChildren().add(obstacleDropdownBox);

    obstacleDropdown.setOnAction(event -> {
      String selectedItem = obstacleDropdown.getValue();
      ArrayList<Obstacle> obstacleListLookup = controller.getObstacleList();
      selectedObstacle = null;
      for (Obstacle obstacle : obstacleListLookup) {
        if (obstacle.getName().equals(selectedItem)) {
          selectedObstacle = obstacle;
        }
      }
      resetInputs(selectedObstacle);
    });

    getObstacleInfo();

  }
  private void getObstacleInfo() {



    Font bold = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 13);
    Font regular = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 13);

    // 2

    Label nameLabel = new Label("Name:");
    nameLabel.setMinWidth(100);
    nameLabel.setFont(bold);

    nameInput = new TextField("");
    nameInput.setMaxWidth(160);

    HBox inputBox2 = new HBox(0, nameLabel, nameInput);
    editObstacleBox.getChildren().add(inputBox2);

    // 3

    Label heightLabel = new Label("Height:");
    heightLabel.setMinWidth(100);
    heightLabel.setFont(bold);

    heightInput = new TextField("");
    heightInput.setMaxWidth(160);

    HBox inputBox3 = new HBox(0, heightLabel, heightInput);
    editObstacleBox.getChildren().add(inputBox3);

    // 4

    Label widthLabel = new Label("Width:");
    widthLabel.setMinWidth(100);
    widthLabel.setFont(bold);

    widthInput = new TextField("");
    widthInput.setMaxWidth(160);

    HBox inputBox4 = new HBox(0, widthLabel, widthInput);
    editObstacleBox.getChildren().add(inputBox4);

    // 5

    Button reset = new Button("Reset");
    reset.setMinWidth(260);
    editObstacleBox.getChildren().add(reset);

    // 6

    Button delete = new Button("Delete Obstacle");
    delete.setMinWidth(260);
    editObstacleBox.getChildren().add(delete);

    // 7

    Button editObstacle = new Button("Edit Obstacle");
    editObstacle.setMinWidth(260);
    editObstacleBox.getChildren().add(editObstacle);

    editObstacle.setOnAction(event -> {
      attemptToEdit(nameInput.getText(), heightInput.getText(), widthInput.getText());
    });

    delete.setOnAction(event -> {
      attemptToDelete();
    });

    reset.setOnAction(event -> {
      resetInputs(selectedObstacle);
    });
  }

  public void resetInputs(Obstacle selectedObstacle){
    nameInput.setText("");
    heightInput.setText("");
    widthInput.setText("");
    if (selectedObstacle != null) {
      nameInput.setText(selectedObstacle.getName());
    }
    if (selectedObstacle != null) {
      heightInput.setText(selectedObstacle.getHeight().toString());
    }
    if (selectedObstacle != null) {
      widthInput.setText(selectedObstacle.getWidth().toString());
    }
  }

  public void attemptToDelete(){
    if (selectedObstacle == null){
      new AlertHandler("Invalid Obstacle: No obstacle selected.");
      return;
    }
    controller.removeObstacle(selectedObstacle);
    editObstacleWindow.close();
    CalculationInput.updateObstacleInformation("");
    CalculationInput.updateObstacleDropdown();
  }

  public void attemptToEdit (String name, String height, String width){
    if (selectedObstacle == null){
      new AlertHandler("Invalid Obstacle: No obstacle selected.");
      return;
    }
    if (!controller.getObstacleList().contains(selectedObstacle)){
      new AlertHandler("Invalid Obstacle: Obstacle no longer exists.");
      return;
    }
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

    Boolean successfulEdit = controller.editObstacle(selectedObstacle, name, heightInt, widthInt);

    if (successfulEdit){
      Notifications.addNotification("Obstacle edited successfully: " + name);
      editObstacleWindow.close();
      CalculationInput.updateObstacleInformation("");
      CalculationInput.updateObstacleDropdown();

    }

  }

}
