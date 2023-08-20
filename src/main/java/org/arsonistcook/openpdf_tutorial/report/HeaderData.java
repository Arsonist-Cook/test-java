package org.arsonistcook.openpdf_tutorial.report;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Singular;

@Builder
@Getter
public class HeaderData {
  @Default
  LocalDateTime dataExecucao = LocalDateTime.now();
  @Default
  String responsavelExecucao = "Script Automatizado - Projeto Solar";// span3
  @Singular
  List<String> sistemas;
  @Default
  String ambienteDeTestes = "PREPROD";
  String squad;
  String nomeCenario;
  Boolean status;
}
