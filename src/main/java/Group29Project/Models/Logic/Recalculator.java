package Group29Project.Models.Logic;


import Group29Project.Models.LogicalRunway;
import Group29Project.Models.Obstacle;
import Group29Project.Models.Runway;
import java.util.ArrayList;
import java.util.Timer;

public class Recalculator {

  private static final Integer BLAST_PROTECTION = 300;
  private static final Integer STRIPEND = 60;
  private static final Integer SLOPEANGLE = 50;

  private int length;
  private LogicalRunway lRunway;
  private  LogicalRunway rRunway;
  private Obstacle obstacle;
  private Integer lThresholdDistance, rThresholdDistance;
  private ArrayList<String> TORAbreakdown, TODAbreakdown, ASDAbreakdown, LDAbreakdown;

  public Recalculator(Runway runway, Obstacle obstacle, Integer lThresholdDistance, Integer rThresholdDistance){

    this.length = runway.getLength();
    this.lRunway = runway.getLeftRunway();
    this.rRunway = runway.getRightRunway();
    this.obstacle = obstacle;
    this.lThresholdDistance = lThresholdDistance;
    this.rThresholdDistance = rThresholdDistance;

    this.TORAbreakdown = new ArrayList<>();
    this.TODAbreakdown = new ArrayList<>();
    this.ASDAbreakdown = new ArrayList<>();
    this.LDAbreakdown = new ArrayList<>();

    calculateDirection();

    runway.setTORAbreakdown(this.TORAbreakdown);
    runway.setTODAbreakdown(this.TODAbreakdown);
    runway.setASDAbreakdown(this.ASDAbreakdown);
    runway.setLDAbreakdown(this.LDAbreakdown);
  }

  // Determine which end of the runway the obstacle is

  private void calculateDirection(){

    String TOALO = " (Take Off Away, Landing Over)";
    String TOTLT = " (Take Off Towards, Landing Towards)";

    if (lThresholdDistance > rThresholdDistance){
      // Left runway
      TORAbreakdown.add(lRunway.getDesignation() + TOTLT);
      TODAbreakdown.add(lRunway.getDesignation() + TOTLT);
      ASDAbreakdown.add(lRunway.getDesignation() + TOTLT);
      LDAbreakdown.add(lRunway.getDesignation() + TOTLT);
      calculateTOTLT(lRunway, lThresholdDistance);

      // Right runway
      TORAbreakdown.add(rRunway.getDesignation() + TOALO);
      TODAbreakdown.add(rRunway.getDesignation() + TOALO);
      ASDAbreakdown.add(rRunway.getDesignation() + TOALO);
      LDAbreakdown.add(rRunway.getDesignation() + TOALO);
      calculateTOALO(rRunway, length - lThresholdDistance);
    }
    else {
      // Right runway
      TORAbreakdown.add(rRunway.getDesignation() + TOTLT);
      TODAbreakdown.add(rRunway.getDesignation() + TOTLT);
      ASDAbreakdown.add(rRunway.getDesignation() + TOTLT);
      LDAbreakdown.add(rRunway.getDesignation() + TOTLT);
      calculateTOTLT(rRunway, rThresholdDistance);

      // Left runway
      TORAbreakdown.add(lRunway.getDesignation() + TOALO);
      TODAbreakdown.add(lRunway.getDesignation() + TOALO);
      ASDAbreakdown.add(lRunway.getDesignation() + TOALO);
      LDAbreakdown.add(lRunway.getDesignation() + TOALO);
      calculateTOALO(lRunway, length - rThresholdDistance);
    }
  }

  // Calculate take over away, land over

  private void calculateTOALO(LogicalRunway runway, Integer thresholdDistance) {

    runway.setRecalculatedTORA(runway.getTORA() - BLAST_PROTECTION - thresholdDistance - runway.getThreshold());
    TORAbreakdown.add("TORA = Original TORA - Blast Protection - Distance From Threshold - Displaced Threshold");
    TORAbreakdown.add("");
    TORAbreakdown.add("Original TORA: " + runway.getTORA());
    TORAbreakdown.add("Blast Protection: " + 300);
    TORAbreakdown.add("Distance From Threshold: " + thresholdDistance);
    TORAbreakdown.add("Displaced Threshold: " + runway.getThreshold());
    TORAbreakdown.add("");
    TORAbreakdown.add("= " + runway.getTORA() + " - " + BLAST_PROTECTION + " - " +  thresholdDistance + " - " + runway.getThreshold());
    TORAbreakdown.add("= " + runway.getRecalculatedTORA() + "m");
    TORAbreakdown.add("");

    runway.setRecalculatedTODA(runway.getRecalculatedTORA() + runway.getClearway());
    TODAbreakdown.add("(R) TORA + Clearway");
    TODAbreakdown.add("");
    TODAbreakdown.add("TORA: " + runway.getRecalculatedTORA());
    TODAbreakdown.add("Clearway: " + runway.getClearway());
    TODAbreakdown.add("");
    TODAbreakdown.add("= " + runway.getRecalculatedTORA() + " + " + runway.getClearway());
    TODAbreakdown.add("= " + runway.getRecalculatedTODA() + "m");
    TODAbreakdown.add("");

    runway.setRecalculatedASDA(runway.getRecalculatedTORA() + runway.getStopway());
    ASDAbreakdown.add("ASDA = (R) TORA + Stopway");
    ASDAbreakdown.add("");
    ASDAbreakdown.add("TORA: " + runway.getRecalculatedTORA());
    ASDAbreakdown.add("Stopway: " + runway.getStopway());
    ASDAbreakdown.add("");
    ASDAbreakdown.add("= " + runway.getRecalculatedTORA() + " + " + runway.getStopway());
    ASDAbreakdown.add("= " + runway.getRecalculatedASDA() + "m");
    ASDAbreakdown.add("");

    // Determine whether RESA, Blast protection, or slope distance is larger
    if (runway.getRESA() > (obstacle.getHeight() * SLOPEANGLE) && runway.getRESA() > BLAST_PROTECTION){
      runway.setRecalculatedLDA(runway.getLDA() - thresholdDistance - STRIPEND - runway.getRESA());
      LDAbreakdown.add("LDA = Original LDA - Distance From Threshold - RESA – Strip End");
      LDAbreakdown.add("");
      LDAbreakdown.add("Original LDA: " + runway.getLDA());
      LDAbreakdown.add("Distance From Threshold: " + thresholdDistance);
      LDAbreakdown.add("RESA: " + runway.getRESA());
      LDAbreakdown.add("Strip End: " + STRIPEND);
      LDAbreakdown.add("");
      LDAbreakdown.add("= " + runway.getLDA() + " - " + thresholdDistance + " - " + runway.getRESA() + " - " + STRIPEND);
    } else if ((obstacle.getHeight() * SLOPEANGLE) > BLAST_PROTECTION) {
      runway.setRecalculatedLDA(runway.getLDA() - thresholdDistance - STRIPEND - (obstacle.getHeight() * SLOPEANGLE));
      LDAbreakdown.add("LDA = Original LDA - Distance From Threshold - Slope Calculation – Strip End");
      LDAbreakdown.add("");
      LDAbreakdown.add("Original LDA: " + runway.getLDA());
      LDAbreakdown.add("Distance From Threshold: " + thresholdDistance);
      LDAbreakdown.add("Slope Calculation: (" + obstacle.getHeight() + "*" + SLOPEANGLE + ")");
      LDAbreakdown.add("Strip End: " + STRIPEND);
      LDAbreakdown.add("");
      LDAbreakdown.add("= " + runway.getLDA()  + " - (" + obstacle.getHeight() + "*" + SLOPEANGLE + ")" + " - " + thresholdDistance + " - " + STRIPEND);
    } else {
      runway.setRecalculatedLDA(runway.getLDA() - thresholdDistance - STRIPEND - BLAST_PROTECTION);
      LDAbreakdown.add("LDA = Original LDA - Distance From Threshold - Blast Protection – Strip End");
      LDAbreakdown.add("");
      LDAbreakdown.add("Original LDA: " + runway.getLDA());
      LDAbreakdown.add("Distance From Threshold: " + thresholdDistance);
      LDAbreakdown.add("Blast Protection: " + BLAST_PROTECTION);
      LDAbreakdown.add("Strip End: " + STRIPEND);
      LDAbreakdown.add("");
      LDAbreakdown.add("= " + runway.getLDA() + " - " + thresholdDistance + " - " + BLAST_PROTECTION + " - " + STRIPEND );
    }
    LDAbreakdown.add("= " + runway.getRecalculatedLDA() + "m");
    LDAbreakdown.add("");
  }

  // Calculate take over towards, land towards

  private void calculateTOTLT(LogicalRunway runway, Integer thresholdDistance){

    // Determine whether RESA or Slope distance is larger
    if (runway.getRESA() > (obstacle.getHeight() * SLOPEANGLE)){
      runway.setRecalculatedTORA(thresholdDistance + runway.getThreshold() - runway.getRESA() - STRIPEND);
      TORAbreakdown.add("TORA = Distance from Threshold + Displaced Threshold - RESA - Strip End");
      TORAbreakdown.add("");
      TORAbreakdown.add("Distance from Threshold: " + thresholdDistance);
      TORAbreakdown.add("Displaced Threshold: " + runway.getThreshold());
      TORAbreakdown.add("RESA: " + runway.getRESA());
      TORAbreakdown.add("Strip End: " + 60);
      TORAbreakdown.add("");
      TORAbreakdown.add("= " + thresholdDistance + " + " + runway.getThreshold() + " - " + runway.getRESA() + " - " + STRIPEND);
    } else {
      runway.setRecalculatedTORA(thresholdDistance + runway.getThreshold() - (obstacle.getHeight() * SLOPEANGLE) - STRIPEND);
      TORAbreakdown.add("TORA = Distance from Threshold + Displaced Threshold - Slope Calculation - Strip End");
      TORAbreakdown.add("");
      TORAbreakdown.add("Distance from Threshold: " + thresholdDistance);
      TORAbreakdown.add("Displaced Threshold: " + runway.getThreshold());
      TORAbreakdown.add("Slope Calculation: (" + obstacle.getHeight() + " * " + SLOPEANGLE + ")");
      TORAbreakdown.add("Strip End: " + 60);
      TORAbreakdown.add("");
      TORAbreakdown.add("= " + thresholdDistance + " + " + runway.getThreshold() + " - (" + obstacle.getHeight() + " * " + SLOPEANGLE + ") - " + STRIPEND);
    }
    TORAbreakdown.add("= " + runway.getRecalculatedTORA() + "m");
    TORAbreakdown.add("");

    runway.setRecalculatedTODA(runway.getRecalculatedTORA());
    TODAbreakdown.add("(R) TORA");
    TODAbreakdown.add("");
    TODAbreakdown.add("TORA: " + runway.getRecalculatedTORA());
    TODAbreakdown.add("");
    TODAbreakdown.add("= " + runway.getRecalculatedTORA());
    TODAbreakdown.add("= " + runway.getRecalculatedTODA() + "m");
    TODAbreakdown.add("");

    runway.setRecalculatedASDA(runway.getRecalculatedTORA());
    ASDAbreakdown.add("(R) TORA");
    ASDAbreakdown.add("");
    ASDAbreakdown.add("TORA: " + runway.getRecalculatedTORA());
    ASDAbreakdown.add("");
    ASDAbreakdown.add("= " + runway.getRecalculatedTORA());
    ASDAbreakdown.add("= " + runway.getRecalculatedASDA() + "m");
    ASDAbreakdown.add("");

    runway.setRecalculatedLDA(thresholdDistance - STRIPEND - runway.getRESA());
    LDAbreakdown.add("LDA = Distance From Threshold - RESA - Strip End");
    LDAbreakdown.add("");
    LDAbreakdown.add("Distance From Threshold: " + thresholdDistance);
    LDAbreakdown.add("RESA: " + runway.getRESA());
    LDAbreakdown.add("Strip End: 60");
    LDAbreakdown.add("");
    LDAbreakdown.add("= " + thresholdDistance + " - " + runway.getRESA() + " - " + STRIPEND);
    LDAbreakdown.add("= " + runway.getRecalculatedLDA() + "m");
    LDAbreakdown.add("");
  }
}
