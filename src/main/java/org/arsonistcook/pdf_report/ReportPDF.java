package org.arsonistcook.pdf_report;

import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.arsonistcook.pdf_report.config.ReportConfig;
import org.arsonistcook.pdf_report.document.DocumentMetaData;
import org.arsonistcook.pdf_report.document.HeaderData;

import com.typesafe.config.Config;

public class ReportPDF {
  private FileOutputStream fileOutput;
  ReportConfig docConfig;

  public ReportPDF(FileOutputStream fileOutput, Config config) {
    this(fileOutput);
    this.docConfig = new ReportConfig(config);
  }

  public ReportPDF(FileOutputStream fileOutput) {
    docConfig = new ReportConfig();
    this.fileOutput = fileOutput;
  }

  public void gerarReportPDF(List<String> prints, List<String> texts, String nomeTestCase, boolean statusTest) {
    gerarReportPDF(prepareDocData(prints, texts), nomeTestCase, statusTest);
  }

  public void gerarReportPDF(Map<String, String> reportData, String nomeTestCase, boolean statusTest) {
    DocumentMetaData metaData = createMetaData(nomeTestCase);
    HeaderData headerData = createHeader(nomeTestCase, statusTest);

    Report report = new Report(fileOutput, headerData, metaData);
    report.createDocument(reportData);
  }

  private HeaderData createHeader(String nomeTestCase, boolean statusTest) {
    return HeaderData.builder()
                     .responsavelExecucao(docConfig.getString("header.responsavelExecucao"))
                     .sistemas(docConfig.getStringList("header.sistemas"))
                     .ambienteDeTestes(docConfig.getString("header.ambienteTestes"))
                     .squad(docConfig.getString("header.squad"))
                     .nomeCenario(nomeTestCase)
                     .status(statusTest)
                     .build();
  }

  private DocumentMetaData createMetaData(String nomeTestCase) {
    return DocumentMetaData.builder()
                           .author(docConfig.getString("metadata.author"))
                           .title(nomeTestCase)
                           .build();
  }

  private Map<String, String> prepareDocData(List<String> prints, List<String> texts) {
    Map<String, String> reportData = new LinkedHashMap<>();
    // prepara dados do relatório
    for (int index = 0; index < texts.size(); index++) {
      String print = null;
      try {
        print = prints.get(index);
      } finally {
        reportData.put(texts.get(index), print);
      }
    }
    return reportData;
  }
}
