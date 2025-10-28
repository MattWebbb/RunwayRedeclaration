package Group29Project.Models.FileHandling.XML;

import Group29Project.Models.Obstacle;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "obstacles")
@XmlAccessorType(XmlAccessType.FIELD)
public class Obstacles {

  @XmlElement(name = "obstacle")
  private List<Obstacle> obstacleList = null;

  public void setObstacleList(List<Obstacle> list){
    this.obstacleList = list;
  }
  public List<Obstacle> getObstacleList() {
    return obstacleList;
  }
}
