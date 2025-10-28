package Group29Project.View;

import Group29Project.App;
import Group29Project.Models.FileHandling.XMLimport;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Region;

public class AlertHandler {

  static ArrayList<AlertHandler> alertHandlers = new ArrayList<>();

  // Throw a new alert with the string passed in

  Alert newAlert;

  public  AlertHandler(String alert){

    alertHandlers.add(this);

    newAlert = new Alert(AlertType.INFORMATION);
    DialogPane dialogPane = newAlert.getDialogPane();
    if (App.dark) {
      dialogPane.setStyle("-fx-base:black");
    } else {
      dialogPane.setStyle("");
    }
    if (XMLimport.importing){
      alert = alert + " No data has been imported.";
    }
    newAlert.setHeaderText("Alert");
    newAlert.setContentText(alert);
    newAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    newAlert.show();

    newAlert.getOnCloseRequest();
  }

  public static void closeAll(){
    for (AlertHandler alertHandler : alertHandlers){
      try {
        alertHandler.newAlert.close();
      } catch (Exception ignored){}
    }
  }

  public static void setDark(boolean dark){
    for (AlertHandler alertHandler : alertHandlers){
      try {
        DialogPane dialogPane = alertHandler.newAlert.getDialogPane();
        if (App.dark) {
          dialogPane.setStyle("-fx-base:black");
        } else {
          dialogPane.setStyle("");
        }
      } catch (Exception ignored){}
    }
  }

}
