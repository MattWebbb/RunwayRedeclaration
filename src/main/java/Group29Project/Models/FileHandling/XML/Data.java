package Group29Project.Models.FileHandling.XML;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"airportList", "obstacleList"})
public class Data {
  @XmlElement(name = "obstacles")
  private Obstacles obstacleList = null;
  @XmlElement(name = "airports")
  private Airports airportList = null;


  public Obstacles getObstacleList() {
    return obstacleList;
  }

  public void setObstacleList(Obstacles obstacleList) {
    this.obstacleList = obstacleList;
  }

  public Airports getAirportList() {
    return airportList;
  }


  public void setAirportList(Airports airportList) {
    this.airportList = airportList;
  }

  public Data(){}

}
