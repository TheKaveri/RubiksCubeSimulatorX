package model;

import model.Face;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class FaceTest {
    Face testFace;
    char[] testArray0;
    char[] testArray1;
    char[] testArray2;

    @BeforeEach
    public void setUp() {
        testFace = new Face('R');
        testArray0 = new char[]{'O', 'Y', 'B'};
        testArray1 = new char[]{'G', 'W', 'G'};
        testArray2 = new char[]{'R', 'O', 'Y'};

    }

    @Test
    public void testConstructor() {
        testFace = new Face('R');
        char[] testArray = new char[]{'R', 'R', 'R'};
        checkAllRowsEquals(testArray);

        testFace = new Face('W');
        testArray = new char[]{'W', 'W', 'W'};

        checkAllRowsEquals(testArray);
    }

    @Test
    public void testConstructor2() {
        testFace = new Face(new char[][]{testArray0, testArray1, testArray2});
        checkAllRowsEquals();
    }

    @Test
    public void testSetRow() {
        setAllRows();
        checkAllRowsEquals();
        assertArrayEquals(testArray1, testFace.getReversedRow(1));
    }


    @Test
    public void testSetColumn() {
        setAllColumns();
        checkAllColumnsEquals();
        assertArrayEquals(testArray1, testFace.getReversedColumnAsRow(1));
    }

    @Test
    public void testRotateClockwise() {
        setAllRows();

        testFace.rotateClockWise();

        assertArrayEquals(testArray2, testFace.getColumnAsRow(0));
        assertArrayEquals(testArray1, testFace.getColumnAsRow(1));
        assertArrayEquals(testArray0, testFace.getColumnAsRow(2));
    }

    @Test
    public void testRotateClockwiseFourTimes() {
        setAllRows();

        testFace.rotateClockWise();
        testFace.rotateClockWise();
        testFace.rotateClockWise();
        testFace.rotateClockWise();

        checkAllRowsEquals();
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        Face testFaceClone = (Face) testFace.clone();

        assertArrayEquals(testFace.getRow(0), testFaceClone.getRow(0));
        assertArrayEquals(testFace.getRow(1), testFaceClone.getRow(1));
        assertArrayEquals(testFace.getRow(2), testFaceClone.getRow(2));
    }

    @Test
    public void testToJson() {
        JSONObject jsonObject = testFace.toJson();
        char[][] facelets = new char[3][3];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                char element = ((String) jsonObject.get("elem" + row + col)).charAt(0);
                facelets[row][col] = element;
            }
        }

        assertArrayEquals(testFace.getRow(0), facelets[0]);
        assertArrayEquals(testFace.getRow(0), facelets[1]);
        assertArrayEquals(testFace.getRow(0), facelets[2]);
    }

    private void checkAllRowsEquals(char[] testArray) {
        assertArrayEquals(testArray, testFace.getRow(0));
        assertArrayEquals(testArray, testFace.getRow(1));
        assertArrayEquals(testArray, testFace.getRow(2));

    }

    private void setAllRows() {
        testFace.setRow(testArray0, 0);
        testFace.setRow(testArray1, 1);
        testFace.setRow(testArray2, 2);
    }

    private void setAllColumns() {
        testFace.setColumn(testArray0, 0);
        testFace.setColumn(testArray1, 1);
        testFace.setColumn(testArray2, 2);
    }

    private void checkAllRowsEquals() {
        assertArrayEquals(testArray0, testFace.getRow(0));
        assertArrayEquals(testArray1, testFace.getRow(1));
        assertArrayEquals(testArray2, testFace.getRow(2));

    }

    private void checkAllColumnsEquals() {
        assertArrayEquals(testArray0, testFace.getColumnAsRow(0));
        assertArrayEquals(testArray1, testFace.getColumnAsRow(1));
        assertArrayEquals(testArray2, testFace.getColumnAsRow(2));
    }
}
