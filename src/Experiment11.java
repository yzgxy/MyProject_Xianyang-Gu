/*
This experiment aims at observing how the run size affects Tim Sort's actual performance
Experiment by changing the RUN parameters in the TimSort class
Corresponding to section 5.1 of the dissertation
 */

import sortingalgorithms.TimSort;
import sortingalgorithms.SortingAlgorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Experiment11 {

    private SortingAlgorithm sortingAlgorithm;
    private String datasetFolder;

    public Experiment11(SortingAlgorithm sortingAlgorithm, String datasetFolder) {
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

        // load all data
        int[] combinedVolumes = new int[0];
        for (File file : files) {
            int[] volumes = loadVolumesFromFile(file);
            combinedVolumes = combineArrays(combinedVolumes, volumes);
        }

        // sorting
        sortingAlgorithm.resetComparisonCount();
        sortingAlgorithm.sort(combinedVolumes);

        // show the number of comparisons
        int actualComparisons = sortingAlgorithm.getComparisonCount();
        System.out.println("Total Comparisons: " + actualComparisons);
    }

    // data process
    private int[] loadVolumesFromFile(File file) {
        List<Integer> volumesList = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                if (line.length >= 6) {
                    try {
                        volumesList.add(Integer.parseInt(line[5]));
                    } catch (NumberFormatException e) {
                        // skip invalid lines
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return volumesList.stream().mapToInt(i -> i).toArray();
    }

    // merge two arrays
    private int[] combineArrays(int[] arr1, int[] arr2) {
        int[] combined = new int[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, combined, 0, arr1.length);
        System.arraycopy(arr2, 0, combined, arr1.length, arr2.length);
        return combined;
    }

    public static void main(String[] args) {
        SortingAlgorithm timSort = new TimSort();
        String datasetFolder = "./Dataset";
        new Experiment11(timSort, datasetFolder);
    }
}

