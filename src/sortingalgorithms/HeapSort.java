package sortingalgorithms;

// the implementation of heap sort in this project
public class HeapSort implements SortingAlgorithm {
    private int comparisonCount;

    public HeapSort() {
        this.comparisonCount = 0;
    }

    @Override
    public void sort(int[] arr) {
        int n = arr.length;

        // Build a max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // extract elements and place them
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    // Ensure the subtree rooted at index i maintains the max heap property
    private void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        comparisonCount++;
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        comparisonCount++;
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    // swap elements
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
        this.comparisonCount = 0;
    }
}

