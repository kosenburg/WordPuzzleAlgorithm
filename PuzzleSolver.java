/**
 * Puzzle solver maintains two grid objects, one is the loadedPuzzleGrid and the other is a solution grid.
 * The solver also maintains a WordList object to compare words against as well as point objects that represent
 * coordinates on the grid. Lastly, an array of checked points is used to keep track of where the letters are
 * found while currently searching in a given direction.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kevin on 2/26/2017.
 */
public class PuzzleSolver {
    private Grid loadedPuzzleGrid;
    private WordList wordList;
    private HashMap<String, ArrayList<Point>> foundWords;
    private Point startingPoint;
    private ArrayList<Point> checkedPoints;

    public PuzzleSolver() {
        startingPoint = new Point(-1,0);
        foundWords = new HashMap<>();
    }

    // Loads the puzzle and word list into their respective objects
    public void loadWordFindPuzzle(String wordPuzzlePath, String wordListPath) {
        createGrid(wordPuzzlePath);
        createWordList(wordListPath);
    }

    private void createGrid(String wordPuzzlePath) {
        loadedPuzzleGrid = new Grid(wordPuzzlePath);
    }

    private void createWordList(String wordListPath) {
        wordList = new WordList(wordListPath);
    }

    public void solve() {
        startSearch();
    }

    // Only keeps searching until the number of words found is the same as on the list.
    private void startSearch() {
        while (foundWords.keySet().size() != wordList.size()) {
            selectAPoint();
            searchAroundPoint();
        }
    }

    // After a start point is selected, we look for words around the point by moving in each direction
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

    // With a direction selected, we keep moving in that direction and comparing the increasing word to the
    // word list looking for a match. We stop when the word matches non in the list.
    private String lookForAWord(Direction direction) {
        checkedPoints = new ArrayList<>();
        StringBuilder cumulatingWord = new StringBuilder();

        Point point = new Point(startingPoint.getX(),startingPoint.getY());

        cumulatingWord.append(loadedPuzzleGrid.getLetterAt(point));
        checkedPoints.add(point);

        while (wordList.contains(cumulatingWord.toString())) {
            point = point.moveIn(direction);
            if (!loadedPuzzleGrid.isInBounds(point)) return cumulatingWord.toString();
            checkedPoints.add(point);
            cumulatingWord.append(loadedPuzzleGrid.getLetterAt(point));
        }

        // fence post correction
        checkedPoints.remove(checkedPoints.size() - 1);
        return cumulatingWord.deleteCharAt(cumulatingWord.length() - 1).toString();
    }

    // Select a new point for each search moving from left to right and then down to the next row.
    private void selectAPoint() {
        if (startingPoint.getX() + 1 < loadedPuzzleGrid.RowSize(startingPoint.getY())) {
            startingPoint = new Point(startingPoint.getX() + 1, startingPoint.getY());
        } else if (startingPoint.getY() + 1 < loadedPuzzleGrid.ColSize(startingPoint.getX())) {
            startingPoint = new Point(0, startingPoint.getY() + 1);
        } else {
            System.out.println("Out of loadedPuzzleGrid points, exiting...");
            System.exit(0);
        }
    }

    public void outputSolution() {
        try{
            PrintWriter writer = new PrintWriter("Solution.txt", "UTF-8");
            StringBuilder line = new StringBuilder();

            for (String word: foundWords.keySet()) {
                line.append(word + ": ");
                for(Point point: foundWords.get(word)) {
                    line.append("(" + point.getX() + ", " + point.getY() + ")" + " ");
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
            PrintWriter writer = new PrintWriter("AnswerGrid.txt", "UTF-8");
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
        Grid answerGrid = new Grid(loadedPuzzleGrid.ColSize(),loadedPuzzleGrid.RowSize(0));
        for (String word: foundWords.keySet()) {
            for (Point p: foundWords.get(word)) {
                answerGrid.addToGrid(p, loadedPuzzleGrid.getLetterAt(p));
            }
        }
        return answerGrid;
    }

}
