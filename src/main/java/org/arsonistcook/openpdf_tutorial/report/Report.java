package org.arsonistcook.openpdf_tutorial.report;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.arsonistcook.openpdf_tutorial.ResourcesUtils;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Report {
  private FileOutputStream fileStream;
  private HeaderData headerData;
  private DocumentMetaData metaData;

  public void createDocument(Map<String, String> docData) {
    try (Document document = new Document(PageSize.A4, 30f, 30f, 15f, 20f)) {
      PdfWriter.getInstance(document, fileStream);
      // cria documento
      // prepara metadata, cabeçalho, rodapé
      generateReportInfo(document);
      document.setHeader(new DocumentHeader().fillupFields(headerData)
                                             .createHeader());
      document.setFooter(DocumentFooter.createFooter());
      // abre documento
      document.open();
      for (Entry<String, String> entry : docData.entrySet()) {
        document.add(createStepEvidence(entry.getKey(), Optional.ofNullable(entry.getValue())));
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

  private Paragraph createStepEvidence(String title, Optional<String> imagePath) {

    Paragraph paragraph = new Paragraph(title, ResourcesUtils.getBaseFontBold(16));

    imagePath.ifPresent(imgPath -> {
      Image img;
      try {
        img = Image.getInstance(imgPath);

        float percentage = 535f / img.getPlainWidth();
//        img.scaleToFit(535f, 535f);
        img.scaleAbsolute(img.getPlainWidth() * percentage, img.getPlainHeight() * percentage);
        Chunk igg = new Chunk(img, 0, -img.getScaledHeight());
        paragraph.add(igg);
        paragraph.setSpacingAfter(img.getScaledHeight());
        paragraph.add(Chunk.NEWLINE);

      } catch (BadElementException | IOException e) {
        e.printStackTrace();
      }
    });

    return paragraph;
  }
}
