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
 * A class that contains information about a single word, it's location(s), and its occurrences.
 */
public class VocabularyEntry extends Word {
    private final List<Long> locations;
    private int occurrences;

    /**
     * Constructor for a VocabularyEntry
     * @param basic Word to add to the list
     */
    public VocabularyEntry(BasicWord basic) {
        super(basic.word);
        this.locations = new ArrayList<>();
        this.addLocation(basic.getLocation());
    }

    public int getOccurrences() {
        return this.occurrences;
    }

    public List<Long> getLocations() {
        return this.locations;
    }

    /**
     * Adds a new locations to an existing Word and increments the number of occurrences.
     * If the location already has been added,
     * or the location is not a valid location (i.e. negative), an exception is thrown.
     * @param location a location of the word
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
     * Generates a String representation of the VocabularyEntry that contains both the word
     * of the entry and the number of occurrences of the entry.
     * @return a String representation of the VocabularyEntry
     */
    @Override
    public String toString() {
        return String.format("%-19s%d", this.word, this.occurrences);
    }

    /**
     * Compares another Object to check if that Object is equal to this VocabularyEntry.
     * Equality is measured by whether the other Object is also a VocabularyEntry and
     * the word contained in this VocabularyEntry matches exactly the word
     * contained in the other VocabularyEntry object.
     * @param o the Object to compare to this
     * @return true if o is both a VocabularyEntry and contains the same word as this
     * VocabularyEntry, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof VocabularyEntry && ((VocabularyEntry)o).word.equals(this.word);
    }

    /**
     * Compares this object with the specified object for order. Returns a negative integer, zero,
     * or a positive integer as this object is less than, equal to,
     * or greater than the specified object.
     * For this class, we are comparing the number of occurrences
     * of the two VocabularyEntry objects.
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Word o) {
        return Integer.compare(this.occurrences, ((VocabularyEntry) o).occurrences);
    }
}
