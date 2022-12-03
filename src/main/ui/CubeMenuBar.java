package ui;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class CubeMenuBar extends JMenuBar {
    protected JMenu fileMenu;
    protected JMenu helpMenu;
    protected JMenuItem loadItem;
    protected JMenuItem saveItem;
    protected JMenuItem quitItem;
    protected JMenuItem readMeItem;

    public CubeMenuBar() {
        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");

        loadItem = new JMenuItem("Load Previous Cube State");
        saveItem = new JMenuItem("Save Current Cube State");
        quitItem = new JMenuItem("Quit");

        readMeItem = new JMenuItem("README");

        fileMenu.setMnemonic(KeyEvent.VK_F);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        loadItem.setMnemonic(KeyEvent.VK_L);
        saveItem.setMnemonic(KeyEvent.VK_S);
        quitItem.setMnemonic(KeyEvent.VK_Q);
        readMeItem.setMnemonic(KeyEvent.VK_R);

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(quitItem);

        helpMenu.add(readMeItem);

        this.add(fileMenu);
        this.add(helpMenu);
    }
}
