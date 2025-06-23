package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import model.scanner.Token;
import utils.TokenTableModel;
import controller.FileController;
import controller.LexerController;

/**
 * Interfaz principal del compilador léxico, con editor, consola y tokens.
 */
public class Compilador extends JFrame {

    private final FileController fileController;
    private final LexerController lexerController;
    private final SyntaxHighlighter syntaxHighlighter;

    private String currentFilePath;
    private boolean dark = false;

    // Componentes de UI
    private JToolBar toolBar;
    private JSplitPane verticalSplit;
    private JSplitPane horizontalSplit;
    private JTextPane editorPane;
    private JTextArea consoleArea;
    private JTable tokensTable;
    private JButton btnToggleTheme;  // referenciamos el botón

    public Compilador() {
        super("Analizador Léxico");
        fileController = new FileController();
        lexerController = new LexerController();
        syntaxHighlighter = new SyntaxHighlighter();

        initComponents();

        // Aplicar resaltado inicial (sin tokens) y dejar consola vacía
        try {
            syntaxHighlighter.initialize((StyledDocument) editorPane.getDocument(), dark);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        consoleArea.setText("");

        // Tamaño por defecto
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        // Toolbar
        toolBar = new JToolBar();
        JButton btnNuevo = new JButton("Nuevo");
        JButton btnAbrir = new JButton("Abrir");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnGuardarComo = new JButton("Guardar como");
        JButton btnCompilar = new JButton("Ejecutar análisis léxico");
        btnToggleTheme = new JButton(dark ? "Light Mode" : "Dark Mode");

        toolBar.add(btnNuevo);
        toolBar.add(btnAbrir);
        toolBar.add(btnGuardar);
        toolBar.add(btnGuardarComo);
        toolBar.addSeparator();
        toolBar.add(btnCompilar);
        toolBar.addSeparator();
        toolBar.add(btnToggleTheme);

        // Editor y consola
        editorPane = new JTextPane();
        editorPane.setFont(new java.awt.Font("Monospaced", 0, 14));
        JScrollPane editorScroll = new JScrollPane(editorPane);

        consoleArea = new JTextArea();
        consoleArea.setEditable(false);
        JScrollPane consoleScroll = new JScrollPane(consoleArea);

        // Split vertical
        verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editorScroll, consoleScroll);
        verticalSplit.setResizeWeight(0.7);

        // Tabla de tokens
        tokensTable = new JTable(new TokenTableModel());
        JScrollPane tokensScroll = new JScrollPane(tokensTable);

        // Split horizontal
        horizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, verticalSplit, tokensScroll);
        horizontalSplit.setResizeWeight(0.75);

        // Layout principal
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(toolBar, BorderLayout.NORTH);
        getContentPane().add(horizontalSplit, BorderLayout.CENTER);

        // Listeners
        btnNuevo.addActionListener(e -> onNuevo());
        btnAbrir.addActionListener(e -> onAbrir());
        btnGuardar.addActionListener(e -> onGuardar());
        btnGuardarComo.addActionListener(e -> onGuardarComo());
        btnCompilar.addActionListener(e -> onCompilar());
        btnToggleTheme.addActionListener(e -> onToggleTheme());

        tokensTable.getSelectionModel().addListSelectionListener(e -> onTokenSelected(e));

        // Ctrl+S
        Action saveAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onGuardar();
            }
        };
        InputMap im = toolBar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = toolBar.getActionMap();
        im.put(KeyStroke.getKeyStroke("ctrl S"), "save");
        am.put("save", saveAction);
    }

    private void onNuevo() {
        editorPane.setText("");
        try {
            syntaxHighlighter.initialize((StyledDocument) editorPane.getDocument(), dark);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        consoleArea.setText("");
        tokensTable.setModel(new TokenTableModel(new ArrayList<>()));
    }

    private void onAbrir() {
        fileController.showOpenDialog(this).ifPresent(file -> {
            try {
                String content = java.nio.file.Files.readString(file.toPath());
                editorPane.setText(content);
                currentFilePath = file.getAbsolutePath();
                syntaxHighlighter.initialize((StyledDocument) editorPane.getDocument(), dark);
                consoleArea.setText("");
            } catch (IOException | BadLocationException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }

    private void onGuardar() {
        if (currentFilePath == null) {
            onGuardarComo();
        } else {
            try {
                java.nio.file.Files.writeString(java.nio.file.Path.of(currentFilePath), editorPane.getText());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
            }
        }
    }

    private void onGuardarComo() {
        fileController.showSaveDialog(this).ifPresent(file -> {
            try {
                java.nio.file.Files.writeString(file.toPath(), editorPane.getText());
                currentFilePath = file.getAbsolutePath();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar archivo: " + ex.getMessage());
            }
        });
    }

    private void onCompilar() {
        String source = editorPane.getText().trim();
        var result = lexerController.analyze(source);
        tokensTable.setModel(new TokenTableModel(result.getTokens()));

        // Estilos para tabla según modo
        tokensTable.setBackground(dark ? new Color(40, 44, 52) : Color.WHITE);
        tokensTable.setForeground(dark ? Color.LIGHT_GRAY : Color.BLACK);

        if (result.getErrors().isEmpty()) {
            consoleArea.setText("No se encontraron errores en el análisis léxico.\n");
        } else {
            StringBuilder sb = new StringBuilder("Se encontraron errores en el análisis léxico:\n");
            for (Token t : result.getErrors()) sb.append(t).append("\n");
            consoleArea.setText(sb.toString());
        }
        try {
            syntaxHighlighter.applyHighlight((StyledDocument) editorPane.getDocument(), result.getTokens());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void onToggleTheme() {
        dark = !dark;
        // Actualizar texto del botón
        btnToggleTheme.setText(dark ? "Light Mode" : "Dark Mode");

        // Editor
        editorPane.setBackground(dark ? new Color(40, 44, 52) : Color.WHITE);
        editorPane.setCaretColor(dark ? Color.WHITE : Color.BLACK);
        // Consola
        consoleArea.setBackground(dark ? new Color(30, 30, 30) : Color.WHITE);
        consoleArea.setForeground(dark ? Color.LIGHT_GRAY : Color.BLACK);
        // Tabla
        tokensTable.setBackground(dark ? new Color(40, 44, 52) : Color.WHITE);
        tokensTable.setForeground(dark ? Color.LIGHT_GRAY : Color.BLACK);

        // Solo reaplicar estilos base, sin invocar análisis
        try {
            syntaxHighlighter.createStyles((StyledDocument) editorPane.getDocument(), dark);
            syntaxHighlighter.applyHighlight((StyledDocument) editorPane.getDocument(), lexerController.analyze(editorPane.getText()).getTokens());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void onTokenSelected(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) return;
        for (int r : tokensTable.getSelectedRows()) {
            System.out.println("Token fila: " + r);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Compilador::new);
    }
}
