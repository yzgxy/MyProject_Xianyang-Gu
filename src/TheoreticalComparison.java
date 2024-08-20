/*
This class provides methods for calculating
the theoretical performance of an algorithm.

Generate one hundred random arrays of the same size as the array to be sorted,
and calculate the average number of comparisons required to sort them.
 */
import sortingalgorithms.SortingAlgorithm;

import java.util.Random;

public class TheoreticalComparison {
    private static final int TRIALS = 100; // the number of random arrays generated

    public static long estimateComparisons(SortingAlgorithm algorithm, int arraySize) {
        Random random = new Random();
        long totalComparisons = 0;

        for (int trial = 1; trial <= TRIALS; trial++) {
            int[] arr = generateRandomArray(arraySize, random); // generate random array
            algorithm.resetComparisonCount(); // reset counter
            algorithm.sort(arr); // sort array generated
            totalComparisons += algorithm.getComparisonCount(); // Accumulate comparison times
        }

        return totalComparisons / TRIALS; // return average comparison number
    }

    // Function to generate random array
    private static int[] generateRandomArray(int size, Random random) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(1000000000);  // Assuming trading volumes are within this range
        }
        return arr;
    }
}

