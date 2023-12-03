/*
 * Course: CSC1110 - 111
 * Fall 2023
 * Lab 13 - Text Processing
 * Name: Michael Wood
 * Last Updated: 11/30/2023
 */
package woodm;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a bigram
 */
public class Bigram extends Word {
    private final List<Long> locations;
    private int occurrences;
    private final String word2;

    /**
     * Constructor for a Bigram
     * @param word1 the first Word in the Bigram
     * @param word2 the second Word in the Bigram
     */
    public Bigram(BasicWord word1, BasicWord word2) {
        super(word1.word);
        this.word2 = word2.word;
        this.locations = new ArrayList<>();
        this.addLocation(word1.getLocation());
    }

    /**
     * Adds a new locations to an existing Bigram and increments the number of occurrences.
     * If the location already has been added,
     * or the location is not a valid location (i.e. negative), an exception is thrown.
     * @param location a location of the bigram
     * @throws IllegalArgumentException thrown if the location already exists or is invalid
     */
    @Override
    public void addLocation(long location) throws IllegalArgumentException {
        for (Long loc : this.locations) {
            if (loc == location || location < 0) {
                throw new IllegalArgumentException();
            }
        }
        this.locations.add(location);
        this.occurrences++;
    }

    /**
     * Generates a String representation of the Bigram
     * that contains both words of the Bigram and the number of occurrences of the Bigram.
     * @return a String representation of the Bigram
     */
    @Override
    public String toString() {
        return String.format("%-16s%-19s%d", this.word, this.word2, this.occurrences);
    }

    /**
     * Compares another Object to check if that Object is equal to this Bigram.
     * Equality is measured by whether the other Object is also a Bigram and both words contained
     * in this Bigram matches exactly the words contained in the
     * other Bigram object in the same order.
     * @param o the Object to compare to this
     * @return true if o is both a Bigram and contains the same words in the same order as this
     * Bigram, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof Bigram && ((Bigram)o).word.equals(this.word) &&
                ((Bigram)o).word2.equals(this.word2);
    }

    /**
     * Compares this object with the specified object for order.
     * Returns a negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * For this class, we are comparing the number of occurrences of the two Bigrams.
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Word o) {
        return Integer.compare(this.occurrences, ((Bigram) o).occurrences);
    }
}
