package org.arsonistcook.pdf_report;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.arsonistcook.pdf_report.config.DocumentBaseFormat;
import org.arsonistcook.pdf_report.document.DocumentFooter;
import org.arsonistcook.pdf_report.document.DocumentHeader;
import org.arsonistcook.pdf_report.document.DocumentMetaData;
import org.arsonistcook.pdf_report.document.HeaderData;

import java.util.Optional;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Report extends DocumentBaseFormat {
  private FileOutputStream fileStream;
  private HeaderData headerData;
  private DocumentMetaData metaData;

  public void createDocument(Map<String, String> docData) {
    try (Document document = new Document(PageSize.A4, 30f, 30f, 20f, 25f)) {
      PdfWriter writer = PdfWriter.getInstance(document, fileStream);
      writer.setStrictImageSequence(true);

      // cria documento
      // prepara metadata, cabeçalho, rodapé
      generateReportInfo(document);
      document.setHeader(new DocumentHeader().fillupFields(headerData)
                                             .createHeader());
      document.setFooter(DocumentFooter.createFooter());
      // abre documento
      document.open();
      for (Entry<String, String> entry : docData.entrySet()) {
        document.add(createStepEvidence(entry.getKey()));
        Optional.ofNullable(entry.getValue())
                .ifPresent(imgPath -> createStepImage(imgPath).ifPresent(document::add));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    // fecha documento
  }

  private void generateReportInfo(Document document) {
    document.setDocumentLanguage(metaData.language());
    document.addAuthor(metaData.author());
    document.addCreator(metaData.creator());
    document.addKeywords(metaData.keywords());
    document.addProducer(metaData.producer());
    document.addSubject(metaData.subject());
    document.addTitle(metaData.title());
  }

  private Optional<Image> createStepImage(String imagePath) {

    Image img = null;

    try {
      img = Image.getInstance(imagePath);
      float percentage = Math.min(288f / img.getPlainHeight(), 490 / img.getPlainWidth()) * 100f;
      img.scalePercent(percentage);
      img.setAlignment(Image.MIDDLE);

    } catch (BadElementException | IOException e) {
      e.printStackTrace();
    }

    return Optional.ofNullable(img);
  }

  private Paragraph createStepEvidence(String title) {
    Paragraph paragraph = new Paragraph(title, getBaseFontBold(16));
    paragraph.setSpacingBefore(25f);
    return paragraph;
  }
}
