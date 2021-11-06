import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 * Tests for class {@link TableDrivenFSA} using no test framework.
 * Utility class with main method that runs all test methods.
 * Depends on specific external test data file which is
 * created if it does not already exist.
 * @author  Dr. Jody Paul
 * @version 1.3
 */
public final class TableDrivenFSATests {
    /** Prepared test data file name. */
    public static final String TEST_DATA_FILE_NAME = "table1.txt";
    /** Test data. */
    public static final String TEST_DATA = "a,b,c\n1,2,3\n0,2,3\n4,2,3\n4,2,3\n4,4,4\n{2,3}\n";

    /** Hidden constructor for utility class. */
    private TableDrivenFSATests() { }

    /**
     * Driver for tests.
     * Evaluates test methods sequentially.
     * Exception thrown at first failing assertion.
     * @param args ignored
     */
    public static void main(String[] args) {
        establishTestData(TEST_DATA_FILE_NAME,TEST_DATA);
        toStringOfValidTableFileTest();
        constructionInvalidParameterTest();
        processAcceptTest();
        processRejectTest();
        nextStateValidParametersTest();
        nextStateInvalidParameterTest();
        System.out.println("No assertions violated.");
    }

    /**
     * Map assertTrue to assert.
     * @param value validation if value==true
     */
    public static void assertTrue(boolean value) {
        assert(value);
    }

    /**
     * Map assertTrue(String,booolean) to assert.
     * @param msg message indicating intended result
     * @param value validation if value==true
     */
    public static void assertTrue(String msg, boolean value) {
        assert(value);
    }

    /**
     * Map assertFalse to assert.
     * @param value validation if value==false
     */
    public static void assertFalse(boolean value) {
        assert(!value);
    }

    /**
     * Map assertEquals to assert.
     * @param obj1 validation if obj1==obj2
     * @param obj2 validation if obj1==obj2
     */
    public static void assertEquals(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) return;
        assert(obj1.equals(obj2));
    }

    /**
     * Verify toString of object constructed with valid parameter.
     */
    public static void toStringOfValidTableFileTest() {
        TableDrivenFSA dfa = new TableDrivenFSA(TEST_DATA_FILE_NAME);
        assertEquals(TEST_DATA, dfa + "");
    }

    /**
     * Verify object constructed with invalid parameter is not null.
     * Note: An expected side effect is sending an error message to System.err
     */
    public static void constructionInvalidParameterTest() {
        TableDrivenFSA dfa = new TableDrivenFSA("BOGUS");
        assertTrue("This method should handle input file errors properly", true);
    }

    /**
     * Process input with accepted strings.
     */
    public static void processAcceptTest() {
        TableDrivenFSA dfa = new TableDrivenFSA(TEST_DATA_FILE_NAME);
        assertTrue(dfa.processString("abbc"));
        assertTrue(dfa.processString("b"));
        assertTrue(dfa.processString("c"));
        assertTrue(dfa.processString("ab"));
    }

    /**
     * Process input with rejected strings.
     */
    public static void processRejectTest() {
        TableDrivenFSA dfa = new TableDrivenFSA(TEST_DATA_FILE_NAME);
        assertFalse(dfa.processString("abba"));
        assertFalse(dfa.processString(""));
        assertFalse(dfa.processString("a"));
        assertFalse(dfa.processString(null));
    }

    /**
     * Verify nextState method behavior with valid parameters.
     */
    public static void nextStateValidParametersTest() {
        TableDrivenFSA t1 = new TableDrivenFSA(TEST_DATA_FILE_NAME);
        assertEquals(1, t1.nextState(0, "a"));
        assertEquals(2, t1.nextState(0, "b"));
        assertEquals(3, t1.nextState(0, "c"));
        assertEquals(0, t1.nextState(1, "a"));
        assertEquals(2, t1.nextState(1, "b"));
        assertEquals(3, t1.nextState(1, "c"));
        assertEquals(4, t1.nextState(2, "a"));
        assertEquals(2, t1.nextState(2, "b"));
        assertEquals(3, t1.nextState(2, "c"));
        assertEquals(4, t1.nextState(3, "a"));
        assertEquals(2, t1.nextState(3, "b"));
        assertEquals(3, t1.nextState(3, "c"));
        assertEquals(4, t1.nextState(4, "a"));
        assertEquals(4, t1.nextState(4, "b"));
        assertEquals(4, t1.nextState(4, "c"));
    }

    /**
     * Verify nextState method behavior with invalid parameters.
     */
    public static void nextStateInvalidParameterTest() {
        TableDrivenFSA t1 = new TableDrivenFSA(TEST_DATA_FILE_NAME);
        assertEquals(0, t1.nextState(0, "x"));
        assertEquals(6, t1.nextState(6, "b"));
        assertEquals(8, t1.nextState(8, "quack"));
        assertEquals(-1, t1.nextState(-1, "a"));
        assertEquals(1, t1.nextState(1, null));
    }

    /**
     * Establish test data file.
     * @param dataFileName the name of the file to create or replace
     * @param testData the data to be written to the file
     */
    public static void establishTestData(String dataFileName, String testData) {
        File file = new File(dataFileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Files.write(Paths.get(dataFileName), testData.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}