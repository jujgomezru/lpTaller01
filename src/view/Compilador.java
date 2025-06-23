package view;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import model.scanner.LexerAnalyzer;
import model.scanner.Token;
import utils.TokenTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import model.scanner.ColorType;
import utils.utils;


public class Compilador extends javax.swing.JFrame {

    public TokenTableModel tblTokensModel;
    public LexerAnalyzer lex;
    public String currentFilePath = null;
    public String currentFileName = null;
    private HashMap<String, Style> colorStyles;
    private List Ctokens;
    private boolean dark=false;

    public Compilador() {
        initComponents();
        init();
    }

    private void init() {
        setTitle("Analizador Léxico");
        utils.initUXLookAndFeel(utils.isWindows(), utils.islinux());
        utils.enumerateJTextComponent(this.jtpCode, this.jspCode);
        utils.autocompleteJTextComponent(this.jtpCode, this.jspCode);
        this.tblTokensModel = new TokenTableModel();
        this.tblTokens.setModel(this.tblTokensModel);
        var tokenstable = this.tblTokens;
        tokenstable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    return;
                }

                // Obtener las filas seleccionadas
                int[] selectedRows = tokenstable.getSelectedRows();

                // Activar algo
                for (int i = 0; i < selectedRows.length; i++) {
                    System.out.println("Fila seleccionada: " + selectedRows[i]);
                }
                System.out.println("\n");
            }
        });

        setLocationRelativeTo(null);
        // Crear una acción
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnGuardarActionPerformed(e);
            }
        };

        // Obtén el InputMap y el ActionMap del panel que contiene el botón
        InputMap inputMap = this.buttonsFilePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.buttonsFilePanel.getActionMap();

        // Vincula la tecla Ctrl + S a la acción
        inputMap.put(KeyStroke.getKeyStroke("ctrl S"), "save");
        actionMap.put("save", action);
        createStyles();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rootPanel = new javax.swing.JPanel();
        buttonsFilePanel = new javax.swing.JPanel();
        btnAbrir = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnGuardarComo = new javax.swing.JButton();
        jspCode = new javax.swing.JScrollPane();
        jtpCode = new javax.swing.JTextPane();
        panelButtonCompilerExecute = new javax.swing.JPanel();
        btnCompilar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaConsole = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblTokens = new javax.swing.JTable();
        bt_bg = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        btnAbrir.setText("Abrir");
        btnAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirActionPerformed(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnGuardarComo.setText("Guardar como");
        btnGuardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarComoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonsFilePanelLayout = new javax.swing.GroupLayout(buttonsFilePanel);
        buttonsFilePanel.setLayout(buttonsFilePanelLayout);
        buttonsFilePanelLayout.setHorizontalGroup(
                buttonsFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(buttonsFilePanelLayout.createSequentialGroup()
                                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAbrir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardarComo)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonsFilePanelLayout.setVerticalGroup(
                buttonsFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(buttonsFilePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(buttonsFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAbrir)
                                        .addComponent(btnNuevo)
                                        .addComponent(btnGuardar)
                                        .addComponent(btnGuardarComo))
                                .addContainerGap(16, Short.MAX_VALUE))
        );

        jtpCode.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jtpCode.setForeground(new java.awt.Color(0, 0, 0));
        jtpCode.setToolTipText("");
        jtpCode.setCaretColor(new java.awt.Color(0, 0, 0));
        jtpCode.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jtpCode.setDisabledTextColor(new java.awt.Color(0, 0, 153));
        jtpCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtpCodeKeyReleased(evt);
            }
        });
        jspCode.setViewportView(jtpCode);

        btnCompilar.setText("Ejecutar análisis léxico");
        btnCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompilarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelButtonCompilerExecuteLayout = new javax.swing.GroupLayout(panelButtonCompilerExecute);
        panelButtonCompilerExecute.setLayout(panelButtonCompilerExecuteLayout);
        panelButtonCompilerExecuteLayout.setHorizontalGroup(
                panelButtonCompilerExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelButtonCompilerExecuteLayout.createSequentialGroup()
                                .addGap(0, 6, Short.MAX_VALUE)
                                .addComponent(btnCompilar, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelButtonCompilerExecuteLayout.setVerticalGroup(
                panelButtonCompilerExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelButtonCompilerExecuteLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnCompilar)
                                .addContainerGap(7, Short.MAX_VALUE))
        );

        jtaConsole.setEditable(false);
        jtaConsole.setBackground(new java.awt.Color(255, 255, 255));
        jtaConsole.setColumns(20);
        jtaConsole.setRows(5);
        jScrollPane2.setViewportView(jtaConsole);

        tblTokens.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null},
                        {null, null, null},
                        {null, null, null},
                        {null, null, null}
                },
                new String [] {
                        "Componente léxico", "Lexema", "[Línea, Columna]"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblTokens);

        bt_bg.setText("*");
        bt_bg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_bgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rootPanelLayout = new javax.swing.GroupLayout(rootPanel);
        rootPanel.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(
                rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(rootPanelLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, rootPanelLayout.createSequentialGroup()
                                                .addComponent(buttonsFilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(panelButtonCompilerExecute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jspCode, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(rootPanelLayout.createSequentialGroup()
                                                .addGap(24, 24, 24)
                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE))
                                        .addGroup(rootPanelLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(bt_bg)))
                                .addGap(24, 24, 24))
        );
        rootPanelLayout.setVerticalGroup(
                rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(rootPanelLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(buttonsFilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(panelButtonCompilerExecute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bt_bg))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(rootPanelLayout.createSequentialGroup()
                                                .addComponent(jspCode)
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE))
                                .addGap(24, 24, 24))
        );

        getContentPane().add(rootPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        System.out.println("nuevo");
        fillTable(this.tblTokens, new ArrayList());
        this.jtaConsole.setText("");
        this.jtpCode.setText("");
        this.Ctokens.clear();


    }//GEN-LAST:event_btnNuevoActionPerformed
    private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt", "cz"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            this.currentFilePath = selectedFile.getAbsolutePath();
            this.currentFileName = selectedFile.getName();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            try {
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();
                this.jtpCode.setText(content.toString());
                this.jtaConsole.setText("");
                fillTable(this.tblTokens, new ArrayList<>());
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        }
        this.updateColors();
    }//GEN-LAST:event_btnAbrirActionPerformed
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (this.currentFilePath == null || this.currentFileName == null) {
            btnAbrirActionPerformed(evt);
        } else {
            System.out.println("guardar");
            // Add code here to save changes to the current file
            try {
                FileWriter fileWriter = new FileWriter(this.currentFilePath);
                fileWriter.write(this.jtpCode.getText());
                fileWriter.close();
            } catch (IOException ex) {
                System.out.println("Error al guardar el archivo: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed
    private void btnGuardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarComoActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt", "cz"));
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(selectedFile);
                writer.write(jtpCode.getText());
                writer.close();
                System.out.println("\nFile saved: " + selectedFile.getAbsolutePath());
                this.currentFileName = selectedFile.getName();
                this.currentFilePath = selectedFile.getAbsolutePath();
            } catch (IOException e) {
                System.out.println("\nError saving file: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnGuardarComoActionPerformed

    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompilarActionPerformed
        System.out.println("compilar");
        String txt = this.jtpCode.getText().trim();
        Reader reader = new BufferedReader(new StringReader(txt));

        this.lex = new LexerAnalyzer((BufferedReader) reader);

        System.out.println("resultado: ");
        lex.printTokens();
        //tblTokens
        fillTable(this.tblTokens, lex.getTokens());

        fillErrorPanel(this.jtaConsole, lex.getErrorTokens());
    }//GEN-LAST:event_btnCompilarActionPerformed

    private void jtpCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtpCodeKeyReleased
        // Obtén el StyledDocument del JTextPane
        this.updateColors();

    }//GEN-LAST:event_jtpCodeKeyReleased
    public void updateColors(){

        StyledDocument doc = (StyledDocument) this.jtpCode.getDocument();
        // Restablece el estilo a su estado original
        Style defaultStyle;

        defaultStyle =StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);



        // Modifica el nuevo estilo
        if(this.dark) StyleConstants.setForeground(defaultStyle, Color.WHITE); // Cambia el color de la fuente a #808000
        else StyleConstants.setForeground(defaultStyle, Color.BLACK);
        doc.setCharacterAttributes(0, doc.getLength(), defaultStyle, true);
        String txt = this.jtpCode.getText().trim();
        Reader reader = new BufferedReader(new StringReader(txt));
        LexerAnalyzer minilex = new LexerAnalyzer((BufferedReader) reader);
        this.Ctokens = (List<Token>) minilex.getTokens();
        // Recorre los tokens
        for (Iterator it = this.Ctokens.iterator(); it.hasNext();) {
            Token token = (Token) it.next();
            // Encuentra el estilo correspondiente al tipo de lexema del token
            Style style = colorStyles.get(token.getLexemeType());

            // Si encontramos un estilo, lo aplicamos al texto del token
            // Si encontramos un estilo, lo aplicamos a todas las ocurrencias del texto del token
            if (style != null) {
                String text = token.getText();
                if (token.getLexemeType().equals("ERR")) {
                    StyleConstants.setItalic(style, true);
                    StyleConstants.setUnderline(style, true);
                    StyleConstants.setForeground(style, Color.RED);

                }
                try {
                    String docText = doc.getText(0, doc.getLength());
                    int start = 0;
                    while ((start = docText.indexOf(text, start)) != -1) {
                        doc.setCharacterAttributes(start, text.length(), style, false);
                        start += text.length();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    private void bt_bgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_bgActionPerformed
        // TODO add your handling code here:
        this.dark = !this.dark;
        if(this.dark ==true){
            Color myColor = new Color(40, 44, 52);
            this.jtpCode.setBackground(myColor);
            this.jtpCode.setCaretColor(Color.WHITE);
            this.jspCode.setForeground(Color.WHITE);
        }else{
            this.jtpCode.setBackground(Color.WHITE);
            this.jtpCode.setCaretColor(Color.BLACK);
            this.jspCode.setForeground(Color.WHITE);
        }
        this.createStyles();
        this.updateColors();


    }//GEN-LAST:event_bt_bgActionPerformed

    public void fillTable(JTable table, List<Token> tokens) {
        TokenTableModel model = new TokenTableModel(tokens);
        table.setModel(model);
    }

    public void fillErrorPanel(JTextArea txt, List<Token> tokens) {
        String result = "";
        if (tokens.isEmpty()) {
            result = "No se encontraron errores en el análisis léxico.\n";
        } else {
            result = "Se encontraron errores en el análisis léxico.\n";

            for (Token token : tokens) {
                result += token.toString() + "\n";
            }
        }
        txt.setText(result);
    }

    public void createStyles() {
        // Obtén el StyledDocument del JTextPane
        StyledDocument doc = (StyledDocument) this.jtpCode.getDocument();
        this.colorStyles = new HashMap<>();
        // Recorre los valores del enum
        for (ColorType colorType : ColorType.values()) {
            // Crea un estilo para cada valor
            Style style = doc.addStyle(colorType.name(), null);
            if(this.dark ==true){
                StyleConstants.setForeground(style, colorType.getdColor());
            }else{
                StyleConstants.setForeground(style, colorType.getColor());
            }
            this.colorStyles.put(colorType.name(), style);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {

            new Compilador().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_bg;
    private javax.swing.JButton btnAbrir;
    private javax.swing.JButton btnCompilar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarComo;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JPanel buttonsFilePanel;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jspCode;
    private javax.swing.JTextArea jtaConsole;
    private javax.swing.JTextPane jtpCode;
    private javax.swing.JPanel panelButtonCompilerExecute;
    private javax.swing.JPanel rootPanel;
    private javax.swing.JTable tblTokens;
    // End of variables declaration//GEN-END:variables
}