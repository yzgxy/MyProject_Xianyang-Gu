/*
This experiment is used to compare the actual performance and
theoretical performance of Merge Sort when sorting a single stock data.
Corresponding to section 4.2.1 of the dissertation
 */

import sortingalgorithms.MergeSort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Experiment3 {
    private String datasetFolder;
    private MergeSort mergeSorter;

    public Experiment3(String datasetFolder) {
        this.datasetFolder = datasetFolder;
        this.mergeSorter = new MergeSort();
        runExperiment();
    }

    public void runExperiment() {
        File folder = new File(datasetFolder);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv"));
        if (files == null) return;

        List<ExperimentResult3> results = new ArrayList<>();

        // Iterate over the dataset
        for (int i = 1; i <= files.length; i++) {
            int[] volumes = loadVolumesFromFile(files[i - 1]);

            long startTime = System.nanoTime();
            // sorting
            mergeSorter.sort(volumes);
            long endTime = System.nanoTime();

            int actualComparisons = mergeSorter.getComparisonCount(); // actual performance
            double theoreticalComparisons = calculateTheoreticalComparisons(volumes.length); // theoretical expectations
            double difference = actualComparisons - theoreticalComparisons;
            double duration = (endTime - startTime) / 1000000.0;  // sorting time, the unit is milliseconds

            results.add(new ExperimentResult3(i, volumes.length, actualComparisons, theoreticalComparisons, difference, duration));

            // reset counter
            mergeSorter.resetComparisonCount();
        }

        writeResultsToCSV(results, "Experiment3_Results.csv");
    }

    // data processing
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

    // calculate theoretical performance
    private double calculateTheoreticalComparisons(int n) {
        if (n <= 1) return 0;
        return n * Math.log(n) / Math.log(2) - (n - 1);
    }

    // Save the results for visualization
    private void writeResultsToCSV(List<ExperimentResult3> results, String outputFileName) {
        try (FileWriter writer = new FileWriter(outputFileName)) {
            writer.append("Stock Count,Total Volume,Actual Comparisons,Theoretical Comparisons,Difference,Time (ms)\n");
            for (ExperimentResult3 result : results) {
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
        new Experiment3("./Dataset");  // Relative path to the dataset
    }
}

// Define the format of the results
class ExperimentResult3 {
    private int stockCount;
    private int totalVolume;
    private int actualComparisons;
    private double theoreticalComparisons;
    private double difference;
    private double time;

    public ExperimentResult3(int stockCount, int totalVolume, int actualComparisons, double theoreticalComparisons, double difference, double time) {
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

