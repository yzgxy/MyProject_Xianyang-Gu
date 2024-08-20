/*
This experiment is used to compare the actual performance and
theoretical performance of Quick Sort when sorting multiple stocks data.
Corresponding to section 4.1.2 of the dissertation
 */

import sortingalgorithms.QuickSort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Experiment2 {
    private String datasetFolder;
    private QuickSort quickSorter;

    public Experiment2(String datasetFolder) {
        this.datasetFolder = datasetFolder;
        this.quickSorter = new QuickSort();
        runExperiment();
    }

    public void runExperiment() {
        File folder = new File(datasetFolder);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv"));
        if (files == null) return;

        List<ExperimentResult2> results = new ArrayList<>();

        int[] combinedVolumes = new int[0];

        // Iterate over the first 50 files in the dataset
        for (int i = 1; i <= 50 && i <= files.length; i++) {
            int[] volumes = loadVolumesFromFile(files[i - 1]);
            combinedVolumes = combineArrays(combinedVolumes, volumes);

            long startTime = System.nanoTime();
            // sorting
            quickSorter.quickSort(combinedVolumes);
            long endTime = System.nanoTime();

            int actualComparisons = quickSorter.getComparisonCount(); // actual performance
            double theoreticalComparisons = calculateTheoreticalComparisons(combinedVolumes.length); // theoretical expectations
            double difference = actualComparisons - theoreticalComparisons;
            double duration = (endTime - startTime) / 1000000.0;  // sorting time, the unit is milliseconds

            results.add(new ExperimentResult2(i, combinedVolumes.length, actualComparisons, theoreticalComparisons, difference, duration));

            // reset counter
            quickSorter = new QuickSort();
        }

        writeResultsToCSV(results, "Experiment2_Results.csv");
    }

    //data processing
    private int[] loadVolumesFromFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            List<Integer> volumesList = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                if (line.length >= 6) {
                    try {
                        volumesList.add(Integer.parseInt(line[5]));  // get volume
                    } catch (NumberFormatException e) {
                        // Skip invalid rows
                    }
                }
            }
            return volumesList.stream().mapToInt(i -> i).toArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new int[0];
        }
    }

    // Merge two arrays
    private int[] combineArrays(int[] arr1, int[] arr2) {
        int[] combined = new int[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, combined, 0, arr1.length);
        System.arraycopy(arr2, 0, combined, arr1.length, arr2.length);
        return combined;
    }

    // calculate theoretical performance due to Wang Xiang's conclusion
    private double calculateTheoreticalComparisons(int n) {
        if (n <= 1) return 0;
        return 1.386 * (n + 1) * Math.log(n) / Math.log(2) - 2.846 * n + 1.154;
    }

    // Save the results for visualization
    private void writeResultsToCSV(List<ExperimentResult2> results, String outputFileName) {
        try (FileWriter writer = new FileWriter(outputFileName)) {
            writer.append("Stock Count,Total Volume,Actual Comparisons,Theoretical Comparisons,Difference,Time (ms)\n");
            for (ExperimentResult2 result : results) {
                writer.append(String.valueOf(result.getStockCount())).append(',')
                        .append(String.valueOf(result.getTotalVolume())).append(',')
                        .append(String.valueOf(result.getActualComparisons())).append(',')
                        .append(String.valueOf(result.getTheoreticalComparisons())).append(',')
                        .append(String.valueOf(result.getDifference())).append(',')
                        .append(String.valueOf(result.getTime())).append('\n');
            }
            System.out.println("Results written to " + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Experiment2("./Dataset");  // Relative path to the dataset
    }
}

// Define the format of the results
class ExperimentResult2 {
    private int stockCount;
    private int totalVolume;
    private int actualComparisons;
    private double theoreticalComparisons;
    private double difference;
    private double time;

    public ExperimentResult2(int stockCount, int totalVolume, int actualComparisons, double theoreticalComparisons, double difference, double time) {
        this.stockCount = stockCount;
        this.totalVolume = totalVolume;
        this.actualComparisons = actualComparisons;
        this.theoreticalComparisons = theoreticalComparisons;
        this.difference = difference;
        this.time = time;
    }

    public int getStockCount() {
        return stockCount;
    }

    public int getTotalVolume() {
        return totalVolume;
    }

    public int getActualComparisons() {
        return actualComparisons;
    }

    public double getTheoreticalComparisons() {
        return theoreticalComparisons;
    }

    public double getDifference() {
        return difference;
    }

    public double getTime() {
        return time;
    }
}


