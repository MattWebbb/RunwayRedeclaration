package Group29Project.View.Visualisation;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Key {

  public Key (){}

  public Pane getKey (boolean colourblind){

    Pane pane = new Pane();

    Rectangle key = new Rectangle(700, 5, 95, 88);
    key.setOpacity(0.5);
    pane.getChildren().add(key);

    Text key1 = new Text("Obstacle");
    key1.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 14));
    key1.setFill(Color.WHITE);
    key1.setX(705);
    key1.setY(25);
    pane.getChildren().add(key1);

    Text key2 = new Text("Threshold");
    key2.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 14));
    key2.setFill(Color.WHITE);
    key2.setX(705);
    key2.setY(45);
    pane.getChildren().add(key2);

    Text key3 = new Text("Clearway");
    key3.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 14));
    key3.setFill(Color.WHITE);
    key3.setX(705);
    key3.setY(65);
    pane.getChildren().add(key3);

    Text key4 = new Text("Stopway");
    key4.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 14));
    key4.setFill(Color.WHITE);
    key4.setX(705);
    key4.setY(85);
    pane.getChildren().add(key4);

    Rectangle key5 = new Rectangle(772, 10, 18,18);
    key5.setFill(Color.valueOf("AA4A44"));
    pane.getChildren().add(key5);

    Rectangle key6 = new Rectangle(772, 30, 18,18);
    if(colourblind){
      key6.setFill(Color.valueOf("#018749"));
    } else {
      key6.setFill(Color.valueOf("d4ac0d"));
    }
    pane.getChildren().add(key6);

    Rectangle key7 = new Rectangle(772, 50, 18,18);
    key7.setFill(Color.valueOf("702963"));
    pane.getChildren().add(key7);

    Rectangle key8 = new Rectangle(772, 70, 18,18);
    key8.setFill(Color.valueOf("D27D2D"));
    pane.getChildren().add(key8);

    return pane;

  }

}
