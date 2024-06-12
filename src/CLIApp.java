import java.util.Scanner;

public class CLIApp {
    private StatisticsManager statisticsManager;
    private Scanner scanner;

    public CLIApp() {
        statisticsManager = new StatisticsManager();
        scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("1. Add a new data set");
            System.out.println("2. Remove an existing data set");
            System.out.println("3. Add numbers to an existing data set");
            System.out.println("4. Perform statistical operations");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addNewDataSet();
                    break;
                case 2:
                    removeDataSet();
                    break;
                case 3:
                    addNumbersToDataSet();
                    break;
                case 4:
                    performStatistics();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addNewDataSet() {
        DataSet dataSet = new DataSet();
        statisticsManager.addDataSet(dataSet);
        System.out.println("A new data set has been added.");
        addNumbersToDataSet(dataSet);
    }

    private void removeDataSet() {
        System.out.println("Select a data set to remove:");
        for (int i = 0; i < statisticsManager.getDataSets().size(); i++) {
            System.out.println((i + 1) + ". Data set " + (i + 1));
        }
        int choice = scanner.nextInt() - 1;
        if (choice >= 0 && choice < statisticsManager.getDataSets().size()) {
            statisticsManager.removeDataSet(statisticsManager.getDataSets().get(choice));
            System.out.println("The data set has been removed.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void addNumbersToDataSet() {
        System.out.println("Select a data set to add numbers to:");
        for (int i = 0; i < statisticsManager.getDataSets().size(); i++) {
            System.out.println((i + 1) + ". Data set " + (i + 1));
        }
        int choice = scanner.nextInt() - 1;
        if (choice >= 0 && choice < statisticsManager.getDataSets().size()) {
            DataSet selectedDataSet = statisticsManager.getDataSets().get(choice);
            addNumbersToDataSet(selectedDataSet);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void addNumbersToDataSet(DataSet dataSet) {
        System.out.println("Enter numbers to add to the data set (enter 'done' to finish):");
        while (true) {
            String input = scanner.next();
            if (input.equalsIgnoreCase("done")) {
                break;
            }
            try {
                double number = Double.parseDouble(input);
                dataSet.addNumber(number);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid number.");
            }
        }
    }

    private void performStatistics() {
        System.out.println("Select a data set to perform statistical operations:");
        for (int i = 0; i < statisticsManager.getDataSets().size(); i++) {
            System.out.println((i + 1) + ". Data set " + (i + 1));
        }
        int choice = scanner.nextInt() - 1;
        if (choice >= 0 && choice < statisticsManager.getDataSets().size()) {
            DataSet selectedDataSet = statisticsManager.getDataSets().get(choice);
            if (selectedDataSet.getNumbers().isEmpty()) {
                System.out.println("Data set is empty. Mean calculation is not possible.");
                System.out.println("Mean: 0.0");
                System.out.println("Data set is empty. Median calculation is not possible.");
                System.out.println("Median: 0.0");
                System.out.println("Data set is empty. Standard deviation calculation is not possible.");
                System.out.println("Standard Deviation: NaN");
            } else {
                System.out.printf("Mean: %.2f%n", statisticsManager.calculateMean(selectedDataSet));
                System.out.printf("Median: %.2f%n", statisticsManager.calculateMedian(selectedDataSet));
                System.out.printf("Standard Deviation: %.2f%n", statisticsManager.calculateStandardDeviation(selectedDataSet));
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }
}