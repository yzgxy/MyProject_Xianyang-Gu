/*
This experiment is used to compare the actual performance and
theoretical performance of Intro Sort when sorting multiple stocks data.
Corresponding to section 4.5.2 of the dissertation
Since there are 4.2 million records in the dataset, the execution time is very long.
reduce the running time by modifying the TRIALS parameter in the TheoreticalComparison class
to observe the result of this code
 */

import sortingalgorithms.IntroSort;
import sortingalgorithms.SortingAlgorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Experiment10 {
    private SortingAlgorithm sortingAlgorithm;
    private String datasetFolder;

    public Experiment10(SortingAlgorithm sortingAlgorithm, String datasetFolder) {
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

        List<ExperimentResult10> results = new ArrayList<>();
        int[] combinedVolumes = new int[0];

        // Iterate over the dataset, incrementing the number of files from 1 to 503
        for (int i = 1; i <= files.length; i++) {
            int[] volumes = loadVolumesFromFile(files[i - 1]);
            combinedVolumes = combineArrays(combinedVolumes, volumes);

            // Estimate theoretical comparisons
            long estimatedTheoreticalComparisons = TheoreticalComparison.estimateComparisons(sortingAlgorithm, combinedVolumes.length);

            // reset counter
            sortingAlgorithm.resetComparisonCount();
            // Sort the combined data and compare
            long startTime = System.nanoTime();
            sortingAlgorithm.sort(combinedVolumes);
            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / 1000000.0;  // sorting time, the unit is milliseconds

            int actualComparisons = sortingAlgorithm.getComparisonCount();

            results.add(new ExperimentResult10(i, combinedVolumes.length, actualComparisons, estimatedTheoreticalComparisons, duration));
            System.out.println("Test " + i + " has finished "); // Check Progress

        }

        writeResultsToCSV(results, "Experiment10_Results.csv");
    }

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

    // Merge two arrays
    private int[] combineArrays(int[] arr1, int[] arr2) {
        int[] combined = new int[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, combined, 0, arr1.length);
        System.arraycopy(arr2, 0, combined, arr1.length, arr2.length);
        return combined;
    }

    // Save the results for visualization
    private void writeResultsToCSV(List<ExperimentResult10> results, String outputFileName) {
        try (FileWriter writer = new FileWriter(outputFileName)) {
            writer.append("File Count,Total Volume,Actual Comparisons,Estimated Theoretical Comparisons,Sorting Time (ms)\n");
            for (ExperimentResult10 result : results) {
                writer.append(String.valueOf(result.getFileCount())).append(',')
                        .append(String.valueOf(result.getTotalVolume())).append(',')
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
        SortingAlgorithm introSort = new IntroSort();
        String datasetFolder = "./Dataset";  // Relative path to the dataset
        new Experiment10(introSort, datasetFolder);
    }
}

// Define the format of the results
class ExperimentResult10 {
    private int fileCount;
    private int totalVolume;
    private int actualComparisons;
    private long estimatedTheoreticalComparisons;
    private double sortingTime;

    public ExperimentResult10(int fileCount, int totalVolume, int actualComparisons, long estimatedTheoreticalComparisons, double sortingTime) {
        this.fileCount = fileCount;
        this.totalVolume = totalVolume;
        this.actualComparisons = actualComparisons;
        this.estimatedTheoreticalComparisons = estimatedTheoreticalComparisons;
        this.sortingTime = sortingTime;
    }

    public int getFileCount() {
        return fileCount;
    }

    public int getTotalVolume() {
        return totalVolume;
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

