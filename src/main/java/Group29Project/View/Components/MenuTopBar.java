package Group29Project.View.Components;

import Group29Project.App;
import Group29Project.Controller.Controller;
import Group29Project.Models.FileHandling.PDFexport;
import Group29Project.Models.FileHandling.TXTexport;
import Group29Project.Models.FileHandling.XMLexport;
import Group29Project.Models.FileHandling.XMLimport;
import Group29Project.View.Closer;
import Group29Project.View.User.AccountManagement;
import Group29Project.View.AlertHandler;
import Group29Project.View.ContentManagement.AddAirport;
import Group29Project.View.ContentManagement.AddObstacle;
import Group29Project.View.ContentManagement.EditAirport;
import Group29Project.View.ContentManagement.EditObstacle;
import Group29Project.View.User.UserManagement;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuTopBar {

  public MenuTopBar() {
  }

  boolean horizontal = true;
  boolean display = true;
  boolean colourblind = false;
  boolean dark = false;
  Menu user;

  ArrayList<Closer> windows = new ArrayList<>();

  ArrayList<Stage> fileChoosers = new ArrayList<>();

  ArrayList<TextInputDialog> textInputDialogs = new ArrayList<>();

  public MenuBar getMenuTopBar(Controller controller, App app, String authentication) {

    // Add different menus

    Menu file = new Menu("File");
    MenuItem importXML = new MenuItem("Import Data (.xml)");
    MenuItem exportXML = new MenuItem("Export Data (.xml)");
    MenuItem exportPDF = new MenuItem("Export Results (.pdf)");
    MenuItem exportTXT = new MenuItem("Export Notifications (.txt)");
    importXML.setOnAction(event -> {
      File fileLocation = getFile();
      if (fileLocation != null) {
        new XMLimport(fileLocation, controller, true);
      }
    });
    exportXML.setOnAction(event -> {
      File fileLocation = getFileLocation();
      if (fileLocation != null) {
        String fileName = getFileName();
        new XMLexport(fileLocation, fileName, controller, true);
      }
    });
    exportPDF.setOnAction(event -> {
      File fileLocation = getFileLocation();
      if (fileLocation != null) {
        String fileName = getFileName();
        if (app.currentRunway == null){
          new AlertHandler("PDF Generation Error: No calculations have been run.");
        } else {
          new PDFexport(fileLocation, fileName, app.currentRunway);
        }
      }
    });
    exportTXT.setOnAction(event -> {
      File fileLocation = getFileLocation();
      if (fileLocation != null) {
        String fileName = getFileName();
        new TXTexport(fileLocation, fileName);
      }
    });

    if (Objects.equals(authentication, "Guest")) {
      importXML.setDisable(true);
    }
    file.getItems().add(importXML);
    file.getItems().add(exportXML);
    file.getItems().add(exportPDF);
    file.getItems().add(exportTXT);

    Menu view = new Menu("View");
    MenuItem topDown = new MenuItem("Top Down View");
    MenuItem sideOn = new MenuItem("Side On View");
    MenuItem orientationHorizontal = new MenuItem("Orientation (Horizontal)");
    MenuItem orientationHeading = new MenuItem("Orientation (Heading)");
    MenuItem colorblindOff = new MenuItem("High Contrast Mode (Off)");
    MenuItem colorblindOn = new MenuItem("High Contrast Mode (On)");
    MenuItem darkOff = new MenuItem("Dark Mode (Off)");
    MenuItem darkOn = new MenuItem("Dark Mode (On)");
    topDown.setOnAction(event -> {
      setDisplay(true);
      app.updateCurrentDisplay(display, colourblind, horizontal);
    });
    sideOn.setOnAction(event -> {
      setDisplay(false);
      app.updateCurrentDisplay(display, colourblind, horizontal);
    });
    orientationHorizontal.setOnAction(event -> {
      setHorizontal(false);
      view.getItems().remove(orientationHorizontal);
      view.getItems().add(2, orientationHeading);
      app.updateCurrentDisplay(display, colourblind, horizontal);
    });
    orientationHeading.setOnAction(event -> {
      setHorizontal(true);
      view.getItems().remove(orientationHeading);
      view.getItems().add(2, orientationHorizontal);
      app.updateCurrentDisplay(display, colourblind, horizontal);
    });
    colorblindOff.setOnAction(event -> {
      setColourblind(true);
      view.getItems().remove(colorblindOff);
      view.getItems().add(3, colorblindOn);
      app.updateCurrentDisplay(display, colourblind, horizontal);
    });
    colorblindOn.setOnAction(event -> {
      setColourblind(false);
      view.getItems().remove(colorblindOn);
      view.getItems().add(3, colorblindOff);
      app.updateCurrentDisplay(display, colourblind, horizontal);
    });
    darkOff.setOnAction(event -> {
      setDark(true);
      setDark();
      App.dark = true;
      view.getItems().remove(darkOff);
      view.getItems().add(darkOn);
      app.toggleDark(dark);
    });
    darkOn.setOnAction(event -> {
      App.dark = false;
      setDark(false);
      setDark();
      view.getItems().remove(darkOn);
      view.getItems().add(darkOff);
      app.toggleDark(dark);
    });
    view.getItems().add(topDown);
    view.getItems().add(sideOn);
    view.getItems().add(orientationHorizontal);
    view.getItems().add(colorblindOff);
    view.getItems().add(darkOff);

    Menu data = new Menu("Data");
    MenuItem addAirport = new MenuItem("Add Airport");
    MenuItem editAirport = new MenuItem("Edit Airport");
    MenuItem addObstacle = new MenuItem("Add Obstacle");
    MenuItem editObstacle = new MenuItem("Edit Obstacle");
    // Make the add airport and obstacle menus work
    addAirport.setOnAction(event -> {
      windows.add(new AddAirport(controller, dark));
    });
    editAirport.setOnAction(event -> {
      windows.add(new EditAirport(controller, dark));
    });
    addObstacle.setOnAction(event -> {
      windows.add(new AddObstacle(controller, dark));
    });
    editObstacle.setOnAction(event -> {
      windows.add(new EditObstacle(controller, dark));
    });

    if (authentication.equals("Guest")){
      addAirport.setDisable(true);
      editAirport.setDisable(true);
      addObstacle.setDisable(true);
      editObstacle.setDisable(true);
    }
    data.getItems().add(addAirport);
    data.getItems().add(editAirport);
    data.getItems().add(addObstacle);
    data.getItems().add(editObstacle);

    Menu acc = new Menu ("Account");
    MenuItem account = new MenuItem("Account");
    MenuItem management = new MenuItem("User Management");
    MenuItem logout = new MenuItem("Logout");
    logout.setOnAction(event -> {
      closeWindows();
      app.start(app.primaryWindow);
    });
    account.setOnAction(event -> {
      AccountManagement accountManagement = new AccountManagement();
      accountManagement.getAccountManagement(app, app.id,false,this, dark, null);
      windows.add(accountManagement);
    });
    management.setOnAction(event -> {
      UserManagement userManagement = new UserManagement();
      userManagement.getUserManagement(app, this, dark);
      windows.add(userManagement);
    });
    if(!authentication.equals("Admin")){
      management.setDisable(true);
    }
    acc.getItems().add(account);
    acc.getItems().add(management);
    acc.getItems().add(logout);

    user = new Menu ("Signed In As: " + app.username);

    Menu help = new Menu("Help");
    MenuItem userGuide = new MenuItem("User Guide");
    userGuide.setOnAction(event -> {
      if (Desktop.isDesktopSupported()) {
        try {
          URL url = getClass().getClassLoader().getResource("User Guide.pdf");
          System.out.println(url);

          URL url2 = getClass().getClassLoader().getResource("src/main/resources/User Guide.pdf");
          System.out.println(url2);

          InputStream inputStream = getClass().getClassLoader().getResourceAsStream("User Guide.pdf");

          if (inputStream == null) {
            throw new FileNotFoundException();
          }

          File tempFile = File.createTempFile("userguide", ".pdf");
          tempFile.deleteOnExit();

          Files.copy(inputStream, tempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

          Desktop.getDesktop().open(tempFile);

        } catch (IOException ex) {
          ex.printStackTrace();
          new AlertHandler("Error: Cannot open help document.");
        }
      }
    });
    help.getItems().add(userGuide);

    MenuBar menuBar = new MenuBar(file, view, data, acc, help, user);

    return menuBar;
  }

  private File getFileLocation() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    Stage stage = new Stage();
    fileChoosers.add(stage);
    return directoryChooser.showDialog(stage);
  }

  private File getFile(){
    FileChooser fileChooser = new FileChooser();
    Stage stage = new Stage();
    fileChoosers.add(stage);
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.xml"));
    return fileChooser.showOpenDialog(stage);
  }

  private String getFileName(){
    TextInputDialog inputDialog = new TextInputDialog();
    textInputDialogs.add(inputDialog);
    inputDialog.setTitle("Input");
    inputDialog.setHeaderText("Input:");
    inputDialog.setContentText("Input File Name:");
    DialogPane dialogPane = inputDialog.getDialogPane();
    if (App.dark) {
      dialogPane.setStyle("-fx-base:black");
    } else {
      dialogPane.setStyle("");
    }
    Optional<String> result = inputDialog.showAndWait();
    if (result.isPresent()){
      File test = new File(result.get());
      try {
        test.createNewFile();
      } catch (Exception e) {
        new AlertHandler("Invalid File Name: File name contains invalid character(s).");
      }
      return result.get();
    }
    return null;
  }

  public void closeWindows(){
    for(Closer window : windows){
      try {
        window.close();
      } catch (Exception ignored) {}
    }
    for(Stage window : fileChoosers){
      try {
        window.close();
      } catch (Exception ignored) {}
    }
    for(TextInputDialog window : textInputDialogs){
      try {
        window.close();
      } catch (Exception ignored) {}
    }
    AlertHandler.closeAll();
  }

  public void setDark() {
    for (Closer window : windows) {
      try {
        window.setDark(dark);
      } catch (Exception ignored) {
      }
    }
    AlertHandler.setDark(dark);
  }


  private void setHorizontal(boolean bool){
    horizontal = bool;
  }
  private void setDisplay (boolean bool){
    display = bool;
  }
  private void setColourblind (boolean bool){
    colourblind = bool;
  }

  private void  setDark(boolean bool){
    dark = bool;
  }

  public void updateUser(String username){
    user.setText("Signed In As: " + username);
  }

}
