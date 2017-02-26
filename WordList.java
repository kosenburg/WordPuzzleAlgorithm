import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kevin on 2/26/2017.
 */
public class WordList {
    private final String pathToList;
    private ArrayList<String> wordList;

    public WordList(String pathToList) {
        this.wordList = new ArrayList<>();
        this.pathToList = pathToList;
        LoadWordList();
    }

    private void LoadWordList() {
        try (BufferedReader br = new BufferedReader(new FileReader(pathToList))) {
            String line;
            while((line = br.readLine()) != null) {
                wordList.add(line.toUpperCase());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to locate file: " + pathToList);
        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getMessage());
        }
    }

    public boolean contains(String characters) {
        for (String word: wordList) {
            if (word.contains(characters)){
                return true;
            }
        }
        return false;
    }

    public boolean wordMatches(String locatedWord) {
        for (String word: wordList){
            if (word.equals(locatedWord)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return wordList.size();
    }
}
