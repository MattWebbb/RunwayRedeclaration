package Group29Project.Models.FileHandling.XML;

import Group29Project.Models.Airport;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "airports")
@XmlAccessorType(XmlAccessType.FIELD)
public class Airports {

  @XmlElement(name = "airport")
  private List<Airport> airportList = null;

  public List<Airport> getAirportList() {
    return airportList;
  }

  public void setAirportList(List<Airport> airportList) {
    this.airportList = airportList;
  }
}
