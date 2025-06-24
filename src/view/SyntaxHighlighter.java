package view;

import java.awt.Color;
import java.util.ArrayList;
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

        // 2) Definir prioridades de resaltado
        Map<ColorType, Integer> priorities = new HashMap<>();
        priorities.put(ColorType.COMENTARIO, 1);    // Máxima prioridad
        priorities.put(ColorType.ID, 1);     // Máxima prioridad
        priorities.put(ColorType.FN, 2);
        priorities.put(ColorType.DT, 3);
        priorities.put(ColorType.RW, 4);
        priorities.put(ColorType.OP_ARIT, 4);
        priorities.put(ColorType.OP_REL, 5); // Baja prioridad
        priorities.put(ColorType.OP_LOG, 5);
        priorities.put(ColorType.OP_ASIGN, 5);
        priorities.put(ColorType.ERR, 6);
        priorities.put(ColorType.DEL, 7);

        // 3) Ordenar tokens por prioridad, posición y tamaño
        tokens.sort((t1, t2) -> {
            // Primero: Prioridad del tipo de token
            int p1 = priorities.getOrDefault(t1.getColor(), 10);
            int p2 = priorities.getOrDefault(t2.getColor(), 10);
            if (p1 != p2) return Integer.compare(p1, p2);

            // Segundo: Posición en el documento
            if (t1.getLine() != t2.getLine()) {
                return Integer.compare(t1.getLine(), t2.getLine());
            }
            if (t1.getColumn() != t2.getColumn()) {
                return Integer.compare(t1.getColumn(), t2.getColumn());
            }

            // Tercero: Longitud (tokens más largos primero)
            return Integer.compare(t2.getText().length(), t1.getText().length());
        });

        // 4) Calcular offsets de líneas
        String fullText = doc.getText(0, doc.getLength());
        int[] lineOffsets = calculateLineOffsets(fullText);

        // 5) Aplicar estilos en orden priorizado
        for (Token t : tokens) {
            Style st = styles.get(t.getLexemeType());
            if (st == null) continue;

            int startOffset = lineOffsets[t.getLine()] + t.getColumn();
            int length = t.getText().length();

            // Validar límites del documento
            if (startOffset >= 0 && startOffset + length <= doc.getLength()) {
                doc.setCharacterAttributes(startOffset, length, st, true);
            }
        }
    }
    private int[] calculateLineOffsets(String text) {
        List<Integer> offsets = new ArrayList<>();
        offsets.add(0); // Línea 0 inicia en 0

        int pos = 0;
        while (pos < text.length()) {
            char c = text.charAt(pos++);
            if (c == '\n' || c == '\r') {
                // Manejar saltos de línea (incluso \r\n)
                if (c == '\r' && pos < text.length() && text.charAt(pos) == '\n') pos++;
                offsets.add(pos);
            }
        }

        return offsets.stream().mapToInt(i -> i).toArray();
    }
}
