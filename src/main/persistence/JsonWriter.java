package persistence;

import model.Cube;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes the JSON representation of a Cube state to a file
public class JsonWriter {
    private static final int INDENT_FACTOR = 3;
    private PrintWriter out;
    private final String destinationPath;

    // EFFECTS: constructs a writer object to write to the destinationPath file
    public JsonWriter(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    // MODIFIES: this
    // EFFECTS: opens out; throws FileNotFoundException if the destinationPath file cannot
    // be opened for writing or if it doesn't exist
    public void open() throws FileNotFoundException {
        out = new PrintWriter(destinationPath);
    }

    // MODIFIES: this
    // EFFECTS: writes the JSON representation of Cube to the destinationPath file
    public void write(Cube rubiksCube) {
        JSONObject json = rubiksCube.toJson();
        saveToFile(json.toString(INDENT_FACTOR));
    }

    // MODIFIES: this
    // EFFECTS: closes the out object
    public void close() {
        out.close();
    }

    // MODIFIES: this
    // EFFECTS: writes a string to the file
    private void saveToFile(String json) {
        out.print(json);
    }
}


