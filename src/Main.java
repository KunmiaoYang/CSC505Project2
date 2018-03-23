import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class Main {
    private static final int[] ARRAY_SIZES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//    private static final int[] ARRAY_SIZES = {100000, 200000, 400000};
    private static final int[] ARRAY_SIZES_FOR_INSERTION_SORT = {1000, 2000, 4000, 8000, 16000, 32000, 64000};
    private static final int[] SAMPLE_SIZES_FOR_INSERTION_SORT = {100, 100, 10, 10, 10, 10, 10};
    private static final int[] SAMPLE_SIZES_FOR_SORTED_INSERTION_SORT = {1024, 512, 256, 128, 64, 32, 16};
    private static final int SAMPLE_SIZE = 100;
    private static final String SEPARATOR_MAIN = " -------------------------------------- ";
    private static final String SEPARATOR_SUB = " ************************************* ";

    public static void testArrayCorrection(Sort sort) throws IOException {
        ArrayFactory sortedFactory = ArrayFactory.getSortedFactory(),
                reverseFactory = ArrayFactory.getReverseFactory(),
                randomFactory = ArrayFactory.getRandomFactory();
        Integer[] array = {5,1,2,4,3,6,9,7,8};
        System.out.println("Before allocate Memory used: " + Runtime.getRuntime().totalMemory() / 1024 / 1024);
        array = randomFactory.createArray(5);
//        System.out.println("Initial array = " + Arrays.toString(array));
//        RecordComparator comp = sort.comp;
        Sort.standardTest(array, sort);
        System.out.println("After sort Memory used: " + Runtime.getRuntime().totalMemory() / 1024 / 1024);

//        System.out.println("Result = " + Arrays.toString(array));
//        System.out.println("Comparison = " + comp.getCount());
    }

    protected static void testAllArray(Sort[] sorts, int[] arraySizes, ArrayFactory arrayFactory, PrintStream out, String title) throws Throwable {
        // Initialize summaries
        ArrayList<Map<String, List<Number>>> summaries = new ArrayList<>(sorts.length);
        for(Sort sort: sorts) {
            Map<String, List<Number>> summary = new HashMap<>();
            summary.put(Sort.KEY_TIME, new ArrayList<>(arraySizes.length));
            summary.put(Sort.KEY_COMPARISON, new ArrayList<>(arraySizes.length));
            summaries.add(summary);
        }

        System.out.println(title);
        out.println(title);
        for(int arraySize: arraySizes) {
            // Log on console
            System.out.print("Array Size = " + arraySize + "\tO");

            // Initialize reports data structure
            ArrayList<Map<String, List<Number>>> reports = new ArrayList<>(sorts.length);
            for(int i = 0; i < sorts.length; i++) {
                Map<String, List<Number>> report = new HashMap<>();
                report.put(Sort.KEY_TIME, new ArrayList<>(SAMPLE_SIZE));
                report.put(Sort.KEY_COMPARISON, new ArrayList<>(SAMPLE_SIZE));
                reports.add(report);
            }

            // Test the same sample on each sort algorithm
            for (int i = 0; i < SAMPLE_SIZE; i++) {
                Integer[][] arrays = new Integer[sorts.length][];
                arrays[0] = arrayFactory.createArray(arraySize);
                for(int j = 1; j < sorts.length; j++) arrays[j] = Arrays.copyOf(arrays[0], arraySize);
                for(int j = 0; j < sorts.length; j++) {
                    Map<String, Number> report = sorts[j].testSort(arrays[j]);
                    reports.get(j).get(Sort.KEY_TIME).add(report.get(Sort.KEY_TIME));
                    reports.get(j).get(Sort.KEY_COMPARISON).add(report.get(Sort.KEY_COMPARISON));
                    System.out.print('.');
                }
                System.out.print('X');
            }
            System.out.println();

            // Output report
            out.println(SEPARATOR_SUB + "Array Size = " + arraySize + SEPARATOR_SUB);
            for(int i = 0; i < sorts.length; i++) {
                Map<String, List<Number>> report = reports.get(i);
                List<Number> times = report.get(Sort.KEY_TIME), comparisons = report.get(Sort.KEY_COMPARISON);
                long totalTime = 0, totalComparison = 0;
                for(Number comparison: comparisons) totalComparison += (long)comparison;
                for(Number time: times) totalTime += (long)time;
                double avgTime = totalTime/(double)SAMPLE_SIZE, avgComparison = totalComparison/(double)SAMPLE_SIZE;
                summaries.get(i).get(Sort.KEY_TIME).add(avgTime);
                summaries.get(i).get(Sort.KEY_COMPARISON).add(avgComparison);

                out.println(sorts[i].getClass().getName() + " report:");
                out.println("\tTimes=\t" + times);
                out.println("\tAverageTime=\t" + avgTime);
                out.println("\tComparisons=\t" + comparisons);
                out.println("\tAverageComparison=\t" + avgComparison);
            }
            out.flush();
        }

        // Output Summary
        out.println(SEPARATOR_SUB + "Summary " + SEPARATOR_SUB);
        out.println("\tArraySize=\t" + Arrays.toString(arraySizes));
        for(int i = 0; i < sorts.length; i++) {
            Map<String, List<Number>> summary = summaries.get(i);
            out.println(sorts[i].getClass().getName() + " summary:");
            out.println("\tAverageTime=\t" + summary.get(Sort.KEY_TIME));
            out.println("\tAverageComparison=\t" + summary.get(Sort.KEY_COMPARISON));
        }
        out.flush();
    }

    protected static ArrayList<Map<String, List<Number>>> testAllArrayTogether(Sort[] sorts, int[] arraySizes, int[] sampleSizes, ArrayFactory arrayFactory, PrintStream out, String title) throws Throwable {
        // Initialize summaries
        ArrayList<Map<String, List<Number>>> summaries = new ArrayList<>(sorts.length);
        for(Sort sort: sorts) {
            Map<String, List<Number>> summary = new HashMap<>();
            summary.put(Sort.KEY_TIME, new ArrayList<>(arraySizes.length));
            summary.put(Sort.KEY_COMPARISON, new ArrayList<>(arraySizes.length));
            summaries.add(summary);
        }

        System.out.println(title);
        out.println(title);
        for(int k = 0; k < arraySizes.length; k++) {
            int arraySize = arraySizes[k], sampleSize = sampleSizes[k];
            // Log on console
            System.out.print("Array Size = " + arraySize + "\tO");

            // Test the same sample on each sort algorithm
            Integer[][] arrays_prototype = new Integer[sampleSize][];
            for(int i = 0; i < sampleSize; i++) arrays_prototype[i] = arrayFactory.createArray(arraySize);
            for(int j = 0; j < sorts.length; j++) {
                Integer[][] arrays = new Integer[sampleSize][];
                for (int i = 0; i < sampleSize; i++) arrays[i] = Arrays.copyOf(arrays_prototype[i], arraySize);
                long startTime, endTime;
                sorts[j].comp.setCount(0);
                startTime= System.currentTimeMillis();
                for(int i = 0; i < sampleSize; i++) {
                    sorts[j].sort(arrays[i]);
                }
                endTime= System.currentTimeMillis();
                summaries.get(j).get(Sort.KEY_TIME).add((endTime - startTime) / (double) sampleSize);
                summaries.get(j).get(Sort.KEY_COMPARISON).add(sorts[j].comp.getCount() / (double) sampleSize);
                System.out.print('X');
            }
            System.out.println();
        }

        // Output Summary
        out.println(SEPARATOR_SUB + "Summary " + SEPARATOR_SUB);
        out.println("\tArraySize=\t" + Arrays.toString(arraySizes));
        for(int i = 0; i < sorts.length; i++) {
            Map<String, List<Number>> summary = summaries.get(i);
            out.println(sorts[i].getClass().getName() + " summary:");
            out.println("\tAverageTime=\t" + summary.get(Sort.KEY_TIME));
            out.println("\tAverageComparison=\t" + summary.get(Sort.KEY_COMPARISON));
        }
        out.flush();

        return summaries;
    }

    static void testQuickSelect(QuickSelect quickSelect) {
//        Integer[] A = {2,5,10,5,12,2,11};
        Integer[] A = {30,27,2,17,45,36,30,29};
        quickSelect.comp.setCount(0);
        int median = quickSelect.findMedian(A);
        System.out.println("median = " + median);
        System.out.println("comparisons = " + quickSelect.comp.getCount());
    }

    static void testMedianOutperform(RecordComparator comp, int[] arraySize, ArrayFactory arrayFactory, PrintStream out, String title) {
        Median[] medians = new Median[2];
        medians[0] = new QuickSelect(comp);
        medians[1] = new JavaSort(comp);

        double[][] times = new double[2][];
        double[][] comparisons = new double[2][];
        for(int i = 0; i < times.length; i++) times[i] = new double[arraySize.length];
        for(int i = 0; i < times.length; i++) comparisons[i] = new double[arraySize.length];

        out.println("%%" + SEPARATOR_MAIN + title + SEPARATOR_MAIN);
        out.print("sizes = ");
        out.println(Arrays.toString(arraySize) + ";");

        for (int j = 0; j<2; j++) {
            for(int k = 0; k < arraySize.length; k++) {
                Integer[][] arrays = new Integer[SAMPLE_SIZE][];
                for(int i = 0; i < SAMPLE_SIZE; i++) arrays[i] = arrayFactory.createArray(arraySize[k]);
                long startTime, endTime;
                comp.setCount(0);
                startTime= System.currentTimeMillis();
                for(int i = 0; i < SAMPLE_SIZE; i++) {
                    medians[j].findMedian(arrays[i]);
                }
                endTime= System.currentTimeMillis();
                times[j][k] = (endTime-startTime)/(double) SAMPLE_SIZE;
                comparisons[j][k] = comp.getCount()/(double) SAMPLE_SIZE;
            }

            // Output
            String className = medians[j].getClass().getName();
            out.println("%" + SEPARATOR_SUB + className + SEPARATOR_SUB);
            out.print(className + "_times = ");
            out.println(Arrays.toString(times[j]) + ";");
            out.print(className + "_comparisons = ");
            out.println(Arrays.toString(comparisons[j]) + ";");
        }

        out.println("%%" + SEPARATOR_MAIN + "Summary" + SEPARATOR_MAIN);
        out.print("data = [sizes; ");
        for(Median median: medians) out.print(median.getClass().getName() + "_times; ");
        for(Median median: medians) out.print(median.getClass().getName() + "_comparisons; ");
        out.print("];");
    }

    public static void main(String[] args) throws Throwable {
        PrintStream ps = new PrintStream(new BufferedOutputStream(new FileOutputStream("objective1.m")));
        RecordComparator comp = new RecordComparator();
        Sort insertSort = new InsertionSort(comp), mergeSort = new MergeSort(comp),
                heapSort = new HeapSort(comp), javaSort = new JavaSort(comp),
                sortGroup1[] = {mergeSort, heapSort, javaSort},
                sortGroup2[] = {mergeSort, insertSort},
                sortGroup3[] = {insertSort},
                tempGroup[] = {mergeSort};
        QuickSelect quickSelect = new QuickSelect(comp);

        //region program 1
        //        testArrayCorrection(new MergeSort(comp));
//        testAllArray(sortGroup1, ARRAY_SIZES, ArrayFactory.getRandomFactory(), ps, SEPARATOR_MAIN + "Random array test" + SEPARATOR_MAIN);
//        testAllArray(sortGroup1, ARRAY_SIZES, ArrayFactory.getSortedFactory(), ps, SEPARATOR_MAIN + "Sorted array test" + SEPARATOR_MAIN);
//        testAllArray(sortGroup1, ARRAY_SIZES, ArrayFactory.getReverseFactory(), ps, SEPARATOR_MAIN + "Reverse array test" + SEPARATOR_MAIN);
//        testAllArrayTogether(sortGroup3, ARRAY_SIZES_FOR_INSERTION_SORT, SAMPLE_SIZES_FOR_INSERTION_SORT, ArrayFactory.getRandomFactory(), ps, SEPARATOR_MAIN + "Random small array test" + SEPARATOR_MAIN);
//        testAllArrayTogether(sortGroup3, ARRAY_SIZES_FOR_INSERTION_SORT, SAMPLE_SIZES_FOR_SORTED_INSERTION_SORT, ArrayFactory.getSortedFactory(), ps, SEPARATOR_MAIN + "Sorted small array test" + SEPARATOR_MAIN);
//        testAllArrayTogether(sortGroup3, ARRAY_SIZES_FOR_INSERTION_SORT, SAMPLE_SIZES_FOR_INSERTION_SORT, ArrayFactory.getReverseFactory(), ps, SEPARATOR_MAIN + "Reverse small array test" + SEPARATOR_MAIN);
        //endregion

//        testQuickSelect(quickSelect);
        testMedianOutperform(comp, ARRAY_SIZES, ArrayFactory.getRandomFactory(), ps, "Objective 1");
        ps.close();
    }
}
