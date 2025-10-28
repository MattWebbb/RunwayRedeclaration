package Group29Project.View.Visualisation;

import Group29Project.Models.LogicalRunway;
import Group29Project.Models.Runway;
import Group29Project.View.AlertHandler;
import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TopdownView {

  private int length;

  public TopdownView(){}

  public Color clearedColour, gradedColour, secondTextColour, thresholdColour;

  public Pane getRunwayDisplay(Runway runway, boolean drawMeasurements, boolean colourblind) {

    if (colourblind){
      clearedColour = Color.valueOf("FFFAA0");
      gradedColour = Color.valueOf("#0E3386");
      secondTextColour = Color.valueOf("#00FFFF");
      thresholdColour = Color.valueOf("#018749");
    } else {
      clearedColour = Color.valueOf("#41980A");
      gradedColour = Color.valueOf("#0071c5");
      secondTextColour = Color.WHITE;
      thresholdColour = Color.valueOf("d4ac0d");
    }

    Pane pane = new Pane();

    Rectangle base = new Rectangle(800, 660);
    base.setFill(clearedColour);
    pane.getChildren().add(base);

    Rectangle clearedAndGraded1 = new Rectangle(0, 200, 800, 260);
    clearedAndGraded1.setFill(gradedColour);
    pane.getChildren().add(clearedAndGraded1);

    Polygon clearedAndGraded2 = new Polygon();
    clearedAndGraded2.setFill(gradedColour);
    clearedAndGraded2.getPoints().addAll(150.0, 200.0, 150.0, 460.0, 240.0, 550.0, 560.0, 550.0, 650.0, 460.0, 650.0, 200.0, 560.0, 110.0, 240.0, 110.0);
    pane.getChildren().add(clearedAndGraded2);

    Rectangle runwayBox = new Rectangle(100, 280, 600, 100);
    runwayBox.setFill(Color.valueOf("848484"));
    pane.getChildren().add(runwayBox);

    Line centerline = new Line(150, 330, 650, 330);
    centerline.setStrokeWidth(3);
    centerline.setStroke(Color.WHITE);
    centerline.getStrokeDashArray().addAll(10d);
    pane.getChildren().add(centerline);

    // Directions

    Text direction1 = new Text("Take off and landing in this direction.");
    direction1.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 14));
    direction1.setFill(secondTextColour);
    direction1.setWrappingWidth(130);
    direction1.setX(25);
    direction1.setY(30);
    pane.getChildren().add(direction1);

    Arrow direction1Arrow = new Arrow(170, 35, 220, 35, "", "", secondTextColour);
    pane.getChildren().add(direction1Arrow);

    Text direction2 = new Text("Take off and landing in this direction.");
    direction2.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 14));
    direction2.setFill(secondTextColour);
    direction2.setWrappingWidth(130);
    direction2.setX(650);
    direction2.setY(620);
    pane.getChildren().add(direction2);

    Arrow direction2Arrow = new Arrow(635, 620, 585, 620,"", "", secondTextColour);
    pane.getChildren().add(direction2Arrow);




    if (drawMeasurements){
      LogicalRunway leftRunway = runway.getLeftRunway();
      LogicalRunway rightRunway = runway.getRightRunway();
      this.length = runway.getLength();
      if (leftRunway.getRecalculatedTORA() < 0 || leftRunway.getRecalculatedTODA() < 0 || leftRunway.getRecalculatedASDA() < 0 || leftRunway.getRecalculatedLDA() < 0){
        new AlertHandler("Cannot Generate Visualisation: At least one parameter is less than zero.");
      } else if (rightRunway.getRecalculatedTORA() < 0 || rightRunway.getRecalculatedTODA() < 0 || rightRunway.getRecalculatedASDA() < 0 || rightRunway.getRecalculatedLDA() < 0){
        new AlertHandler("Cannot Generate Visualisation: At least one parameter is less than zero.");
      } else {
        pane = (addMeasurements(runway, leftRunway, rightRunway, pane));
      }
    }

    Line line1 = new Line(30, 330, 30, 300);
    line1.setStrokeWidth(3);
    line1.setStroke(secondTextColour);
    Polygon p1 = new Polygon();
    p1.getPoints().addAll(25.0, 300.0, 35.0, 300.0, 30.0, 290.0);
    p1.setFill(secondTextColour);
    Line line2 = new Line(30, 331, 30, 360);
    line2.setStrokeWidth(3);
    line2.setStroke(secondTextColour);
    Polygon p2 = new Polygon();
    p2.getPoints().addAll(25.0, 360.0, 35.0, 360.0, 30.0, 370.0);
    p2.setFill(secondTextColour);

    Text t1 = new Text(24, 285, "L");
    t1.setFill(secondTextColour);
    t1.setTextAlignment(TextAlignment.LEFT);
    t1.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 20));

    Text t2 = new Text(24, 390, "R");
    t2.setFill(secondTextColour);
    t2.setTextAlignment(TextAlignment.LEFT);
    t2.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 20));

    pane.getChildren().add(line1);
    pane.getChildren().add(line2);
    pane.getChildren().add(p1);
    pane.getChildren().add(p2);
    pane.getChildren().add(t1);
    pane.getChildren().add(t2);

    return pane;
  }

  private Pane addMeasurements(Runway runway, LogicalRunway leftRunway, LogicalRunway rightRunway, Pane pane) {

    // Clearways

    if (rightRunway.getClearway() > 0) {
      Rectangle leftClearway = new Rectangle(100 - scaleMetersToPixels(leftRunway.getClearway()), 250, scaleMetersToPixels(rightRunway.getClearway()), 160);
      leftClearway.setFill(Color.valueOf("702963"));
      leftClearway.getStrokeDashArray().addAll(10d);
      pane.getChildren().add(leftClearway);
    }

    if (leftRunway.getClearway() > 0) {
      Rectangle rightClearway = new Rectangle(700, 250, scaleMetersToPixels(leftRunway.getClearway()), 160);
      rightClearway.setFill(Color.valueOf("702963"));
      rightClearway.getStrokeDashArray().addAll(10d);
      pane.getChildren().add(rightClearway);
    }

    // Stopways

    if (rightRunway.getStopway() > 0) {
      Rectangle leftStopway = new Rectangle(100 - scaleMetersToPixels(rightRunway.getStopway()), 280, scaleMetersToPixels(leftRunway.getStopway()), 100);
      leftStopway.setFill(Color.valueOf("D27D2D"));
      leftStopway.getStrokeDashArray().addAll(10d);
      pane.getChildren().add(leftStopway);
    }

    if (leftRunway.getStopway() > 0) {
      Rectangle rightStopway = new Rectangle(700, 280, scaleMetersToPixels(leftRunway.getStopway()), 100);
      rightStopway.setFill(Color.valueOf("D27D2D"));
      rightStopway.getStrokeDashArray().addAll(10d);
      pane.getChildren().add(rightStopway);
    }

    ArrayList<Integer> leftStartLinesDrawn = new ArrayList<>();
    ArrayList<Integer> rightStartLinesDrawn = new ArrayList<>();
    ArrayList<Integer> leftEndLinesDrawn = new ArrayList<>();
    ArrayList<Integer> rightEndLinesDrawn = new ArrayList<>();


    // Left arrows

    if (runway.getLeftObstacleThreshold() > runway.getRightObstacleThreshold()) {
      Arrow TORAarrow = new Arrow(100, 80, 100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA()), 80, "TORA: " + leftRunway.getRecalculatedTORA() + "m", "", secondTextColour);
      Arrow TODAarrow = new Arrow(100, 130, 100 + scaleMetersToPixels(leftRunway.getRecalculatedTODA()), 130, "TODA: " + leftRunway.getRecalculatedTODA() + "m", "", secondTextColour);
      Arrow ASDAarrow = new Arrow(100, 180, 100 + scaleMetersToPixels(leftRunway.getRecalculatedASDA()), 180, "ASDA: " + leftRunway.getRecalculatedASDA() + "m", "", secondTextColour);
      leftStartLinesDrawn.add(100);
      leftStartLinesDrawn.add(100);
      leftStartLinesDrawn.add(100);
      leftEndLinesDrawn.add(100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA()));
      leftEndLinesDrawn.add(100 + scaleMetersToPixels(leftRunway.getRecalculatedTODA()));
      leftEndLinesDrawn.add(100 + scaleMetersToPixels(leftRunway.getRecalculatedASDA()));
      if ((runway.getObstacle().getHeight() * 50) < 60) {
        Arrow resaArrow = new Arrow(100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA()), 80, 100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA() + 60), 80, "RESA: " + leftRunway.getRESA() + "m", "", Color.BLACK );
        Arrow resaArrow2 = new Arrow(100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA()), 130, 100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA() + 60), 130, "RESA: " + leftRunway.getRESA() + "m", "", Color.BLACK );
        Arrow resaArrow3 = new Arrow(100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA()), 180, 100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA() + 60), 180, "RESA: " + leftRunway.getRESA() + "m", "", Color.BLACK );
        Arrow stripendArrow = new Arrow(100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA() + 60), 80, 100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA() + 120), 80, "", "STRIP END: 60m", Color.BLACK );
        Arrow stripendArrow2 = new Arrow(100 + scaleMetersToPixels(leftRunway.getRecalculatedTODA() + 60), 130, 100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA() + 120), 130, "", "STRIP END: 60m", Color.BLACK );
        Arrow stripendArrow3 = new Arrow(100 + scaleMetersToPixels(leftRunway.getRecalculatedASDA() + 60), 180, 100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA() + 120), 180, "", "STRIP END: 60m", Color.BLACK );
        pane.getChildren().add(stripendArrow);
        pane.getChildren().add(stripendArrow2);
        pane.getChildren().add(stripendArrow3);
        pane.getChildren().add(resaArrow);
        pane.getChildren().add(resaArrow2);
        pane.getChildren().add(resaArrow3);
      } else {
        Arrow slopeArrow = new Arrow (100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA()), 80, 100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA() + (runway.getObstacle().getHeight() * 50)), 80, "SLOPE CALCULATION: " + runway.getObstacle().getHeight() * 50 + "m","", Color.BLACK);
        Arrow slopeArrow2 = new Arrow (100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA()), 130, 100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA() + (runway.getObstacle().getHeight() * 50)), 130, "SLOPE CALCULATION: " + runway.getObstacle().getHeight() * 50 + "m","", Color.BLACK);
        Arrow slopeArrow3 = new Arrow (100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA()), 180, 100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA() + (runway.getObstacle().getHeight() * 50)), 180, "SLOPE CALCULATION: " + runway.getObstacle().getHeight() * 50 + "m","", Color.BLACK);
        Arrow stripendArrow = new Arrow(100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA() + (runway.getObstacle().getHeight() * 50)), 80, 100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA() + (runway.getObstacle().getHeight() * 50) + 60), 80, "", "STRIP END: 60m", Color.BLACK );
        Arrow stripendArrow2 = new Arrow(100 + scaleMetersToPixels(leftRunway.getRecalculatedTODA() + (runway.getObstacle().getHeight() * 50)), 130, 100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA() + (runway.getObstacle().getHeight() * 50) + 60), 130, "", "STRIP END: 60m", Color.BLACK );
        Arrow stripendArrow3 = new Arrow(100 + scaleMetersToPixels(leftRunway.getRecalculatedASDA() + (runway.getObstacle().getHeight() * 50)), 180, 100 + scaleMetersToPixels(leftRunway.getRecalculatedTORA() + (runway.getObstacle().getHeight() * 50) + 60), 180, "", "STRIP END: 60m", Color.BLACK );
        pane.getChildren().add(stripendArrow);
        pane.getChildren().add(stripendArrow2);
        pane.getChildren().add(stripendArrow3);
        pane.getChildren().add(slopeArrow);
        pane.getChildren().add(slopeArrow2);
        pane.getChildren().add(slopeArrow3);
      }
      Arrow LDAarrow = new Arrow(100 + scaleMetersToPixels(leftRunway.getThreshold()), 230, 100 + scaleMetersToPixels(leftRunway.getThreshold() + leftRunway.getRecalculatedLDA()), 230, "LDA: " + leftRunway.getRecalculatedLDA() + "m", "", secondTextColour);
      leftStartLinesDrawn.add(100 + scaleMetersToPixels(leftRunway.getThreshold()));
      leftEndLinesDrawn.add(100 + scaleMetersToPixels(leftRunway.getThreshold() + leftRunway.getRecalculatedLDA()));
      Arrow resaArrow = new Arrow(100 + scaleMetersToPixels(leftRunway.getThreshold()) + scaleMetersToPixels(leftRunway.getRecalculatedLDA()), 230, 100 + scaleMetersToPixels(leftRunway.getThreshold()) + scaleMetersToPixels(leftRunway.getRecalculatedLDA() + 240), 230, "RESA: " + leftRunway.getRESA() + "m", "", Color.BLACK );

      Arrow stripendArrow4 = new Arrow(100 + scaleMetersToPixels(leftRunway.getThreshold()) + scaleMetersToPixels(leftRunway.getRecalculatedLDA() + 240), 230, 100 + scaleMetersToPixels(leftRunway.getThreshold()) + scaleMetersToPixels(leftRunway.getRecalculatedLDA() + 300), 230, "", "STRIP END: 60m", Color.BLACK );
      pane.getChildren().add(TORAarrow);
      pane.getChildren().add(TODAarrow);
      pane.getChildren().add(ASDAarrow);
      pane.getChildren().add(LDAarrow);
      pane.getChildren().add(resaArrow);
      pane.getChildren().add(stripendArrow4);
    } else {
      Arrow blastArrow = new Arrow(100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() - runway.getRightRunway().getThreshold()), 80, 100 + scaleMetersToPixels((length - runway.getRightRunway().getThreshold() - runway.getRightObstacleThreshold()) + 300), 80, "", ("BLAST PROTECTION: 300m"), Color.BLACK);
      Arrow blastArrow2 = new Arrow(100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() - runway.getRightRunway().getThreshold()), 130, 100 + scaleMetersToPixels((length - runway.getRightRunway().getThreshold() - runway.getRightObstacleThreshold()) + 300), 130, "", ("BLAST PROTECTION: 300m"), Color.BLACK);
      Arrow blastArrow3 = new Arrow(100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() - runway.getRightRunway().getThreshold()), 180, 100 + scaleMetersToPixels((length - runway.getRightRunway().getThreshold() - runway.getRightObstacleThreshold()) + 300), 180, "", ("BLAST PROTECTION: 300m"), Color.BLACK);
      Arrow TORAarrow = new Arrow(100 + scaleMetersToPixels((length - runway.getRightObstacleThreshold() - runway.getRightRunway().getThreshold()) + 300), 80, 100 + scaleMetersToPixels((length - runway.getRightRunway().getThreshold() - runway.getRightObstacleThreshold()) + 300) + scaleMetersToPixels(leftRunway.getRecalculatedTORA()), 80, "TORA: " + leftRunway.getRecalculatedTORA() + "m", "", secondTextColour);
      Arrow TODAarrow = new Arrow(100 + scaleMetersToPixels((length - runway.getRightObstacleThreshold() - runway.getRightRunway().getThreshold()) + 300), 130, 100 + scaleMetersToPixels((length - runway.getRightRunway().getThreshold() - runway.getRightObstacleThreshold() + 300) + leftRunway.getRecalculatedTODA()), 130, "TODA: " + leftRunway.getRecalculatedTODA() + "m", "", secondTextColour);
      Arrow ASDAarrow = new Arrow(100 + scaleMetersToPixels((length - runway.getRightObstacleThreshold() - runway.getRightRunway().getThreshold()) + 300), 180, 100 + scaleMetersToPixels((length - runway.getRightRunway().getThreshold() - runway.getRightObstacleThreshold() + 300) + leftRunway.getRecalculatedASDA()), 180, "ASDA: " + leftRunway.getRecalculatedASDA() + "m", "", secondTextColour);
      leftStartLinesDrawn.add(100 + scaleMetersToPixels((length - runway.getRightObstacleThreshold() - runway.getRightRunway().getThreshold()) + 300));
      leftStartLinesDrawn.add(100 + scaleMetersToPixels((length - runway.getRightObstacleThreshold() - runway.getRightRunway().getThreshold()) + 300));
      leftStartLinesDrawn.add(100 + scaleMetersToPixels((length - runway.getRightObstacleThreshold() - runway.getRightRunway().getThreshold()) + 300));
      leftEndLinesDrawn.add(100 + scaleMetersToPixels((length - runway.getRightObstacleThreshold() - runway.getRightRunway().getThreshold()) + 300) + scaleMetersToPixels(leftRunway.getRecalculatedTORA()));
      leftEndLinesDrawn.add(100 + scaleMetersToPixels((length - runway.getRightObstacleThreshold() - runway.getRightRunway().getThreshold()) + 300) + scaleMetersToPixels(leftRunway.getRecalculatedTODA()));
      leftEndLinesDrawn.add(100 + scaleMetersToPixels((length - runway.getRightObstacleThreshold() - runway.getRightRunway().getThreshold()) + 300) + scaleMetersToPixels(leftRunway.getRecalculatedTODA()));

      // Determine whether RESA, Blast protection, or slope distance is larger
      if (leftRunway.getRESA() > (runway.getObstacle().getHeight() * 50) && leftRunway.getRESA() > 300){
        Arrow resaArrow = new Arrow(100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold()), 230, 100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + 240), 230, "", "RESA: " + leftRunway.getRESA() + "m", Color.BLACK);
        Arrow LDAarrow = new Arrow(100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + 240), 230, 100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + 240) + scaleMetersToPixels(leftRunway.getRecalculatedLDA()), 230, "LDA: " + leftRunway.getRecalculatedLDA() + "m", "", secondTextColour);
        Arrow stripendArrow = new Arrow(100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + 240) + scaleMetersToPixels(leftRunway.getRecalculatedLDA()),230,100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + 240) + scaleMetersToPixels(leftRunway.getRecalculatedLDA() + 60),230, "", "STRIP END: 60m", Color.BLACK);
        pane.getChildren().add(stripendArrow);
        pane.getChildren().add(resaArrow);
        pane.getChildren().add(LDAarrow);
        leftStartLinesDrawn.add(100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + 240));
        leftEndLinesDrawn.add(100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + 240) + scaleMetersToPixels(leftRunway.getRecalculatedLDA()));
      } else if ((runway.getObstacle().getHeight() * 50) > 300) {
        Arrow slopeArrow = new Arrow(100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold()), 230, 100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + (runway.getObstacle().getHeight() * 50)), 230, "", "SLOPE CALCULATION: " + runway.getObstacle().getHeight() * 50 + "m", Color.BLACK);
        Arrow LDAarrow = new Arrow(100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + (runway.getObstacle().getHeight() * 50)), 230, 100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + (runway.getObstacle().getHeight() * 50)) + scaleMetersToPixels(leftRunway.getRecalculatedLDA()), 230, "LDA: " + leftRunway.getRecalculatedLDA() + "m", "", secondTextColour);
        Arrow stripendArrow = new Arrow(100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + (runway.getObstacle().getHeight() * 50)) + scaleMetersToPixels(leftRunway.getRecalculatedLDA()),230,100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + (runway.getObstacle().getHeight() * 50)) + scaleMetersToPixels(leftRunway.getRecalculatedLDA() + 60),230, "", "STRIP END: 60m", Color.BLACK);
        leftStartLinesDrawn.add(100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + (runway.getObstacle().getHeight() * 50)));
        leftEndLinesDrawn.add(100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + (runway.getObstacle().getHeight() * 50)) + scaleMetersToPixels(leftRunway.getRecalculatedLDA()));
        pane.getChildren().add(stripendArrow);
        pane.getChildren().add(slopeArrow);
        pane.getChildren().add(LDAarrow);
      } else {
        Arrow blastArrow4 = new Arrow(100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold()), 230, 100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + 300), 230, "", "BLAST PROTECTION: 300m", Color.BLACK);
        Arrow LDAarrow = new Arrow(100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + 300), 230, 100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + 300) + scaleMetersToPixels(leftRunway.getRecalculatedLDA()), 230, "LDA: " + leftRunway.getRecalculatedLDA() + "m", "", secondTextColour);
        Arrow stripendArrow = new Arrow(100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + 300) + scaleMetersToPixels(leftRunway.getRecalculatedLDA()),230,100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + 300) + scaleMetersToPixels(leftRunway.getRecalculatedLDA() + 60),230, "", "STRIP END: 60m", Color.BLACK);
        leftStartLinesDrawn.add(100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + 300));
        leftEndLinesDrawn.add(100 + scaleMetersToPixels(length - runway.getRightObstacleThreshold() + 300) + scaleMetersToPixels(leftRunway.getRecalculatedLDA()));
        pane.getChildren().add(stripendArrow);
        pane.getChildren().add(blastArrow4);
        pane.getChildren().add(LDAarrow);
      }

      pane.getChildren().add(TORAarrow);
      pane.getChildren().add(TODAarrow);
      pane.getChildren().add(ASDAarrow);
      pane.getChildren().add(blastArrow);
      pane.getChildren().add(blastArrow2);
      pane.getChildren().add(blastArrow3);

    }

    // Right arrows

    if (runway.getLeftObstacleThreshold() < runway.getRightObstacleThreshold()) {
      Arrow TORAarrow = new Arrow(700, 430, 700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA()), 430, "","TORA: " + rightRunway.getRecalculatedTORA() + "m",  secondTextColour);
      Arrow TODAarrow = new Arrow(700, 480, 700 - scaleMetersToPixels(rightRunway.getRecalculatedTODA()), 480, "","TODA: " + rightRunway.getRecalculatedTODA() + "m", secondTextColour);
      Arrow ASDAarrow = new Arrow(700, 530, 700 - scaleMetersToPixels(rightRunway.getRecalculatedASDA()), 530, "","ASDA: " + rightRunway.getRecalculatedASDA() + "m", secondTextColour);
      rightStartLinesDrawn.add(700);
      rightStartLinesDrawn.add(700);
      rightStartLinesDrawn.add(700);
      rightEndLinesDrawn.add(700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA()));
      rightEndLinesDrawn.add(700 - scaleMetersToPixels(rightRunway.getRecalculatedTODA()));
      rightEndLinesDrawn.add(700 - scaleMetersToPixels(rightRunway.getRecalculatedASDA()));
      if ((runway.getObstacle().getHeight() * 50) < 60) {
        Arrow resaArrow = new Arrow(700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA()), 430, 700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA() + 60), 430, "RESA: " + leftRunway.getRESA() + "m", "", Color.BLACK );
        Arrow resaArrow2 = new Arrow(700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA()), 480, 700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA() + 60), 480, "RESA: " + leftRunway.getRESA() + "m", "", Color.BLACK );
        Arrow resaArrow3 = new Arrow(700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA()), 530, 700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA() + 60), 530, "RESA: " + leftRunway.getRESA() + "m", "", Color.BLACK );
        Arrow stripendArrow = new Arrow(700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA() + 60), 430, 700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA() + 120), 430, "", "STRIP END: 60m", Color.BLACK );
        Arrow stripendArrow2 = new Arrow(700 - scaleMetersToPixels(rightRunway.getRecalculatedTODA() + 60), 480, 700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA() + 120), 480, "", "STRIP END: 60m", Color.BLACK );
        Arrow stripendArrow3 = new Arrow(700 - scaleMetersToPixels(rightRunway.getRecalculatedASDA() + 60), 530, 700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA() + 120), 530, "", "STRIP END: 60m", Color.BLACK );
        pane.getChildren().add(stripendArrow);
        pane.getChildren().add(stripendArrow2);
        pane.getChildren().add(stripendArrow3);
        pane.getChildren().add(resaArrow);
        pane.getChildren().add(resaArrow2);
        pane.getChildren().add(resaArrow3);
      } else {
        Arrow slopeArrow = new Arrow (700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA()), 430, 700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA() + (runway.getObstacle().getHeight() * 50)), 430, "SLOPE CALCULATION: " + runway.getObstacle().getHeight() * 50 + "m","", Color.BLACK);
        Arrow slopeArrow2 = new Arrow (700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA()), 480, 700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA() + (runway.getObstacle().getHeight() * 50)), 480, "SLOPE CALCULATION: " + runway.getObstacle().getHeight() * 50 + "m","", Color.BLACK);
        Arrow slopeArrow3 = new Arrow (700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA()), 530, 700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA() + (runway.getObstacle().getHeight() * 50)), 530, "SLOPE CALCULATION: " + runway.getObstacle().getHeight() * 50 + "m","", Color.BLACK);
        Arrow stripendArrow = new Arrow(700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA() + (runway.getObstacle().getHeight() * 50)), 430, 700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA() + (runway.getObstacle().getHeight() * 50) + 60), 430, "", "STRIP END: 60m", Color.BLACK );
        Arrow stripendArrow2 = new Arrow(700 - scaleMetersToPixels(rightRunway.getRecalculatedTODA() + (runway.getObstacle().getHeight() * 50)), 480, 700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA() + (runway.getObstacle().getHeight() * 50) + 60), 480, "", "STRIP END: 60m", Color.BLACK );
        Arrow stripendArrow3 = new Arrow(700 - scaleMetersToPixels(rightRunway.getRecalculatedASDA() + (runway.getObstacle().getHeight() * 50)), 530, 700 - scaleMetersToPixels(rightRunway.getRecalculatedTORA() + (runway.getObstacle().getHeight() * 50) + 60), 530, "", "STRIP END: 60m", Color.BLACK );
        pane.getChildren().add(stripendArrow);
        pane.getChildren().add(stripendArrow2);
        pane.getChildren().add(stripendArrow3);
        pane.getChildren().add(slopeArrow);
        pane.getChildren().add(slopeArrow2);
        pane.getChildren().add(slopeArrow3);
      }
      Arrow LDAarrow = new Arrow(700 - scaleMetersToPixels(rightRunway.getThreshold()), 580, 700 - scaleMetersToPixels(rightRunway.getThreshold() + rightRunway.getRecalculatedLDA()), 580, "", "\u200E                         LDA: " + rightRunway.getRecalculatedLDA() + "m", secondTextColour);
      rightStartLinesDrawn.add(700 - scaleMetersToPixels(rightRunway.getThreshold()));
      rightEndLinesDrawn.add(700 - scaleMetersToPixels(rightRunway.getThreshold() + rightRunway.getRecalculatedLDA()));
      Arrow resaArrow = new Arrow(700 - scaleMetersToPixels(rightRunway.getThreshold() + rightRunway.getRecalculatedLDA()), 580, 700 - scaleMetersToPixels(rightRunway.getThreshold() + rightRunway.getRecalculatedLDA() + 240), 580, "RESA: " + rightRunway.getRESA() + "m", "", Color.BLACK );
      Arrow stripendArrow4 = new Arrow(700 - scaleMetersToPixels(rightRunway.getThreshold() + rightRunway.getRecalculatedLDA() + 240), 580, 700 - scaleMetersToPixels(rightRunway.getThreshold() + rightRunway.getRecalculatedLDA() + 300), 580, "", "STRIP END: 60m", Color.BLACK );

      pane.getChildren().add(TORAarrow);
      pane.getChildren().add(TODAarrow);
      pane.getChildren().add(ASDAarrow);

      pane.getChildren().add(LDAarrow);
      pane.getChildren().add(resaArrow);
      pane.getChildren().add(stripendArrow4);
    } else {
      Arrow blastArrow = new Arrow(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() - runway.getLeftRunway().getThreshold()), 430, 700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() - runway.getLeftRunway().getThreshold() + 300), 430, "", ("BLAST PROTECTION: 300m"), Color.BLACK);
      Arrow blastArrow2 = new Arrow(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() - runway.getLeftRunway().getThreshold()), 480, 700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() - runway.getLeftRunway().getThreshold() + 300), 480, "", ("BLAST PROTECTION: 300m"), Color.BLACK);
      Arrow blastArrow3 = new Arrow(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() - runway.getLeftRunway().getThreshold()), 530, 700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() - runway.getLeftRunway().getThreshold() + 300), 530, "", ("BLAST PROTECTION: 300m"), Color.BLACK);
      Arrow TORAarrow = new Arrow(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() - runway.getLeftRunway().getThreshold() + 300), 430, 700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() - runway.getLeftRunway().getThreshold() + 300) - scaleMetersToPixels(rightRunway.getRecalculatedTORA()), 430, "TORA: " + rightRunway.getRecalculatedTORA() + "m", "", secondTextColour);
      Arrow TODAarrow = new Arrow(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() - runway.getLeftRunway().getThreshold() + 300), 480, 700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() - runway.getLeftRunway().getThreshold() + 300) - scaleMetersToPixels(rightRunway.getRecalculatedTODA()), 480, "TODA: " + rightRunway.getRecalculatedTODA() + "m", "", secondTextColour);
      Arrow ASDAarrow = new Arrow(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() - runway.getLeftRunway().getThreshold() + 300), 530, 700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() - runway.getLeftRunway().getThreshold() + 300) - scaleMetersToPixels(rightRunway.getRecalculatedASDA()), 530, "ASDA: " + rightRunway.getRecalculatedASDA() + "m", "", secondTextColour);

      rightStartLinesDrawn.add(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() - runway.getLeftRunway().getThreshold() + 300));
      rightStartLinesDrawn.add(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() - runway.getLeftRunway().getThreshold() + 300));
      rightStartLinesDrawn.add(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() - runway.getLeftRunway().getThreshold() + 300));
      rightEndLinesDrawn.add(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() - runway.getLeftRunway().getThreshold() + 300) - scaleMetersToPixels(rightRunway.getRecalculatedTORA()));
      rightEndLinesDrawn.add(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() - runway.getLeftRunway().getThreshold() + 300) - scaleMetersToPixels(rightRunway.getRecalculatedTODA()));
      rightEndLinesDrawn.add(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() - runway.getLeftRunway().getThreshold() + 300) - scaleMetersToPixels(rightRunway.getRecalculatedASDA()));

      // Determine whether RESA, Blast protection, or slope distance is larger
      if (rightRunway.getRESA() > (runway.getObstacle().getHeight() * 50) && rightRunway.getRESA() > 300){
        Arrow resaArrow = new Arrow(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold()), 580, 700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + 240), 580, "", "RESA: " + rightRunway.getRESA() + "m", Color.BLACK);
        Arrow LDAarrow = new Arrow(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + 240), 580, 700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + 240) - scaleMetersToPixels(rightRunway.getRecalculatedLDA()), 580, "LDA: " + rightRunway.getRecalculatedLDA() + "m", "", secondTextColour);
        Arrow stripendArrow = new Arrow(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + 240) - scaleMetersToPixels(rightRunway.getRecalculatedLDA()),580,700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + 300) - scaleMetersToPixels(rightRunway.getRecalculatedLDA()),580, "", "STRIP END: 60m", Color.BLACK);
        rightStartLinesDrawn.add(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + 240));
        rightEndLinesDrawn.add(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + 240) - scaleMetersToPixels(rightRunway.getRecalculatedLDA()));
        pane.getChildren().add(stripendArrow);
        pane.getChildren().add(resaArrow);
        pane.getChildren().add(LDAarrow);
      } else if ((runway.getObstacle().getHeight() * 50) > 300) {
        Arrow slopeArrow = new Arrow(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold()), 580, 700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + (runway.getObstacle().getHeight() * 50)), 580, "", "SLOPE CALCULATION: " + runway.getObstacle().getHeight() * 50 + "m", Color.BLACK);
        Arrow LDAarrow = new Arrow(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + (runway.getObstacle().getHeight() * 50)), 580, 700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + (runway.getObstacle().getHeight() * 50)) - scaleMetersToPixels(rightRunway.getRecalculatedLDA()), 580, "LDA: " + rightRunway.getRecalculatedLDA() + "m", "", secondTextColour);
        Arrow stripendArrow = new Arrow(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + (runway.getObstacle().getHeight() * 50)) - scaleMetersToPixels(rightRunway.getRecalculatedLDA()), 580, 700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + (runway.getObstacle().getHeight() * 50)) - scaleMetersToPixels(rightRunway.getRecalculatedLDA() + 60), 580, "", "STRIP END: 60m", Color.BLACK);
        rightStartLinesDrawn.add(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + (runway.getObstacle().getHeight() * 50)));
        rightEndLinesDrawn.add(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + (runway.getObstacle().getHeight() * 50)) - scaleMetersToPixels(rightRunway.getRecalculatedLDA()));
        pane.getChildren().add(stripendArrow);
        pane.getChildren().add(slopeArrow);
        pane.getChildren().add(LDAarrow);
      } else {
        Arrow blastArrow4 = new Arrow(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold()), 580, 700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + 300), 580, "", "BLAST PROTECTION: 300m", Color.BLACK);
        Arrow LDAarrow = new Arrow(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + 300), 580, 700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + 300) - scaleMetersToPixels(rightRunway.getRecalculatedLDA()), 580, "LDA: " + rightRunway.getRecalculatedLDA() + "m", "", secondTextColour);
        Arrow stripendArrow = new Arrow(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + 300) - scaleMetersToPixels(rightRunway.getRecalculatedLDA()),580,700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + 300) - scaleMetersToPixels(rightRunway.getRecalculatedLDA() + 60),580, "", "STRIP END: 60m", Color.BLACK);
        rightStartLinesDrawn.add(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + 300));
        rightEndLinesDrawn.add(700 - scaleMetersToPixels(length - runway.getLeftObstacleThreshold() + 300) - scaleMetersToPixels(rightRunway.getRecalculatedLDA()));
        pane.getChildren().add(stripendArrow);
        pane.getChildren().add(blastArrow4);
        pane.getChildren().add(LDAarrow);
      }
      pane.getChildren().add(TORAarrow);
      pane.getChildren().add(TODAarrow);
      pane.getChildren().add(ASDAarrow);
      pane.getChildren().add(blastArrow);
      pane.getChildren().add(blastArrow2);
      pane.getChildren().add(blastArrow3);
    }

    // Left threshold

    Rectangle leftThreshold = new Rectangle(100 + scaleMetersToPixels(leftRunway.getThreshold()) - 3, 280, 6, 100);
    leftThreshold.setFill(thresholdColour);
    pane.getChildren().add(leftThreshold);

    // Right threshold

    Rectangle rightThreshold = new Rectangle(700 - scaleMetersToPixels(rightRunway.getThreshold()) - 3, 280, 6, 100);
    rightThreshold.setFill(thresholdColour);
    pane.getChildren().add(rightThreshold);

    // Obstacle

    Rectangle obstacle = new Rectangle(100 + scaleMetersToPixels(runway.getLeftObstacleThreshold() + runway.getLeftRunway().getThreshold()), 330 + runway.getCenterlineThreshold() - (0.5 * runway.getObstacle().getWidth()), scaleMetersToPixels(length - (runway.getLeftObstacleThreshold() + runway.getLeftRunway().getThreshold()) - (runway.getRightObstacleThreshold() + runway.getRightRunway().getThreshold())), runway.getObstacle().getWidth());
    obstacle.setFill(Color.valueOf("AA4A44"));
    pane.getChildren().add(obstacle);

    pane.setMinSize(800, 660);
    pane.setMaxSize(800, 660);

    // Line drawing

    ArrayList<Integer> drawnLines = new ArrayList<>();

    for (int i = 0; i < 4; i++) {
      if (!drawnLines.contains(leftStartLinesDrawn.get(i))){
        Line line = new Line(leftStartLinesDrawn.get(i), 230 - ((3-i) * 50), leftStartLinesDrawn.get(i), 380);
        line.setStroke(secondTextColour);
        line.getStrokeDashArray().addAll(10d);
        pane.getChildren().add(21, line);
        drawnLines.add(leftStartLinesDrawn.get(i));
      }
    }

    for (int i = 0; i < 4; i++) {
      if (!drawnLines.contains(leftEndLinesDrawn.get(i))){
        Line line = new Line(leftEndLinesDrawn.get(i), 230 - ((3-i) * 50), leftEndLinesDrawn.get(i), 380);
        line.setStroke(secondTextColour);
        line.getStrokeDashArray().addAll(10d);
        pane.getChildren().add(21, line);
        drawnLines.add(leftEndLinesDrawn.get(i));
      }
    }

    ArrayList<Integer> rightDrawnLines = new ArrayList<>();

    for (int i = 0; i < 4; i++) {
      if (!rightDrawnLines.contains(rightStartLinesDrawn.get(i))) {
        Line line;
        if (drawnLines.contains(rightStartLinesDrawn.get(i))){
          line = new Line(rightStartLinesDrawn.get(i), 430 + (i * 50), rightStartLinesDrawn.get(i), 380);
        } else {
          line = new Line(rightStartLinesDrawn.get(i), 430 + (i * 50), rightStartLinesDrawn.get(i), 280);
        }
        line.setStroke(secondTextColour);
        line.getStrokeDashArray().addAll(10d);
        pane.getChildren().add(21, line);
        rightDrawnLines.add(rightStartLinesDrawn.get(i));
      }
    }

    for (int i = 0; i < 4; i++) {
      if (!rightDrawnLines.contains(rightEndLinesDrawn.get(i))) {
        Line line;
        if (drawnLines.contains(rightEndLinesDrawn.get(i))){
          line = new Line(rightEndLinesDrawn.get(i), 430 + (i * 50), rightEndLinesDrawn.get(i), 380);
        } else {
          line = new Line(rightEndLinesDrawn.get(i), 430 + (i * 50), rightEndLinesDrawn.get(i), 280);
        }
        line.setStroke(secondTextColour);
        line.getStrokeDashArray().addAll(10d);
        pane.getChildren().add(21, line);
        rightDrawnLines.add(rightEndLinesDrawn.get(i));
      }
    }

    // Runway text

    Text leftRunwayText = new Text(100, 340, leftRunway.getDesignation());
    leftRunwayText.setFill(Color.WHITE);
    leftRunwayText.setTextAlignment(TextAlignment.LEFT);
    leftRunwayText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 20));
    leftRunwayText.setRotate(90);
    pane.getChildren().add(leftRunwayText);

    Text rightRunwayText = new Text(665, 335, rightRunway.getDesignation());
    rightRunwayText.setFill(Color.WHITE);
    rightRunwayText.setTextAlignment(TextAlignment.CENTER);
    rightRunwayText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 20));
    rightRunwayText.setRotate(270);
    pane.getChildren().add(rightRunwayText);

    return pane;

  }

  private int scaleMetersToPixels (int len){
    float constant = (float) 600 / length;
    return (Math.round(constant * len));
  }

}
