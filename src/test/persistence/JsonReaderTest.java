package persistence;

import model.Cube;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {
    char[][] up;
    char[][] down;
    char[][] right;
    char[][] left;
    char[][] front;
    char[][] back;

    @BeforeEach
    public void setUp() {
        up = new char[][]{{'R', 'R', 'R'}, {'R', 'R', 'R'}, {'R', 'R', 'R'}};
        down = new char[][]{{'O', 'O', 'O'}, {'O', 'O', 'O'}, {'O', 'O', 'O'}};
        right = new char[][]{{'G', 'G', 'G'}, {'G', 'G', 'G'}, {'G', 'G', 'G'}};
        left = new char[][]{{'B', 'B', 'B'}, {'B', 'B', 'B'}, {'B', 'B', 'B'}};
        front = new char[][]{{'W', 'W', 'W'}, {'W', 'W', 'W'}, {'W', 'W', 'W'}};
        back = new char[][]{{'Y', 'Y', 'Y'}, {'Y', 'Y', 'Y'}, {'Y', 'Y', 'Y'}};
    }

    @Test
    public void testReadNonExistentFile() {
        JsonReader jsonReader = new JsonReader("./data/noSuchFile.json");
        try {
            Cube cube = jsonReader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // do nothing
        }
    }

    @Test
    public void testReadInitialCubeState() {
        JsonReader jsonReader = new JsonReader("./data/testReadInitialCubeState.json");
        try {
            Cube rubiksCube = jsonReader.read();

            assertArrayEquals(up, rubiksCube.getFace("up"));
            assertArrayEquals(down, rubiksCube.getFace("down"));
            assertArrayEquals(right, rubiksCube.getFace("right"));
            assertArrayEquals(left, rubiksCube.getFace("left"));
            assertArrayEquals(front, rubiksCube.getFace("front"));
            assertArrayEquals(back, rubiksCube.getFace("back"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReadGenericCube() {
        JsonReader jsonReader = new JsonReader("./data/testReadGenericCube.json");
        try {
            Cube rubiksCube = jsonReader.read();
            testReadGenericCubeHelper();

            assertArrayEquals(up, rubiksCube.getFace("up"));
            assertArrayEquals(down, rubiksCube.getFace("down"));
            assertArrayEquals(right, rubiksCube.getFace("right"));
            assertArrayEquals(left, rubiksCube.getFace("left"));
            assertArrayEquals(front, rubiksCube.getFace("front"));
            assertArrayEquals(back, rubiksCube.getFace("back"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    private void testReadGenericCubeHelper() {
        up = new char[][]{{'B', 'O', 'G'}, {'Y', 'R', 'Y'}, {'Y', 'G', 'Y'}};
        down = new char[][]{{'W', 'B', 'B'}, {'W', 'O', 'B'}, {'W', 'O', 'G'}};
        right = new char[][]{{'B', 'B', 'W'}, {'G', 'G', 'W'}, {'O', 'O', 'R'}};
        left = new char[][]{{'Y', 'G', 'G'}, {'Y', 'B', 'B'}, {'R', 'R', 'R'}};
        front = new char[][]{{'O', 'W', 'O'}, {'R', 'W', 'R'}, {'G', 'W', 'W'}};
        back = new char[][]{{'O', 'Y', 'R'}, {'O', 'Y', 'R'}, {'Y', 'G', 'B'}};
    }
}

