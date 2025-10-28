package Group29Project.View.Visualisation;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Arrow extends Group {
    public Arrow(double startX, double startY, double endX, double endY, String belowStr, String aboveStr, Color color){

        Text belowText = new Text(belowStr);
        belowText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        belowText.setFill(color);
        belowText.setY(startY + 15);

        Text aboveText = new Text(aboveStr);
        aboveText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        aboveText.setFill(color);
        aboveText.setY(startY - 10);

        Line line;
        Polygon p1 = new Polygon();

        int arrowHeadOffset;

        if (startX > endX){
            aboveText.setX(endX);
            belowText.setX(endX);
            arrowHeadOffset = - 10;
            line = new Line(startX, startY, endX + 10, endY);
            p1.getPoints().addAll(endX + 10, endY + 5, endX + 10, endY - 5, endX + arrowHeadOffset + 10, endY);
        } else {
            aboveText.setX(startX);
            belowText.setX(startX);
            arrowHeadOffset = 10;
            line = new Line(startX, startY, endX - 10, endY);
            p1.getPoints().addAll(endX - 10, endY + 5, endX - 10, endY - 5, endX + arrowHeadOffset - 10, endY);
        }

        line.setStrokeWidth(3);
        line.setStroke(color);
        p1.setFill(color);

        getChildren().add(p1);
        getChildren().add(line);
        getChildren().add(aboveText);
        getChildren().add(belowText);

    }


}