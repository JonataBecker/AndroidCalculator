package jonatabecker.com.br.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.jonatabecker.mathexpression.MathExpression;

public class CalculatorActivity extends AppCompatActivity {

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addValue(View v) {
        String buttonValue = String.valueOf(((Button) v).getText());
        String textValue = String.valueOf(textView.getText());
        String expressionValue = String.valueOf(expression.getText());
        // Token is a operation token
        if (mathExpression.isTokenOperation(buttonValue)) {
            // The last token is a operation token
            if (textValue.isEmpty() || lastToken.equals(buttonValue)) {
                return;
            }
            StringBuilder val = new StringBuilder();
            val.append(expressionValue);
            val.append(textValue);
            val.append(" ").append(buttonValue).append(" ");
            expression.setText(val.toString());
        } else {
            if (mathExpression.isTokenOperation(lastToken)) {
                textView.setText(buttonValue);
            } else {
                textView.setText(textView.getText() + buttonValue);
            }
        }
        lastToken = buttonValue;
    }

    private boolean isLastInformationOperator(String expressionValue) {
        return mathExpression.isTokenOperation(getLastToken(expressionValue));
    }

    private String getLastToken(String textValue) {
        if (textValue.isEmpty()) {
            return "";
        }
        String internalValue = textValue.trim();
        return internalValue.substring(internalValue.length() - 1);
    }
}
