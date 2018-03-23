import java.util.ArrayList;

/**
 * Heap Sort
 * Created by Kunmiao Yang on 2/1/2018.
 */
public class HeapSort extends Sort {
    private int end;    // Indicate in the array the current end position of the tree

    /**
     * Initialize sort with comparator
     * @param comp comparator used int the sort
     */
    public HeapSort(RecordComparator comp) {
        super(comp);
    }

    /**
     * Sort the array
     * @param array the array to be sorted
     */
    @Override
    public void sort(Integer[] array) {
        // Initialize the end index position
        end = array.length - 1;

        // Build heap
        for(int i = (end - 1) / 2; i >= 0; i--) sift(array, i);

        // Sort
        while (end > 0) {
            swap(array, end--, 0);
            sift(array, 0);
        }
    }

    /**
     * Sift the number in the tree
     * @param array the array to be sorted
     * @param i the index of the number to be sifted
     */
    private void sift(ArrayList<Integer> array, int i) {
        // Calculate the index of left child and assume it is the max of the children
        int max = 2 * i + 1;

        // Return when current node doesn't have child
        if(max > end) return;

        // If right child doesn't exist, the current max would be the larger child
        // else if current max child is less than the right child, use right child as max
        if(max < end && comp.compare(array.get(max), array.get(max + 1)) < 0) max++;

        if(comp.compare(array.get(i), array.get(max)) < 0) {
            // Swap if super node is less than the max of the child
            swap(array, i, max);

            // Continue sift in the next level
            sift(array, max);
        }
    }

    /**
     * Sift the number in the tree
     * @param array the array to be sorted
     * @param i the index of the number to be sifted
     */
    private void sift(Integer[] array, int i) {
        // Calculate the index of left child and assume it is the max of the children
        int max = 2 * i + 1;

        // Return when current node doesn't have child
        if(max > end) return;

        // If right child doesn't exist, the current max would be the larger child
        // else if current max child is less than the right child, use right child as max
        if(max < end && comp.compare(array[max], array[max + 1]) < 0) max++;

        if(comp.compare(array[i], array[max]) < 0) {
            // Swap if super node is less than the max of the child
            swap(array, i, max);

            // Continue sift in the next level
            sift(array, max);
        }
    }

    /**
     * Main method
     * @param args arguments
     * @throws Throwable
     */
    public static void main(String[] args) throws Throwable {
        // Invoke the standard test framework
        standardTest(readArray(System.in), new HeapSort(new RecordComparator()));
    }
}
