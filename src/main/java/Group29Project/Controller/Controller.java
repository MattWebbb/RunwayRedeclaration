package Group29Project.Controller;


import Group29Project.Models.Airport;
import Group29Project.Models.Logic.Recalculator;
import Group29Project.Models.Logic.ValidityChecker;
import Group29Project.Models.LogicalRunway;
import Group29Project.Models.Obstacle;
import Group29Project.Models.Runway;
import Group29Project.View.AlertHandler;
import java.util.ArrayList;
import java.util.Objects;

public class Controller {

  private ArrayList<Airport> airportList = new ArrayList<>();
  private ArrayList<Obstacle> obstacleList = new ArrayList<>();
  private final ValidityChecker validityChecker = new ValidityChecker();

  public Boolean runCalculations(Runway runway, Obstacle obstacle, Integer centreDistance, Integer lThresholdDistance, Integer rThresholdDistance){
    runway.setCenterlineThreshold(centreDistance);
    runway.setLeftObstacleThreshold(lThresholdDistance);
    runway.setRightObstacleThreshold(rThresholdDistance);
    runway.setObstacle(obstacle);
    String error = validityChecker.checkCalculationValidity(runway,obstacle,centreDistance,lThresholdDistance,rThresholdDistance);
    if (!Objects.equals(error, "")) {
      new AlertHandler(error);
      return Boolean.FALSE;
    } else {
      new Recalculator(runway, obstacle, lThresholdDistance, rThresholdDistance);
      return Boolean.TRUE;
    }
  }

// Obstacle handling.

  public Boolean addObstacle(String name, Integer height, Integer width){
    String error = validityChecker.checkObstacleValidity(name, height, width);
    ArrayList<Obstacle> currentObstacleList = this.getObstacleList();
    for (Obstacle obstacle : currentObstacleList){
      if (obstacle.getName().equals(name)){
        new AlertHandler("Invalid Name: Obstacle has the same name as an existing obstacle.");
        return Boolean.FALSE;
      }
    }
    if (!Objects.equals(error, "")) {
      new AlertHandler(error);
      return Boolean.FALSE;
    } else{
      Obstacle obstacle = new Obstacle(name, width, height);
      obstacleList.add(obstacle);
      return Boolean.TRUE;
    }
  }

  public Boolean editObstacle(Obstacle obstacle, String name, Integer height, Integer width){
    ArrayList<Obstacle> currentObstacleList = this.getObstacleList();
    for (Obstacle currentObstacle : currentObstacleList){
      if (currentObstacle.getName().equals(name) && !currentObstacle.equals(obstacle)){
        new AlertHandler("Invalid Name: Obstacle has the same name as an existing obstacle.");
        return Boolean.FALSE;
      }
    }
    String error = validityChecker.checkObstacleValidity(name, height, width);
    if (!Objects.equals(error, "")) {
      new AlertHandler(error);
      return Boolean.FALSE;
    } else{
      obstacle.setName(name);
      obstacle.setHeight(height);
      obstacle.setWidth(width);
      return Boolean.TRUE;
    }
  }

  public Boolean removeObstacle(Obstacle obstacle){
    obstacleList.remove(obstacle);
    return Boolean.TRUE;
  }

  public ArrayList<Obstacle> getObstacleList(){
    return obstacleList;
  }


// Airport handling.

  // Create a blank airport

  public Airport addAirport(){
    return new Airport();
  }

  // Edit an existing airport

  public Boolean editAirport(Airport airport, String code, String name){
    String error = validityChecker.checkAirportValidity(airport, code, name);
    ArrayList<Airport> currentAirportList = this.getAirportList();
    for (Airport currentAirport : currentAirportList){
      if (currentAirport.getCode().equals(code) && !currentAirport.equals(airport)){
        new AlertHandler("Invalid Code: Airport has the same code as an existing airport.");
        return Boolean.FALSE;
      }
    }
    if (!Objects.equals(error, "")) {
      new AlertHandler(error);
      return Boolean.FALSE;
    } else {
      if (airport.getName() == null){
        airportList.add(airport);
      }
      airport.setCode(code);
      airport.setName(name);
      return Boolean.TRUE;
    }
  }

  // Add a runway to an airport

  private Boolean addRunway(Airport airport, Runway runway, String position){
    ArrayList<Runway> runwayList = airport.getRunwayList();
    if (runwayList.isEmpty()){
      runwayList.add(runway);
    } else if (runwayList.size() == 1) {
      if (Objects.equals(position, "L")){
        runwayList.add(0, runway);
      } else {
        runwayList.add(runway);
      }
    } else if (runwayList.size() == 2) {
      if (Objects.equals(position, "L")){
        runwayList.add(0, runway);
      } else if (Objects.equals(position, "C")){
        runwayList.add(1, runway);
      } else {
        runwayList.add(runway);
      }
    } else {
      new AlertHandler("Invalid Runway: A runway already exists in that position.");
      return Boolean.FALSE;
    }
    airport.setRunwayList(runwayList);
    return Boolean.TRUE;
  }

  // Remove an airport

  public Boolean removeAirport(Airport airport){
    airportList.remove(airport);
    return Boolean.TRUE;
  }

  public ArrayList<Airport> getAirportList(){
    return airportList;
  }

  // Runway handling

  // Create a new runway -> add to airport

  public Boolean createRunway(Airport airport, Integer length, Integer degree, String position, Integer TORA1, Integer TODA1, Integer ASDA1, Integer LDA1, Integer threshold1, Integer clearway1, Integer stopway1, Integer TORA2, Integer TODA2, Integer ASDA2, Integer LDA2, Integer threshold2, Integer clearway2, Integer stopway2){
    String error1 = validityChecker.checkLogicalRunwayValidity(TORA1, TODA1, ASDA1, LDA1, threshold1, clearway1, stopway1);
    String error2 = validityChecker.checkLogicalRunwayValidity(TORA2, TODA2, ASDA2, LDA2, threshold2, clearway2, stopway2);

    LogicalRunway logicalRunway1 = null;
    LogicalRunway logicalRunway2 = null;

    if (!Objects.equals(error1, "")) {
      new AlertHandler(error1);
      return Boolean.FALSE;
    } else {
      logicalRunway1 = new LogicalRunway(TORA1, TODA1, ASDA1, LDA1, threshold1, clearway1, stopway1);
    }
    if (!Objects.equals(error2, "")) {
      new AlertHandler(error2);
      return Boolean.FALSE;
    } else {
      logicalRunway2 = new LogicalRunway(TORA2, TODA2, ASDA2, LDA2, threshold2, clearway2, stopway2);
    }

    String error3 = validityChecker.checkRunwayValidity(length, degree, position);
    if (!Objects.equals(error3, "")) {
      new AlertHandler(error3);
      return Boolean.FALSE;
    } else {
      Runway runway = new Runway(length, degree, position, logicalRunway1, logicalRunway2, airport);
      return addRunway(airport, runway, position);
    }
  }

  // Edit an existing runway

  public Boolean editRunway(Runway runway, Airport airport, Integer length, Integer degree, String position, Integer TORA1, Integer TODA1, Integer ASDA1, Integer LDA1, Integer threshold1, Integer clearway1, Integer stopway1, Integer TORA2, Integer TODA2, Integer ASDA2, Integer LDA2, Integer threshold2, Integer clearway2, Integer stopway2) {
    ArrayList<Runway> previousRunwayList = airport.getRunwayList();
    removeRunway(airport, runway);
    Boolean successfulCreation = createRunway(airport, length, degree, position, TORA1, TODA1, ASDA1, LDA1, threshold1, clearway1, stopway1, TORA2, TODA2, ASDA2, LDA2, threshold2, clearway2, stopway2);
    if (successfulCreation){
      return Boolean.TRUE;
    } else {
      airport.setRunwayList(previousRunwayList);
      return Boolean.FALSE;
    }
  }

  // Remove an existing runway

  public Boolean removeRunway(Airport airport, Runway runway){
    ArrayList<Runway> runwayList = airport.getRunwayList();
    runwayList.remove(runway);
    airport.setRunwayList(runwayList);
    return Boolean.TRUE;
  }

  public void setAirportList(ArrayList<Airport> airportList){
    this.airportList = airportList;
  }

  public void setObstacleList(ArrayList<Obstacle> obstacleList) {
    this.obstacleList = obstacleList;
  }
}
