package org.arsonistcook.openpdf_tutorial.report;

import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ReportPDF {
  private Map<String, String> reportData;
  private FileOutputStream fileOutput;
  Config docConfig;

  public ReportPDF(FileOutputStream fileOutput) {
    reportData = new LinkedHashMap<>();
    Config defaultConfig = ConfigFactory.parseResources("reportConfigDefault.json");
    docConfig = ConfigFactory.parseResources("reportConfig.json")
                             .withFallback(defaultConfig)
                             .resolve();
    this.fileOutput = fileOutput;
  }

  public void gerarReporPDF(List<String> prints, List<String> texts, String nomeTestCase, boolean statusTest) {
    DocumentMetaData metaData = createMetaData(nomeTestCase);
    HeaderData headerData = createHeader(nomeTestCase, statusTest);
    prepareDocData(prints, texts);

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

  private void prepareDocData(List<String> prints, List<String> texts) {
    // prepara dados do relatório
    for (int index = 0; index < texts.size(); index++) {
      String print = null;
      try {
        print = prints.get(index);
      } finally {
        reportData.put(texts.get(index), print);

      }
    }
  }
}
