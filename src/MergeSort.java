import java.util.ArrayList;

/**
 * Merge sort
 * Created by Kunmiao Yang on 2/1/2018.
 */
public class MergeSort extends Sort {

    /**
     * Initialize sort with comparator
     * @param comp comparator used int the sort
     */
    public MergeSort(RecordComparator comp) {
        super(comp);
    }

    /**
     * Sort the array
     * @param array the array to be sorted
     */
    @Override
    public void sort(Integer[] array) {
        // Allocate temporary array to store the merged array
        Integer[] tempArray = new Integer[array.length];

        // Use the recursive method to sort
        sort(array, tempArray, 0, array.length - 1);
    }

    /**
     * Sort the array recursively
     * @param array the array to be sorted
     * @param tempArray temporary array to store the merged array
     * @param left the first index of the sub array
     * @param right the last index of the sub array
     */
    private void sort(Integer[] array, Integer[] tempArray, int left, int right) {
        // If only one number is in this range, there is no need to sort
        if(left == right) return;

        // Divide the array into 2 half, and sort them separately
        int mid = (left + right - 1) / 2;
        sort(array, tempArray, left, mid);
        sort(array, tempArray, mid + 1, right);

        // Merge the 2 sorted array until one of them finishes
        int i, l, r;
        for(i = left, l = left, r = mid + 1; l <= mid && r <= right; i++) {
            tempArray[i] = comp.compare(array[l], array[r]) > 0? array[r++] : array[l++];
        }

        // Put the rest of the unfinished array to the tail
        if(l <= mid) System.arraycopy(array, l, tempArray, i, right - i + 1);
        else System.arraycopy(array, r, tempArray, i, right - i + 1);

        // Update the array with the merged temporary array
        System.arraycopy(tempArray, left, array, left, right - left + 1);
    }

    /**
     * Sort the array recursively
     * @param array the array to be sorted
     * @param tempArray temporary array to store the merged array
     * @param left the first index of the sub array
     * @param right the last index of the sub array
     */
    private void sort(ArrayList<Integer> array, Integer[] tempArray, int left, int right) {
        // If only one number is in this range, there is no need to sort
        if(left == right) return;

        // Divide the array into 2 half, and sort them separately
        int mid = (left + right) / 2;
        sort(array, tempArray, left, mid);
        sort(array, tempArray, mid + 1, right);

        // Merge the 2 sorted array until one of them finishes
        int i, l, r;
        for(i = left, l = left, r = mid + 1; l <= mid && r <= right; i++) {
            tempArray[i] = comp.compare(array.get(l), array.get(r)) > 0? array.get(r++) : array.get(l++);
        }

        // Put the rest of the unfinished array to the tail
        while (l <= mid) tempArray[i++] = array.get(l++);
        while (r <= right) tempArray[i++] = array.get(r++);

        // Update the array with the merged temporary array
        for(i = left; i <= right; i++) array.set(i, tempArray[i]);
    }

    /**
     * Main method
     * @param args arguments
     * @throws Throwable
     */
    public static void main(String[] args) throws Throwable {
        // Invoke the standard test framework
        standardTest(readArray(System.in), new MergeSort(new RecordComparator()));
    }
}
