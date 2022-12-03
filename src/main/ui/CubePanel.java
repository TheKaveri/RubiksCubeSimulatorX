package ui;

import model.Cube;

import javax.swing.*;
import java.awt.*;

public class CubePanel extends JPanel {
    private static final int SIDE = 30;
    Cube cube;

    CubePanel(Cube cube) {
        this.setBounds(20, 20, 12 * SIDE + 10, 9 * SIDE + 10);
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        this.cube = cube;
    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        paintFaces(g2D);
    }

    private void paintFaces(Graphics2D graphics2D) {
        this.paintFace(cube.getFace("up"), SIDE * 3, 0, graphics2D);
        this.paintFace(cube.getFace("left"), 0, SIDE * 3, graphics2D);
        this.paintFace(cube.getFace("front"), SIDE * 3, SIDE * 3, graphics2D);
        this.paintFace(cube.getFace("right"), SIDE * 6, SIDE * 3, graphics2D);
        this.paintFace(cube.getFace("back"), SIDE * 9, SIDE * 3, graphics2D);
        this.paintFace(cube.getFace("down"), SIDE * 3, SIDE * 6, graphics2D);
    }

    private void paintFace(char[][] face, int x, int y, Graphics2D graphics2D) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                graphics2D.setPaint(Color.black);
                graphics2D.drawRoundRect(x + j * SIDE, y + SIDE * i, SIDE, SIDE, 10, 10);
                graphics2D.setPaint(this.getColor(face[i][j]));
                graphics2D.fillRoundRect(x + j * SIDE, y + SIDE * i, SIDE, SIDE, 10, 10);
            }
        }
    }

    private Color getColor(char c) {
        if (c == 'R') {
            return Color.red;
        }
        if (c == 'O') {
            return Color.orange;
        }
        if (c == 'G') {
            return Color.green;
        }
        if (c == 'B') {
            return Color.blue;
        }
        if (c == 'W') {
            return Color.white;
        }
        if (c == 'Y') {
            return Color.yellow;
        }
        return null;
    }

    public void setCube(Cube cube) {
        this.cube = cube;
    }
}
