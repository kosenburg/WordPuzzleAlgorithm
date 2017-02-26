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
            cumulatingWord.append(grid.getLetterAt(point));
        }
        return cumulatingWord.deleteCharAt(cumulatingWord.length() - 1).toString();
    }

    private void selectAPoint() {
        if (startingPoint.getX() + 1 < grid.RowSize(startingPoint.getY())) {
            startingPoint = new Point(startingPoint.getX() + 1, startingPoint.getY());
        } else if (startingPoint.getY() + 1 < grid.ColSize(startingPoint.getX())) {
            startingPoint = new Point(0, startingPoint.getY() + 1);
        } else {
            System.out.println("Out of grid, exiting...");
            System.exit(0);
        }
    }

}
