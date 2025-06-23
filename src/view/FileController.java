package view;

import java.io.File;
import java.util.Optional;
import java.awt.Component;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileController {
    private JFileChooser chooser;
    public FileController() {
        chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt", "cz"));
    }

    public Optional<File> showOpenDialog(Component parent) {
        int res = chooser.showOpenDialog(parent);
        return res == JFileChooser.APPROVE_OPTION
                ? Optional.of(chooser.getSelectedFile())
                : Optional.empty();
    }

    public Optional<File> showSaveDialog(Component parent) {
        int res = chooser.showSaveDialog(parent);
        return res == JFileChooser.APPROVE_OPTION
                ? Optional.of(chooser.getSelectedFile())
                : Optional.empty();
    }
}
