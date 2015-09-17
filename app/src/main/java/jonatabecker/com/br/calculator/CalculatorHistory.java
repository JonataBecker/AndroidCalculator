package jonatabecker.com.br.calculator;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalculatorHistory {

    private static CalculatorHistory instance;
    private List<String> history;

    private CalculatorHistory () {
        history =new ArrayList<>();
    }

    public void addHistory(String history) {
        this.history.add(history);
    }

    public List<String> getHistory() {
        return Collections.unmodifiableList(history);
    }

    public static CalculatorHistory getInstance() {
        if (instance == null) {
            instance = new CalculatorHistory();
        }
        return instance;
    }
}
