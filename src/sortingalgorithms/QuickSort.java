package sortingalgorithms;

// the implementation of quick sort in this project
public class QuickSort {
    private int comparisonCount; // counter

    public QuickSort() {
        this.comparisonCount = 0;
    }

    public void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            // recursively process the left and right parts
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    // Functions for array partitioning
    private int partition(int[] arr, int low, int high) {
         int pivot = arr[high];
        // Select the last element as the pivot
        //use this pivot selection strategy for experiment1 and experiment2


//        use this pivot selection strategy for experiment 12
//        int mid = low + (high - low) / 2;
//        int pivot = medianOfThree(arr, low, mid, high);
        int i = low - 1; //Initializes the pointer of the element smaller than the pivot

        /*
        Iterate over the array, if an element smaller than the pivot is found,
        swap elements and increment the pointer of the element smaller than the pivot
         */
        for (int j = low; j < high; j++) {
            comparisonCount++;  //
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high); // place the pivot
        return i + 1;   // return the pivot index
    }

    // Function for swapping elements
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Function to perform the median-of-three pivot selection
    private int medianOfThree(int[] arr, int low, int mid, int high) {
        if (arr[low] > arr[mid]) {
            swap(arr, low, mid);
        }
        if (arr[low] > arr[high]) {
            swap(arr, low, high);
        }
        if (arr[mid] > arr[high]) {
            swap(arr, mid, high);
        }
        // Swap the median value with the last element to use as pivot
        swap(arr, mid, high);
        return arr[high];
    }

    // return comparison numbers
    public int getComparisonCount() {
        return comparisonCount;
    }
}
