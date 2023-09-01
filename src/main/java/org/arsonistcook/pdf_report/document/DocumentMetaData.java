package org.arsonistcook.pdf_report.document;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

@Builder
@Getter
public class DocumentMetaData {
  @Default
  private String language = "pt-BR";
  private String author;
  @Default
  private String creator = "Report PDF Generator";
  @Default
  private String keywords = "teste, automatizado, resultado, ntt data, claro, solar";
  @Default
  private String producer = "OpenPDF";
  @Default
  private String subject = "Evidência de execução de teste automatizado";
  private String title;
}
