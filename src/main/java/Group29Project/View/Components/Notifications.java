package Group29Project.View.Components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Notifications {

  static VBox notificationArea;

  static ArrayList<String> notifications = new ArrayList<>();
  public Notifications(){}
  public ScrollPane getNotification(){

    Font regular = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 13);

    // Define notification box
    notificationArea = new VBox();
    notificationArea.setAlignment(Pos.TOP_LEFT);
    Label newLabel = new Label("Notifications: \n");
    newLabel.setFont(regular);
    notificationArea.getChildren().add(newLabel);
    BorderPane.setAlignment(notificationArea, Pos.CENTER);

    // Make the box scrollable
    ScrollPane scrollPane = new ScrollPane(notificationArea);
    scrollPane.setMinSize(800, 115);
    scrollPane.setMaxSize(800, 115);
    scrollPane.setStyle("-fx-border-color:black transparent transparent transparent; -fx-border-width: 2px 0 0 0");
    scrollPane.setPadding(new Insets(10,10,10,10));
    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

    return scrollPane;
  }

  public static ArrayList<String> getNotificationList(){
    return notifications;
  }
  public static void addNotification(String notification){
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedDate = myDateObj.format(myFormatObj);

    Label newNotification = new Label(formattedDate + ": " + notification);
    newNotification.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 13));
    notifications.add(newNotification.getText());
    notificationArea.getChildren().add(newNotification);
  }
}
