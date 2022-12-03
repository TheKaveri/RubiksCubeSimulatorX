package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Arrays;

// Represents a face that is a part of a 3 by 3 rubik's cube.
// Each face has a fixed number of rows (FACE_ROWS) and columns (FACE_COLUMNS).
// Each face has facelets (facelets), which stores the nine individual cube-lets of the face.
public class Face implements Cloneable, Writable {
    private static final int FACE_ROWS = 3;
    private static final int FACE_COLUMNS = 3;
    private char[][] facelets = new char[FACE_ROWS][FACE_COLUMNS];

    // constructors:

    // REQUIRES: faceletColor must be either 'W', 'R', 'G', 'O', 'B' or 'Y'
    // EFFECTS: constructs a face with a specific color
    public Face(char faceletColor) {
        for (int row = 0; row < FACE_ROWS; row++) {
            for (int col = 0; col < FACE_COLUMNS; col++) {
                this.facelets[row][col] = faceletColor;
            }
        }
    }

    // REQUIRES: facelets must be a 3 by 3 2D array
    // EFFECTS: constructs a face with a specific color
    public Face(char[][] facelets) {
        for (int row = 0; row < FACE_ROWS; row++) {
            System.arraycopy(facelets[row], 0, this.facelets[row], 0, FACE_COLUMNS);
        }
    }


    // setters:

    // REQUIRES: srcFaceletRow has exactly 3 (FACE_ROWS) elements, rowIdx must be between 0 and 2 (inclusive)
    // MODIFIES: this
    // EFFECTS: replaces a group of cube-lets in the rowIdx-th row of the face by the given list of cube-lets
    public void setRow(char[] srcFaceletRow, int rowIdx) {
        System.arraycopy(srcFaceletRow, 0, this.facelets[rowIdx], 0, FACE_COLUMNS);
        EventLog.getInstance().logEvent(
                new Event("Updated row" + rowIdx + " of this face with " + Arrays.toString(srcFaceletRow)));
    }

    // REQUIRES: srcFaceletColumn has exactly 3 (FACE_COLUMNS) elements, colIdx must be between 0 and 2 (inclusive)
    // MODIFIES: this
    // EFFECTS: replaces a group of cube-lets in the colIdx-th column of the face by the given list of cube-lets
    public void setColumn(char[] srcFaceletColumn, int colIdx) {
        for (int i = 0; i < FACE_ROWS; i++) {
            this.facelets[i][colIdx] = srcFaceletColumn[i];
        }
        EventLog.getInstance().logEvent(
                new Event("Updated column" + colIdx + " of this face with " + Arrays.toString(srcFaceletColumn)));
    }

    // MODIFIES: this
    // EFFECTS: rotates the face clockwise once by realigning the cube-lets of the face
    public void rotateClockWise() {
        char[][] tempFacelets = new char[FACE_COLUMNS][FACE_ROWS];

        tempFacelets[0][0] = this.facelets[2][0];
        tempFacelets[0][1] = this.facelets[1][0];
        tempFacelets[0][2] = this.facelets[0][0];

        tempFacelets[1][0] = this.facelets[2][1];
        tempFacelets[1][1] = this.facelets[1][1];
        tempFacelets[1][2] = this.facelets[0][1];

        tempFacelets[2][0] = this.facelets[2][2];
        tempFacelets[2][1] = this.facelets[1][2];
        tempFacelets[2][2] = this.facelets[0][2];

        this.facelets = tempFacelets;
        EventLog.getInstance().logEvent(new Event("Rotated this face clockwise"));
    }


    // getters:

    // REQUIRES: rowIdx must be between 0 and 2 (inclusive)
    // EFFECTS: returns a group of cube-lets in the rowIdx-th row of the face
    public char[] getRow(int rowIdx) {
        char[] temp = new char[FACE_ROWS];
        System.arraycopy(this.facelets[rowIdx], 0, temp, 0, FACE_ROWS);
        return temp;
    }

    // REQUIRES: colIdx must be between 0 and 2 (inclusive)
    // EFFECTS: returns a group of cube-lets in the colIdx-th column of the face as a row
    public char[] getColumnAsRow(int colIdx) {
        char[] columnAsRow = new char[FACE_COLUMNS];
        for (int row = 0; row < FACE_ROWS; row++) {
            columnAsRow[row] = this.facelets[row][colIdx];
        }
        return columnAsRow;
    }

    // REQUIRES: rowIdx must be between 0 and 2 (inclusive)
    // EFFECTS: returns the reverse of a group of cube-lets in the rowIdx-th row of the face
    public char[] getReversedRow(int rowIdx) {
        char[] temp = new char[FACE_ROWS];
        for (int j = 0; j < FACE_ROWS; j++) {
            temp[FACE_ROWS - 1 - j] = this.facelets[rowIdx][j];
        }
        return temp;
    }

    // REQUIRES: colIdx must be between 0 and 2 (inclusive)
    // EFFECTS: returns the reverse of a group of cube-lets in the colIdx-th column of the face as a row
    public char[] getReversedColumnAsRow(int colIdx) {
        char[] temp = new char[FACE_ROWS];
        char[] column = this.getColumnAsRow(colIdx);
        for (int i = 0; i < FACE_ROWS; i++) {
            temp[FACE_ROWS - 1 - i] = column[i];
        }
        return temp;
    }

    // cloner:

    // EFFECTS: returns a field-by-field clone of the face
    public Object clone() throws CloneNotSupportedException {
        Face faceClone = (Face) super.clone();
        faceClone.facelets = new char[FACE_ROWS][FACE_COLUMNS];
        for (int row = 0; row < FACE_ROWS; row++) {
            System.arraycopy(this.facelets[row], 0, faceClone.facelets[row], 0, FACE_COLUMNS);
        }
        EventLog.getInstance().logEvent(new Event("Created a face clone"));
        return faceClone;
    }

    @Override
    // EFFECTS: returns a json representation of facelets
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                json.put("elem" + row + col, facelets[row][col] + "");
            }
        }
        EventLog.getInstance().logEvent(new Event("Created a face json object"));

        return json;
    }
}


