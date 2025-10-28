package Group29Project;

import Group29Project.Controller.Controller;
import Group29Project.Models.Airport;

public class TestContent {

  public TestContent(Controller controller){

    // Add a few obstacles
    controller.addObstacle("Airbus A380", 24, 80);
    controller.addObstacle("Boeing 787", 17, 60);
    controller.addObstacle("Boeing 777", 19, 65);
    controller.addObstacle("Boeing 747", 19, 68);
    controller.addObstacle("Airbus A350", 17, 65);
    controller.addObstacle("Airbus A320", 12, 36);

    // Add an airport
    Airport heathrow = controller.addAirport();
    controller.createRunway(heathrow, 3658, 27, "L", 3660, 3660, 3660, 3660, 0, 0, 0, 3660, 3660, 3660,3660,307,0,0);
    controller.createRunway(heathrow,3902,27,"R",3902,3902,3902,3595,306,300,200,3884,3962,3884,3884,0,0,0);
    controller.editAirport(heathrow,"LHA", "Heathrow");
  }

}