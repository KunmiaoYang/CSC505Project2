import java.util.Arrays;

/**
 * Sort used in Java Utility
 * Created by Kunmiao Yang on 2/2/2018.
 */
public class JavaSort extends Sort{
    /**
     * Initialize sort with comparator
     * @param comp comparator used int the sort
     */
    public JavaSort(RecordComparator comp) {
        super(comp);
    }

    /**
     * Sort the array
     * @param array the array to be sorted
     */
    @Override
    public void sort(Integer[] array) {
        // Invoke Java array sort
        Arrays.sort(array, comp);
    }

    /**
     * Main method
     * @param args arguments
     * @throws Throwable
     */
    public static void main(String[] args) throws Throwable {
        // Invoke the standard test framework
        standardTest(readArray(System.in), new JavaSort(new RecordComparator()));
    }
}
