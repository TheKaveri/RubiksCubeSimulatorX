package persistence;

import model.Cube;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads a Cube state from JSON data stored in sourcePath file
public class JsonReader {
    private final String sourcePath;

    // EFFECTS: constructs a reader object to read from the sourcePath file
    public JsonReader(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    // EFFECTS: reads the Cube state from the file and returns it;
    // throws IOException if an error occurs while reading data from the file
    public Cube read() throws IOException {
        String jsonData = readFile(sourcePath);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCube(jsonObject);
    }

    // EFFECTS: reads sourcePath file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Cube from JSON object and returns it
    private Cube parseCube(JSONObject jsonObject) {
        Cube rubiksCube = new Cube();


        rubiksCube.setFace("up", getFacelets((JSONObject) jsonObject.get("up")));
        rubiksCube.setFace("down", getFacelets((JSONObject) jsonObject.get("down")));
        rubiksCube.setFace("right", getFacelets((JSONObject) jsonObject.get("right")));
        rubiksCube.setFace("left", getFacelets((JSONObject) jsonObject.get("left")));
        rubiksCube.setFace("front", getFacelets((JSONObject) jsonObject.get("front")));
        rubiksCube.setFace("back", getFacelets((JSONObject) jsonObject.get("back")));
        rubiksCube.setMovesLog(getMovesLog((JSONArray) jsonObject.get("movesLog")));

        return rubiksCube;
    }

    // EFFECTS: returns the string representation of previously stored facelets from the JSON object
    private String getFacelets(JSONObject jsonObject) {
        StringBuilder faceletAsString = new StringBuilder();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                faceletAsString.append(jsonObject.get("elem" + row + col)).append(" ");
            }
        }

        return faceletAsString.toString().trim();
    }

    // EFFECTS: returns the array representation of previously stored movesLog from the JSON object
    private ArrayList<String> getMovesLog(JSONArray jsonArray) {
        ArrayList<String> moves = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            moves.add((String) jsonArray.get(i));
        }

        return moves;
    }
}


