package anjali.learning.bosscalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity {

    TextView display;
    StringBuilder expression = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
    }

    public void onClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        if (buttonText.equals("=")) {
            try {
                Context rhino = Context.enter();//start rhinoscript engine
                rhino.setOptimizationLevel(-1); // Required for Android

                Scriptable scope = rhino.initStandardObjects();
                Object result = rhino.evaluateString(scope, expression.toString(), "JavaScript", 1, null);

                display.setText(result.toString());
                expression.setLength(0);
                expression.append(result.toString());

                Context.exit();
            } catch (Exception e) {
                display.setText("Error");
                expression.setLength(0);
            }
        } else if (buttonText.equals("C")) {
            expression.setLength(0);
            display.setText("");
        } else {
            expression.append(buttonText);
            display.setText(expression.toString());
        }
    }
}
