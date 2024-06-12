import java.util.ArrayList;
import java.util.List;

public class DataSet {
    private List<Double> numbers;

    public DataSet() {
        this.numbers = new ArrayList<>();
    }

    public void addNumber(double number) {
        numbers.add(number);
    }

    public void removeNumber(double number) {
        numbers.remove(number);
    }

    public List<Double> getNumbers() {
        return numbers;
    }
}