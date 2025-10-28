package Group29Project.Models.FileHandling;

import Group29Project.App;
import Group29Project.Controller.Controller;
import Group29Project.Models.Airport;
import Group29Project.Models.FileHandling.XML.Airports;
import Group29Project.Models.FileHandling.XML.Data;
import Group29Project.Models.FileHandling.XML.Obstacles;
import Group29Project.Models.FileHandling.XML.Runways;
import Group29Project.Models.LogicalRunway;
import Group29Project.Models.Obstacle;
import Group29Project.Models.Runway;
import Group29Project.View.AlertHandler;
import Group29Project.View.Components.CalculationInput;
import Group29Project.View.Components.Notifications;
import jakarta.xml.bind.JAXBContext;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.AbstractDocument.Content;

public class XMLimport {

  public static boolean importing = false;

  public XMLimport(File location, Controller controller, boolean ack){

    ArrayList<Airport> airportListClone = controller.getAirportList();
    ArrayList<Obstacle> obstacleListClone = controller.getObstacleList();

    importing = true;
    if (importData(location, controller)) {
      importing = false;
      if (ack) {
        new AlertHandler("XML content imported successfully.");
        Notifications.addNotification("Data imported successfully.");
      }
      CalculationInput.updateAirportDropdown();
      CalculationInput.updateRunwayDropdown("");
      CalculationInput.updateRunwayInformation("");
      CalculationInput.updateObstacleInformation("");
      CalculationInput.updateObstacleDropdown();
    } else {
      controller.setAirportList(airportListClone);
      controller.setObstacleList(obstacleListClone);
    }
    importing = false;
  }

  private boolean importData(File location, Controller controller){
    try {
      JAXBContext context = JAXBContext.newInstance(Obstacle.class);
      Data data = (Data) context.createUnmarshaller().unmarshal(location);
      Obstacles obstacles = data.getObstacleList();
      List<Obstacle> obstacleList = obstacles.getObstacleList();
      for(Obstacle obstacle : obstacleList){
        if(!controller.addObstacle(obstacle.getName(), obstacle.getHeight(), obstacle.getWidth())){
          return false;
        }
      }

      Airports airports = data.getAirportList();
      List<Airport> airportList = airports.getAirportList();

      for (Airport airport : airportList){

        Airport newAirport = controller.addAirport();
        if (!controller.editAirport(newAirport, airport.getCode(), airport.getName())){
          return false;
        }

        Runways runways = airport.xmlRunwayList;
        List<Runway> runwayList = runways.getRunwayList();
        for (Runway runway : runwayList){
          LogicalRunway left = runway.getLeftRunway();
          LogicalRunway right = runway.getRightRunway();
          if (!controller.createRunway(newAirport,runway.getLength(), runway.getDegree(), runway.getPosition(), left.getTORA(), left.getTODA(), left.getASDA(), left.getLDA(), left.getThreshold(), left.getClearway(), left.getStopway(), right.getTORA(), right.getTODA(), right.getASDA(), right.getLDA(), right.getThreshold(), right.getClearway(), right.getStopway())){
            return false;
          }
        }
      }

      return true;
    } catch (Exception e) {
      new AlertHandler("Import Error: Invalid file format.");
      return false;
    }
  }


}
