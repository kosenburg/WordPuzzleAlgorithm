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
    private String wordPuzzlePath;
    private char[][] characterGrid;
    private boolean isEditable = true;

    public Grid(String wordPuzzlePath) {
        wordGrid = new ArrayList<>();
        this.wordPuzzlePath = wordPuzzlePath;
        loadWordFile();
    }

    public Grid (int numberRows, int numberCols) {
        wordGrid = new ArrayList<>();
        createEmptyGrid(numberRows, numberCols);

    }

    private void createEmptyGrid(int numberRows, int numberCols) {
        StringBuilder builder;
        for (int i = 0; i < numberRows; i++) {
            builder = new StringBuilder();
            for (int j = 0; j < numberCols; j++) {
                builder.append(" ");
            }
            wordGrid.add(builder.toString());
        }
    }

    public void addToGrid(Point p, char character) {
        if (isEditable) {
            String row = wordGrid.get(p.getY());
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < row.length(); i++) {
                if (i == p.getX()) {
                    builder.append(character);
                } else {
                    builder.append(row.charAt(i));
                }
            }
            wordGrid.add(p.getY(), builder.toString());
            wordGrid.remove(p.getY() + 1);
        } else {
            System.out.println("Grid object is not editable");
        }
    }


    private void loadWordFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(wordPuzzlePath))) {
            String line;
            while(((line = br.readLine()) != null) && !line.equals("")) {
                wordGrid.add(line);
            }
            isEditable = false;
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

    public String getRow(int y) {
        return wordGrid.get(y);
    }

    public int ColSize() {
        return wordGrid.size();
    }
}
