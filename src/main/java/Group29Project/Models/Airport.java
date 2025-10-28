package Group29Project.Models;

import Group29Project.Models.FileHandling.XML.Airports;
import Group29Project.Models.FileHandling.XML.Data;
import Group29Project.Models.FileHandling.XML.Obstacles;
import Group29Project.Models.FileHandling.XML.Runways;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
@XmlRootElement(name = "airport")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"name", "code", "xmlRunwayList"})
@XmlSeeAlso({Obstacles.class, Data.class, Airports.class})
public class Airport {

  @XmlElement(name = "code")
  private String code = "";

  @XmlElement(name = "name")
  private String name;

  @XmlElement(name = "runways")
  public Runways xmlRunwayList = new Runways();


  @XmlTransient
  private ArrayList<Runway> runwayList = new ArrayList<>();

  public Airport (){}


  public void setName(String name) {
    this.name = name;
  }


  public void setCode(String code) {
    this.code = code;
  }


  public void setRunwayList(ArrayList<Runway> runwayList) {
    this.runwayList = runwayList;
    xmlRunwayList.setRunwayList(this.runwayList);
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }

  public ArrayList<Runway> getRunwayList() {
    return runwayList;
  }

  public String getInfo(){
    return ("(" + code + ") " + name);
  }
}
