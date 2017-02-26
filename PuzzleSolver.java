import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kevin on 2/26/2017.
 */
public class PuzzleSolver {
    private Grid grid;
    private WordList wordList;
    private Direction direction;
    private HashMap<String, ArrayList<Point>> foundWords;
    private Point startingPoint;
    private ArrayList<Point> checkedPoints;

    public PuzzleSolver() {
        startingPoint = new Point(-1,0);
        foundWords = new HashMap<>();
    }

    public void loadWordFindPuzzle(String wordPuzzlePath, String wordListPath) {
        createGrid(wordPuzzlePath);
        createWordList(wordListPath);
    }

    private void createGrid(String wordPuzzlePath) {
        grid = new Grid(wordPuzzlePath);
    }

    private void createWordList(String wordListPath) {
        wordList = new WordList(wordListPath);
    }

    public void solve() {
        startSearch();
    }

    private void startSearch() {
        while (foundWords.keySet().size() != wordList.size()) {
            selectAPoint();
            searchAroundPoint();
        }
    }

    private void searchAroundPoint() {
        for (Direction direction: Direction.values()) {
            String foundWord = lookForAWord(direction);

            if (wordList.wordMatches(foundWord)) {
                foundWords.put(foundWord, checkedPoints);
            } else {
                checkedPoints = null;
            }
        }
    }

    private String lookForAWord(Direction direction) {
        checkedPoints = new ArrayList<>();
        StringBuilder cumulatingWord = new StringBuilder();

        Point point = new Point(startingPoint.getX(),startingPoint.getY());

        cumulatingWord.append(grid.getLetterAt(point));
        checkedPoints.add(point);

        while (wordList.contains(cumulatingWord.toString())) {
            point = point.moveIn(direction);
            if (!grid.isInBounds(point)) return cumulatingWord.toString();
            checkedPoints.add(point);
            cumulatingWord.append(grid.getLetterAt(point));
        }

        // fence post issue
        checkedPoints.remove(checkedPoints.size() - 1);
        return cumulatingWord.deleteCharAt(cumulatingWord.length() - 1).toString();
    }

    private void selectAPoint() {
        if (startingPoint.getX() + 1 < grid.RowSize(startingPoint.getY())) {
            startingPoint = new Point(startingPoint.getX() + 1, startingPoint.getY());
        } else if (startingPoint.getY() + 1 < grid.ColSize(startingPoint.getX())) {
            startingPoint = new Point(0, startingPoint.getY() + 1);
        } else {
            System.out.println("Out of grid points, exiting...");
            System.exit(0);
        }
    }

    public void outputSolution() {
        try{
            PrintWriter writer = new PrintWriter("FoundWords.txt", "UTF-8");
            StringBuilder line = new StringBuilder();

            for (String word: foundWords.keySet()) {
                line.append(word + ": ");
                for(Point point: foundWords.get(word)) {
                    line.append(point + " ");
                }
                writer.println(line.toString());
                line = new StringBuilder();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Issue writing to file: " + e.getMessage());
        }
    }



    public void outputAnswerGrid() {
        try {
            PrintWriter writer = new PrintWriter("PuzzleWords.txt", "UTF-8");
            Grid answerGrid = createAnswerGrid();
            for (int i = 0; i < answerGrid.ColSize(); i++) {
                writer.println(answerGrid.getRow(i));
            }
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private Grid createAnswerGrid() {
        Grid answerGrid = new Grid(50,50);
        for (String word: foundWords.keySet()) {
            for (Point p: foundWords.get(word)) {
                answerGrid.addToGrid(p, grid.getLetterAt(p));
            }
        }
        return answerGrid;
    }

}
