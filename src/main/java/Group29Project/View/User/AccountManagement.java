package Group29Project.View.User;

import Group29Project.App;
import Group29Project.Controller.RemoteAccessController;
import Group29Project.Models.Logic.HashFunction;
import Group29Project.View.AlertHandler;
import Group29Project.View.Closer;
import Group29Project.View.Components.MenuTopBar;
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
import org.json.JSONArray;
import org.json.JSONObject;

public class AccountManagement extends Closer {

  String userData;

  String username = "";
  String access = "";

  Stage accountManagementWindow;
  Scene scene;


  @Override
  public void close() {
    accountManagementWindow.close();
  }

  @Override
  public void setDark(boolean dark){
    if (dark) {
      scene.getRoot().setStyle("-fx-base:black");
    } else {
      scene.getRoot().setStyle("");
    }
  }

  public AccountManagement(){
  }

  public void getAccountManagement (App app, int id, boolean roleAccess, MenuTopBar topBar, boolean dark, UserManagement userManagement) {

    StackPane primaryStackPane = new StackPane();
    scene = new Scene(primaryStackPane, 410, 220);
    if (dark) {
      scene.getRoot().setStyle("-fx-base:black");
    } else {
      scene.getRoot().setStyle("");
    }

    username = "Matt";
    access = "Staff";

    String data = RemoteAccessController.getUser(id);
    if (Objects.equals(data, "fail")){
      return;
    } else {
      JSONObject jsonObject = new JSONObject(data);
      JSONArray arr = jsonObject.getJSONArray("data");
      username = arr.getJSONObject(0).getString("username");
      access = arr.getJSONObject(0).getString("access");
    }

    accountManagementWindow = new Stage();
    accountManagementWindow.setTitle("Account");
    accountManagementWindow.setResizable(false);
    accountManagementWindow.setScene(scene);
    accountManagementWindow.show();

    Font bold = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 13);
    Font regular = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 13);

    VBox accountManagementBox = new VBox(16);
    accountManagementBox.setPadding(new Insets(20,20,20,20));
    primaryStackPane.getChildren().add(accountManagementBox);

    // 1

    Label titleLabel = new Label("Account Management");
    titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
    accountManagementBox.getChildren().add(titleLabel);

    // 2 -- Get needed inputs

    Label usernameLabel = new Label("Username:");
    usernameLabel.setMinWidth(80);
    usernameLabel.setFont(bold);

    TextField usernameInput = new TextField(username);
    usernameInput.setDisable(true);
    usernameInput.setMinWidth(160);
    usernameInput.setMaxWidth(160);

    Button editUsernameButton = new Button("Edit");
    editUsernameButton.setMinWidth(130);

    Button cancelUsernameButton = new Button("Cancel");
    cancelUsernameButton.setMinWidth(60);

    Button submitUsernameButton = new Button("Submit");
    submitUsernameButton.setMinWidth(60);

    final HBox inputBox2 = new HBox(10, usernameLabel, usernameInput, editUsernameButton);
    accountManagementBox.getChildren().add(inputBox2);

    editUsernameButton.setOnAction(event -> {
      usernameInput.setDisable(false);
      inputBox2.getChildren().remove(editUsernameButton);
      inputBox2.getChildren().add(cancelUsernameButton);
      inputBox2.getChildren().add(submitUsernameButton);
    });

    cancelUsernameButton.setOnAction(event -> {
      usernameInput.setDisable(true);
      usernameInput.setText(username);
      inputBox2.getChildren().remove(cancelUsernameButton);
      inputBox2.getChildren().remove(submitUsernameButton);
      inputBox2.getChildren().add(editUsernameButton);
    });

    submitUsernameButton.setOnAction(event -> {
      if (usernameInput.getText().length() < 4){
        new AlertHandler("Invalid Value For Username: Username must be longer than 4 characters.");
        return;
      }
      boolean successful = RemoteAccessController.updateUsername(id, usernameInput.getText());
      if (successful){
        if(!roleAccess){
          app.username = usernameInput.getText();
          topBar.updateUser(app.username);
        }
        if (userManagement != null){
          userManagement.regenList();
        }
        username = app.username;
        usernameInput.setDisable(true);
        inputBox2.getChildren().remove(cancelUsernameButton);
        inputBox2.getChildren().remove(submitUsernameButton);
        inputBox2.getChildren().add(editUsernameButton);
      }
    });

    // 3

    Label passwordLabel = new Label("Password:");
    passwordLabel.setMinWidth(80);
    passwordLabel.setFont(bold);

    TextField passwordInput = new TextField("--------");
    passwordInput.setDisable(true);
    passwordInput.setMinWidth(160);
    passwordInput.setMaxWidth(160);

    Button editPasswordButton = new Button("Edit");
    editPasswordButton.setMinWidth(130);

    Button cancelPasswordButton = new Button("Cancel");
    cancelPasswordButton.setMinWidth(60);

    Button submitPasswordButton = new Button("Submit");
    submitPasswordButton.setMinWidth(60);

    HBox inputBox3 = new HBox(10, passwordLabel, passwordInput, editPasswordButton);
    accountManagementBox.getChildren().add(inputBox3);

    editPasswordButton.setOnAction(event -> {
      passwordInput.setDisable(false);
      passwordInput.setText("");
      inputBox3.getChildren().remove(editPasswordButton);
      inputBox3.getChildren().add(cancelPasswordButton);
      inputBox3.getChildren().add(submitPasswordButton);
    });

    cancelPasswordButton.setOnAction(event -> {
      passwordInput.setDisable(true);
      passwordInput.setText("--------");
      inputBox3.getChildren().remove(cancelPasswordButton);
      inputBox3.getChildren().remove(submitPasswordButton);
      inputBox3.getChildren().add(editPasswordButton);
    });

    submitPasswordButton.setOnAction(event -> {
      if (passwordInput.getText().length() < 8){
        new AlertHandler("Invalid Value For Password: Password must be longer than 8 characters.");
        return;
      }
      boolean successful = RemoteAccessController.updatePassword(id, HashFunction.hash(passwordInput.getText()));
      if (successful){
        passwordInput.setDisable(true);
        passwordInput.setText("--------");
        inputBox3.getChildren().remove(cancelPasswordButton);
        inputBox3.getChildren().remove(submitPasswordButton);
        inputBox3.getChildren().add(editPasswordButton);
      }
    });

    // 4

    Label accessLabel = new Label("Access:");
    accessLabel.setMinWidth(80);
    accessLabel.setFont(bold);

    ComboBox<String> accessInput = new ComboBox<>();
    accessInput.getItems().add("Admin");
    accessInput.getItems().add("Staff");
    accessInput.getItems().add("Guest");
    accessInput.setDisable(true);
    accessInput.setValue(access);
    accessInput.setMinWidth(160);
    accessInput.setMaxWidth(160);

    Button editAccessButton = new Button("Edit");
    editAccessButton.setMinWidth(130);

    Button cancelAccessButton = new Button("Cancel");
    cancelAccessButton.setMinWidth(60);

    Button submitAccessButton = new Button("Submit");
    submitAccessButton.setMinWidth(60);

    HBox inputBox4 = new HBox(10, accessLabel, accessInput, editAccessButton);
    accountManagementBox.getChildren().add(inputBox4);

    editAccessButton.setOnAction(event -> {
      accessInput.setDisable(false);
      inputBox4.getChildren().remove(editAccessButton);
      inputBox4.getChildren().add(cancelAccessButton);
      inputBox4.getChildren().add(submitAccessButton);
    });

    cancelAccessButton.setOnAction(event -> {
      accessInput.setDisable(true);
      accessInput.setValue(access);
      inputBox4.getChildren().remove(cancelAccessButton);
      inputBox4.getChildren().remove(submitAccessButton);
      inputBox4.getChildren().add(editAccessButton);
    });

    submitAccessButton.setOnAction(event -> {
      boolean successful = RemoteAccessController.updateAccess(id, accessInput.getValue());
      if (successful){
        accessInput.setDisable(true);
        access = accessInput.getValue();
        if (userManagement != null){
          userManagement.regenList();
        }
        inputBox4.getChildren().remove(cancelAccessButton);
        inputBox4.getChildren().remove(submitAccessButton);
        inputBox4.getChildren().add(editAccessButton);
      }
    });

    Button deleteButton = new Button("Delete User");
    deleteButton.setMinWidth(390);
    deleteButton.setMaxWidth(390);
    accountManagementBox.getChildren().add(deleteButton);

    deleteButton.setOnAction(event -> {
      boolean successful = RemoteAccessController.deleteUser(id);
      if(successful){
        if (userManagement != null){
          userManagement.regenList();
        }
        accountManagementWindow.close();
      }
    });

    if(!roleAccess){
      editAccessButton.setDisable(true);
      deleteButton.setDisable(true);
    }

  }
}
