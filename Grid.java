import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kevin on 2/26/2017.
 */
public class Grid {
    private ArrayList<String> wordGrid;
    private final String wordPuzzlePath;

    public Grid(String wordPuzzlePath) {
        wordGrid = new ArrayList<>();
        this.wordPuzzlePath = wordPuzzlePath;
        loadWordFile();
    }

    private void loadWordFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(wordPuzzlePath))) {
            String line;
            while((line = br.readLine()) != null) {
                wordGrid.add(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to locate file: " + wordPuzzlePath);
        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getMessage());
        }
    }


    public char getLetterAt(int x, int y) {
        String row = wordGrid.get(y);
        return row.charAt(x);
    }

    public int RowSize(int y) {
        return wordGrid.get(y).length();
    }

    public int ColSize(int x) {
        return wordGrid.size();
    }
}
