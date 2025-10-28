package Group29Project.Models.FileHandling;

import Group29Project.Models.Airport;
//import com.itextpdf.layout.element.Image;
import Group29Project.Models.LogicalRunway;
import Group29Project.Models.Obstacle;
import Group29Project.Models.Runway;
import Group29Project.View.AlertHandler;
import Group29Project.View.Components.Notifications;
import Group29Project.View.Visualisation.Key;
import Group29Project.View.Visualisation.SideOnView;
import Group29Project.View.Visualisation.TopdownView;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import com.itextpdf.text.Image;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;

public class PDFexport {

  public PDFexport(File location, String name, Runway runway){
    try {
      Document document = new Document();
      PdfWriter.getInstance(document, new FileOutputStream(location + "/" + name + ".pdf"));
      document.open();

      Font heading = FontFactory.getFont("Arial", 20, Font.BOLD);
      Font subheading = FontFactory.getFont("Arial", 16, Font.BOLD);
      Font text = FontFactory.getFont("Arial", 14, Font.NORMAL);

      Obstacle obstacle = runway.getObstacle();
      Airport airport = runway.getAirport();
      LogicalRunway leftRunway = runway.getLeftRunway();
      LogicalRunway rightRunway = runway.getRightRunway();
      LocalDateTime myDateObj = LocalDateTime.now();
      DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
      String formattedDate = myDateObj.format(myFormatObj);

      PdfPTable title = new PdfPTable(1);
      title.setHorizontalAlignment(Element.ALIGN_LEFT);
      title.setSpacingBefore(10);
      title.setSpacingAfter(10);
      title.completeRow();
      title.getDefaultCell().setBorder(0);
      title.addCell(new Paragraph("Runway Re-Declaration Report", heading));
      title.addCell(new Paragraph("Generated: " + formattedDate, subheading));
      document.add(title);


      PdfPCell airportDetailsTitle = new PdfPCell(new Phrase("Airport details: ", subheading));
      airportDetailsTitle.setColspan(2);
      airportDetailsTitle.setPaddingBottom(8);
      airportDetailsTitle.setBorder(0);
      PdfPTable airportDetails = new PdfPTable(2);
      airportDetails.setHorizontalAlignment(0);
      airportDetails.getDefaultCell().setBorder(0);
      airportDetails.setSpacingBefore(10);
      airportDetails.setSpacingAfter(10);
      airportDetails.addCell(airportDetailsTitle);
      airportDetails.addCell(new Phrase("Name:", text));
      airportDetails.addCell(airport.getName());
      airportDetails.addCell(new Phrase("Runway:", text));
      airportDetails.addCell(runway.getInfo());
      airportDetails.addCell(new Phrase("Length:", text));
      airportDetails.addCell(runway.getLength() + "m");
      airportDetails.addCell(new Phrase("Blast Protection:", text));
      airportDetails.addCell("300m");
      airportDetails.addCell(new Phrase("Strip End:", text));
      airportDetails.addCell("60m");

      PdfPCell obstacleDetailsTitle = new PdfPCell(new Phrase("Obstacle details:", subheading));
      obstacleDetailsTitle.setColspan(2);
      obstacleDetailsTitle.setPaddingBottom(8);
      obstacleDetailsTitle.setBorder(0);
      PdfPTable obstacleDetails = new PdfPTable(2);
      obstacleDetails.setHorizontalAlignment(0);
      obstacleDetails.getDefaultCell().setBorder(0);
      obstacleDetails.setSpacingBefore(10);
      obstacleDetails.setSpacingAfter(10);
      obstacleDetails.addCell(obstacleDetailsTitle);
      obstacleDetails.addCell(new Phrase("Name:", text));
      obstacleDetails.addCell(obstacle.getName());
      obstacleDetails.addCell(new Phrase("Width:", text));
      obstacleDetails.addCell(obstacle.getWidth().toString()+ "m");
      obstacleDetails.addCell(new Phrase("Height:", text));
      obstacleDetails.addCell(obstacle.getHeight().toString()+ "m");
      obstacleDetails.addCell(new Phrase("(Distance) Left Threshold:", text));
      obstacleDetails.addCell(runway.getLeftObstacleThreshold() + "m");
      obstacleDetails.addCell(new Phrase("(Distance) Right Threshold:", text));
      obstacleDetails.addCell(runway.getRightObstacleThreshold() + "m");
      obstacleDetails.addCell(new Phrase("(Distance) centerline", text));
      obstacleDetails.addCell(runway.getCenterlineThreshold() + "m");
      document.add(airportDetails);
      document.add(obstacleDetails);

      PdfPCell redeclaredTitle = new PdfPCell(new Phrase("Original And Re-Declared Distances:", subheading));
      redeclaredTitle.setColspan(5);
      redeclaredTitle.setPaddingBottom(8);
      redeclaredTitle.setBorder(0);
      PdfPCell originalTitle = new PdfPCell(new Phrase("Original Distances:", subheading));
      originalTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
      originalTitle.setColspan(5);
      originalTitle.setPaddingBottom(8);
      PdfPCell recalculatedTitle = new PdfPCell(new Phrase("Original Distances:", subheading));
      recalculatedTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
      recalculatedTitle.setColspan(5);
      recalculatedTitle.setPaddingBottom(8);

      PdfPTable redeclaredTable = new PdfPTable(5);
      redeclaredTable.setWidths(new int[] {2,2,2,2,2});
      redeclaredTable.setHorizontalAlignment(0);
      redeclaredTable.setWidthPercentage(100);
      redeclaredTable.setSpacingBefore(10);
      redeclaredTable.setSpacingAfter(10);

      redeclaredTable.addCell(redeclaredTitle);
      redeclaredTable.addCell("");
      redeclaredTable.addCell(new Phrase("TORA:", text));
      redeclaredTable.addCell(new Phrase("TODA:", text));
      redeclaredTable.addCell(new Phrase("ASDA:", text));
      redeclaredTable.addCell(new Phrase("LDA:", text));

      redeclaredTable.addCell(originalTitle);
      redeclaredTable.addCell(new Phrase(leftRunway.getDesignation() + ":", text));
      redeclaredTable.addCell(leftRunway.getTORA().toString() + "m");
      redeclaredTable.addCell(leftRunway.getTODA().toString() + "m");
      redeclaredTable.addCell(leftRunway.getASDA().toString() + "m");
      redeclaredTable.addCell(leftRunway.getLDA().toString() + "m");
      redeclaredTable.addCell(new Phrase(rightRunway.getDesignation() + ":", text));
      redeclaredTable.addCell(rightRunway.getTORA().toString() + "m");
      redeclaredTable.addCell(rightRunway.getTODA().toString() + "m");
      redeclaredTable.addCell(rightRunway.getASDA().toString() + "m");
      redeclaredTable.addCell(rightRunway.getLDA().toString() + "m");

      redeclaredTable.addCell(recalculatedTitle);
      redeclaredTable.addCell(new Phrase(leftRunway.getDesignation() + ":", text));
      redeclaredTable.addCell(leftRunway.getRecalculatedTORA().toString() + "m");
      redeclaredTable.addCell(leftRunway.getRecalculatedTODA().toString() + "m");
      redeclaredTable.addCell(leftRunway.getRecalculatedASDA().toString() + "m");
      redeclaredTable.addCell(leftRunway.getRecalculatedLDA().toString() + "m");
      redeclaredTable.addCell(new Phrase(rightRunway.getDesignation() + ":", text));
      redeclaredTable.addCell(rightRunway.getRecalculatedTORA().toString() + "m");
      redeclaredTable.addCell(rightRunway.getRecalculatedTODA().toString() + "m");
      redeclaredTable.addCell(rightRunway.getRecalculatedASDA().toString() + "m");
      redeclaredTable.addCell(rightRunway.getRecalculatedLDA().toString() + "m");
      document.add(redeclaredTable);
      document.newPage();

      PdfPCell calculationBreakdownTitle = new PdfPCell(new Phrase("Calculation Breakdowns:", subheading));
      calculationBreakdownTitle.setHorizontalAlignment(0);
      calculationBreakdownTitle.setBorder(0);
      calculationBreakdownTitle.setColspan(1);
      calculationBreakdownTitle.setPaddingBottom(8);

      PdfPTable calculationBreakdownTable = new PdfPTable(1);
      calculationBreakdownTable.getDefaultCell().setBorder(0);
      calculationBreakdownTable.setHorizontalAlignment(0);
      calculationBreakdownTable.setWidthPercentage(100);
      calculationBreakdownTable.setSpacingBefore(10);
      calculationBreakdownTable.setSpacingAfter(10);

      calculationBreakdownTable.addCell(calculationBreakdownTitle);

      calculationBreakdownTable.addCell(new Phrase("TORA: ", text));
      ArrayList<String> TORAbreakdown = runway.getTORAbreakdown();
      for (String line : TORAbreakdown){
        if (line == ""){
          calculationBreakdownTable.addCell("ㅤ");
        }
        calculationBreakdownTable.addCell(line);
      }
      calculationBreakdownTable.addCell(new Phrase("TODA: ", text));
      ArrayList<String> TODAbreakdown = runway.getTODAbreakdown();
      for (String line : TODAbreakdown){
        if (line == ""){
          calculationBreakdownTable.addCell("ㅤ");
        }
        calculationBreakdownTable.addCell(line);
      }

      PdfPCell calculationBreakdownTitle2 = new PdfPCell(new Phrase("Calculation Breakdowns:", subheading));
      calculationBreakdownTitle2.setHorizontalAlignment(0);
      calculationBreakdownTitle2.setBorder(0);
      calculationBreakdownTitle2.setColspan(1);
      calculationBreakdownTitle2.setPaddingBottom(8);

      PdfPTable calculationBreakdownTable2 = new PdfPTable(1);
      calculationBreakdownTable2.getDefaultCell().setBorder(0);
      calculationBreakdownTable2.setHorizontalAlignment(0);
      calculationBreakdownTable2.setWidthPercentage(100);
      calculationBreakdownTable2.setSpacingBefore(10);
      calculationBreakdownTable2.setSpacingAfter(10);

      calculationBreakdownTable2.addCell(calculationBreakdownTitle2);
      calculationBreakdownTable2.addCell(new Phrase("ASDA: ", text));
      ArrayList<String> ASDAbreakdown = runway.getASDAbreakdown();
      for (String line : ASDAbreakdown){
        if (line == ""){
          calculationBreakdownTable2.addCell("ㅤ");
        }
        calculationBreakdownTable2.addCell(line);
      }
      calculationBreakdownTable2.addCell(new Phrase("LDA: ", text));
      ArrayList<String> LDAbreakdown = runway.getLDAbreakdown();
      for (String line : LDAbreakdown){
        if (line == ""){
          calculationBreakdownTable2.addCell("ㅤ");
        }
        calculationBreakdownTable2.addCell(line);
      }

      document.add(calculationBreakdownTable);
      document.newPage();
      document.add(calculationBreakdownTable2);
      document.newPage();


      PdfPCell topDownTitle = new PdfPCell(new Phrase("Top-Down Visualisation:", subheading));
      topDownTitle.setHorizontalAlignment(0);
      topDownTitle.setBorder(0);
      topDownTitle.setColspan(1);
      topDownTitle.setPaddingBottom(8);

      PdfPTable topDownTable = new PdfPTable(1);
      topDownTable.getDefaultCell().setBorder(0);
      topDownTable.setHorizontalAlignment(0);
      topDownTable.setWidthPercentage(100);
      topDownTable.setSpacingBefore(10);
      topDownTable.setSpacingAfter(10);

      Pane topDown = new TopdownView().getRunwayDisplay(runway, true, false);
      topDown.getChildren().add(new Key().getKey(false));
      WritableImage topDownImg = topDown.snapshot(new SnapshotParameters(), null);
      BufferedImage topDownBufferedImg = SwingFXUtils.fromFXImage(topDownImg, null);
      ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
      ImageIO.write(topDownBufferedImg, "png", byteArrayOutputStream1);
      Image topDownImage = Image.getInstance(byteArrayOutputStream1.toByteArray());

      topDownImage.setAlignment(Element.ALIGN_CENTER);
      topDownImage.scaleAbsolute(topDownImage.getPlainWidth()*0.6f, topDownImage.getPlainHeight()*0.6f);
      topDownTable.addCell(topDownTitle);
      topDownTable.addCell(topDownImage);
      document.add(topDownTable);
      document.newPage();


      PdfPCell sideOnTitle = new PdfPCell(new Phrase("Side-On Visualisation:", subheading));
      sideOnTitle.setHorizontalAlignment(0);
      sideOnTitle.setBorder(0);
      sideOnTitle.setColspan(1);
      sideOnTitle.setPaddingBottom(8);

      PdfPTable sideOnTable = new PdfPTable(1);
      sideOnTable.getDefaultCell().setBorder(0);
      sideOnTable.setHorizontalAlignment(0);
      sideOnTable.setWidthPercentage(100);
      sideOnTable.setSpacingBefore(10);
      sideOnTable.setSpacingAfter(10);

      WritableImage sideOnImg = new SideOnView().getRunwayDisplay(runway, true, false).snapshot(new SnapshotParameters(), null);
      BufferedImage sideOnBufferedImg = SwingFXUtils.fromFXImage(sideOnImg, null);
      ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
      ImageIO.write(sideOnBufferedImg, "png", byteArrayOutputStream2);
      Image sideOnImage = Image.getInstance(byteArrayOutputStream2.toByteArray());

      sideOnImage.setAlignment(Element.ALIGN_CENTER);
      sideOnImage.scaleAbsolute(sideOnImage.getPlainWidth()*0.6f, sideOnImage.getPlainHeight()*0.6f);
      sideOnTable.addCell(sideOnTitle);
      sideOnTable.addCell(sideOnImage);
      document.add(sideOnTable);

      document.close();

      File q = new File (name);
      q.delete();

      new AlertHandler("Report generated and exported successfully.");
      Notifications.addNotification("Report generated and exported successfully.");
    } catch (Exception e) {
      new AlertHandler("Export Error: Error generating or exporting report.");
    }
  }

}
