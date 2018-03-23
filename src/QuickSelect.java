import java.io.IOException;

/**
 * Quick Select algorithm
 * Created by Kunmiao Yang on 3/21/2018.
 */
public class QuickSelect implements Median {
    RecordComparator comp;                                // Comparator used in the algorithm

    /**
     * Initialize quick select with comparator
     * @param comp comparator used int the quick select
     */
    public QuickSelect(RecordComparator comp) {
        this.comp = comp;
    }

    /**
     * Return a key x such that i - p keys of A[p], ..., A[r]
     * are <= x and r - i are >= x
     * Called initially as select(A, 0, n-1, i)
     * This algorithm comes from <Introduction to algorithms> 3rd Edition Page 216
     * I added a pivot initialization due to the requirement of pivot selection
     * I also made some changes due to the 0-based index
     * @param A the array to be selected
     * @param p the first index of the subarray
     * @param r the last index of the subarray
     * @param i the rank
     * @return the i-th smallest number
     */
    public int select(Integer[] A, int p, int r, int i) {
        if(p == r) return A[p];                     // If only one element exists in the current subarray return it
        initPivot(A, p, r);                         // Initialize the pivot to the r position
        int q = partition(A, p, r);                 // Partition A[p], ..., A[r] in place and get the pivot index
        int k = q - p;                              // Get the 0-based pivot relative position in current subarray
        if(i == k) return A[q];                     // If pivot is just at the i-th position, return it
        if(i < k) return select(A, p, q - 1, i);    // If i-th position is before k, select i-th from the former subarray
        return select(A, q + 1, r, i - k - 1);      // If i-th position is after k, select i-th from the latter subarray
    }

    /**
     * Partition the array. Rearranges the subarray A[p ...r] in place
     * This algorithm comes from <Introduction to algorithms> 3rd Edition Page 117
     * @param A the array to be partitioned
     * @param p the first index of the subarray
     * @param r the last index of the subarray
     * @return the index of the pivot after rearrange
     */
    public int partition(Integer[] A, int p, int r) {
        int x = A[r];                       // The value of the pivot
        int i = p - 1;                      // Last index of the subarray before pivot, let's call it subarray s
        for(int j = p; j < r; j++)
            if(comp.compare(A[j],x) <= 0)   // If A[j] can be arranged before x
                Sort.swap(A, ++i, j);       // Swap A[j] to the last position of the subarray s
        Sort.swap(A, i + 1, r);             // Swap pivot to the position after the subarray s
        return i + 1;                       // Return the index right after the subarray s
    }

    /**
     * Initialize pivot according to the requirement in this program
     * If subarray has at least 9 elements, find the median of first,
     * middle and last elements and swap it to the last index of sub-
     * array. If subarray has less than 9 elements just use the last
     * element as pivot.
     * @param A the array to be initialized
     * @param p the first index of the subarray
     * @param r the last index of the subarray
     */
    void initPivot(Integer[] A, int p, int r) {
        if(r - p < 8) return;                                           // If subarray has less than 9 elements just use
                                                                        // the last element as pivot
        int mid = (r+p)/2;                                              // Get the middle index
        if(comp.compare(A[p], A[mid])<0){
            if(comp.compare(A[r], A[p])<0) Sort.swap(A, p, r);          // Swap p and r if p is median
            else if(comp.compare(A[mid], A[r])<0) Sort.swap(A, mid, r); // Swap mid and r if mid is median
        } else {
            if(comp.compare(A[r], A[mid])<0) Sort.swap(A, mid, r);      // Swap mid and r if mid is median
            else if(comp.compare(A[p], A[r])<0) Sort.swap(A, p, r);     // Swap p and r if p is median
        }
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
        return select(A, 0, A.length - 1, (A.length - 1)/2);    // Invoke Quick Select method to find median
    }


    /**
     * Main method
     * @param args arguments
     * @throws Throwable
     */
    public static void main(String[] args) throws IOException {
        Integer[] A = Sort.readArray(System.in);// Read A from standard input
        RecordComparator comp = new RecordComparator(); // Initialize comparator
        QuickSelect quickSelect = new QuickSelect(comp);// Initialize QuickSelect with comparator
        long startTime, endTime;                // Initialize the time variables
        comp.setCount(0);                       // Set the comparator counter to 0
        startTime= System.currentTimeMillis();  // Record start time
        int median = quickSelect.findMedian(A); // Sort array
        endTime= System.currentTimeMillis();    // Record end time

        // Output the statistics report to error output
        System.out.println("median," + median);
        System.out.println("runtime," + (endTime - startTime));
        System.out.println("comparisons," + comp.getCount());
    }
}
