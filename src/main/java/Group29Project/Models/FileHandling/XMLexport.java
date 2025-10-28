package Group29Project.Models.FileHandling;

import Group29Project.Controller.Controller;
import Group29Project.Models.Airport;
import Group29Project.Models.FileHandling.XML.Airports;
import Group29Project.Models.FileHandling.XML.Data;
import Group29Project.Models.FileHandling.XML.Obstacles;
import Group29Project.Models.Obstacle;
import Group29Project.View.AlertHandler;
import Group29Project.View.Components.Notifications;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;

public class XMLexport {
  public XMLexport(File location, String name, Controller controller, boolean ack){
    try {
      File fileToWrite = new File (location.getAbsolutePath() + "/" + name + ".xml");
      fileToWrite.deleteOnExit();
      fileToWrite.createNewFile();
      fileToWrite.delete();
      fileToWrite.createNewFile();

      JAXBContext obstacleContext = JAXBContext.newInstance(Obstacle.class);
      Marshaller marshaller = obstacleContext.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

      Data data = new Data();
      Obstacles obstacles = new Obstacles();
      Airports airports = new Airports();

      ArrayList<Obstacle> obstacleList = controller.getObstacleList();
      ArrayList<Airport> airportList = controller.getAirportList();

      obstacles.setObstacleList(obstacleList);
      airports.setAirportList(airportList);

      data.setObstacleList(obstacles);
      data.setAirportList(airports);

      marshaller.marshal(data, fileToWrite);

      File q = new File (name);
      q.delete();

      if (ack) {
        new AlertHandler("XML content exported successfully.");
        Notifications.addNotification("Data exported successfully.");
      }
    } catch (Exception e){
      e.printStackTrace();
      new AlertHandler("Export Error: Error when exporting data.");
    }


  }
}
