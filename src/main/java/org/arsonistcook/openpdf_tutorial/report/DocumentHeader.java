package org.arsonistcook.openpdf_tutorial.report;

import static org.arsonistcook.openpdf_tutorial.report.ConfigurationConstants.BORDER_COLOR;
import static org.arsonistcook.openpdf_tutorial.report.ConfigurationConstants.ERROR_COLOR;
import static org.arsonistcook.openpdf_tutorial.report.ConfigurationConstants.SUCCESS_COLOR;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class DocumentHeader {
  private static final float[] COLUMN_WIDTH = {15f, 36f, 16f, 16f};

  private PdfPTable table;
  private Image nttData;
  private Image claro;
  private Image solar;
  private Image successIcon;
  private Image errorIcon;
  private Font title;
  private Font label;
  private Font field;
  private Font successFont;
  private Font errorFont;

  public DocumentHeader() throws BadElementException, IOException {
    // configura as fontes usadas
    title = ResourcesUtils.getBaseFontBold(14);
    label = ResourcesUtils.getBaseFontBold(8);
    field = ResourcesUtils.getBaseFont(9);
    successFont = ResourcesUtils.getBaseFontBold(9);
    successFont.setColor(SUCCESS_COLOR);
    errorFont = ResourcesUtils.getBaseFontBold(9);
    errorFont.setColor(ERROR_COLOR);

    // Configura as figuras usadas
    claro = ResourcesUtils.loadImageResource("Claro.png");
    claro.scaleToFit(25, 25);
    nttData = ResourcesUtils.loadImageResource("NTTData.png");
    nttData.scaleToFit(60, 20);
    solar = ResourcesUtils.loadImageResource("Solar.png");
    solar.scaleToFit(35, 25);
    successIcon = ResourcesUtils.loadImageResource("success.png");
    successIcon.scaleToFit(field.getSize(), field.getSize());
    errorIcon = ResourcesUtils.loadImageResource("error.png");
    errorIcon.scaleToFit(field.getSize(), field.getSize());

    // Configura a tabela do cabeçalho
    table = new PdfPTable(4);
    table.getDefaultCell()
         .setBorderWidth(0.7f);
    table.getDefaultCell()
         .setBorderColor(BORDER_COLOR);
    table.getDefaultCell()
         .setHorizontalAlignment(Element.ALIGN_LEFT);
    table.getDefaultCell()
         .setVerticalAlignment(Element.ALIGN_MIDDLE);
    table.getDefaultCell()
         .setMinimumHeight(18.725f);
    table.getDefaultCell()
         .setPaddingLeft(5.39f);
    table.getDefaultCell()
         .setPaddingRight(5.39f);
    table.setWidthPercentage(100f);
    table.setWidths(COLUMN_WIDTH);

    // initialize headerTable Title
    table.addCell(generateTitle());
  }

  public DocumentHeader fillupFields(HeaderData fieldData) {
    table.addCell(new Phrase("RESPONSÁVEL EXECUÇÃO", label));
    table.addCell(new Phrase(fieldData.responsavelExecucao(), field));

    table.addCell(new Phrase("DATA\nHORA", label));
    String dateNow = fieldData.dataExecucao()
                              .format(DateTimeFormatter.ofPattern("dd/MM/yyy"));
    String timeNow = fieldData.dataExecucao()
                              .format(DateTimeFormatter.ofPattern("HH:mm"));
    table.addCell(new Phrase("%s%n%s".formatted(dateNow, timeNow), field));

    table.addCell(new Phrase("SISTEMAS", label));
    table.addCell(new Phrase(fieldData.sistemas()
                                      .toString(),
        field));

    table.addCell(new Phrase("AMBIENTE DE TESTES", label));
    table.addCell(new Phrase(fieldData.ambienteDeTestes(), field));

    table.addCell(new Phrase("SQUAD", label));
    PdfPCell squad = new PdfPCell(table.getDefaultCell());
    squad.addElement(new Phrase(fieldData.squad(), field));
    squad.setColspan(3);
    squad.setPaddingTop(-5);
    table.addCell(squad);

    table.addCell(new Phrase("NOME DO CENÁRIO", label));
    table.addCell(new Phrase(fieldData.nomeCenario(), field));

    table.addCell(new Phrase("STATUS", label));
    table.addCell(Boolean.TRUE.equals(fieldData.status()) ? generateSuccessMessage() : generateErrorMessage());
    return this;
  }

  public HeaderFooter createHeader() {

    // configuração do container da tabela com linha nova após.
    Paragraph head = new Paragraph();
    head.setLeading(1);
    head.add(table);
    head.add(Chunk.NEWLINE);

    // criação e configuração do header
    HeaderFooter header = new HeaderFooter(head, false);
    header.setBorderColor(BORDER_COLOR);
    header.setBorder(0);
    return header;
  }

  private Phrase generateSuccessMessage() {
    Phrase successMessage = new Phrase();
    successMessage.add(new Chunk(successIcon, 0, -successIcon.getScaledHeight() / 8));
    successMessage.add(new Phrase(" SUCESSO", successFont));
    return successMessage;
  }

  private Phrase generateErrorMessage() {
    Phrase errorMessage = new Phrase();
    errorMessage.add(new Chunk(errorIcon, 0, -errorIcon.getScaledHeight() / 8));
    errorMessage.add(new Phrase(" ERRO", errorFont));
    return errorMessage;
  }

  private PdfPCell generateTitle() {
    Paragraph paragraphTitle = new Paragraph();
    paragraphTitle.setAlignment(Element.ALIGN_CENTER);
    paragraphTitle.add(new Phrase(new Chunk(nttData, -80, 0)));
    paragraphTitle.add(new Phrase("EVIDÊNCIA DE TESTES AUTOMATIZADOS", title));
    paragraphTitle.add(new Phrase(new Chunk(claro, 70, 5)));
    paragraphTitle.add(new Phrase(new Chunk(solar, 80, 0)));

    PdfPCell titleCell = new PdfPCell();
    titleCell.addElement(paragraphTitle);
    titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    titleCell.setBorder(0);
    titleCell.setColspan(4);
    titleCell.setPaddingBottom(10);
    return titleCell;
  }
}
