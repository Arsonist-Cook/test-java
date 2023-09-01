package org.arsonistcook.pdf_report.config;

import static org.arsonistcook.pdf_report.config.ConfigurationConstants.*;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;

public class DocumentBaseFormat {
  private boolean fontsLoaded = false;

  public Image loadImageResource(String fileName) throws BadElementException, IOException {
    InputStream inputStream = getClass().getClassLoader()
                                        .getResourceAsStream(PICTURES_RESOURCE_PATH.concat(fileName));
    BufferedInputStream inputReader = new BufferedInputStream(inputStream);

    return Image.getInstance(inputReader.readAllBytes());
  }

  public Font getBaseFont(int size) {
    loadDocumentBaseFont();
    return FontFactory.getFont(BASE_FONT_NAME, size);
  }

  public Font getBaseFontBold(int size) {
    Font font = getBaseFont(size);
    font.setStyle(Font.BOLD);
    return font;
  }

  public Font getBaseFontColored(int size, Color color) {
    Font font = getBaseFont(size);
    font.setColor(color);
    return font;
  }

  public Font getBaseFontBoldColored(int size, Color color) {
    Font font = getBaseFontBold(size);
    font.setColor(color);
    return font;
  }

  public void loadDocumentBaseFont() {
    if (!fontsLoaded) {
      FontFactory.register(Path.of(FONT_BASE_PATH, BASE_FONT_FILE_NORMAL)
                               .toString());
      FontFactory.register(Path.of(FONT_BASE_PATH, BASE_FONT_FILE_BOLD)
                               .toString());
      fontsLoaded = true;
    }
  }

}
