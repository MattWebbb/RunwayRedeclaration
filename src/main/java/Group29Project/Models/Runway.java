package Group29Project.Models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.Objects;

@XmlRootElement(name = "runway")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"length", "degree", "position", "leftRunway", "rightRunway"})
public class Runway {

  @XmlTransient
  private Airport airport;

  @XmlElement(name = "left")
  private LogicalRunway leftRunway;
  @XmlElement(name = "right")
  private LogicalRunway rightRunway;
  @XmlElement(name = "length")
  private Integer length;
  @XmlElement(name = "heading")
  private Integer degree;
  @XmlElement(name = "position")
  private String position;

  @XmlTransient
  private ArrayList<String> TORAbreakdown, TODAbreakdown, ASDAbreakdown, LDAbreakdown;

  @XmlTransient
  private int leftObstacleThreshold, rightObstacleThreshold, centerlineThreshold;

  @XmlTransient
  private Obstacle obstacle;


  public Runway (Integer length, Integer degree, String position, LogicalRunway leftRunway, LogicalRunway rightRunway, Airport airport){

    this.airport = airport;
    this.length = length;
    this.degree = degree;
    this.position = position;

    this.leftRunway = leftRunway;
    this.rightRunway = rightRunway;

    // Determine direction of opposite logical runways
    // Detemie the direction in which they will be displayed

    int oppositeDegree;
    if (degree + 18 > 36){
      oppositeDegree = degree - 18;
    } else {
      oppositeDegree = degree + 18;
    }
    if (degree < oppositeDegree) {
      if (Objects.equals(position, "L")) {
        leftRunway.setDesignation(String.format("%02d", degree) + "L");
        rightRunway.setDesignation(String.format("%02d", oppositeDegree) + "R");
      } else if (Objects.equals(position, "R")) {
        leftRunway.setDesignation(String.format("%02d", degree) + "R");
        rightRunway.setDesignation(String.format("%02d", oppositeDegree) + "L");
      } else {
        leftRunway.setDesignation(String.format("%02d", degree) + "C");
        rightRunway.setDesignation(String.format("%02d", oppositeDegree) + "C");
      }
    }
    else {
      if (Objects.equals(position, "L")){
        rightRunway.setDesignation(String.format("%02d", degree) + "L");
        leftRunway.setDesignation(String.format("%02d", oppositeDegree) + "R");
      } else if (Objects.equals(position, "R")) {
        rightRunway.setDesignation(String.format("%02d", degree) + "R");
        leftRunway.setDesignation(String.format("%02d", oppositeDegree) + "L");
      } else {
        rightRunway.setDesignation(String.format("%02d", degree) + "C");
        leftRunway.setDesignation(String.format("%02d", oppositeDegree) + "C");
      }
    }


    this.TORAbreakdown = new ArrayList<>();
    this.TODAbreakdown = new ArrayList<>();
    this.ASDAbreakdown = new ArrayList<>();
    this.LDAbreakdown = new ArrayList<>();
  }

  public String getPosition() {
    return position;
  }
  public Integer getDegree(){
    return degree;
  }


  // Cool formating stuff
  public String getInfo() {
    if (airport.getRunwayList().size() == 1){
      return (String.format("%02d", degree));
    }
    return (String.format("%02d", degree) + position);
  }

  public LogicalRunway getLeftRunway() {
    return leftRunway;
  }

  public LogicalRunway getRightRunway() {
    return rightRunway;
  }

  public Integer getLength() {
    return length;
  }

  public ArrayList<String> getASDAbreakdown() {
    return ASDAbreakdown;
  }

  public ArrayList<String> getLDAbreakdown() {
    return LDAbreakdown;
  }

  public ArrayList<String> getTODAbreakdown() {
    return TODAbreakdown;
  }

  public ArrayList<String> getTORAbreakdown() {
    return TORAbreakdown;
  }


  public void setASDAbreakdown(ArrayList<String> ASDAbreakdown) {
    this.ASDAbreakdown = ASDAbreakdown;
  }

  public void setLDAbreakdown(ArrayList<String> LDAbreakdown) {
    this.LDAbreakdown = LDAbreakdown;
  }

  public void setTODAbreakdown(ArrayList<String> TODAbreakdown) {
    this.TODAbreakdown = TODAbreakdown;
  }

  public void setTORAbreakdown(ArrayList<String> TORAbreakdown) {
    this.TORAbreakdown = TORAbreakdown;
  }

  public void setLeftObstacleThreshold(int leftObstacleThreshold) {
    this.leftObstacleThreshold = leftObstacleThreshold;
  }

  public int getLeftObstacleThreshold() {
    return leftObstacleThreshold;
  }

  public int getRightObstacleThreshold() {
    return rightObstacleThreshold;
  }

  public void setRightObstacleThreshold(int rightObstacleThreshold) {
    this.rightObstacleThreshold = rightObstacleThreshold;
  }
  public int getCenterlineThreshold() {
    return centerlineThreshold;
  }

  public void setCenterlineThreshold(int centerlineThreshold) {
    this.centerlineThreshold = centerlineThreshold;
  }

  public Obstacle getObstacle() {
    return obstacle;
  }

  public void setObstacle(Obstacle obstacle) {
    this.obstacle = obstacle;
  }

  public Airport getAirport() {
    return airport;
  }

  public Runway(){}
}
