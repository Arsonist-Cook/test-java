package org.arsonistcook.openpdf_tutorial.report;

import static org.arsonistcook.openpdf_tutorial.report.ConfigurationConstants.BORDER_COLOR;

import java.awt.Color;

import org.arsonistcook.openpdf_tutorial.ResourcesUtils;

import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;

public class DocumentFooter {
  public static HeaderFooter createFooter() {

    Paragraph direitos = new Paragraph();
    direitos.add(new Phrase("As informações contidas neste documento são de uso interno e de propriedade da Claro S/A.", ResourcesUtils.getBaseFontBoldColored(8, Color.GRAY)));
    direitos.add(Chunk.NEWLINE);

    HeaderFooter footer = new HeaderFooter(direitos, true);
    footer.setAlignment(Element.ALIGN_CENTER);
    footer.setBorderColor(BORDER_COLOR);
    footer.setBorderWidthTop(1.0f);
    footer.setBorder(1);
    return footer;
  }
}
