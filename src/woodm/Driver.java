/*
 * Course: CSC-1110
 * Assignment: Text Processing
 * Name: Michael Wood
 */
package woodm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * A Driver class for processing text from a file.
 */
public class Driver {
    private static final String DATA_FOLDER = "data";

    public static void main(String[] args) {
        // Instantiate your collections and other variables here
        final List<BasicWord> words = new ArrayList<>();
        final List<Word> bigrams = new ArrayList<>();
        final List<Word> vocabularyEntries = new ArrayList<>();
        Scanner in = new Scanner(System.in);

        // ask user for file
        String fileName = getInput(in, "Enter the file to read: ");
        System.out.println("Processing...");

        // read file into Scanner
        try (Scanner read = new Scanner(Path.of(
                DATA_FOLDER + System.getProperty("file.separator") + fileName))) {

            // trim heading out of file
            removeHeader(read);

            // generate words and add to a list
            System.out.println("Getting the words...");
            addWords(words, read);

            // generate bigrams from list
            System.out.println("Making bigrams...");
            addBigrams(bigrams, words);

            // generate vocabulary from list
            System.out.println("Generating vocabulary...");
            addVocabulary(vocabularyEntries, words);

            // sort the data
            System.out.println("Sorting lists...");
            long startTime = System.currentTimeMillis();
            Collections.sort(bigrams);
            Collections.reverse(bigrams);
            Collections.sort(vocabularyEntries);
            Collections.reverse(vocabularyEntries);
            getElapsedTime(startTime, System.currentTimeMillis());

            // Save vocabulary as a text file
            System.out.println("Saving vocabulary...");
            saveFile(vocabularyEntries,
                    new File(getInput(in, "Enter the vocabulary file to save: ")));

            // Save bigrams as a text file
            System.out.println("Saving bigrams...");
            saveFile(bigrams, new File(getInput(in, "Enter the bigram file to save: ")));

            // generate reports
            System.out.println("Generating reports...");
            // Ask for how many top entries to show
            boolean flag;
            int topHits = 0;
            do {
                try {
                    String numHits = getInput(in, "Enter the number of top entries to show: ");
                    topHits = Integer.parseInt(numHits);
                    flag = topHits <= 0;
                } catch (NumberFormatException e) {
                    flag = true;
                }
                if(flag) {
                    System.out.println("\nPlease enter a positive integer");
                }
            } while(flag);

            // report the top entries for vocabulary
            report(vocabularyEntries, "vocabulary", topHits);

            // report the top entries for bigrams
            report(bigrams, "bigrams", topHits);

        } catch (FileNotFoundException e) {
            System.out.println("Invalid text file. Please enter a file name with extension .txt");
        } catch (IOException e) {
            System.out.println("File could not be read or something else went wrong");
        }
    }

    /**
     * A helper method to get input from the user.
     * @param in Scanner using System.in as input
     * @param message the message with which to prompt the user
     * @return the user input
     */
    private static String getInput(Scanner in, String message) {
        System.out.print(message);
        return in.nextLine();
    }

    /**
     * A helper method to remove the header information from a Project Gutenberg file.
     * The method will continue to consume the buffer of the Scanner until
     * the header text has been removed, then will stop.
     * @param read Scanner using a Project Gutenberg text file as input
     */
    private static void removeHeader(Scanner read) {
        long startTime = System.currentTimeMillis();
        String line = read.nextLine();
        while(!line.startsWith("*** START OF THE PROJECT GUTENBERG EBOOK")) {
            line = read.nextLine();
        }
        while(line.isBlank()) {
            line = read.nextLine();
        }
        read.nextLine();
        getElapsedTime(startTime, System.currentTimeMillis());
    }

    /**
     * A helper method that reads words from a text file one at a time and stores the
     * normalized words in a List of BasicWords.
     * Any word that contains only whitespace should be ignored.
     * @param words the List used to store the words
     * @param read Scanner using a Project Gutenberg text file as input
     */
    private static void addWords(List<BasicWord> words, Scanner read) {
        long startTime = System.currentTimeMillis();
        long location = 0;
        String line = read.nextLine();
        while(!line.startsWith("*** END OF THE PROJECT GUTENBERG EBOOK")) {
            String[] strings = line.split("\\s+");
            for (String word : strings) {
                if (!normalize(word).isBlank()) {
                    words.add(new BasicWord(normalize(word), location));
                    location++;
                }
            }
            line = read.nextLine();
        }
        getElapsedTime(startTime, System.currentTimeMillis());
    }

    /**
     * A helper method that removes all punctuation from a String and converts the resulting
     * punctuation-less String to lowercase
     * @param s the String to normalize
     * @return the normalized String
     */
    private static String normalize(String s) {
        return s.replaceAll("[\\p{Punct}“”]", "").toLowerCase();
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
        long startTime = System.currentTimeMillis();
        boolean flag;
        for(int i = 0; i < words.size() - 1; i++) {
            flag = false;
            Bigram tempBigram = new Bigram(words.get(i), words.get(i+1));
            for (Word bigram : bigrams) {
                if (tempBigram.equals(bigram)) {
                    bigram.addLocation(words.get(i).getLocation());
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                bigrams.add(tempBigram);
            }
        }
        getElapsedTime(startTime, System.currentTimeMillis());
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
        long startTime = System.currentTimeMillis();
        boolean flag;
        for (BasicWord word : words) {
            flag = false;
            VocabularyEntry tempVocab = new VocabularyEntry(word);
            for (Word vocab : vocabulary) {
                if (tempVocab.equals(vocab)) {
                    vocab.addLocation(word.getLocation());
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                vocabulary.add(tempVocab);
            }
        }
        getElapsedTime(startTime, System.currentTimeMillis());
    }

    /**
     * A helper method to save a List of Words as a text file
     * @param list the List of Word objects to save
     * @param output the File to save the data into
     * @throws FileNotFoundException thrown if the File cannot be found
     */
    private static void saveFile(List<Word> list, File output) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        if(output.getPath().isBlank() || !output.getPath().endsWith(".txt")) {
            throw new FileNotFoundException();
        }
        try (PrintWriter writer = new PrintWriter(output)) {
            for(Word word : list) {
                writer.println(word);
            }
        }
        getElapsedTime(startTime, System.currentTimeMillis());
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
        long startTime = System.currentTimeMillis();
        System.out.println("Top " + topHits + " " + type + " are:");
        int maxTopHits = Math.min(topHits, list.size());
        for(int i = 0; i < maxTopHits; i++) {
            System.out.printf("%-6d%20s%s", i + 1, list.get(i), System.lineSeparator());
        }
        getElapsedTime(startTime, System.currentTimeMillis());
    }

    /**
     * A helper method used to display the total amount of time that each method takes to complete.
     * @param startTime the time in ms that the method begins.
     * @param endTime the time in ms that the method ends.
     */
    private static void getElapsedTime(long startTime, long endTime) {
        final double msPerSecond = 1000;
        long duration = endTime - startTime;
        System.out.print("Time Elapsed: " + duration + " ms");
        if(duration >= msPerSecond) {
            System.out.print(" (" + duration / msPerSecond + " s)");
        }
        System.out.println();
    }
}
