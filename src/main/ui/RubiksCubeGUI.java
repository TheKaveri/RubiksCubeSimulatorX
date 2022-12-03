package ui;

import model.Cube;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Represents a Rubiks Cube UI with a panel, menu-bar and various buttons
public class RubiksCubeGUI extends JFrame implements ActionListener {
    private static final String[] MOVES = new String[]{"F", "f", "F2", "R", "r", "R2", "U", "u", "U2",
            "D", "d", "D2", "L", "l", "L2", "B", "b", "B2",};
    private static final String JSON_PATH = "./data/rubiksCube.json";
    private final CubeMenuBar cubeMenuBar;
    private Cube rubiksCube;
    private ArrayList<Cube> cubeStates;
    private int cubeStateIdx;

    private final JTextArea movesArea;

    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private CubePanel cubePanel;
    private final JButton undoButton;
    private final JButton redoButton;
    private final JButton shuffleButton;
    private final JButton resetButton;

    private final JButton normalF;
    private final JButton normalU;
    private final JButton normalL;
    private final JButton normalR;
    private final JButton normalB;
    private final JButton normalD;

    private final JButton primeF;
    private final JButton primeU;
    private final JButton primeL;
    private final JButton primeR;
    private final JButton primeB;
    private final JButton primeD;

    private final JButton twiceF;
    private final JButton twiceU;
    private final JButton twiceL;
    private final JButton twiceR;
    private final JButton twiceB;
    private final JButton twiceD;

    // EFFECTS: creates a RubiksCubeGUI instance
    RubiksCubeGUI() throws CloneNotSupportedException {
        rubiksCube = new Cube();
        jsonWriter = new JsonWriter(JSON_PATH);
        jsonReader = new JsonReader(JSON_PATH);

        cubeStates = new ArrayList<>();
        cubeStateIdx = -1;
        addCubeState((Cube) rubiksCube.clone());

        this.setTitle("Rubiks Cube Simulator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(700, 600);

        cubeMenuBar = getCubeMenuBar();

        movesArea = new JTextArea("Applied Moves: ", 13, 30);
        JScrollPane movesAreaScrollPane = new JScrollPane(movesArea);
        movesAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel movesPanel = new JPanel();
        movesPanel.add(movesAreaScrollPane);
        movesPanel.setBounds(5, 300, 400, 400);
        this.add(movesPanel);

        undoButton = new JButton();
        undoButton.setText("Undo");
        undoButton.addActionListener(this);
        undoButton.setBounds(450, 20, 200, 50);

        redoButton = new JButton();
        redoButton.setText("Redo");
        redoButton.addActionListener(this);
        redoButton.setBounds(450, 70, 200, 50);


        shuffleButton = new JButton();
        shuffleButton.setText("Shuffle");
        shuffleButton.addActionListener(this);
        shuffleButton.setBounds(450, 120, 200, 50);

        normalF = new JButton();
        normalF.setText("F");
        normalF.addActionListener(this);
        normalF.setBounds(450, 170, 66, 50);

        normalU = new JButton();
        normalU.setText("U");
        normalU.addActionListener(this);
        normalU.setBounds(450, 220, 66, 50);

        normalL = new JButton();
        normalL.setText("L");
        normalL.addActionListener(this);
        normalL.setBounds(450, 270, 66, 50);

        normalR = new JButton();
        normalR.setText("R");
        normalR.addActionListener(this);
        normalR.setBounds(450, 320, 66, 50);

        normalB = new JButton();
        normalB.setText("B");
        normalB.addActionListener(this);
        normalB.setBounds(450, 370, 66, 50);

        normalD = new JButton();
        normalD.setText("D");
        normalD.addActionListener(this);
        normalD.setBounds(450, 420, 66, 50);

        primeF = new JButton();
        primeF.setText("f");
        primeF.addActionListener(this);
        primeF.setBounds(450 + 66, 170, 66, 50);

        primeU = new JButton();
        primeU.setText("u");
        primeU.addActionListener(this);
        primeU.setBounds(450 + 66, 220, 66, 50);

        primeL = new JButton();
        primeL.setText("l");
        primeL.addActionListener(this);
        primeL.setBounds(450 + 66, 270, 66, 50);

        primeR = new JButton();
        primeR.setText("r");
        primeR.addActionListener(this);
        primeR.setBounds(450 + 66, 320, 66, 50);

        primeB = new JButton();
        primeB.setText("b");
        primeB.addActionListener(this);
        primeB.setBounds(450 + 66, 370, 66, 50);

        primeD = new JButton();
        primeD.setText("d");
        primeD.addActionListener(this);
        primeD.setBounds(450 + 66, 420, 66, 50);

        twiceF = new JButton();
        twiceF.setText("F2");
        twiceF.addActionListener(this);
        twiceF.setBounds(450 + 66 + 66, 170, 66, 50);

        twiceU = new JButton();
        twiceU.setText("U2");
        twiceU.addActionListener(this);
        twiceU.setBounds(450 + 66 + 66, 220, 66, 50);

        twiceL = new JButton();
        twiceL.setText("L2");
        twiceL.addActionListener(this);
        twiceL.setBounds(450 + 66 + 66, 270, 66, 50);

        twiceR = new JButton();
        twiceR.setText("R2");
        twiceR.addActionListener(this);
        twiceR.setBounds(450 + 66 + 66, 320, 66, 50);

        twiceB = new JButton();
        twiceB.setText("B2");
        twiceB.addActionListener(this);
        twiceB.setBounds(450 + 66 + 66, 370, 66, 50);

        twiceD = new JButton();
        twiceD.setText("D2");
        twiceD.addActionListener(this);
        twiceD.setBounds(450 + 66 + 66, 420, 66, 50);

        resetButton = new JButton();
        resetButton.setText("Reset");
        resetButton.addActionListener(this);
        resetButton.setBounds(450, 420 + 50, 200, 50);

        this.setJMenuBar(cubeMenuBar);
        this.add(undoButton);
        this.add(redoButton);
        this.add(shuffleButton);
        this.add(cubePanel);

        this.add(normalF);
        this.add(normalU);
        this.add(normalL);
        this.add(normalR);
        this.add(normalB);
        this.add(normalD);

        this.add(primeF);
        this.add(primeU);
        this.add(primeL);
        this.add(primeR);
        this.add(primeB);
        this.add(primeD);

        this.add(twiceF);
        this.add(twiceU);
        this.add(twiceL);
        this.add(twiceR);
        this.add(twiceB);
        this.add(twiceD);

        this.add(resetButton);

        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: returns the UI menu bar
    private CubeMenuBar getCubeMenuBar() {
        final CubeMenuBar cubeMenuBar;
        cubeMenuBar = new CubeMenuBar();
        cubeMenuBar.loadItem.addActionListener(this);
        cubeMenuBar.saveItem.addActionListener(this);
        cubeMenuBar.quitItem.addActionListener(this);
        cubeMenuBar.readMeItem.addActionListener(this);
        cubePanel = new CubePanel(rubiksCube);
        return cubeMenuBar;
    }


    // MODIFIES: this
    // EFFECTS: provides an action to all interactive buttons and options
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == cubeMenuBar.quitItem) {
                printLog(EventLog.getInstance());
                System.exit(0);
            } else if (e.getSource() == cubeMenuBar.loadItem) {
                rubiksCube = jsonReader.read();

                cubeStates = new ArrayList<>();
                cubeStateIdx = -1;
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == cubeMenuBar.saveItem) {

                jsonWriter.open();
                jsonWriter.write(rubiksCube);
                jsonWriter.close();
            } else if (e.getSource() == cubeMenuBar.readMeItem) {
                new ReadMeWindow();
            } else if (e.getSource() == normalF) {
                rubiksCube.doMove("F");
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == normalU) {
                rubiksCube.doMove("U");
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == normalL) {
                rubiksCube.doMove("L");
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == normalR) {
                rubiksCube.doMove("R");
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == normalB) {
                rubiksCube.doMove("B");
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == normalD) {
                rubiksCube.doMove("D");
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == primeF) {
                rubiksCube.doMove("f");
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == primeU) {
                rubiksCube.doMove("u");
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == primeL) {
                rubiksCube.doMove("l");
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == primeR) {
                rubiksCube.doMove("r");
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == primeB) {
                rubiksCube.doMove("b");
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == primeD) {
                rubiksCube.doMove("d");
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == twiceF) {
                rubiksCube.doMove("F2");
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == twiceU) {
                rubiksCube.doMove("U2");
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == twiceL) {
                rubiksCube.doMove("L2");
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == twiceR) {
                rubiksCube.doMove("R2");
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == twiceB) {
                rubiksCube.doMove("B2");
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == twiceD) {
                rubiksCube.doMove("D2");
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == resetButton) {
                rubiksCube.reset();
                addCubeState((Cube) rubiksCube.clone());
            } else if (e.getSource() == undoButton) {
                rubiksCube = getPreviousCubeState();
            } else if (e.getSource() == redoButton) {
                rubiksCube = getNextCubeState();
            } else if (e.getSource() == shuffleButton) {
                for (int i = 0; i < 20; i++) {
                    int randomMoveIdx = (int) (Math.random() * MOVES.length);
                    String randomMove = MOVES[randomMoveIdx];
                    rubiksCube.doMove(randomMove);
                }
                addCubeState((Cube) rubiksCube.clone());
            }

            cubePanel.setCube(rubiksCube);
            cubePanel.repaint();
            setMovesArea();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

    }


    // MODIFIES: this
    // EFFECTS: sets a text field for viewing all the moves performed
    private void setMovesArea() {
        movesArea.setText("");
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < rubiksCube.getMovesLog().size(); i++) {
            s.append(rubiksCube.getMovesLog().get(i)).append(", ");

            if (i % 18 == 0 && i > 0) {
                s.append("\n");
            }
        }
        movesArea.setText(s.toString());
    }


    private void addCubeState(Cube cube) {
        if (cubeStateIdx != cubeStates.size() - 1) {
            cubeStates.subList(cubeStateIdx + 1, cubeStates.size()).clear();
        }
        cubeStates.add(cube);
        cubeStateIdx = cubeStates.size() - 1;
    }


    private Cube getPreviousCubeState() throws CloneNotSupportedException {
        cubeStateIdx--;
        if (cubeStateIdx == -1) {
            cubeStateIdx = 0;
        }
        return (Cube) cubeStates.get(cubeStateIdx).clone();
    }

    private Cube getNextCubeState() throws CloneNotSupportedException {
        cubeStateIdx++;
        if (cubeStateIdx == cubeStates.size()) {
            cubeStateIdx = cubeStates.size() - 1;
        }
        return (Cube) cubeStates.get(cubeStateIdx).clone();
    }

    // EFFECTS: displays all the events that have occurred
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.print((next.toString() + "\n\n"));
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        new RubiksCubeGUI();
    }
}
