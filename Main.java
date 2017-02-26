public class Main {

    public static void main(String[] args) {
        PuzzleSolver solver = new PuzzleSolver();
        solver.loadWordFindPuzzle("Files/puzzleinput.txt", "Files/wordlist.txt");
        solver.solve();
        solver.outputSolution();
        solver.outputAnswerGrid();
    }
}
