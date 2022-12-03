package ui;

import javax.swing.*;
import java.awt.*;

public class ReadMeWindow extends JDialog {
    JDialog readMeWindow;

    public ReadMeWindow() {
        GraphicsDevice gd = this.getGraphicsConfiguration().getDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        readMeWindow = new JDialog();
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("data/README.jpg");
        System.out.println(width);
        Image image =
                imageIcon.getImage().getScaledInstance(1936, 1500, Image.SCALE_SMOOTH);
        imageIcon.setImage(image);
        imageLabel.setIcon(imageIcon);

        JScrollPane imageScrollPane = new JScrollPane(imageLabel);

        readMeWindow.setTitle("README");
        readMeWindow.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        readMeWindow.add(imageScrollPane);
        readMeWindow.setSize(width - 100, height - 100);
        readMeWindow.setLocationRelativeTo(null);
        readMeWindow.setVisible(true);
    }
}
