package sortingalgorithms;

// the implementation of Tim sort in this project
public class TimSort implements SortingAlgorithm {
    private static final int RUN = 32;  // Size of the RUN
    private int comparisonCount;

    public TimSort() {
        this.comparisonCount = 0;
    }

    @Override
    public void sort(int[] arr) {
        comparisonCount = 0;

        int n = arr.length;
        // Sort individual subarrays of size RUN
        for (int i = 0; i < n; i += RUN) {
            // Process each run using insertion sort
            // When the size of the last run is not 32
            // Pass in the actual run boundary
            insertionSort(arr, i, Math.min((i + RUN - 1), (n - 1)));
        }

        // Start merging from size RUN
        for (int size = RUN; size < n; size = 2 * size) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1), (n - 1));

                // Merge subarrays
                if (mid < right) {
                    merge(arr, left, mid, right);
                }
            }
        }
    }

    // Insertion sort function
    // Standard insertion sort implementation
    private void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int temp = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > temp) {
                comparisonCount++;
                arr[j + 1] = arr[j];
                j--;
            }
            comparisonCount++;
            arr[j + 1] = temp;
        }
    }

    // merge sorted arrays, like the implementation in merge sort
    private void merge(int[] arr, int left, int mid, int right) {
        int len1 = mid - left + 1, len2 = right - mid;
        int[] leftArr = new int[len1];
        int[] rightArr = new int[len2];
        System.arraycopy(arr, left, leftArr, 0, len1);
        System.arraycopy(arr, mid + 1, rightArr, 0, len2);

        int i = 0, j = 0, k = left;

        while (i < len1 && j < len2) {
            comparisonCount++;
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }

        while (i < len1) {
            arr[k] = leftArr[i];
            k++;
            i++;
        }

        while (j < len2) {
            arr[k] = rightArr[j];
            k++;
            j++;
        }
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

