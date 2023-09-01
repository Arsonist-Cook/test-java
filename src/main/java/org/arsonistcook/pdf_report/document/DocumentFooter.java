package org.arsonistcook.pdf_report.document;

import static org.arsonistcook.pdf_report.config.ConfigurationConstants.BORDER_COLOR;

import java.awt.Color;

import org.arsonistcook.pdf_report.config.DocumentBaseFormat;

import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentFooter {
  public static HeaderFooter createFooter() {

    Paragraph direitos = new Paragraph();
    direitos.add(new Phrase("As informações contidas neste documento são de uso interno e de propriedade da Claro S/A.", new DocumentBaseFormat().getBaseFontBoldColored(8, Color.GRAY)));
    direitos.add(Chunk.NEWLINE);

    HeaderFooter footer = new HeaderFooter(direitos, true);
    footer.setAlignment(Element.ALIGN_CENTER);
    footer.setBorderColor(BORDER_COLOR);
    footer.setBorderWidthTop(1.0f);
    footer.setBorder(1);
    return footer;
  }
}
