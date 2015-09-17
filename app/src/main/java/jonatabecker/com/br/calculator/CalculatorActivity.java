package jonatabecker.com.br.calculator;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.jonatabecker.mathexpression.MathExpression;

public class CalculatorActivity extends AppCompatActivity {

    private static final String ZERO_VALUE = "0";

    private TextView expression;
    private TextView textView;
    private MathExpression mathExpression;
    private String lastToken;

    public CalculatorActivity() {
        mathExpression = new MathExpression();
        lastToken = "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        expression = (TextView) findViewById(R.id.expression);
        textView = (TextView) findViewById(R.id.editText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.history_menu) {
            Intent i = new Intent(this, HistoryActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.credits_menu) {
            Intent i = new Intent(this, CreditsActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addValue(View v) {
        String buttonValue = String.valueOf(((Button) v).getText());
        String textValue = String.valueOf(textView.getText());
        String expressionValue = String.valueOf(expression.getText());
        if (textValue.equals(ZERO_VALUE)) {
            if (buttonValue.equals(ZERO_VALUE)) {
                return;
            }
            textValue = "";
        }
        // Token is a operation token
        if (mathExpression.isTokenOperation(buttonValue)) {
            // The last token is a operation token
            if (textValue.isEmpty() || lastToken.equals(buttonValue)) {
                return;
            }
            String tmpExpression = expressionValue + textValue;
            StringBuilder val = new StringBuilder();
            val.append(tmpExpression);
            val.append(" ").append(buttonValue).append(" ");
            expression.setText(val.toString());
            textView.setText(String.valueOf(mathExpression.calc(tmpExpression)));
        } else {
            if (mathExpression.isTokenOperation(lastToken)) {
                textView.setText(buttonValue);
            } else {
                textView.setText(textValue + buttonValue);
            }
        }
        lastToken = buttonValue;
    }

    public void showResult(View v) {
        String textValue = String.valueOf(textView.getText());
        String expressionValue = String.valueOf(expression.getText());
        String tmpExpression = expressionValue + textValue;
        String result = String.valueOf(mathExpression.calc(tmpExpression));
        expression.setText("");
        textView.setText(result);
        CalculatorHistory.getInstance().addHistory(tmpExpression + " = " + result);
    }

    public void excCE(View v) {
        expression.setText("");
        textView.setText("");
    }

    public void excC(View v) {
        textView.setText(ZERO_VALUE);
    }

    public void erase(View v) {
        String textValue = String.valueOf(textView.getText());
        if (textValue.length() > 0) {
            textValue = textValue.substring(0, textValue.length() - 1);
        }
        if (textValue.length() == 0) {
            textView.setText(ZERO_VALUE);
            return;
        }
        textView.setText(textValue);
    }
}
