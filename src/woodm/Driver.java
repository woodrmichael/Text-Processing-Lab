/*
 * Course: CSC-1110
 * Assignment: Text Processing
 * Name: Michael Wood
 */
package woodm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A Driver class for processing text from a file.
 */
public class Driver {
    private static final String DATA_FOLDER = "data";

    public static void main(String[] args) {
        // Instantiate your collections and other variables here

        // ask user for file

        // read file into Scanner
      
        // trim heading out of file

        // generate words and add to a list

        // generate bigrams from list
      
        // generate vocabulary from list

        // sort the data

        // Save vocabulary as a text file

        // Save bigrams as a text file

        // generate reports

        // Ask for how many top entries to show

        // report the top entries for vocabulary

        // report the top entries for bigrams

    }

    /**
     * A helper method to get input from the user.
     * @param in Scanner using System.in as input
     * @param message the message with which to prompt the user
     * @return the user input
     */
    private static String getInput(Scanner in, String message) {
        System.out.println(message);
        return in.nextLine();
    }

    /**
     * A helper method to remove the header information from a Project Gutenberg file.
     * The method will continue to consume the buffer of the Scanner until
     * the header text has been removed, then will stop.
     * @param read Scanner using a Project Gutenberg text file as input
     */
    private static void removeHeader(Scanner read) {
        String line = read.nextLine();
        while(!line.startsWith("*** START OF THE PROJECT GUTENBERG EBOOK")) {
            line = read.nextLine();
        }
        while(line.isBlank()) {
            line = read.nextLine();
        }
        read.nextLine();
    }

    /**
     * A helper method that reads words from a text file one at a time and stores the
     * normalized words in a List of BasicWords.
     * Any word that contains only whitespace should be ignored.
     * @param words the List used to store the words
     * @param read Scanner using a Project Gutenberg text file as input
     */
    private static void addWords(List<BasicWord> words, Scanner read) {
        long location = 0;
        String line = read.nextLine();
        while(!line.startsWith("*** END OF THE PROJECT GUTENBERG EBOOK")) {
            String[] strings = line.split(" ");
            for (String word : strings) {
                if (!normalize(word).isBlank()) {
                    words.add(new BasicWord(normalize(word), location));
                    location++;
                }
            }
            line = read.nextLine();
        }
    }

    /**
     * A helper method that removes all punctuation from a String and converts the resulting
     * punctuation-less String to lowercase
     * @param s the String to normalize
     * @return the normalized String
     */
    private static String normalize(String s) {
        return s.replaceAll("\\p{Punct}", "").toLowerCase();
    }

    /**
     * A helper method that generates Bigrams from the ordered List of BasicWords and
     * stores the Bigrams in a List.
     * There should only be one instance of each Bigram in the List.
     * When successive copies of the same Bigram are found, the location should be added to the
     * existing Bigram and the occurrence count should be incremented.
     * @param bigrams the List in which to store the resulting Bigrams
     * @param words the ordered List of BasicWord to use to generate the Bigrams
     */
    private static void addBigrams(List<Word> bigrams, List<BasicWord> words) {
        boolean flag;
        for(int i = 0; i < words.size() - 1; i++) {
            flag = false;
            Bigram tempBigram = new Bigram(words.get(i), words.get(i+1));
            for (Word bigram : bigrams) {
                if (tempBigram.equals(bigram)) {
                    bigram.addLocation(words.get(i).getLocation());
                    flag = true;
                }
            }
            if(!flag) {
                bigrams.add(tempBigram);
            }
        }
    }

    /**
     * A helper method that generates VocabularyEntry objects from the ordered List of
     * BasicWords and stores the entries in a List.
     * There should only be one instance of each VocabularyEntry in the List.
     * When successive copies of the same entry are found, the location should be added to the
     * existing entry and the occurrence count should be incremented.
     * @param vocabulary the List in which to store the resulting VocabularyEntry objects
     * @param words the ordered List of BasicWord to use to generate the vocabulary
     */
    private static void addVocabulary(List<Word> vocabulary, List<BasicWord> words) {
        boolean flag;
        for (BasicWord word : words) {
            flag = false;
            VocabularyEntry tempVocab = new VocabularyEntry(word);
            for (Word vocab : vocabulary) {
                if (tempVocab.equals(vocab)) {
                    vocab.addLocation(word.getLocation());
                    flag = true;
                }
            }
            if (!flag) {
                vocabulary.add(tempVocab);
            }
        }
    }

    /**
     * A helper method to save a List of Words as a text file
     * @param list the List of Word objects to save
     * @param output the File to save the data into
     * @throws FileNotFoundException thrown if the File cannot be found
     */
    private static void saveFile(List<Word> list, File output) throws FileNotFoundException {

    }

    /**
     * A helper method that generates a report of the most frequent entries from the given sorted
     * List. If topHits is greater than the total number of entries in the list,
     * it will print out the entire list
     * @param list the List from which to generate the report
     * @param type a String describing what the contents of the list are
     *            (i.e. "Words", "Bigrams", etc.)
     * @param topHits the number of items to display in the report
     */
    private static void report(List<Word> list, String type, int topHits) {

    }
}
