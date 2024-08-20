package sortingalgorithms;

import java.util.Arrays;

// the implementation of intro sort in this project
public class IntroSort implements SortingAlgorithm {
    private int comparisonCount;

    public IntroSort() {
        this.comparisonCount = 0;
    }

    @Override
    public void sort(int[] arr) {
        comparisonCount = 0;
        // Recursion depth when switching to heap sort
        int depthLimit = 2 * (int) (Math.log(arr.length) / Math.log(2));
        introSort(arr, 0, arr.length - 1, depthLimit);
    }

    private void introSort(int[] arr, int low, int high, int depthLimit) {
        int size = high - low + 1;
        // When the array size is less than 16
        // use insertion sort to process
        if (size < 16) {
            insertionSort(arr, low, high);
        } else if (depthLimit == 0) { // When the recursion depth exceeds the threshold
            heapSort(arr, low, high); // use heap sort to process
        } else {
            if (low < high) { // Otherwise, use quick sort recursively
                int pivot = partition(arr, low, high);
                introSort(arr, low, pivot - 1, depthLimit - 1);
                introSort(arr, pivot + 1, high, depthLimit - 1);
            }
        }
    }

    // Insertion sort function
    // Standard insertion sort implementation
    private void insertionSort(int[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= low && arr[j] > key) {
                comparisonCount++;
                arr[j + 1] = arr[j];
                j--;
            }
            comparisonCount++;
            arr[j + 1] = key;
        }
    }

    //heap sort function
    private void heapSort(int[] arr, int low, int high) {
        int size = high - low + 1;
        int[] temp = Arrays.copyOfRange(arr, low, high + 1);
        buildMaxHeap(temp, size);

        for (int i = size - 1; i >= 0; i--) {
            swap(temp, 0, i);
            maxHeapify(temp, i, 0);
        }

        System.arraycopy(temp, 0, arr, low, size);
    }

    //buildMaxHeap function for heap sort
    private void buildMaxHeap(int[] arr, int size) {
        for (int i = size / 2 - 1; i >= 0; i--) {
            maxHeapify(arr, size, i);
        }
    }

    // like the implementation of heap sort in this project
    private void maxHeapify(int[] arr, int size, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < size && arr[left] > arr[largest]) {
            comparisonCount++;
            largest = left;
        }
        if (right < size && arr[right] > arr[largest]) {
            comparisonCount++;
            largest = right;
        }
        if (largest != i) {
            swap(arr, i, largest);
            maxHeapify(arr, size, largest);
        }
    }

    // like the implementation of quick sort in this project
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            comparisonCount++;
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    // Function for swapping elements
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    @Override
    public int getComparisonCount() {
        return comparisonCount;
    }

    @Override
    public void resetComparisonCount() {
        comparisonCount = 0;
    }
}

