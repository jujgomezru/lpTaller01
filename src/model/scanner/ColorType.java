package model.scanner;

import java.awt.Color;

/**
 * Colores para tema oscuro en el resaltado de sintaxis.
 * @author dsrodriguezse
 */

public enum ColorType {
    RW(Color.decode("#E06C75")),      // Palabras clave
    FN(Color.decode("#E5C07B")),      // Funciones
    DT(Color.decode("#98C379")),      // Tipos de dato
    LIT(Color.decode("#808000")),     // Literales
    ID(Color.decode("#61AFEF")),      // Identificadores
    OP_ARIT(Color.WHITE),             // Operadores aritméticos
    OP_REL(Color.decode("#C678DD")),  // Operadores relacionales
    OP_LOG(Color.decode("#C678DD")),  // Operadores lógicos
    OP_ASIGN(Color.WHITE),            // Asignación
    DEL(Color.WHITE),                 // Delimitadores
    ERR(Color.decode("#BE5046")),     // Errores
    EOF(Color.decode("#282C34"));     // Fondo (end of file)

    private final Color color;

    ColorType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    // Alias temporal si el código aún llama a getdColor()
    public Color getdColor() {
        return getColor();
    }

    public String getColorCode() {
        return String.format("#%06x", color.getRGB() & 0xFFFFFF);
    }
}