package sortingalgorithms;

/*
Interface implemented by Heap Sort, Tim Sort, Intro Sort,
allowing them to use methods that calculate theoretical performance
 */
public interface SortingAlgorithm {
    void sort(int[] arr); // sorting
    int getComparisonCount(); // return number of comparison
    void resetComparisonCount(); // reset counter
}

