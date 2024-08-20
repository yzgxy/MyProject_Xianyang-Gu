/*
This experiment is used to compare the actual performance and
theoretical performance of optimized Quick Sort when sorting single stock data.
Corresponding to section 5.2 of the dissertation
 */

import sortingalgorithms.QuickSort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Experiment12 {
    private String datasetFolder;
    private QuickSort quickSorter;

    public Experiment12(String datasetFolder) {
        this.datasetFolder = datasetFolder;
        this.quickSorter = new QuickSort();
        runExperiment();
    }

    public void runExperiment() {
        File folder = new File(datasetFolder);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv"));
        if (files == null) return;

        List<ExperimentResult12> results = new ArrayList<>();

        // Iterate over the dataset
        for (File file : files) {
            System.out.println("Processing file: " + file.getName());
            int[] volumes = loadVolumesFromFile(file);
            int n = volumes.length; // for calculating theoretical performance

            // sort
            quickSorter.quickSort(volumes);

            int actualComparisons = quickSorter.getComparisonCount(); // actual performance
            double theoreticalComparisons = calculateTheoreticalComparisons(n); // theoretical expectation
            double difference = actualComparisons - theoreticalComparisons;

            results.add(new ExperimentResult12(file.getName(), actualComparisons, theoreticalComparisons, difference));

            // Reset the QuickSort object for the next iteration
            quickSorter = new QuickSort();
        }

        writeResultsToCSV(results, "Experiment12_Results.csv");
    }

    // Data processing
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

    // calculate theoretical performance due to Wang Xiang's conclusion
    private double calculateTheoreticalComparisons(int n) {
        if (n <= 1) return 0;
        return 1.386 * (n + 1) * Math.log(n) / Math.log(2) - 2.846 * n + 1.154;
    }
    // Save the results for visualization
    private void writeResultsToCSV(List<ExperimentResult12> results, String outputFileName) {
        try (FileWriter writer = new FileWriter(outputFileName)) {
            writer.append("Stock,Actual Comparisons,Theoretical Comparisons,Difference\n");
            for (ExperimentResult12 result : results) {
                writer.append(result.getStock()).append(',')
                        .append(String.valueOf(result.getActualComparisons())).append(',')
                        .append(String.valueOf(result.getTheoreticalComparisons())).append(',')
                        .append(String.valueOf(result.getDifference())).append('\n');
            }
            System.out.println("Results written to " + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Main function, run experiment1
    public static void main(String[] args) {
        new Experiment12("./Dataset"); // Relative path to the dataset
    }
}

// Define the format of the results
class ExperimentResult12 {
    private String stock;
    private int actualComparisons;
    private double theoreticalComparisons;
    private double difference;

    public ExperimentResult12(String stock, int actualComparisons, double theoreticalComparisons, double difference) {
        this.stock = stock;
        this.actualComparisons = actualComparisons;
        this.theoreticalComparisons = theoreticalComparisons;
        this.difference = difference;
    }

    public String getStock() {
        return stock;
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
}

