import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatisticsManager implements StatisticsOperations {
    private List<DataSet> dataSets;

    public StatisticsManager() {
        this.dataSets = new ArrayList<>();

        dataSets.add(new DataSet());
        dataSets.add(new DataSet());
        dataSets.add(new DataSet());
    }

    public void addDataSet(DataSet dataSet) {
        dataSets.add(dataSet);
    }

    public void removeDataSet(DataSet dataSet) {
        dataSets.remove(dataSet);
    }

    public List<DataSet> getDataSets() {
        return dataSets;
    }

    @Override
    public double calculateMean(DataSet dataSet) {
        List<Double> numbers = dataSet.getNumbers();
        if (numbers.isEmpty()) {
            System.out.println("Data set is empty. Mean calculation is not possible.");
            return 0.0;
        }
        double sum = 0;
        for (double num : numbers) {
            sum += num;
        }
        return sum / numbers.size();
    }

    @Override
    public double calculateMedian(DataSet dataSet) {
        List<Double> numbers = new ArrayList<>(dataSet.getNumbers());
        if (numbers.isEmpty()) {
            System.out.println("Data set is empty. Median calculation is not possible.");
            return 0.0;
        }
        Collections.sort(numbers);
        int size = numbers.size();
        if (size % 2 == 1) {
            return numbers.get(size / 2);
        } else {
            return (numbers.get((size / 2) - 1) + numbers.get(size / 2)) / 2.0;
        }
    }

    @Override
    public double calculateStandardDeviation(DataSet dataSet) {
        List<Double> numbers = dataSet.getNumbers();
        if (numbers.isEmpty()) {
            System.out.println("Data set is empty. Standard deviation calculation is not possible.");
            return Double.NaN;
        }
        double mean = calculateMean(dataSet);
        double sumOfSquares = 0;
        for (double num : numbers) {
            sumOfSquares += Math.pow(num - mean, 2);
        }
        return Math.sqrt(sumOfSquares / numbers.size());
    }
}