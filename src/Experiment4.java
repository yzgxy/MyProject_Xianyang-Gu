/*
This experiment is used to observe how the running time changes
as the number of stocks increases when sorting multiple stocks data
Corresponding to section 4.2.2 of the dissertation
 */
import sortingalgorithms.MergeSort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Experiment4 {
    private String datasetFolder;
    private MergeSort mergeSorter;

    public Experiment4(String datasetFolder) {
        this.datasetFolder = datasetFolder;
        this.mergeSorter = new MergeSort();
        runExperiment();
    }

    public void runExperiment() {
        File folder = new File(datasetFolder);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv"));
        if (files == null) return;

        List<ExperimentResult4> results = new ArrayList<>();

        int[] combinedVolumes = new int[0];

        // Iterate over the dataset, incrementing the number of stocks from 1 to 503
        for (int i = 1; i <= files.length; i++) {
            int[] volumes = loadVolumesFromFile(files[i - 1]);
            combinedVolumes = combineArrays(combinedVolumes, volumes);

            long startTime = System.nanoTime();
            // sorting
            mergeSorter.sort(combinedVolumes);
            long endTime = System.nanoTime();
            double duration = (endTime - startTime) / 1000000.0;  // sorting time, the unit is milliseconds

            results.add(new ExperimentResult4(i, combinedVolumes.length, duration));

            mergeSorter.resetComparisonCount(); // reset counter
        }

        writeResultsToCSV(results, "Experiment4_Results.csv");
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

    // Merge two arrays
    private int[] combineArrays(int[] arr1, int[] arr2) {
        int[] combined = new int[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, combined, 0, arr1.length);
        System.arraycopy(arr2, 0, combined, arr1.length, arr2.length);
        return combined;
    }

    // Save the results for visualization
    private void writeResultsToCSV(List<ExperimentResult4> results, String outputFileName) {
        try (FileWriter writer = new FileWriter(outputFileName)) {
            writer.append("Stock Count,Total Volume,Time (ms)\n");
            for (ExperimentResult4 result : results) {
                writer.append(String.valueOf(result.getStockCount())).append(',')
                        .append(String.valueOf(result.getTotalVolume())).append(',')
                        .append(String.valueOf(result.getTime())).append('\n');
            }
            System.out.println("Results written to " + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Experiment4("./Dataset");  // Relative path to the dataset
    }
}

// Define the format of the results
class ExperimentResult4 {
    private int stockCount;
    private int totalVolume;
    private double time;

    public ExperimentResult4(int stockCount, int totalVolume, double time) {
        this.stockCount = stockCount;
        this.totalVolume = totalVolume;
        this.time = time;
    }

    public int getStockCount() {
        return stockCount;
    }

    public int getTotalVolume() {
        return totalVolume;
    }

    public double getTime() {
        return time;
    }
}


