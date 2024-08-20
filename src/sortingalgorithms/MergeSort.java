package sortingalgorithms;

// the implementation of merge sort in this project
public class MergeSort {
    private int comparisonCount; // counter

    public MergeSort() {
        this.comparisonCount = 0;
    }

    public void sort(int[] arr) {
        // When the array size is greater than 1
        // Split the array into two
        if (arr.length > 1) {
            int mid = arr.length / 2;

            int[] left = new int[mid];
            int[] right = new int[arr.length - mid];

            System.arraycopy(arr, 0, left, 0, mid);
            System.arraycopy(arr, mid, right, 0, arr.length - mid);
            // Recursively process the two parts separately
            sort(left);
            sort(right);
            // Merge sorted arrays
            merge(arr, left, right);
        }
    }

    // Function to merge sorted arrays
    private void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        // Iterate over two arrays, start the comparison from the first element of each array
        // Copy the smaller elements into a temporary array and shift the array pointer right
        // Until all elements in an array are copied to the temporary array
        while (i < left.length && j < right.length) {
            comparisonCount++;
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }
        // Copy the unprocessed elements to the temporary array
        while (i < left.length) {
            arr[k++] = left[i++];
        }

        while (j < right.length) {
            arr[k++] = right[j++];
        }
    }
    // get the number of comparison
    public int getComparisonCount() {
        return comparisonCount;
    }
    // reset counter
    public void resetComparisonCount() {
        this.comparisonCount = 0;
    }
}

