package Group29Project.Models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "logicalrunway")
@XmlType(propOrder = {"TORA", "TODA", "ASDA", "LDA", "threshold", "clearway", "stopway"})
@XmlAccessorType(XmlAccessType.FIELD)
public class LogicalRunway {

  @XmlTransient
  private String designation;
  @XmlElement(name = "tora")
  private Integer TORA;
  @XmlElement(name = "toda")
  private Integer TODA;
  @XmlElement(name = "asda")
  private Integer ASDA;
  @XmlElement(name = "lda")
  private Integer LDA;
  @XmlTransient
  private Integer RESA;
  @XmlElement(name = "threshold")
  private Integer threshold;
  @XmlElement(name = "clearway")
  private Integer clearway;
  @XmlElement(name = "stopway")
  private Integer stopway;

  @XmlTransient
  private Integer recalculatedTORA, recalculatedTODA, recalculatedASDA, recalculatedLDA;

  public LogicalRunway(){}

  public LogicalRunway(Integer TORA, Integer TODA, Integer ASDA, Integer LDA, Integer threshold, Integer clearway, Integer stopway){
    this.TORA = TORA;
    this.TODA = TODA;
    this.ASDA = ASDA;
    this.LDA = LDA;
    this.RESA = 240;
    this.threshold = threshold;
    this.clearway = clearway;
    this.stopway = stopway;
  }

  public Integer getASDA() {
    return ASDA;
  }

  public Integer getClearway() {
    return clearway;
  }

  public Integer getLDA() {
    return LDA;
  }

  public Integer getStopway() {
    return stopway;
  }

  public Integer getThreshold() {
    return threshold;
  }

  public Integer getTODA() {
    return TODA;
  }

  public Integer getTORA() {
    return TORA;
  }

  public String getDesignation() {
    return designation;
  }

  public void setASDA(Integer ASDA) {
    this.ASDA = ASDA;
  }

  public void setClearway(Integer clearway) {
    this.clearway = clearway;
  }

  public void setDesignation(String designation) {
    this.designation = designation;
  }

  public void setLDA(Integer LDA) {
    this.LDA = LDA;
  }

  public void setStopway(Integer stopway) {
    this.stopway = stopway;
  }

  public void setThreshold(Integer threshold) {
    this.threshold = threshold;
  }

  public void setTODA(Integer TODA) {
    this.TODA = TODA;
  }

  public void setRESA(Integer RESA) {
    this.RESA = RESA;
  }

  public Integer getRESA() {
    return RESA;
  }

  public Integer getRecalculatedASDA() {
    return recalculatedASDA;
  }

  public Integer getRecalculatedLDA() {
    return recalculatedLDA;
  }

  public Integer getRecalculatedTODA() {
    return recalculatedTODA;
  }

  public Integer getRecalculatedTORA() {
    return recalculatedTORA;
  }

  public void setRecalculatedASDA(Integer recalculatedASDA) {
    this.recalculatedASDA = recalculatedASDA;
  }

  public void setRecalculatedLDA(Integer recalculatedLDA) {
    this.recalculatedLDA = recalculatedLDA;
  }

  public void setRecalculatedTODA(Integer recalculatedTODA) {
    this.recalculatedTODA = recalculatedTODA;
  }

  public void setRecalculatedTORA(Integer recalculatedTORA) {
    this.recalculatedTORA = recalculatedTORA;
  }
}


