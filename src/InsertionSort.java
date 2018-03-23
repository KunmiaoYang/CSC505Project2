/**
 * Insertion sort
 * Created by Kunmiao Yang on 2/1/2018.
 */
public class InsertionSort extends Sort {
    /**
     * Initialize sort with comparator
     * @param comp comparator used int the sort
     */
    public InsertionSort(RecordComparator comp) {
        super(comp);
    }

    /**
     * Sort the array
     * @param array the array to be sorted
     */
    @Override
    public void sort(Integer[] array) {
        int n = array.length;
        for(int i = 1; i < n; i++) {
            // Get the current number
            int cur = array[i];
            // Declare the integer to record the first position where the number is less or equal than current number
            int j;
            // Loop all the numbers before current number that are larger than current number
            for(j = i - 1; j >= 0 && comp.compare(array[j], cur) > 0; j--) {
                // Move all the numbers to the next position
                array[j + 1] = array[j];
            }
            // Put the current number next to the first position where the number is less or equal than current number
            array[j + 1] = cur;
        }
    }

    /**
     * Main method
     * @param args arguments
     * @throws Throwable
     */
    public static void main(String[] args) throws Throwable {
        // Invoke the standard test framework
        standardTest(readArray(System.in), new InsertionSort(new RecordComparator()));
    }
}
