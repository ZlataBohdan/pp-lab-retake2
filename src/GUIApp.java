import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class GUIApp extends Application {
    private StatisticsManager statisticsManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        statisticsManager = new StatisticsManager();

        primaryStage.setTitle("Statistical Analysis Tool");

        // Основное меню
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(10));
        Button btnAddDataSet = new Button("1. Add a new data set");
        Button btnRemoveDataSet = new Button("2. Remove an existing data set");
        Button btnAddNumbers = new Button("3. Add numbers to an existing data set");
        Button btnPerformStatistics = new Button("4. Perform statistical operations");
        Button btnExit = new Button("5. Exit");

        menu.getChildren().addAll(btnAddDataSet, btnRemoveDataSet, btnAddNumbers, btnPerformStatistics, btnExit);

        Scene scene = new Scene(menu, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Обработчики событий для кнопок
        btnAddDataSet.setOnAction(e -> addNewDataSet());
        btnRemoveDataSet.setOnAction(e -> removeDataSet());
        btnAddNumbers.setOnAction(e -> addNumbersToDataSet());
        btnPerformStatistics.setOnAction(e -> performStatistics());
        btnExit.setOnAction(e -> primaryStage.close());
    }

    private void addNewDataSet() {
        DataSet dataSet = new DataSet();
        statisticsManager.addDataSet(dataSet);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Data Set");
        alert.setHeaderText(null);
        alert.setContentText("A new data set has been added.");

        alert.showAndWait();

        addNumbersToDataSet(dataSet);
    }

    private void removeDataSet() {
        if (statisticsManager.getDataSets().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Remove Data Set", "No data sets available.");
            return;
        }

        ChoiceDialog<DataSet> dialog = new ChoiceDialog<>(null, statisticsManager.getDataSets());
        dialog.setTitle("Remove Data Set");
        dialog.setHeaderText("Select a data set to remove:");
        dialog.setContentText("Data set:");

        Optional<DataSet> result = dialog.showAndWait();
        result.ifPresent(dataSet -> {
            statisticsManager.removeDataSet(dataSet);
            showAlert(Alert.AlertType.INFORMATION, "Remove Data Set", "The data set has been removed.");
        });
    }

    private void addNumbersToDataSet() {
        if (statisticsManager.getDataSets().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Add Numbers", "No data sets available.");
            return;
        }

        ChoiceDialog<DataSet> dialog = new ChoiceDialog<>(null, statisticsManager.getDataSets());
        dialog.setTitle("Add Numbers");
        dialog.setHeaderText("Select a data set to add numbers to:");
        dialog.setContentText("Data set:");

        Optional<DataSet> result = dialog.showAndWait();
        result.ifPresent(this::addNumbersToDataSet);
    }

    private void addNumbersToDataSet(DataSet dataSet) {
        Stage stage = new Stage();
        stage.setTitle("Add Numbers");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(5);
        grid.setVgap(5);

        TextField numberInput = new TextField();
        numberInput.setPromptText("Enter number");

        Button btnAdd = new Button("Add");
        btnAdd.setOnAction(e -> {
            String input = numberInput.getText();
            try {
                double number = Double.parseDouble(input);
                dataSet.addNumber(number);
                numberInput.clear();
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Invalid number. Please enter a valid number.");
            }
        });

        Button btnDone = new Button("Done");
        btnDone.setOnAction(e -> stage.close());

        grid.add(new Label("Enter numbers to add to the data set (enter 'done' to finish):"), 0, 0, 2, 1);
        grid.add(numberInput, 0, 1);
        grid.add(btnAdd, 1, 1);
        grid.add(btnDone, 1, 2);

        Scene scene = new Scene(grid, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void performStatistics() {
        if (statisticsManager.getDataSets().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Perform Statistics", "No data sets available.");
            return;
        }

        ChoiceDialog<DataSet> dialog = new ChoiceDialog<>(null, statisticsManager.getDataSets());
        dialog.setTitle("Perform Statistics");
        dialog.setHeaderText("Select a data set to perform statistical operations:");
        dialog.setContentText("Data set:");

        Optional<DataSet> result = dialog.showAndWait();
        result.ifPresent(dataSet -> {
            if (dataSet.getNumbers().isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Statistics", "Data set is empty.\nMean: 0.0\nMedian: 0.0\nStandard Deviation: NaN");
            } else {
                double mean = statisticsManager.calculateMean(dataSet);
                double median = statisticsManager.calculateMedian(dataSet);
                double stdDev = statisticsManager.calculateStandardDeviation(dataSet);

                showAlert(Alert.AlertType.INFORMATION, "Statistics",
                        String.format("Mean: %.2f\nMedian: %.2f\nStandard Deviation: %.2f", mean, median, stdDev));
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}