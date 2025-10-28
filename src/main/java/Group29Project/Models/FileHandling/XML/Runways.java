package Group29Project.Models.FileHandling.XML;

import Group29Project.Models.Runway;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "runways")
@XmlAccessorType(XmlAccessType.FIELD)
public class Runways {

  @XmlElement(name = "runway")
  private List<Runway> runwayList = null;

  public void setRunwayList(List<Runway> runwayList) {
    this.runwayList = runwayList;
  }

  public List<Runway> getRunwayList() {
    return runwayList;
  }
}
