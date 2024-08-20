/*
This experiment is used to compare the actual performance and
theoretical performance of Heap Sort when sorting single stock data.
Corresponding to section 4.3.1 of the dissertation
 */

import sortingalgorithms.HeapSort;
import sortingalgorithms.SortingAlgorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Experiment5 {
    private SortingAlgorithm sortingAlgorithm;
    private String datasetFolder;

    public Experiment5(SortingAlgorithm sortingAlgorithm, String datasetFolder) {
        this.sortingAlgorithm = sortingAlgorithm;
        this.datasetFolder = datasetFolder;
        runExperiment();
    }

    public void runExperiment() {
        File folder = new File(datasetFolder);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv"));
        if (files == null) {
            System.out.println("No CSV files found in the specified directory.");
            return;
        }

        List<ExperimentResult5> results = new ArrayList<>();

        for (File file : files) {
            int[] data = loadVolumesFromFile(file);
            if (data.length == 0) continue;  // Skip empty files

            // Estimate theoretical comparisons
            long estimatedTheoreticalComparisons = TheoreticalComparison.estimateComparisons(sortingAlgorithm, data.length);

            // reset counter
            sortingAlgorithm.resetComparisonCount();
            // Sort the actual data and compare
            sortingAlgorithm.sort(data);
            int actualComparisons = sortingAlgorithm.getComparisonCount();

            results.add(new ExperimentResult5(file.getName(), data.length, actualComparisons, estimatedTheoreticalComparisons));
        }

        writeResultsToCSV(results, "Experiment5_Results.csv");
    }

    // Data processing
    private int[] loadVolumesFromFile(File file) {
        List<Integer> volumesList = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                if (line.length >= 6) {  // get volume
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
    private void writeResultsToCSV(List<ExperimentResult5> results, String outputFileName) {
        try (FileWriter writer = new FileWriter(outputFileName)) {
            writer.append("File Name,Array Size,Actual Comparisons,Estimated Theoretical Comparisons\n");
            for (ExperimentResult5 result : results) {
                writer.append(result.getFileName()).append(',')
                        .append(String.valueOf(result.getArraySize())).append(',')
                        .append(String.valueOf(result.getActualComparisons())).append(',')
                        .append(String.valueOf(result.getEstimatedTheoreticalComparisons())).append('\n');
            }
            System.out.println("Results written to " + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SortingAlgorithm heapSort = new HeapSort();
        String datasetFolder = "./Dataset";  // Relative path to the dataset
        new Experiment5(heapSort, datasetFolder);
    }
}

// Define the format of the results
class ExperimentResult5 {
    private String fileName;
    private int arraySize;
    private int actualComparisons;
    private long estimatedTheoreticalComparisons;

    public ExperimentResult5(String fileName, int arraySize, int actualComparisons, long estimatedTheoreticalComparisons) {
        this.fileName = fileName;
        this.arraySize = arraySize;
        this.actualComparisons = actualComparisons;
        this.estimatedTheoreticalComparisons = estimatedTheoreticalComparisons;
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
}
