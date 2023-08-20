package org.arsonistcook.openpdf_tutorial.report;

import static org.arsonistcook.openpdf_tutorial.report.ConfigurationConstants.*;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;

public class ResourcesUtils {
  private static boolean fontsLoaded = false;

  public static Image loadImageResource(String fileName) throws BadElementException, IOException {
    InputStream inputStream = ResourcesUtils.class.getClassLoader()
                                                  .getResourceAsStream(PICTURES_RESOURCE_PATH.concat(fileName));
    BufferedInputStream inputReader = new BufferedInputStream(inputStream);

    return Image.getInstance(inputReader.readAllBytes());
//  return Image.getInstance(Path.of(PICTURES_RESOURCE_PATH, fileName)
//  .toString());
  }

  public static Font getBaseFont(int size) {
    loadDocumentBaseFont();
    return FontFactory.getFont(BASE_FONT_NAME, size);
  }

  public static Font getBaseFontBold(int size) {
    Font font = getBaseFont(size);
    font.setStyle(Font.BOLD);
    return font;
  }

  public static Font getBaseFontColored(int size, Color color) {
    Font font = getBaseFont(size);
    font.setColor(color);
    return font;
  }

  public static Font getBaseFontBoldColored(int size, Color color) {
    Font font = getBaseFontBold(size);
    font.setColor(color);
    return font;
  }

  public static void loadDocumentBaseFont() {
    if (!fontsLoaded) {
      FontFactory.register(Path.of(FONT_BASE_PATH, BASE_FONT_FILE_NORMAL)
                               .toString());
      FontFactory.register(Path.of(FONT_BASE_PATH, BASE_FONT_FILE_BOLD)
                               .toString());
      fontsLoaded = true;
    }
  }

}
