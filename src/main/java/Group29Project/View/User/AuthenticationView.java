package Group29Project.View.User;

import Group29Project.App;
import Group29Project.Controller.AccessController;
import Group29Project.Controller.RemoteAccessController;
import Group29Project.View.AlertHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class AuthenticationView{

  Button button = new Button();
  Button switchButton = new Button();
  Label switchText = new Label();
  VBox authenticationBox;
  TextField usernameField;
  TextField passwordField;
  TextField passwordField2;
  HBox passwordBox2;
  int userID;
  String username, password, access;
  App app;
  public AuthenticationView(){}

  public VBox getAuthentication(App app){

    this.app = app;
    RemoteAccessController.start();

    Font bold = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 40);
    Font regularL = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 20);
    Font regular = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 16);

    authenticationBox = new VBox(20);
    authenticationBox.setAlignment(Pos.CENTER);

    Label title = new Label("Runway Re-Declaration Tool");
    title.setPadding(new Insets(0,0,50,0));
    title.setAlignment(Pos.TOP_CENTER);
    title.setFont(bold);

    Label username = new Label("                                                                                           Username:");
    username.setFont(regularL);
    username.setMinWidth(120);

    usernameField = new TextField();
    usernameField.setFont(regularL);
    usernameField.setMinSize(200, 40);

    HBox usernameBox = new HBox(20);
    usernameBox.getChildren().add(username);
    usernameBox.getChildren().add(usernameField);
    usernameBox.setAlignment(Pos.CENTER_LEFT);

    Label password = new Label("                                                                                            Password:");
    password.setFont(regularL);
    password.setMinWidth(120);

    Label password2 = new Label("                                                                             Re-Enter Password:");
    password2.setFont(regularL);
    password2.setMinWidth(120);


    passwordField = new PasswordField();
    passwordField.setFont(regularL);
    passwordField.setMinSize(200, 40);

    passwordField2 = new PasswordField();
    passwordField2.setFont(regularL);
    passwordField2.setMinSize(200, 40);

    HBox passwordBox = new HBox(20);
    passwordBox.getChildren().add(password);
    passwordBox.getChildren().add(passwordField);
    passwordBox.setAlignment(Pos.CENTER_LEFT);

    passwordBox2 = new HBox(20);
    passwordBox2.getChildren().add(password2);
    passwordBox2.getChildren().add(passwordField2);
    passwordBox2.setAlignment(Pos.CENTER_LEFT);

    authenticationBox.getChildren().add(title);
    authenticationBox.getChildren().add(usernameBox);
    authenticationBox.getChildren().add(passwordBox);

    Label empty = new Label("");

    button = new Button("");
    button.setFont(regularL);
    button.setMinWidth(300);

    switchText.setFont(regular);

    switchButton = new Button("");
    switchButton.setFont(regular);
    switchButton.setMinWidth(150);
    switchButton.setBackground(null);
    switchButton.setTextFill(Paint.valueOf("#0000FF"));

    HBox switchBox = new HBox();
    switchText.setAlignment(Pos.CENTER_RIGHT);
    switchText.setMinWidth(250);
    switchButton.setAlignment(Pos.CENTER_LEFT);
    switchBox.getChildren().add(switchText);
    switchBox.getChildren().add(switchButton);
    switchBox.setAlignment(Pos.CENTER);

    authenticationBox.getChildren().add(empty);
    authenticationBox.getChildren().add(button);
    authenticationBox.getChildren().add(switchBox);

    switchToLogin();
    return authenticationBox;
  }

  public void switchToLogin(){
    authenticationBox.getChildren().remove(passwordBox2);

    usernameField.setText("");
    passwordField.setText("");
    passwordField2.setText("");

    button.setText("Log In");
    button.setOnAction(event -> {
      loginAttempt();
    });
    switchText.setText("Don't have an account?");
    switchButton.setText("Sign Up");
    switchButton.setOnAction(event -> {
      switchToSignup();
    });
  }

  public void switchToSignup(){
    authenticationBox.getChildren().add(3,passwordBox2);

    usernameField.setText("");
    passwordField.setText("");
    passwordField2.setText("");

    button.setText("Sign Up");
    button.setOnAction(event -> {
      signupAttempt();
    });
    switchText.setText("Have an account?");
    switchButton.setText("Log In");
    switchButton.setOnAction(event -> {
      switchToLogin();
    });
  }

  public void loginAttempt(){
    String username = usernameField.getText();
    String password = passwordField.getText();
    boolean successful = RemoteAccessController.authenticateUser(username, password);
    if(successful){
      int id = RemoteAccessController.getUserID(username);
      String info = RemoteAccessController.getUser(id);
      if(id != 0 && info != "fail"){
        app.username = username;
        app.id = id;
        app.data = info;
        app.loggedIn();
      }
    }
  }

  public void signupAttempt(){
    String username = usernameField.getText();
    String password = passwordField.getText();
    String password2 = passwordField2.getText();
    if (!password.equals(password2)){
      new AlertHandler("Password and re-entered password do not match.");
      return;
    }
    if (username.length() < 4){
      new AlertHandler("Invalid Value For Username: Username must be longer than 4 characters.");
      return;
    }
    if (password.length() < 8){
      new AlertHandler("Invalid Value For Password: Password must be longer than 8 characters.");
      return;
    }
    boolean successful = RemoteAccessController.insertUser(username, password, "Guest");
    if (successful){
      int id = RemoteAccessController.getUserID(username);
      String info = RemoteAccessController.getUser(id);
      if(id != 0 && info != "fail"){
        app.username = username;
        app.id = id;
        app.data = info;
        app.loggedIn();
      }
    }
  }




}
