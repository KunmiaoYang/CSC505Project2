import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract sort class
 * Created by Kunmiao Yang on 2/1/2018.
 */
abstract public class Sort implements Median {
    public static final String KEY_COMPARISON = "Comparison";       // Comparison key in report
    public static final String KEY_TIME = "Time";                   // Time key in report
    public static final String KEY_MEDIAN = "Median";               // Median key in report
    protected RecordComparator comp;                                // Comparator used in the sort

    /**
     * Initialize sort with comparator
     * @param comp Comparator used in the sort
     */
    public Sort(RecordComparator comp) {
        // Initialize sort with comparator
        this.comp = comp;
    }

    /**
     * Sort the array
     * @param array the array to be sorted
     */
    abstract public void sort(Integer[] array);


    /**
     * Swap numbers in ArrayList
     * @param array the array to where the 2 numbers are in
     * @param i the index of the first number
     * @param j the index of the second number
     */
    public static void swap(ArrayList<Integer> array, int i, int j) {
        Integer temp = array.get(i);
        array.set(i, array.get(j));
        array.set(j, temp);
    }

    /**
     * Swap numbers in ArrayList
     * @param array the array to where the 2 numbers are in
     * @param i the index of the first number
     * @param j the index of the second number
     */
    public static void swap(Integer[] array, int i, int j) {
        Integer temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Read Array from given input stream
     * @param in the input stream to read the array
     * @return an integer array read from the input stream
     * @throws IOException
     */
    public static Integer[] readArray(InputStream in) throws IOException {
        // Initialize reader
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        // Initialize array
        List<Integer> buff = new ArrayList<>();

        // Read array number from standard input
        for(String line = br.readLine(); null != line; line = br.readLine()) buff.add(Integer.parseInt(line));

        return buff.toArray(new Integer[buff.size()]);
    }

    /**
     * Standard test framework
     * @param array the array to be sorted
     * @param sort the sort object to be used
     * @throws IOException
     */
    public static void standardTest(Integer[] array, Sort sort) throws IOException {
        // Sort array and get the report
        Map<String, Number> report = sort.testSort(array);

        // Output the statistics report to error output
        System.out.println("median," + report.get(KEY_MEDIAN));
        System.out.println("runtime," + report.get(KEY_TIME));
        System.out.println("comparisons," + report.get(KEY_COMPARISON));
    }

    /**
     * Sort test with time and comparison statistics
     * @param array the array to be sorted
     * @return the report of the comparison statistics
     */
    public Map<String, Number> testSort(Integer[] array) {
        // Initialize the report
        Map<String, Number> report = new HashMap<>();

        // Initialize the time variables
        long startTime, endTime;

        // Set the comparator counter to 0
        comp.setCount(0);

        // Record start time
        startTime= System.currentTimeMillis();

        // Sort array
        int median = findMedian(array);

        // Record end time
        endTime= System.currentTimeMillis();

        // Put statistics into report and return
        report.put(KEY_TIME, endTime - startTime);
        report.put(KEY_COMPARISON, comp.getCount());
        report.put(KEY_MEDIAN, median);
        return report;
    }

    /**
     * Find the median of an array using Quick Select.
     * The median here is defined as the element at (n-1)/2 position
     * according to the requirement in program 2
     * @param A the array we need to find median in
     * @return the median in array A
     */
    @Override
    public int findMedian(Integer[] A) {
        sort(A);
        return A[(A.length - 1)/2];    // Invoke Quick Select method to find median
    }
}
