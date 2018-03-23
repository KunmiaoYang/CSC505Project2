/**
 * This interface helps in my testing framework
 * Created by Kunmiao Yang on 3/22/2018.
 */
public interface Median {
    /**
     * Find the median of an array using Quick Select.
     * The median here is defined as the element at (n-1)/2 position
     * according to the requirement in program 2
     * @param A the array we need to find median in
     * @return the median in array A
     */
    int findMedian(Integer[] A);
}
