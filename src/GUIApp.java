import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIApp extends Application {

    private StatisticsManager statisticsManager;
    private TextField addNumbersInput;
    private Label outputLabel;

    @Override
    public void start(Stage primaryStage) {
        statisticsManager = new StatisticsManager();

        addNumbersInput = new TextField();
        Button addNumbersButton = new Button("Add Numbers");
        addNumbersButton.setOnAction(event -> addNumbers());

        Button performStatisticsButton = new Button("Perform Statistics");
        performStatisticsButton.setOnAction(event -> performStatistics());

        outputLabel = new Label();

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(addNumbersInput, addNumbersButton, performStatisticsButton, outputLabel);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Statistics App");
        primaryStage.show();
    }

    private void addNumbers() {
        String input = addNumbersInput.getText();
        String[] numbersStr = input.split("\\s+");
        try {
            for (String str : numbersStr) {
                double number = Double.parseDouble(str);
                statisticsManager.getDataSets().get(0).addNumber(number);
            }
            outputLabel.setText("Numbers added successfully.");
        } catch (NumberFormatException e) {
            outputLabel.setText("Invalid input! Please enter valid numbers separated by spaces.");
        }
    }

    private void performStatistics() {
        DataSet selectedDataSet = statisticsManager.getDataSets().get(0);
        if (selectedDataSet.getNumbers().isEmpty()) {
            outputLabel.setText("Data set is empty. Mean calculation is not possible.\nMean: 0.0\nMedian: 0.0\nStandard Deviation: NaN");
        } else {
            double mean = statisticsManager.calculateMean(selectedDataSet);
            double median = statisticsManager.calculateMedian(selectedDataSet);
            double standardDeviation = statisticsManager.calculateStandardDeviation(selectedDataSet);
            outputLabel.setText(String.format("Mean: %.2f\nMedian: %.2f\nStandard Deviation: %.2f", mean, median, standardDeviation));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}