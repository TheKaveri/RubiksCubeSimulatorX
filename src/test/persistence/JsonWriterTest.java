package persistence;

import model.Cube;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    Cube rubiksCube;

    @BeforeEach
    public void setUp() {
        rubiksCube = new Cube();
    }

    @Test
    public void testWriteToInvalidFile() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/:invalid\0name.json");
            jsonWriter.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // do nothing
        }
    }

    @Test
    public void testWriteInitialCubeState() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/testWriteInitialCubeState.json");
            jsonWriter.open();
            jsonWriter.write(rubiksCube);
            jsonWriter.close();

            JsonReader reader = new JsonReader("./data/testWriteInitialCubeState.json");
            Cube rubiksCubeRead = reader.read();

            assertArrayEquals(rubiksCube.getFace("up"), rubiksCubeRead.getFace("up"));
            assertArrayEquals(rubiksCube.getFace("down"), rubiksCubeRead.getFace("down"));
            assertArrayEquals(rubiksCube.getFace("right"), rubiksCubeRead.getFace("right"));
            assertArrayEquals(rubiksCube.getFace("left"), rubiksCubeRead.getFace("left"));
            assertArrayEquals(rubiksCube.getFace("front"), rubiksCubeRead.getFace("front"));
            assertArrayEquals(rubiksCube.getFace("back"), rubiksCubeRead.getFace("back"));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriteGenericCubeState() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/testWriteGenericCubeState.json");
            jsonWriter.open();

            rubiksCube.doMove("B2");
            rubiksCube.doMove("D");
            rubiksCube.doMove("f");
            rubiksCube.doMove("L");
            rubiksCube.doMove("r");

            jsonWriter.write(rubiksCube);
            jsonWriter.close();

            JsonReader reader = new JsonReader("./data/testWriteGenericCubeState.json");
            Cube rubiksCubeRead = reader.read();

            assertArrayEquals(rubiksCube.getFace("up"), rubiksCubeRead.getFace("up"));
            assertArrayEquals(rubiksCube.getFace("down"), rubiksCubeRead.getFace("down"));
            assertArrayEquals(rubiksCube.getFace("right"), rubiksCubeRead.getFace("right"));
            assertArrayEquals(rubiksCube.getFace("left"), rubiksCubeRead.getFace("left"));
            assertArrayEquals(rubiksCube.getFace("front"), rubiksCubeRead.getFace("front"));
            assertArrayEquals(rubiksCube.getFace("back"), rubiksCubeRead.getFace("back"));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (CloneNotSupportedException e) {
            fail("Exception in Cube class");

        }
    }
}

