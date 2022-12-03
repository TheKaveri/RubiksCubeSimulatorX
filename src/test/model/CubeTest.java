package model;

import model.Cube;
import model.Face;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CubeTest {
    Cube testCube;
    Face testUpFace;
    Face testDownFace;
    Face testRightFace;
    Face testLeftFace;
    Face testFrontFace;
    Face testBackFace;
    ArrayList<String> testMovesLog;
    char[] redArray;
    char[] orangeArray;
    char[] greenArray;
    char[] blueArray;
    char[] whiteArray;
    char[] yellowArray;

    @BeforeEach
    public void setUp() {
        testCube = new Cube();
        testUpFace = new Face('R');
        testDownFace = new Face('O');
        testRightFace = new Face('G');
        testLeftFace = new Face('B');
        testFrontFace = new Face('W');
        testBackFace = new Face('Y');
        redArray = new char[]{'R', 'R', 'R'};
        orangeArray = new char[]{'O', 'O', 'O'};
        greenArray = new char[]{'G', 'G', 'G'};
        blueArray = new char[]{'B', 'B', 'B'};
        whiteArray = new char[]{'W', 'W', 'W'};
        yellowArray = new char[]{'Y', 'Y', 'Y'};
        testMovesLog = new ArrayList<>();
    }

    @Test
    public void testConstructor() {
        checkAllEquals();
    }

    @Test
    public void testSetCube() {
        testCube.setFace("front", "R O Y R O Y R O Y");
        testFrontFace.setColumn(redArray, 0);
        testFrontFace.setColumn(orangeArray, 1);
        testFrontFace.setColumn(yellowArray, 2);
        checkAllEquals();


        testCube.setFace("back", "B B B G G G O O O");
        testBackFace.setRow(blueArray, 0);
        testBackFace.setRow(greenArray, 1);
        testBackFace.setRow(orangeArray, 2);
        checkAllEquals();


        testCube.setFace("left", "Y G R Y G R Y G R");
        testLeftFace.setRow(new char[]{'Y', 'G', 'R'}, 0);
        testLeftFace.setRow(new char[]{'Y', 'G', 'R'}, 1);
        testLeftFace.setRow(new char[]{'Y', 'G', 'R'}, 2);
        checkAllEquals();


        testCube.setFace("right", "R R R G G G O O O");
        testRightFace.setRow(redArray, 0);
        testRightFace.setRow(greenArray, 1);
        testRightFace.setRow(orangeArray, 2);
        checkAllEquals();


        testCube.setFace("up", "R B B W G O B Y R");
        testUpFace.setRow(new char[]{'R', 'B', 'B'}, 0);
        testUpFace.setRow(new char[]{'W', 'G', 'O'}, 1);
        testUpFace.setRow(new char[]{'B', 'Y', 'R'}, 2);
        checkAllEquals();


        testCube.setFace("down", "R B B W G O B Y R");
        testDownFace.setColumn(new char[]{'R', 'W', 'B'}, 0);
        testDownFace.setColumn(new char[]{'B', 'G', 'Y'}, 1);
        testDownFace.setColumn(new char[]{'B', 'O', 'R'}, 2);
        checkAllEquals();
    }

    @Test
    public void testNormalUOnce() throws CloneNotSupportedException {
        testUpFace.rotateClockWise();
        testRightFace.setRow(yellowArray, 0);
        testLeftFace.setRow(whiteArray, 0);
        testFrontFace.setRow(greenArray, 0);
        testBackFace.setRow(blueArray, 0);
        testMovesLog.add("U");

        testCube.doMove("U");

        checkAllEquals();
    }

    @Test
    public void testNormalUManyTimes() throws CloneNotSupportedException {
        testUpFace.rotateClockWise();
        testUpFace.rotateClockWise();
        testRightFace.setRow(blueArray, 0);
        testLeftFace.setRow(greenArray, 0);
        testFrontFace.setRow(yellowArray, 0);
        testBackFace.setRow(whiteArray, 0);
        testMovesLog.add("U");
        testMovesLog.add("U");


        testCube.doMove("U");
        testCube.doMove("U");

        checkAllEquals();

        testUpFace.rotateClockWise();
        testRightFace.setRow(whiteArray, 0);
        testLeftFace.setRow(yellowArray, 0);
        testFrontFace.setRow(blueArray, 0);
        testBackFace.setRow(greenArray, 0);
        testMovesLog.add("U");

        testCube.doMove("U");

        checkAllEquals();

        testUpFace.rotateClockWise();
        testRightFace.setRow(greenArray, 0);
        testLeftFace.setRow(blueArray, 0);
        testFrontFace.setRow(whiteArray, 0);
        testBackFace.setRow(yellowArray, 0);
        testMovesLog.add("U");

        testCube.doMove("U");

        checkAllEquals();
    }

    @Test
    public void testNormalD() throws CloneNotSupportedException {
        testDownFace.rotateClockWise();
        testRightFace.setRow(whiteArray, 2);
        testLeftFace.setRow(yellowArray, 2);
        testFrontFace.setRow(blueArray, 2);
        testBackFace.setRow(greenArray, 2);
        testMovesLog.add("D");

        testCube.doMove("D");

        checkAllEquals();
    }

    @Test
    public void testNormalDManyTimes() throws CloneNotSupportedException {
        testDownFace.rotateClockWise();
        testDownFace.rotateClockWise();
        testRightFace.setRow(blueArray, 2);
        testLeftFace.setRow(greenArray, 2);
        testFrontFace.setRow(yellowArray, 2);
        testBackFace.setRow(whiteArray, 2);
        testMovesLog.add("D");
        testMovesLog.add("D");

        testCube.doMove("D");
        testCube.doMove("D");

        checkAllEquals();

        testDownFace.rotateClockWise();
        testRightFace.setRow(yellowArray, 2);
        testLeftFace.setRow(whiteArray, 2);
        testFrontFace.setRow(greenArray, 2);
        testBackFace.setRow(blueArray, 2);
        testMovesLog.add("D");

        testCube.doMove("D");

        checkAllEquals();

        testDownFace.rotateClockWise();
        testRightFace.setRow(greenArray, 2);
        testLeftFace.setRow(blueArray, 2);
        testFrontFace.setRow(whiteArray, 2);
        testBackFace.setRow(yellowArray, 2);
        testMovesLog.add("D");

        testCube.doMove("D");

        checkAllEquals();
    }

    @Test
    public void testNormalR() throws CloneNotSupportedException {
        testRightFace.rotateClockWise();
        testUpFace.setColumn(whiteArray, 2);
        testDownFace.setColumn(yellowArray, 2);
        testFrontFace.setColumn(orangeArray, 2);
        testBackFace.setColumn(redArray, 0);
        testMovesLog.add("R");

        testCube.doMove("R");

        checkAllEquals();

    }

    @Test
    public void testNormalRManyTimes() throws CloneNotSupportedException {
        testRightFace.rotateClockWise();
        testRightFace.rotateClockWise();
        testUpFace.setColumn(orangeArray, 2);
        testDownFace.setColumn(redArray, 2);
        testFrontFace.setColumn(yellowArray, 2);
        testBackFace.setColumn(whiteArray, 0);
        testMovesLog.add("R");
        testMovesLog.add("R");

        testCube.doMove("R");
        testCube.doMove("R");

        checkAllEquals();

        testRightFace.rotateClockWise();
        testUpFace.setColumn(yellowArray, 2);
        testDownFace.setColumn(whiteArray, 2);
        testFrontFace.setColumn(redArray, 2);
        testBackFace.setColumn(orangeArray, 0);
        testMovesLog.add("R");

        testCube.doMove("R");

        checkAllEquals();

        testRightFace.rotateClockWise();
        testUpFace.setColumn(redArray, 2);
        testDownFace.setColumn(orangeArray, 2);
        testFrontFace.setColumn(whiteArray, 2);
        testBackFace.setColumn(yellowArray, 0);
        testMovesLog.add("R");

        testCube.doMove("R");

        checkAllEquals();
    }

    @Test
    public void testNormalL() throws CloneNotSupportedException {
        testLeftFace.rotateClockWise();
        testUpFace.setColumn(yellowArray, 0);
        testDownFace.setColumn(whiteArray, 0);
        testFrontFace.setColumn(redArray, 0);
        testBackFace.setColumn(orangeArray, 2);
        testMovesLog.add("L");

        testCube.doMove("L");

        checkAllEquals();
    }

    @Test
    public void testNormalLManyTimes() throws CloneNotSupportedException {
        testLeftFace.rotateClockWise();
        testLeftFace.rotateClockWise();
        testUpFace.setColumn(orangeArray, 0);
        testDownFace.setColumn(redArray, 0);
        testFrontFace.setColumn(yellowArray, 0);
        testBackFace.setColumn(whiteArray, 2);
        testMovesLog.add("L");
        testMovesLog.add("L");

        testCube.doMove("L");
        testCube.doMove("L");

        checkAllEquals();

        testLeftFace.rotateClockWise();
        testUpFace.setColumn(whiteArray, 0);
        testDownFace.setColumn(yellowArray, 0);
        testFrontFace.setColumn(orangeArray, 0);
        testBackFace.setColumn(redArray, 2);
        testMovesLog.add("L");

        testCube.doMove("L");

        checkAllEquals();

        testLeftFace.rotateClockWise();
        testUpFace.setColumn(redArray, 0);
        testDownFace.setColumn(orangeArray, 0);
        testFrontFace.setColumn(whiteArray, 0);
        testBackFace.setColumn(yellowArray, 2);
        testMovesLog.add("L");

        testCube.doMove("L");

        checkAllEquals();
    }

    @Test
    public void testNormalF() throws CloneNotSupportedException {
        testFrontFace.rotateClockWise();
        testUpFace.setRow(blueArray, 2);
        testDownFace.setRow(greenArray, 0);
        testRightFace.setColumn(redArray, 0);
        testLeftFace.setColumn(orangeArray, 2);
        testMovesLog.add("F");

        testCube.doMove("F");

        checkAllEquals();
    }

    @Test
    public void testNormalFManyTimes() throws CloneNotSupportedException {
        testFrontFace.rotateClockWise();
        testFrontFace.rotateClockWise();
        testUpFace.setRow(orangeArray, 2);
        testDownFace.setRow(redArray, 0);
        testRightFace.setColumn(blueArray, 0);
        testLeftFace.setColumn(greenArray, 2);
        testMovesLog.add("F");
        testMovesLog.add("F");

        testCube.doMove("F");
        testCube.doMove("F");

        checkAllEquals();

        testFrontFace.rotateClockWise();
        testUpFace.setRow(greenArray, 2);
        testDownFace.setRow(blueArray, 0);
        testRightFace.setColumn(orangeArray, 0);
        testLeftFace.setColumn(redArray, 2);
        testMovesLog.add("F");

        testCube.doMove("F");

        checkAllEquals();

        testFrontFace.rotateClockWise();
        testUpFace.setRow(redArray, 2);
        testDownFace.setRow(orangeArray, 0);
        testRightFace.setColumn(greenArray, 0);
        testLeftFace.setColumn(blueArray, 2);
        testMovesLog.add("F");

        testCube.doMove("F");

        checkAllEquals();
    }

    @Test
    public void testNormalB() throws CloneNotSupportedException {
        testBackFace.rotateClockWise();
        testUpFace.setRow(greenArray, 0);
        testDownFace.setRow(blueArray, 2);
        testRightFace.setColumn(orangeArray, 2);
        testLeftFace.setColumn(redArray, 0);
        testMovesLog.add("B");

        testCube.doMove("B");

        checkAllEquals();
    }

    @Test
    public void testNormalBManyTimes() throws CloneNotSupportedException {
        testBackFace.rotateClockWise();
        testBackFace.rotateClockWise();
        testUpFace.setRow(orangeArray, 0);
        testDownFace.setRow(redArray, 2);
        testRightFace.setColumn(blueArray, 2);
        testLeftFace.setColumn(greenArray, 0);
        testMovesLog.add("B");
        testMovesLog.add("B");

        testCube.doMove("B");
        testCube.doMove("B");

        checkAllEquals();

        testBackFace.rotateClockWise();
        testUpFace.setRow(blueArray, 0);
        testDownFace.setRow(greenArray, 2);
        testRightFace.setColumn(redArray, 2);
        testLeftFace.setColumn(orangeArray, 0);
        testMovesLog.add("B");

        testCube.doMove("B");

        checkAllEquals();

        testBackFace.rotateClockWise();
        testUpFace.setRow(redArray, 0);
        testDownFace.setRow(orangeArray, 2);
        testRightFace.setColumn(greenArray, 2);
        testLeftFace.setColumn(blueArray, 0);
        testMovesLog.add("B");

        testCube.doMove("B");

        checkAllEquals();
    }

    @Test
    public void testPrimeUOnce() throws CloneNotSupportedException {
        testUpFace.rotateClockWise();
        testUpFace.rotateClockWise();
        testUpFace.rotateClockWise();
        testRightFace.setRow(whiteArray, 0);
        testLeftFace.setRow(yellowArray, 0);
        testFrontFace.setRow(blueArray, 0);
        testBackFace.setRow(greenArray, 0);
        testMovesLog.add("u");

        testCube.doMove("u");

        checkAllEquals();
    }

    @Test
    public void testPrimeUManyTimes() throws CloneNotSupportedException {
        testUpFace.rotateClockWise();
        testUpFace.rotateClockWise();
        testUpFace.rotateClockWise();
        testUpFace.rotateClockWise();
        testUpFace.rotateClockWise();
        testUpFace.rotateClockWise();
        testRightFace.setRow(blueArray, 0);
        testLeftFace.setRow(greenArray, 0);
        testFrontFace.setRow(yellowArray, 0);
        testBackFace.setRow(whiteArray, 0);
        testMovesLog.add("u");
        testMovesLog.add("u");

        testCube.doMove("u");
        testCube.doMove("u");

        checkAllEquals();

        testUpFace.rotateClockWise();
        testUpFace.rotateClockWise();
        testUpFace.rotateClockWise();
        testRightFace.setRow(yellowArray, 0);
        testLeftFace.setRow(whiteArray, 0);
        testFrontFace.setRow(greenArray, 0);
        testBackFace.setRow(blueArray, 0);
        testMovesLog.add("u");

        testCube.doMove("u");

        checkAllEquals();

        testUpFace.rotateClockWise();
        testUpFace.rotateClockWise();
        testUpFace.rotateClockWise();
        testRightFace.setRow(greenArray, 0);
        testLeftFace.setRow(blueArray, 0);
        testFrontFace.setRow(whiteArray, 0);
        testBackFace.setRow(yellowArray, 0);
        testMovesLog.add("u");

        testCube.doMove("u");

        checkAllEquals();
    }

    @Test
    public void testPrimeD() throws CloneNotSupportedException {
        testDownFace.rotateClockWise();
        testDownFace.rotateClockWise();
        testDownFace.rotateClockWise();
        testRightFace.setRow(yellowArray, 2);
        testLeftFace.setRow(whiteArray, 2);
        testFrontFace.setRow(greenArray, 2);
        testBackFace.setRow(blueArray, 2);
        testMovesLog.add("d");

        testCube.doMove("d");

        checkAllEquals();
    }

    @Test
    public void testPrimeDManyTimes() throws CloneNotSupportedException {
        testDownFace.rotateClockWise();
        testDownFace.rotateClockWise();
        testDownFace.rotateClockWise();
        testDownFace.rotateClockWise();
        testDownFace.rotateClockWise();
        testDownFace.rotateClockWise();
        testRightFace.setRow(blueArray, 2);
        testLeftFace.setRow(greenArray, 2);
        testFrontFace.setRow(yellowArray, 2);
        testBackFace.setRow(whiteArray, 2);
        testMovesLog.add("d");
        testMovesLog.add("d");

        testCube.doMove("d");
        testCube.doMove("d");

        checkAllEquals();

        testDownFace.rotateClockWise();
        testDownFace.rotateClockWise();
        testDownFace.rotateClockWise();
        testRightFace.setRow(whiteArray, 2);
        testLeftFace.setRow(yellowArray, 2);
        testFrontFace.setRow(blueArray, 2);
        testBackFace.setRow(greenArray, 2);
        testMovesLog.add("d");

        testCube.doMove("d");

        checkAllEquals();

        testDownFace.rotateClockWise();
        testDownFace.rotateClockWise();
        testDownFace.rotateClockWise();
        testRightFace.setRow(greenArray, 2);
        testLeftFace.setRow(blueArray, 2);
        testFrontFace.setRow(whiteArray, 2);
        testBackFace.setRow(yellowArray, 2);
        testMovesLog.add("d");

        testCube.doMove("d");

        checkAllEquals();
    }

    @Test
    public void testPrimeR() throws CloneNotSupportedException {
        testRightFace.rotateClockWise();
        testRightFace.rotateClockWise();
        testRightFace.rotateClockWise();
        testUpFace.setColumn(yellowArray, 2);
        testDownFace.setColumn(whiteArray, 2);
        testFrontFace.setColumn(redArray, 2);
        testBackFace.setColumn(orangeArray, 0);
        testMovesLog.add("r");

        testCube.doMove("r");

        checkAllEquals();
    }

    @Test
    public void testPrimeRManyTimes() throws CloneNotSupportedException {
        testRightFace.rotateClockWise();
        testRightFace.rotateClockWise();
        testRightFace.rotateClockWise();
        testRightFace.rotateClockWise();
        testRightFace.rotateClockWise();
        testRightFace.rotateClockWise();
        testUpFace.setColumn(orangeArray, 2);
        testDownFace.setColumn(redArray, 2);
        testFrontFace.setColumn(yellowArray, 2);
        testBackFace.setColumn(whiteArray, 0);
        testMovesLog.add("r");
        testMovesLog.add("r");

        testCube.doMove("r");
        testCube.doMove("r");

        checkAllEquals();

        testRightFace.rotateClockWise();
        testRightFace.rotateClockWise();
        testRightFace.rotateClockWise();
        testUpFace.setColumn(whiteArray, 2);
        testDownFace.setColumn(yellowArray, 2);
        testFrontFace.setColumn(orangeArray, 2);
        testBackFace.setColumn(redArray, 0);
        testMovesLog.add("r");

        testCube.doMove("r");

        checkAllEquals();

        testRightFace.rotateClockWise();
        testRightFace.rotateClockWise();
        testRightFace.rotateClockWise();
        testUpFace.setColumn(redArray, 2);
        testDownFace.setColumn(orangeArray, 2);
        testFrontFace.setColumn(whiteArray, 2);
        testBackFace.setColumn(yellowArray, 0);
        testMovesLog.add("r");

        testCube.doMove("r");

        checkAllEquals();
    }

    @Test
    public void testPrimeL() throws CloneNotSupportedException {
        testLeftFace.rotateClockWise();
        testLeftFace.rotateClockWise();
        testLeftFace.rotateClockWise();
        testUpFace.setColumn(whiteArray, 0);
        testDownFace.setColumn(yellowArray, 0);
        testFrontFace.setColumn(orangeArray, 0);
        testBackFace.setColumn(redArray, 2);
        testMovesLog.add("l");

        testCube.doMove("l");

        checkAllEquals();
    }

    @Test
    public void testPrimeLManyTimes() throws CloneNotSupportedException {
        testLeftFace.rotateClockWise();
        testLeftFace.rotateClockWise();
        testLeftFace.rotateClockWise();
        testLeftFace.rotateClockWise();
        testLeftFace.rotateClockWise();
        testLeftFace.rotateClockWise();
        testUpFace.setColumn(orangeArray, 0);
        testDownFace.setColumn(redArray, 0);
        testFrontFace.setColumn(yellowArray, 0);
        testBackFace.setColumn(whiteArray, 2);
        testMovesLog.add("l");
        testMovesLog.add("l");

        testCube.doMove("l");
        testCube.doMove("l");

        checkAllEquals();

        testLeftFace.rotateClockWise();
        testLeftFace.rotateClockWise();
        testLeftFace.rotateClockWise();
        testUpFace.setColumn(yellowArray, 0);
        testDownFace.setColumn(whiteArray, 0);
        testFrontFace.setColumn(redArray, 0);
        testBackFace.setColumn(orangeArray, 2);
        testMovesLog.add("l");

        testCube.doMove("l");

        checkAllEquals();

        testLeftFace.rotateClockWise();
        testLeftFace.rotateClockWise();
        testLeftFace.rotateClockWise();
        testUpFace.setColumn(redArray, 0);
        testDownFace.setColumn(orangeArray, 0);
        testFrontFace.setColumn(whiteArray, 0);
        testBackFace.setColumn(yellowArray, 2);
        testMovesLog.add("l");

        testCube.doMove("l");

        checkAllEquals();
    }

    @Test
    public void testPrimeF() throws CloneNotSupportedException {
        testFrontFace.rotateClockWise();
        testFrontFace.rotateClockWise();
        testFrontFace.rotateClockWise();
        testUpFace.setRow(greenArray, 2);
        testDownFace.setRow(blueArray, 0);
        testRightFace.setColumn(orangeArray, 0);
        testLeftFace.setColumn(redArray, 2);
        testMovesLog.add("f");

        testCube.doMove("f");

        checkAllEquals();
    }

    @Test
    public void testPrimeFManyTimes() throws CloneNotSupportedException {
        testFrontFace.rotateClockWise();
        testFrontFace.rotateClockWise();
        testFrontFace.rotateClockWise();
        testFrontFace.rotateClockWise();
        testFrontFace.rotateClockWise();
        testFrontFace.rotateClockWise();
        testUpFace.setRow(orangeArray, 2);
        testDownFace.setRow(redArray, 0);
        testRightFace.setColumn(blueArray, 0);
        testLeftFace.setColumn(greenArray, 2);
        testMovesLog.add("f");
        testMovesLog.add("f");

        testCube.doMove("f");
        testCube.doMove("f");

        checkAllEquals();

        testFrontFace.rotateClockWise();
        testFrontFace.rotateClockWise();
        testFrontFace.rotateClockWise();
        testUpFace.setRow(blueArray, 2);
        testDownFace.setRow(greenArray, 0);
        testRightFace.setColumn(redArray, 0);
        testLeftFace.setColumn(orangeArray, 2);
        testMovesLog.add("f");

        testCube.doMove("f");

        checkAllEquals();

        testFrontFace.rotateClockWise();
        testFrontFace.rotateClockWise();
        testFrontFace.rotateClockWise();
        testUpFace.setRow(redArray, 2);
        testDownFace.setRow(orangeArray, 0);
        testRightFace.setColumn(greenArray, 0);
        testLeftFace.setColumn(blueArray, 2);
        testMovesLog.add("f");

        testCube.doMove("f");

        checkAllEquals();
    }

    @Test
    public void testPrimeB() throws CloneNotSupportedException {
        testBackFace.rotateClockWise();
        testBackFace.rotateClockWise();
        testBackFace.rotateClockWise();
        testUpFace.setRow(blueArray, 0);
        testDownFace.setRow(greenArray, 2);
        testRightFace.setColumn(redArray, 2);
        testLeftFace.setColumn(orangeArray, 0);
        testMovesLog.add("b");

        testCube.doMove("b");

        checkAllEquals();
    }

    @Test
    public void testPrimeBManyTimes() throws CloneNotSupportedException {
        testBackFace.rotateClockWise();
        testBackFace.rotateClockWise();
        testBackFace.rotateClockWise();
        testBackFace.rotateClockWise();
        testBackFace.rotateClockWise();
        testBackFace.rotateClockWise();
        testUpFace.setRow(orangeArray, 0);
        testDownFace.setRow(redArray, 2);
        testRightFace.setColumn(blueArray, 2);
        testLeftFace.setColumn(greenArray, 0);
        testMovesLog.add("b");
        testMovesLog.add("b");

        testCube.doMove("b");
        testCube.doMove("b");

        checkAllEquals();

        testBackFace.rotateClockWise();
        testBackFace.rotateClockWise();
        testBackFace.rotateClockWise();
        testUpFace.setRow(greenArray, 0);
        testDownFace.setRow(blueArray, 2);
        testRightFace.setColumn(orangeArray, 2);
        testLeftFace.setColumn(redArray, 0);
        testMovesLog.add("b");

        testCube.doMove("b");

        checkAllEquals();

        testBackFace.rotateClockWise();
        testBackFace.rotateClockWise();
        testBackFace.rotateClockWise();
        testUpFace.setRow(redArray, 0);
        testDownFace.setRow(orangeArray, 2);
        testRightFace.setColumn(greenArray, 2);
        testLeftFace.setColumn(blueArray, 0);
        testMovesLog.add("b");

        testCube.doMove("b");

        checkAllEquals();
    }


    @Test
    public void testSetMovesLog() {
        testMovesLog.add("f");
        testMovesLog.add("R");
        testMovesLog.add("B2");

        ArrayList<String> testArray = new ArrayList<>();
        testArray.add("f");
        testArray.add("R");
        testArray.add("B2");

        testCube.setMovesLog(testArray);

        assertEquals(testMovesLog, testCube.getMovesLog());
    }

    @Test
    public void testReset() throws CloneNotSupportedException {
        testCube.doMove("F2");
        testCube.doMove("b");
        testCube.doMove("L");

        testCube.reset();

        assertEquals(0, testCube.getMovesLog().size());
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        Cube testCubeClone = (Cube) testCube.clone();
        assertArrayEquals(testCube.getFace("up"), testCubeClone.getFace("up"));
        assertArrayEquals(testCube.getFace("down"), testCubeClone.getFace("down"));
        assertArrayEquals(testCube.getFace("right"), testCubeClone.getFace("right"));
        assertArrayEquals(testCube.getFace("left"), testCubeClone.getFace("left"));
        assertArrayEquals(testCube.getFace("front"), testCubeClone.getFace("front"));
        assertArrayEquals(testCube.getFace("back"), testCubeClone.getFace("back"));
    }

    @Test
    public void testToJson() {
        JSONObject jsonObject = testCube.toJson();

        String[] faces = {"up", "down", "right", "left", "front", "back"};
        char[] colorsAsInt = {'R', 'O', 'G', 'B', 'W', 'Y'};
        int idx = 0;
        for (String face : faces) {
            JSONObject facelets = (JSONObject) jsonObject.get(face);
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    char element = ((String) facelets.get("elem" + row + col)).charAt(0);
                    assertEquals(colorsAsInt[idx], element);
                }
            }
            idx++;
        }
    }


    private void checkAllEquals() {
        assertArrayEquals(getFacelets(testUpFace), testCube.getFace("up"));
        assertArrayEquals(getFacelets(testDownFace), testCube.getFace("down"));
        assertArrayEquals(getFacelets(testRightFace), testCube.getFace("right"));
        assertArrayEquals(getFacelets(testLeftFace), testCube.getFace("left"));
        assertArrayEquals(getFacelets(testFrontFace), testCube.getFace("front"));
        assertArrayEquals(getFacelets(testBackFace), testCube.getFace("back"));

        assertEquals(testMovesLog, testCube.getMovesLog());

    }

    private char[][] getFacelets(Face face) {
        return new char[][]{face.getRow(0), face.getRow(1), face.getRow(2)};
    }

}
