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
            while(((line = br.readLine()) != null) && !line.equals("")) {
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

    public char getLetterAt(Point point) {
        String row = wordGrid.get(point.getY());
        return row.charAt(point.getX());
    }

    public int RowSize(int y) {
        return wordGrid.get(y).length();
    }

    public int ColSize(int x) {
        return wordGrid.size();
    }

    public boolean isInBounds(Point point) {
        if ((point.getY() < ColSize(point.getX())) && (point.getY() >= 0)) {
            if ((point.getX() < RowSize(point.getY())) && (point.getX() >= 0)) {
                return true;
            }
        }
        return false;
    }

    public void outputRows() {
        int i = 0;
        for (String line: wordGrid) {
            i++;
            System.out.println(line);
        }
        System.out.println(i);
    }
    public void outputLine(int index) {
        String line = wordGrid.get(index);
        for (int i = 0; i < line.length(); i++) {
            System.out.println(line.charAt(i));
        }
        System.out.println(line.length());
    }
}
