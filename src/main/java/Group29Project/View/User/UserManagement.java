package Group29Project.View.User;

import Group29Project.App;
import Group29Project.Controller.AccessController;
import Group29Project.Controller.RemoteAccessController;
import Group29Project.View.Closer;
import Group29Project.View.Components.MenuTopBar;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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

public class UserManagement extends Closer {

  App app;
  MenuTopBar topBar;
  boolean dark;

  JSONArray arr;

  VBox userDataBox;

  ArrayList<AccountManagement> windows = new ArrayList<>();

  Stage userManagementWindow;
  Scene scene;

  @Override
  public void close() {
    userManagementWindow.close();
    for(AccountManagement window : windows){
      try {
        window.close();
      } catch (Exception ignored) {}
    }
  }

  @Override
  public void setDark(boolean dark){
    if (dark) {
      scene.getRoot().setStyle("-fx-base:black");
    } else {
      scene.getRoot().setStyle("");
    }
    for(AccountManagement window : windows){
      try {
        window.setDark(dark);
      } catch (Exception ignored) {}
    }
  }

  public UserManagement(){
  }

  public void getUserManagement(App app, MenuTopBar topBar, boolean dark) {

    this.app = app;
    this.topBar = topBar;
    this.dark = dark;

    StackPane primaryStackPane = new StackPane();
    scene = new Scene(primaryStackPane, 300, 400);
    if (dark) {
      scene.getRoot().setStyle("-fx-base:black");
    } else {
      scene.getRoot().setStyle("");
    }

    userManagementWindow = new Stage();
    userManagementWindow.setTitle("User Management");
    userManagementWindow.setResizable(false);
    userManagementWindow.setScene(scene);
    userManagementWindow.show();





    Label empty = new Label();
    empty.setMaxHeight(0);

    VBox userManagementBox = new VBox(11, empty);
    userManagementBox.setPadding(new Insets(10, 20, 20, 20));
    primaryStackPane.getChildren().add(userManagementBox);
    // 1

    Label userManagementLabel = new Label("  User Management");
    userManagementLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
    userManagementBox.getChildren().add(userManagementLabel);

    userDataBox = new VBox(10);
    userDataBox.setAlignment(Pos.BOTTOM_CENTER);

    ScrollPane scrollPane = new ScrollPane(userDataBox);
    scrollPane.setMinSize(300, 350);
    scrollPane.setMaxSize(300, 350);
    scrollPane.setPadding(new Insets(10,10,10,10));
    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
    userManagementBox.getChildren().add(scrollPane);

    regenList();
    }

    public void regenList(){

    String data = RemoteAccessController.getUserList();
    if (data.equals("fail")) {
      return;
    } else {
      JSONObject jsonObject = new JSONObject(data);
      arr = jsonObject.getJSONArray("data");
    }

    userDataBox.getChildren().clear();

      Font bold = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 13);
      Font regular = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 13);

      Label usernameLabel = new Label("Username");
      usernameLabel.setFont(bold);
      usernameLabel.setMinWidth(100);
      usernameLabel.setMaxWidth(100);
      Label accessLabel = new Label("Access");
      accessLabel.setFont(bold);
      accessLabel.setMinWidth(100);
      accessLabel.setMaxWidth(100);
      HBox infoBox = new HBox(usernameLabel, accessLabel);
      userDataBox.getChildren().add(infoBox);

      for (int i = 0; i < arr.length(); i++){
        int userID = app.id;
        int id = arr.getJSONObject(i).getInt("id");
        if(userID != id) {
          Label usernameLabel1 = new Label(arr.getJSONObject(i).getString("username"));
          usernameLabel1.setFont(regular);
          usernameLabel1.setMinWidth(100);
          usernameLabel1.setMaxWidth(100);
          Label accessLabel1 = new Label(arr.getJSONObject(i).getString("access"));
          accessLabel1.setFont(regular);
          accessLabel1.setMinWidth(100);
          accessLabel1.setMaxWidth(100);
          Button viewButton = new Button("View");
          viewButton.setMinWidth(60);
          viewButton.setMaxWidth(60);
          viewButton.setOnAction(event -> {
            AccountManagement accountManagement = new AccountManagement();
            accountManagement.getAccountManagement(app, id, true, topBar, dark, this);
            windows.add(accountManagement);
          });
          HBox infoBox1 = new HBox(usernameLabel1, accessLabel1, viewButton);
          userDataBox.getChildren().add(infoBox1);
        }
    }


  }
}
