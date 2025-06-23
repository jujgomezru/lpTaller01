package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import model.scanner.LexerAnalyzer;
import model.scanner.Token;
import utils.TokenTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import model.scanner.ColorType;
import utils.utils;

public class Compilador extends javax.swing.JFrame {

    public TokenTableModel tblTokensModel;
    private final FileController fileController;
    private final LexerController lexerController;
    private SyntaxHighlighter syntaxHighlighter;
    public String currentFilePath = null;
    public String currentFileName = null;
    private HashMap<String, Style> colorStyles;
    private List Ctokens;
    private boolean dark=false;

    public Compilador() {
        initComponents();
        fileController = new FileController();
        lexerController = new LexerController();
        syntaxHighlighter = new SyntaxHighlighter();
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
        StyledDocument doc = (StyledDocument) jtpCode.getDocument();
        syntaxHighlighter.createStyles(doc, dark);
        updateColors();
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
    private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) {
        fileController.showOpenDialog(this).ifPresent(file -> {
            currentFilePath = file.getAbsolutePath();
            currentFileName = file.getName();
            try {
                String content = java.nio.file.Files.readString(file.toPath());
                jtpCode.setText(content);
                jtaConsole.setText("");
                fillTable(tblTokens, new ArrayList<>());
                updateColors();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al leer archivo: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        if (currentFilePath == null) {
            // si no hay ruta, use "Guardar como"
            btnGuardarComoActionPerformed(evt);
        } else {
            try {
                java.nio.file.Files.writeString(java.nio.file.Path.of(currentFilePath), jtpCode.getText());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnGuardarComoActionPerformed(java.awt.event.ActionEvent evt) {
        fileController.showSaveDialog(this).ifPresent(file -> {
            try {
                java.nio.file.Files.writeString(file.toPath(), jtpCode.getText());
                currentFilePath = file.getAbsolutePath();
                currentFileName = file.getName();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al guardar archivo: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) {
        String source = jtpCode.getText().trim();
        LexerController.AnalysisResult result = lexerController.analyze(source);
        fillTable(tblTokens, result.getTokens());
        fillErrorPanel(jtaConsole, result.getErrors());
    }

    private void jtpCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtpCodeKeyReleased
        // Obtén el StyledDocument del JTextPane
        this.updateColors();

    }//GEN-LAST:event_jtpCodeKeyReleased
    public void updateColors() {
        StyledDocument doc = (StyledDocument) this.jtpCode.getDocument();
        // Regenera los estilos según tema actual
        syntaxHighlighter.createStyles(doc, dark);

        String txt = this.jtpCode.getText().trim();
        LexerController.AnalysisResult result = lexerController.analyze(txt);

        try {
            syntaxHighlighter.applyHighlight(doc, result.getTokens());
        } catch (Exception e) {
            e.printStackTrace();
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
        StyledDocument doc = (StyledDocument) jtpCode.getDocument();
        syntaxHighlighter.createStyles(doc, dark);
        updateColors();


    }//GEN-LAST:event_bt_bgActionPerformed

    public void fillTable(JTable table, List<Token> tokens) {
        TokenTableModel model = new TokenTableModel(tokens);
        table.setModel(model);
    }

    public void fillErrorPanel(JTextArea outputArea, List<Token> errors) {
        StringBuilder result = new StringBuilder();

        if (errors.isEmpty()) {
            result.append("No se encontraron errores en el análisis léxico.\n");
        } else {
            result.append("Se encontraron errores en el análisis léxico:\n\n");
            for (Token token : errors) {
                result.append(token).append("\n");
            }
        }

        outputArea.setText(result.toString());
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