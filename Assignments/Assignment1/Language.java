import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
/**
 * Represents a finite language.
 *
 * @author Dr. Jody Paul
 * @author Noemi Marquez
 * @version 1.3.1
 */
public final class Language implements Iterable<String>, java.io.Serializable {
    /** The empty string. */
    private static final String EMPTY_STRING = "";
    /** The empty set. */
    private static final Set<String> EMPTY_SET = new TreeSet<String>();

    /** The set of strings in this language, initially empty. */
    private Set<String> strings;

    /**
     * Create a language with no strings.
     */
    public Language() {
        // Initializing strings set as empty set
        strings = EMPTY_SET;
    }

    /**
     * Indicates if this language has no strings.
     * @return true if the language is equivalent to the empty set;
     *         false otherwise
     */
    public boolean isEmpty() {
        // Checks if the strings set is empty
        return strings.isEmpty();
    }

    /**
     * Accesses the number of strings (cardinality) in this language.
     * @return the cardinality of the language
     */
    public int cardinality() {
        // Checks the size of the strings set
        return strings.size();
    }

    /**
     * Determines if a specified string is in this language.
     * @param candidate the string to check
     * @return true if the string is in the language,
     *         false if not in the language or the parameter is null
     */
    public boolean includes(final String candidate) {
        // Checks if the strings set contains the candidate string
        return strings.contains(candidate);
    }

    /**
     * Ensures that this language includes the given string
     * (adds it if necessary).
     * @param memberString the string to be included in the language
     * @return true if this language changed as a result of the call
     */
    public boolean addString(final String memberString) {
        // Adds memberString to the strings set
        return strings.add(memberString);
    }

    /**
     * Ensures that this language includes all of the strings
     * in the given parameter (adds any as necessary).
     * @param memberStrings the strings to be included in the language
     * @return true if this language changed as a result of the call
     */
    public boolean addAllStrings(final Collection<String> memberStrings) {
        // adds all memberStrings in the collection to strings set
        return strings.addAll(memberStrings);
    }

    /**
     * Provides an iterator over the strings in this language.
     * @return an iterator over the strings in this language in ascending order
     */
    public Iterator<String> iterator() {
        // Returns the iterator for strings set
        return strings.iterator();
    }

    /**
     * Creates a language that is the concatenation of this language
     * with another language.
     * @param language the language to be concatenated to this language
     * @return the concatenation of this language with the parameter language
     */
    public Language concatenate(final Language language) {
        // Create a new Language instance which will contain the concatenated string set
        Language concatenated = new Language();

        // Add the string set of this instance to the concatenated language instance
        concatenated.addAllStrings(strings);

        // Get iterator for passed in language to have access to its strings set
        Iterator<String> iterator = language.iterator();

        // Using the iterator, loop through the strings in the passed in language string set
        // and add each string to the new concatenated language string set.
        while(iterator.hasNext()) {
            concatenated.addString(iterator.next());
        }

        // Return the concatenated language
        return concatenated;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Language) {
            // Cast obj to Language class
            final Language other = (Language) obj;

            // If the hashCode of this language is equal to the hashCode of the passed in language,
            // the classes are equal. Otherwise, they are not equal.
            return hashCode() == other.hashCode();
        }

        // If the passed in object is not of type Language, it cannot be equal to this language.
        return false;
    }
    @Override
    public int hashCode() {
        // Use the hashCode of the strings set
        return strings.hashCode();
    }
}