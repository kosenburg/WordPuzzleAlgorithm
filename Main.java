/**
 * Kevin Osenburg
 * Programming Assignment 2 (Word Puzzle)
 * Inputs: word puzzle, word list
 * Outputs: Solution.txt - contains the words found and the points they were found at.
 *          AnswerGrid.txt - contains the words found in their positions from the crossword puzzle
 *
 *  This program will locate all of the words on a wordlist located in a loaded word puzzle. It will output
 *  the results to two files, Solution.txt and AnswerGrid.txt. The program creates a puzzle solver object
 *  and loads the file containing the word puzzle and a file containing the word list. The puzzle solver is then
 *  asked to solve the puzzle. To get the solution, the user askes the solver to display the output using outputSolution
 *  and outputAnswerGrid.
 */

public class Main {
    public static void main(String[] args) {
        PuzzleSolver solver = new PuzzleSolver();

        // loads the word puzzle and word list.
        solver.loadWordFindPuzzle("Files/puzzleinput.txt", "Files/wordlist.txt");

        long startTime = System.nanoTime();
        solver.solve();
        long runTime = System.nanoTime() - startTime;

        System.out.println("The algorithm took " + (double)runTime / Math.pow(10, 9) + "seconds to run");
        solver.outputSolution();
        solver.outputAnswerGrid();
    }
}
