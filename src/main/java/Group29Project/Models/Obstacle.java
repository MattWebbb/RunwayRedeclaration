package Group29Project.Models;

import Group29Project.Models.FileHandling.XML.Airports;
import Group29Project.Models.FileHandling.XML.Data;
import Group29Project.Models.FileHandling.XML.Obstacles;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;


@XmlRootElement(name = "obstacle")
@XmlType(propOrder = {"name", "width", "height"})
@XmlSeeAlso({Obstacles.class, Data.class, Airports.class, Runway.class})

public class Obstacle {

  private String name;
  private Integer width, height;

  public  Obstacle(){

  }
  public Obstacle (String name, Integer width, Integer height){
    this.name = name;
    this.width = width;
    this.height = height;
  }


  // Return nice formatted response
  public ArrayList<String> getInfo(){
    ArrayList<String> infoList = new ArrayList<>();
    infoList.add("Height: " + this.height);
    infoList.add("Width: " + this.width);
    return infoList;
  }

  @XmlElement(name = "name")
  public void setName(String name) {
    this.name = name;
  }

  @XmlElement(name = "width")
  public void setWidth(Integer width) {
    this.width = width;
  }

  @XmlElement(name = "height")
  public void setHeight(Integer height) {
    this.height = height;
  }

  public String getName() {
    return name;
  }

  public Integer getWidth() {
    return width;
  }

  public Integer getHeight() {
    return height;
  }
}
