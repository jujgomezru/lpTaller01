package controller;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;
import java.util.List;
import model.scanner.LexerAnalyzer;
import model.scanner.Token;

/**
 * Controlador para invocar el análisis léxico y devolver resultados.
 */
public class LexerController {
    /**
     * Contenedor simple de resultados de análisis.
     */
    public static class AnalysisResult {
        private final List<Token> tokens;
        private final List<Token> errors;

        public AnalysisResult(List<Token> tokens, List<Token> errors) {
            this.tokens = tokens;
            this.errors = errors;
        }

        public List<Token> getTokens() {
            return tokens;
        }

        public List<Token> getErrors() {
            return errors;
        }
    }

    /**
     * Ejecuta el análisis léxico sobre el texto fuente proporcionado.
     *
     * @param source texto de código a analizar
     * @return un objeto AnalysisResult con la lista de tokens y errores encontrados
     */
    public AnalysisResult analyze(String source) {
        try (BufferedReader reader = new BufferedReader(new StringReader(source))) {
            LexerAnalyzer lexer = new LexerAnalyzer(reader);
            List<Token> tokenList = lexer.getTokens();
            List<Token> errorList = lexer.getErrorTokens();
            return new AnalysisResult(tokenList, errorList);
        } catch (IOException e) {
            // En este caso StringReader no lanza IOException, pero la firma lo requiere
            throw new RuntimeException("Error interno de análisis léxico", e);
        }
    }
}
