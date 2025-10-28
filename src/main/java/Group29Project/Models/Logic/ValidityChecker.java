package Group29Project.Models.Logic;


import Group29Project.Models.Airport;
import Group29Project.Models.Obstacle;
import Group29Project.Models.Runway;

public class ValidityChecker {
  // Basically for validity checking of inputs, nothing interesting
  public String checkCalculationValidity(Runway runway, Obstacle obstacle, Integer centerlineDistance, Integer lThresholdDistance, Integer rThresholdDistance){
    if (centerlineDistance - (obstacle.getWidth() / 2) > 75 || centerlineDistance + (obstacle.getWidth() / 2) < -75){
      return ("Obstacle Out Of Bounds: Obstacle is more than 75m away from runway centre-line, no recalculation is required.");
    } else if (lThresholdDistance < - 60 || rThresholdDistance < - 60) {
      return ("Obstacle Out Of Bounds: Obstacle is more than 60m away from runway, no recalculation is required.");
    } else if ((runway.getLength() - runway.getLeftRunway().getThreshold() - runway.getRightRunway().getThreshold()) < (lThresholdDistance + rThresholdDistance)){
      return ("Invalid Left Or Right Threshold: Obstacle currently has a negative length.");
    } else {
      return ("");
    }
  }

  public String checkObstacleValidity(String name, Integer height, Integer width){
    if (name.length() < 4 || name.length() > 20){
      return ("Invalid Name: Name must be between 4 and 20 characters in length.");
    } else if (height <= 0){
      return ("Invalid Height: Height must be greater than zero.");
    } else if (width <=0){
      return ("Invalid Width: Width must be greater than zero.");
    } else {
      return ("");
    }
  }

  public String checkAirportValidity(Airport airport, String code, String name){
    try {
      Double.parseDouble(name);
      return ("Invalid Airport Name: Name cannot be a number.");
    } catch(NumberFormatException ignored){}
    if (code.length() != 3){
      return ("Invalid Airport Code: The airport code must be 3 characters.");
    } else if (name.isEmpty()){
      return ("Invalid Airport Name: Name cannot be empty");
    } else {
      return ("");
    }
  }

  public String checkRunwayValidity(Integer length, Integer degree, String position){
    if (length == 0){
      return("Invalid Runway Length: Runway length must be greater than 0.");
    } else if (degree < 1 || degree > 36){
      return ("Invalid Runway Direction: Runway direction must be between 1 and 36.");
    } else {
      return ("");
    }
  }

  public String checkLogicalRunwayValidity(Integer TORA, Integer TODA, Integer ASDA, Integer LDA, Integer threshold, Integer clearway, Integer stopway){
    if (TORA < 0){
      return("Invalid TORA: TORA must be greater than or equal to 0.");
    } else if (TODA < 0){
      return("Invalid TODA: TODA must be greater than or equal to 0.");
    } else if (ASDA < 0){
      return("Invalid ASDA: ASDA must be greater than or equal to 0.");
    } else if (LDA < 0){
      return("Invalid LDA: LDA must be greater than or equal to 0.");
    } else if (threshold < 0){
      return("Invalid Threshold: Threshold must be greater than or equal to 0.");
    } else if (clearway < 0){
      return("Invalid Clearway: Clearway must be greater than or equal to 0.");
    } else if (stopway < 0){
      return("Invalid LDA: Stopway must be greater than or equal to 0.");
    } else {
      return ("");
    }
  }

}
