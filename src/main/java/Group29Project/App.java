package Group29Project;

import Group29Project.Controller.Controller;
import Group29Project.Models.FileHandling.XMLimport;
import Group29Project.Models.Runway;
import Group29Project.View.User.AuthenticationView;
import Group29Project.View.Components.CalculationInput;
import Group29Project.View.Components.CalculationOutput;
import Group29Project.View.Components.MenuTopBar;
import Group29Project.View.Components.Notifications;
import Group29Project.View.Visualisation.Key;
import Group29Project.View.Visualisation.SideOnView;
import Group29Project.View.Visualisation.TopdownView;
import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * JavaFX App
 */
public class App extends Application {
    Controller controller = new Controller();
    BorderPane primaryBorderPane;
    VBox centrePane;
    Pane currentSideOnView;
    Pane currentTopDownView;

    public Runway currentRunway;
    Boolean currentDisplay = true;
    Boolean colourblind = false;

    Boolean horizontal = true;
    Scene scene;
    public static boolean dark = false;

    public Stage primaryWindow;

    public String username = "";

    public int id = 0;

    public String data = "";

    @Override
    public void start(Stage primaryWindow) {

        controller = new Controller();
        primaryBorderPane = null;
        centrePane = null;
        currentSideOnView = null;
        currentTopDownView = null;
        currentRunway = null;
        currentDisplay = true;
        colourblind = false;
        horizontal = true;
        scene = null;
        dark = false;
        this.primaryWindow = primaryWindow;

        username = "";
        id = 0;
        data = "";

        scene = new Scene(new AuthenticationView().getAuthentication(this), 1400, 800);
        primaryWindow.setScene(scene);
        primaryWindow.setTitle("Runway Re-declaration Software");
        primaryWindow.setResizable(false);
        primaryWindow.show();
    }

    public void loggedIn(){

        new TestContent(controller);
        JSONObject jsonObject = new JSONObject(data);
        JSONArray arr = jsonObject.getJSONArray("data");
        String authentication = arr.getJSONObject(0).getString("access");

        CalculationInput calculationInput = new CalculationInput(controller, this);
        CalculationOutput calculationOutput = new CalculationOutput();
        VBox calculationOutputBox = calculationOutput.getCalculationOutput(null);
        Notifications notificationHandler = new Notifications();
        MenuTopBar topBar = new MenuTopBar();

        currentRunway = null;
        currentTopDownView = (new TopdownView()).getRunwayDisplay(currentRunway, false, false);
        currentSideOnView = (new SideOnView()).getRunwayDisplay(currentRunway, false, false);
        Pane pane = new Pane();
        pane.getChildren().add(currentTopDownView);
        pane.getChildren().add((new Key().getKey(false)));
        centrePane = new VBox(pane, notificationHandler.getNotification());

        primaryBorderPane = new BorderPane();
        primaryBorderPane.setLeft(calculationInput.generateCalculationInput());
        primaryBorderPane.setRight(calculationOutputBox);
        primaryBorderPane.setCenter(centrePane);
        primaryBorderPane.setTop(topBar.getMenuTopBar(controller, this, authentication));

        StackPane primaryStackPane = new StackPane(primaryBorderPane);
        scene = new Scene(primaryStackPane, 1400, 800);
        primaryWindow.setScene(scene);

        primaryWindow.show();

        primaryWindow.setOnCloseRequest(windowEvent -> {
            topBar.closeWindows();
        });


    }

    public void toggleDark(Boolean bool) {
        if (bool) {
            scene.getRoot().setStyle("-fx-base:black");
        } else {
            scene.getRoot().setStyle("");
        }
    }

    public void calculationsCompleted(Runway runway){
        currentRunway = runway;
        updateCurrentDisplay(currentDisplay, this.colourblind, this.horizontal);
    }

    public void updateCurrentDisplay(Boolean bool, Boolean colourblind, Boolean horizontal){
        this.colourblind = colourblind;
        this.horizontal = horizontal;
        primaryBorderPane.setRight(new CalculationOutput().getCalculationOutput(currentRunway));
        if (currentRunway == null){
            currentTopDownView = (new TopdownView()).getRunwayDisplay(currentRunway, false, colourblind);
            currentSideOnView = (new SideOnView()).getRunwayDisplay(currentRunway, false, colourblind);
        } else {
            currentTopDownView = (new TopdownView()).getRunwayDisplay(currentRunway, true, colourblind);
            currentSideOnView = (new SideOnView()).getRunwayDisplay(currentRunway, true, colourblind);
        }
        if (horizontal || currentRunway == null){
            if (bool) {

                Pane pane = new Pane();
                pane.getChildren().add(currentTopDownView);
                pane.getChildren().add((new Key()).getKey(colourblind));

                centrePane.getChildren().remove(0);
                centrePane.getChildren().add(0, pane);
                currentDisplay = true;
            } else {
                centrePane.getChildren().remove(0);
                centrePane.getChildren().add(0, currentSideOnView);
                currentDisplay = false;
            }
        } else {
            if (bool) {
                Pane pane = new Pane();

                Rectangle base = new Rectangle(800, 660);
                if (colourblind){
                    base.setFill(Color.valueOf("FFFAA0"));
                } else {
                    base.setFill(Color.valueOf("#41980A"));
                }
                pane.getChildren().add(base);

                pane.getChildren().add(currentTopDownView);
                pane.getChildren().add((new Key()).getKey(colourblind));

                int degree;
                if (currentRunway.getDegree() < 19){
                    degree = (currentRunway.getDegree() * 10) - 90;
                } else {
                    degree = (currentRunway.getDegree() * 10) - 270;
                }

                double scale;
                double p = Math.toRadians(degree);
                double w = 800 * Math.abs(Math.cos(p)) + 660 * Math.abs(Math.sin(p));
                double h = 800 * Math.abs(Math.sin(p)) + 660 * Math.abs(Math.cos(p));
                scale = Math.min(800 / w, 660 / h);

                currentTopDownView.getTransforms().add(new Rotate(degree, 400,330));
                currentTopDownView.getTransforms().add(new Scale(scale, scale, 400, 330));
                centrePane.getChildren().remove(0);
                centrePane.getChildren().add(0, pane);
                currentDisplay = true;

            } else {
                centrePane.getChildren().remove(0);
                centrePane.getChildren().add(0, currentSideOnView);
                currentDisplay = false;
            }
        }
    }

    public static void Main(String[] args) {
        launch();
    }

}