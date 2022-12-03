package ui;

import model.Cube;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

// Represents a Rubiks Cube app
public class RubiksCubeConsoleUI {
    private static final String JSON_PATH = "./data/rubiksCube.json";
    private Cube rubiksCube;
    private Cube rubiksCubeUndoState;
    private Cube rubiksCubeRedoState;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    private static final HashMap<Character, String> COLOR_MAPPER = new HashMap<>();
    private static final String[] MOVES = new String[]{"F", "f", "F2", "R", "r", "R2", "U", "u", "U2", "D", "d",
            "D2", "L", "l", "L2", "B", "b", "B2",};

    // EFFECTS: runs the rubiks cube application
    public RubiksCubeConsoleUI() throws CloneNotSupportedException {
        rubiksCube = new Cube();
        rubiksCubeUndoState = (Cube) rubiksCube.clone();
        rubiksCubeRedoState = (Cube) rubiksCube.clone();
        jsonWriter = new JsonWriter(JSON_PATH);
        jsonReader = new JsonReader(JSON_PATH);
        this.initializeMapper();
        this.runMenu();
    }

    // MODIFIES: Cube
    // EFFECTS: initializes the COLOR_MAPPER to improvise output
    private void initializeMapper() {
        COLOR_MAPPER.put('W', "\033[1;37m");
        COLOR_MAPPER.put('R', "\033[1;31m");
        COLOR_MAPPER.put('G', "\033[1;32m");
        COLOR_MAPPER.put('O', "\033[1;38;5;166m");
        COLOR_MAPPER.put('B', "\033[1;34m");
        COLOR_MAPPER.put('Y', "\033[1;33m");
        COLOR_MAPPER.put('X', "\033[0m");
    }


    // MODIFIES: Cube
    // EFFECTS: runs and processes menu options of the user
    private void runMenu() throws CloneNotSupportedException {
        System.out.println("Hello There! Welcome to the Rubik's Cube App");
        Scanner input = new Scanner(System.in);

        displayMenu();
        do {
            System.out.print(">> ");
            String options = input.nextLine().trim();
            String[] optionsArray = options.split(";");
            for (String option : optionsArray) {
                option = option.trim();
                if (option.equals("menu")) {
                    displayMenu();
                } else if (option.equals("view")) {
                    printCube();
                } else if (option.equals("view moves")) {
                    printAvailableMoves();
                } else if (option.startsWith("do move")) {
                    doMoves(option);
                } else if (option.equals("undo")) {
                    doUndo();
                } else if (option.equals("redo")) {
                    doRedo();
                } else if (option.startsWith("shuffle")) {
                    doShuffle(option);
                } else if (option.equals("reset")) {
                    doReset();
                } else if (option.equals("custom")) {
                    setCustomPermutation();
                } else if (option.equals("load")) {
                    doLoadCube();
                } else if (option.equals("quit")) {
                    doQuit();
                } else {
                    System.out.println("invalid choice");
                }
            }
        } while (true);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nChose from the following options:");
        System.out.println("\tmenu -> view this menu again");
        System.out.println("\tview -> view the cube");
        System.out.println("\tview moves -> view all possible cube moves and also moves performed");
        System.out.println("\tdo move <some move(s) separated by spaces> -> move the cube "
                + "(eg: do move r D F2 -> does the moves r, D and F2 on the cube)");
        System.out.println("\tundo -> undo the previous move(s)/shuffle/reset");
        System.out.println("\tredo -> redo the previous move(s)/shuffle/reset");
        System.out.println("\tshuffle n -> shuffle the cube using n moves (eg: shuffle 7 -> shuffles using 7 moves)");
        System.out.println("\treset -> reset the cube to original state");
        System.out.println("\tcustom -> set a custom cube permutation");
        System.out.println("\tload -> loads cube state from the file");
        System.out.println("\tquit -> quit while having a choice to save the cube state to the file");
        System.out.println("Note: To chose multiple options at once, enter their keywords separated by ;");
    }


    // EFFECTS: displays the cube on the console
    private void printCube() {
        int rowLength = rubiksCube.getFace("up").length;
        for (int row = 0; row < rowLength; row++) {
            System.out.print("       ");
            printRow(rubiksCube.getFace("up"), row);
            System.out.println();
        }

        for (int row = 0; row < rowLength; row++) {
            printRow(rubiksCube.getFace("left"), row);
            printRow(rubiksCube.getFace("front"), row);
            printRow(rubiksCube.getFace("right"), row);
            printRow(rubiksCube.getFace("back"), row);
            System.out.println();
        }

        for (int row = 0; row < rowLength; row++) {
            System.out.print("       ");
            printRow(rubiksCube.getFace("down"), row);
            System.out.println();
        }
    }

    // EFFECTS: displays all available moves
    private void printAvailableMoves() {
        System.out.print("available moves: ");
        System.out.println(Arrays.toString(MOVES));
        System.out.print("performed moves: ");

        ArrayList<String> performed = new ArrayList<>(rubiksCube.getMovesLog());
        System.out.println(performed);
    }

    // REQUIRES: facelets is a 3 by 3 2D array and rowIdx is between 0 and 2 (inclusive)
    // EFFECTS: displays the cube's face's row on the console
    private void printRow(char[][] facelets, int rowIdx) {
        for (int col = 0; col < facelets[rowIdx].length; col++) {
            printFacelet(facelets[rowIdx][col]);
        }
        System.out.print(" ");
    }

    // REQUIRES: color must be either 'W', 'R', 'G', 'O', 'B' or 'Y'
    // EFFECTS: displays the facelet on the console
    private void printFacelet(char color) {
        System.out.print(COLOR_MAPPER.get(color) + color + COLOR_MAPPER.get('X') + " ");
    }

    // REQUIRES: option should follow this format: do move <some move(s) separated by spaces>
    // MODIFIES: Cube
    // EFFECTS: does move(s) on the cube
    private void doMoves(String option) throws CloneNotSupportedException {
        rubiksCubeUndoState = (Cube) rubiksCube.clone();
        String[] optionList = option.split(" ");
        String[] moveNames = new String[optionList.length - 2];
        System.arraycopy(optionList, 2, moveNames, 0, moveNames.length);
        for (String moveName : moveNames) {
            rubiksCube.doMove(moveName);
        }
        System.out.print("performed the following " + moveNames.length + " move(s) on the cube: ");
        System.out.println(Arrays.toString(moveNames));
    }

    // MODIFIES: Cube
    // EFFECTS: undoes the cube to the nearest previous state
    private void doUndo() throws CloneNotSupportedException {
        rubiksCubeRedoState = (Cube) rubiksCube.clone();
        rubiksCube = (Cube) rubiksCubeUndoState.clone();
    }

    // MODIFIES: Cube
    // EFFECTS: redoes the cube to the nearest future state
    private void doRedo() throws CloneNotSupportedException {
        rubiksCube = (Cube) rubiksCubeRedoState.clone();
    }

    // REQUIRES: option
    // MODIFIES: Cube
    // EFFECTS: shuffles cube by some n moves
    private void doShuffle(String option) throws CloneNotSupportedException {
        rubiksCubeUndoState = (Cube) rubiksCube.clone();
        String[] optionList = option.split(" ");
        int numberOfMoves = Integer.parseInt(optionList[1]);
        System.out.print("shuffled the cube using the following " + numberOfMoves + " move(s): ");
        System.out.println(shuffleCubeHelper(numberOfMoves));
    }

    // REQUIRES: n should be positive
    // MODIFIES: Cube
    // EFFECTS: shuffles cube by some n moves
    private ArrayList<String> shuffleCubeHelper(int n) throws CloneNotSupportedException {
        ArrayList<String> moves = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int randomMoveIdx = (int) (Math.random() * MOVES.length);
            String randomMove = MOVES[randomMoveIdx];
            rubiksCube.doMove(randomMove);
            moves.add(randomMove);
        }
        return moves;
    }

    // MODIFIES: Cube
    // EFFECTS: resets cube to the original state
    private void doReset() throws CloneNotSupportedException {
        rubiksCubeUndoState = (Cube) rubiksCube.clone();
        rubiksCube = new Cube();
        System.out.println("cube returned to original state");
    }

    // REQUIRES: faceletColors must be either 'W', 'R', 'G', 'O', 'B' or 'Y' and should be 9 in total for each face
    // MODIFIES: Cube
    // EFFECTS: sets a custom permutation to the cube
    private void setCustomPermutation() throws CloneNotSupportedException {
        rubiksCubeUndoState = (Cube) rubiksCube.clone();
        Scanner input = new Scanner(System.in);

        String[] faces = {"Up", "Down", "Right", "Left", "Front", "Back"};

        for (String face : faces) {
            System.out.println("Enter a custom " + face + " Face: ");
            System.out.print(">> ");
            String facelets = input.nextLine();
            rubiksCube.setFace(face.toLowerCase(), facelets);
        }
    }

    // EFFECTS: saves the cube state to file
    private void doSaveCube() {
        try {
            jsonWriter.open();
            jsonWriter.write(rubiksCube);
            jsonWriter.close();
            System.out.println("saved  to  " + JSON_PATH);
        } catch (IOException e) {
            System.out.println("unable to write to file: " + JSON_PATH);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads cube state from file
    private void doLoadCube() {
        try {
            rubiksCube = jsonReader.read();
            System.out.println("loaded the cube from " + JSON_PATH);
        } catch (IOException e) {
            System.out.println("unable to read from file: " + JSON_PATH);
        }
    }

    // EFFECTS: terminates the program, after saving the cube state as per the user's wish
    private void doQuit() {
        System.out.println("would you like to save the cube state (y/n):");
        Scanner input = new Scanner(System.in);
        System.out.print(">> ");
        String answer = input.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            doSaveCube();
            System.exit(0);
        } else if (answer.equalsIgnoreCase("n")) {
            System.exit(0);
        } else {
            System.out.println("invalid choice, going back to menu.");
        }
        printLog(EventLog.getInstance());
    }

    // EFFECTS: displays all the events that have occurred
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.print((next.toString() + "\n\n"));
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        new RubiksCubeConsoleUI();
    }
}
