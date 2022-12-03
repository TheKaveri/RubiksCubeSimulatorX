package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.HashMap;

// Represents a 3 by 3 rubik's cube which is a collection of  six faces (front, up, right, down, left, back)
public class Cube implements Cloneable, Writable {
    private HashMap<String, Face> faceHashMap;
    private ArrayList<String> movesLog;

    // constructors:

    // EFFECTS: constructs a standard 3 x 3 rubik's cube using standard colors
    public Cube() {
        faceHashMap = new HashMap<>();
        faceHashMap.put("up", new Face('R'));
        faceHashMap.put("down", new Face('O'));
        faceHashMap.put("right", new Face('G'));
        faceHashMap.put("left", new Face('B'));
        faceHashMap.put("front", new Face('W'));
        faceHashMap.put("back", new Face('Y'));

        movesLog = new ArrayList<>();
    }

    // setters:

    // REQUIRES: faceName must be "up", "down", "right", "left", "front" or "back"
    // MODIFIES: this
    // EFFECTS: sets the faces of the cube in a custom order
    public void setFace(String faceName, String faceletsAsString) {
        String[] arrayFacelets = faceletsAsString.trim().split(" ");
        char[][] facelets = new char[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                facelets[row][col] = arrayFacelets[col + 3 * row].charAt(0);
            }
        }
        EventLog.getInstance().logEvent(new Event("Updated the cube's " + faceName + "face with " + faceletsAsString));
        setFaceHelper(faceName, facelets);
    }

    // REQUIRES: faceName must be "up", "down", "right", "left", "front" or "back"
    // facelets must be a 3 x 3 char array with
    // MODIFIES: this
    // EFFECTS: sets the faces of the cube in a custom order
    private void setFaceHelper(String faceName, char[][] facelets) {
        if (faceHashMap.containsKey(faceName)) {
            faceHashMap.put(faceName, new Face(facelets));
        }
    }

    // MODIFIES: this
    // EFFECTS: twists the up face of the cube clockwise
    private void normalU() throws CloneNotSupportedException {
        faceHashMap.get("up").rotateClockWise();

        Face temp = (Face) faceHashMap.get("front").clone();

        faceHashMap.get("front").setRow(faceHashMap.get("right").getRow(0), 0);
        faceHashMap.get("right").setRow(faceHashMap.get("back").getRow(0), 0);
        faceHashMap.get("back").setRow(faceHashMap.get("left").getRow(0), 0);
        faceHashMap.get("left").setRow(temp.getRow(0), 0);

    }

    // MODIFIES: this
    // EFFECTS: twists the down face of the cube clockwise
    private void normalD() throws CloneNotSupportedException {
        faceHashMap.get("down").rotateClockWise();

        Face temp = (Face) faceHashMap.get("front").clone();

        faceHashMap.get("front").setRow(faceHashMap.get("left").getRow(2), 2);
        faceHashMap.get("left").setRow(faceHashMap.get("back").getRow(2), 2);
        faceHashMap.get("back").setRow(faceHashMap.get("right").getRow(2), 2);
        faceHashMap.get("right").setRow(temp.getRow(2), 2);
    }

    // MODIFIES: this
    // EFFECTS: twists the right face of the cube clockwise
    private void normalR() throws CloneNotSupportedException {
        faceHashMap.get("right").rotateClockWise();

        Face temp = (Face) faceHashMap.get("up").clone();

        faceHashMap.get("up").setColumn(faceHashMap.get("front").getColumnAsRow(2), 2);
        faceHashMap.get("front").setColumn(faceHashMap.get("down").getColumnAsRow(2), 2);
        faceHashMap.get("down").setColumn(faceHashMap.get("back").getReversedColumnAsRow(0), 2);
        faceHashMap.get("back").setColumn(temp.getReversedColumnAsRow(2), 0);
    }

    // MODIFIES: this
    // EFFECTS: twists the left face of the cube clockwise
    private void normalL() throws CloneNotSupportedException {
        faceHashMap.get("left").rotateClockWise();

        Face temp = (Face) faceHashMap.get("down").clone();

        faceHashMap.get("down").setColumn(faceHashMap.get("front").getColumnAsRow(0), 0);
        faceHashMap.get("front").setColumn(faceHashMap.get("up").getColumnAsRow(0), 0);
        faceHashMap.get("up").setColumn(faceHashMap.get("back").getReversedColumnAsRow(2), 0);
        faceHashMap.get("back").setColumn(temp.getReversedColumnAsRow(0), 2);

    }

    // MODIFIES: this
    // EFFECTS: twists the front face of the cube clockwise
    private void normalF() throws CloneNotSupportedException {
        faceHashMap.get("front").rotateClockWise();

        Face temp = (Face) faceHashMap.get("up").clone();

        faceHashMap.get("up").setRow(faceHashMap.get("left").getReversedColumnAsRow(2), 2);
        faceHashMap.get("left").setColumn(faceHashMap.get("down").getRow(0), 2);
        faceHashMap.get("down").setRow(faceHashMap.get("right").getReversedColumnAsRow(0), 0);
        faceHashMap.get("right").setColumn(temp.getRow(2), 0);
    }

    // MODIFIES: this
    // EFFECTS: twists the back face of the cube clockwise
    private void normalB() throws CloneNotSupportedException {
        faceHashMap.get("back").rotateClockWise();

        Face temp = (Face) faceHashMap.get("down").clone();

        faceHashMap.get("down").setRow(faceHashMap.get("left").getColumnAsRow(0), 2);
        faceHashMap.get("left").setColumn(faceHashMap.get("up").getReversedRow(0), 0);
        faceHashMap.get("up").setRow(faceHashMap.get("right").getColumnAsRow(2), 0);
        faceHashMap.get("right").setColumn(temp.getReversedRow(2), 2);

    }

    // MODIFIES: this
    // EFFECTS: performs a single move on the cube
    public void doMove(String move) throws CloneNotSupportedException {
        this.movesLog.add(move);
        if (Character.isUpperCase(move.charAt(0)) && move.length() == 1) {
            this.doNormalMove(move);
        } else if (Character.isLowerCase(move.charAt(0)) && move.length() == 1) {
            this.doPrimeMove(move);
        } else if (move.length() == 2) {
            this.doTwiceMove(move);
        }
        EventLog.getInstance().logEvent(
                new Event("Performed the move: " + move + " on the cube"));
    }

    // MODIFIES: this
    // EFFECTS: performs a single "normal move" on the cube
    private void doNormalMove(String move) throws CloneNotSupportedException {
        switch (move.toLowerCase()) {
            case "u":
                this.normalU();
                break;
            case "d":
                this.normalD();
                break;
            case "r":
                this.normalR();
                break;
            case "l":
                this.normalL();
                break;
            case "f":
                this.normalF();
                break;
            case "b":
                this.normalB();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: performs a single "prime move" on the cube
    private void doPrimeMove(String move) throws CloneNotSupportedException {
        switch (move.toLowerCase()) {
            case "u":
                this.normalU();
                this.normalU();
                this.normalU();
                break;
            case "d":
                this.normalD();
                this.normalD();
                this.normalD();
                break;
            case "r":
                this.normalR();
                this.normalR();
                this.normalR();
                break;
            case "l":
                this.normalL();
                this.normalL();
                this.normalL();
                break;
            case "f":
                this.normalF();
                this.normalF();
                this.normalF();
                break;
            case "b":
                this.normalB();
                this.normalB();
                this.normalB();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: performs a single "twice move" on the cube
    private void doTwiceMove(String move) throws CloneNotSupportedException {
        if (move.equalsIgnoreCase("U2")) {
            this.normalU();
            this.normalU();
        } else if (move.equalsIgnoreCase("D2")) {
            this.normalD();
            this.normalD();
        } else if (move.equalsIgnoreCase("R2")) {
            this.normalR();
            this.normalR();
        } else if (move.equalsIgnoreCase("L2")) {
            this.normalL();
            this.normalL();
        } else if (move.equalsIgnoreCase("F2")) {
            this.normalF();
            this.normalF();
        } else if (move.equalsIgnoreCase("B2")) {
            this.normalB();
            this.normalB();
        }
    }

    public void reset() {
        faceHashMap = new HashMap<>();
        faceHashMap.put("up", new Face('R'));
        faceHashMap.put("down", new Face('O'));
        faceHashMap.put("right", new Face('G'));
        faceHashMap.put("left", new Face('B'));
        faceHashMap.put("front", new Face('W'));
        faceHashMap.put("back", new Face('Y'));

        movesLog = new ArrayList<>();

        EventLog.getInstance().clear();
    }

    // MODIFIES: this
    // EFFECTS: sets a custom list of moves to the movesLog
    public void setMovesLog(ArrayList<String> movesLog) {
        this.movesLog = movesLog;
        EventLog.getInstance().logEvent(new Event("Added " + movesLog.size() + " moves to movesLog"));
    }

    // getters:

    // REQUIRES: faceName should be either up, down, right, left, front or back
    // EFFECTS: returns an array representation of the cube's "faceName" face
    public char[][] getFace(String faceName) {
        if (faceHashMap.containsKey(faceName)) {
            return new char[][]
                    {faceHashMap.get(faceName).getRow(0),
                            faceHashMap.get(faceName).getRow(1),
                            faceHashMap.get(faceName).getRow(2)};
        } else {
            return null;
        }
    }

    // EFFECTS: returns the list of moves performed on the cube
    public ArrayList<String> getMovesLog() {
        return this.movesLog;
    }

    // cloner:

    // EFFECTS: returns a field-by-field clone of the cube
    public Object clone() throws CloneNotSupportedException {
        Cube cubeClone = (Cube) super.clone();
        cubeClone.faceHashMap = new HashMap<>();
        cubeClone.faceHashMap.put("up", (Face) this.faceHashMap.get("up").clone());
        cubeClone.faceHashMap.put("down", (Face) this.faceHashMap.get("down").clone());
        cubeClone.faceHashMap.put("right", (Face) this.faceHashMap.get("right").clone());
        cubeClone.faceHashMap.put("left", (Face) this.faceHashMap.get("left").clone());
        cubeClone.faceHashMap.put("front", (Face) this.faceHashMap.get("front").clone());
        cubeClone.faceHashMap.put("back", (Face) this.faceHashMap.get("back").clone());

        cubeClone.movesLog = new ArrayList<>();
        cubeClone.movesLog = (ArrayList<String>) this.movesLog.clone();

        EventLog.getInstance().logEvent(new Event("Created a cube clone"));
        return cubeClone;
    }

    @Override
    // EFFECTS: returns a json representation of faces
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("up", faceHashMap.get("up").toJson());
        json.put("down", faceHashMap.get("down").toJson());
        json.put("right", faceHashMap.get("right").toJson());
        json.put("left", faceHashMap.get("left").toJson());
        json.put("front", faceHashMap.get("front").toJson());
        json.put("back", faceHashMap.get("back").toJson());
        json.put("movesLog", movesLog);

        EventLog.getInstance().logEvent(new Event("Created a cube json object"));
        return json;
    }
}


