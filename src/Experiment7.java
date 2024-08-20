/*
This experiment is used to compare the actual performance and
theoretical performance of Tim Sort when sorting single stock data.
Corresponding to section 4.4.1 of the dissertation
 */

import sortingalgorithms.SortingAlgorithm;
import sortingalgorithms.TimSort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Experiment7 {
    private SortingAlgorithm sortingAlgorithm;
    private String datasetFolder;

    public Experiment7(SortingAlgorithm sortingAlgorithm, String datasetFolder) {
        this.sortingAlgorithm = sortingAlgorithm;
        this.datasetFolder = datasetFolder;
        runExperiment();
    }

    public void runExperiment() {
        File folder = new File(datasetFolder);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv"));
        if (files == null || files.length == 0) {
            System.out.println("No CSV files found in the specified directory.");
            return;
        }

        List<ExperimentResult7> results = new ArrayList<>();

        for (File file : files) {
            int[] data = loadVolumesFromFile(file);
            if (data.length == 0) continue;  // Skip empty files

            // Estimate theoretical comparisons
            long estimatedTheoreticalComparisons = TheoreticalComparison.estimateComparisons(sortingAlgorithm, data.length);

            // reset counter
            sortingAlgorithm.resetComparisonCount();
            // Sort the actual data and compare
            long startTime = System.nanoTime();
            sortingAlgorithm.sort(data);
            long endTime = System.nanoTime();
            double sortingTime = (endTime - startTime) / 1000000.0; // sorting time, the unit is milliseconds

            int actualComparisons = sortingAlgorithm.getComparisonCount();

            results.add(new ExperimentResult7(file.getName(), data.length, actualComparisons, estimatedTheoreticalComparisons, sortingTime));
        }

        writeResultsToCSV(results, "Experiment7_Results.csv");
    }

    // Data processing
    private int[] loadVolumesFromFile(File file) {
        List<Integer> volumesList = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                if (line.length >= 6) {  // the volume is in the 6th column
                    try {
                        volumesList.add(Integer.parseInt(line[5]));  // Volume is in the sixth column
                    } catch (NumberFormatException e) {
                        // Skip invalid lines
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return volumesList.stream().mapToInt(i -> i).toArray();
    }

    // Save the results for visualization
    private void writeResultsToCSV(List<ExperimentResult7> results, String outputFileName) {
        try (FileWriter writer = new FileWriter(outputFileName)) {
            writer.append("File Name,Array Size,Actual Comparisons,Estimated Theoretical Comparisons,Sorting Time (ms)\n");
            for (ExperimentResult7 result : results) {
                writer.append(result.getFileName()).append(',')
                        .append(String.valueOf(result.getArraySize())).append(',')
                        .append(String.valueOf(result.getActualComparisons())).append(',')
                        .append(String.valueOf(result.getEstimatedTheoreticalComparisons())).append(',')
                        .append(String.valueOf(result.getSortingTime())).append('\n');
            }
            System.out.println("Results written to " + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SortingAlgorithm timSort = new TimSort();
        String datasetFolder = "./Dataset";  // Relative path to the dataset
        new Experiment7(timSort, datasetFolder);
    }
}

// Define the format of the results
class ExperimentResult7 {
    private String fileName;
    private int arraySize;
    private int actualComparisons;
    private long estimatedTheoreticalComparisons;
    private double sortingTime;

    public ExperimentResult7(String fileName, int arraySize, int actualComparisons, long estimatedTheoreticalComparisons, double sortingTime) {
        this.fileName = fileName;
        this.arraySize = arraySize;
        this.actualComparisons = actualComparisons;
        this.estimatedTheoreticalComparisons = estimatedTheoreticalComparisons;
        this.sortingTime = sortingTime;
    }

    public String getFileName() {
        return fileName;
    }

    public int getArraySize() {
        return arraySize;
    }

    public int getActualComparisons() {
        return actualComparisons;
    }

    public long getEstimatedTheoreticalComparisons() {
        return estimatedTheoreticalComparisons;
    }

    public double getSortingTime() {
        return sortingTime;
    }
}

