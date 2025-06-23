package view;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import model.scanner.ColorType;
import model.scanner.Token;

/**
 * Gestiona la creación de estilos, el resaltado inicial y el resaltado según tokens.
 */
public class SyntaxHighlighter {
    private Map<String, Style> styles = new HashMap<>();

    /**
     * Inicializa el resaltado: crea estilos y aplica un primer pase sin tokens.
     *
     * @param doc  el StyledDocument del JTextPane
     * @param dark si true, usa paleta de modo oscuro
     * @throws BadLocationException nunca, porque no hay tokens en este primer pase
     */
    public void initialize(StyledDocument doc, boolean dark) throws BadLocationException {
        createStyles(doc, dark);
        applyHighlight(doc, List.of());  // pase vacío, solo para pintar el texto con estilo por defecto
    }

    /**
     * Crea estilos de texto según los tipos de token y el tema (claro/oscuro).
     *
     * @param doc  documento estilizado del JTextPane
     * @param dark si true, usa colores de modo oscuro
     */
    public void createStyles(StyledDocument doc, boolean dark) {
        styles.clear();
        for (ColorType type : ColorType.values()) {
            Style st = doc.addStyle(type.name(), null);
            Color c = dark ? type.getdColor() : type.getColor();
            StyleConstants.setForeground(st, c);
            styles.put(type.name(), st);
        }
    }

    /**
     * Aplica el resaltado sintáctico a todo el texto según los tokens.
     *
     * @param doc    documento estilizado del JTextPane
     * @param tokens lista de tokens generada por el analizador
     * @throws BadLocationException si hay un problema con posiciones de texto
     */
    public void applyHighlight(StyledDocument doc, List<Token> tokens) throws BadLocationException {
        // 1) Reset a estilo por defecto
        Style defaultStyle = StyleContext.getDefaultStyleContext()
                .getStyle(StyleContext.DEFAULT_STYLE);
        doc.setCharacterAttributes(0, doc.getLength(), defaultStyle, true);

        // 2) Re-pintar cada token
        String fullText = doc.getText(0, doc.getLength());
        for (Token t : tokens) {
            Style st = styles.get(t.getLexemeType());
            if (st == null) continue;

            String lexeme = t.getText();
            int index = fullText.indexOf(lexeme);
            while (index >= 0) {
                doc.setCharacterAttributes(index, lexeme.length(), st, true);
                index = fullText.indexOf(lexeme, index + lexeme.length());
            }
        }
    }
}
