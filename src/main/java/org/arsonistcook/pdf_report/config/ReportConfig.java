package org.arsonistcook.pdf_report.config;

import java.util.List;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ReportConfig {
  private Config config;
  private static final String CONFIG_ROOT = "pdf-report";
  private static final String CONFIG_BASE_PATH = CONFIG_ROOT.concat(".");

  public ReportConfig(Config config) {
    this.config = config;
    config.checkValid(ConfigFactory.defaultReference(), CONFIG_ROOT);

  }

  public ReportConfig() {
    this.config = ConfigFactory.load();
  }

  public String getString(String conf) {
    return config.getString(CONFIG_BASE_PATH.concat(conf));
  }

  public boolean getBoolean(String conf) {
    return config.getBoolean(CONFIG_BASE_PATH.concat(conf));
  }

  public List<String> getStringList(String conf) {
    return config.getStringList(CONFIG_BASE_PATH.concat(conf));
  }
}
