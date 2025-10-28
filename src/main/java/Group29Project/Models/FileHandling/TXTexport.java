package Group29Project.Models.FileHandling;

import Group29Project.View.AlertHandler;
import Group29Project.View.Components.Notifications;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class TXTexport {

  public TXTexport (File location, String name){
    try {
      System.out.println(location.getAbsolutePath() + "/" + name + ".txt");
      File fileToWrite = new File (location.getAbsolutePath() + "/" + name + ".txt");
      fileToWrite.createNewFile();
      fileToWrite.delete();
      fileToWrite.createNewFile();



      FileWriter writer = new FileWriter(fileToWrite);
      ArrayList<String> notificationList = Notifications.getNotificationList();
      for (String notification : notificationList){
        writer.write(notification);
        writer.write(System.lineSeparator());
      }
      writer.close();
      new AlertHandler("Notifications exported successfully.");
      Notifications.addNotification("Notifications exported successfully.");

      File q = new File (name);
      q.delete();

    } catch (Exception e) {
      new AlertHandler("Export Error: Error when exporting notifications.");
    }
  }

}
