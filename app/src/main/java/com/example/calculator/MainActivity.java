package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText display;
    private String currentInput = "";
    private String operator = "";
    private double firstNumber = 0;
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        setupButtons();
    }

    private void setupButtons() {
        // الأرقام 0-9
        for (int i = 0; i <= 9; i++) {
            int resId = getResources().getIdentifier("btn" + i, "id", getPackageName());
            if (resId != 0) {
                findViewById(resId).setOnClickListener(v -> appendNumber(String.valueOf(i)));
            }
        }

        // عمليات Krono
        findViewById(R.id.btnAdd).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.btnSub).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.btnMul).setOnClickListener(v -> setOperator("×"));
        findViewById(R.id.btnDiv).setOnClickListener(v -> setOperator("÷"));
        findViewById(R.id.btnEqual).setOnClickListener(v -> calculate());
        findViewById(R.id.btnClear).setOnClickListener(v -> clear());
        findViewById(R.id.btnDot).setOnClickListener(v -> appendNumber("."));
        findViewById(R.id.btnDelete).setOnClickListener(v -> deleteLast());
    }

    private void appendNumber(String num) {
        if (isNewOperation) {
            display.setText("");
            isNewOperation = false;
        }
        String current = display.getText().toString();
        if (num.equals(".") && current.contains(".")) return;
        display.setText(current + num);
    }

    private void setOperator(String op) {
        if (!display.getText().toString().isEmpty()) {
            firstNumber = Double.parseDouble(display.getText().toString());
            operator = op;
            isNewOperation = true;
        }
    }

    private void calculate() {
        if (operator.isEmpty()) return;
        double secondNumber = Double.parseDouble(display.getText().toString());
        double result = 0;

        switch (operator) {
            case "+": result = firstNumber + secondNumber; break;
            case "-": result = firstNumber - secondNumber; break;
            case "×": result = firstNumber * secondNumber; break;
            case "÷":
                if (secondNumber == 0) {
                    display.setText("خطأ");
                    isNewOperation = true;
                    return;
                }
                result = firstNumber / secondNumber;
                break;
        }

        display.setText(String.valueOf(result));
        operator = "";
        isNewOperation = true;
    }

    private void clear() {
        display.setText("");
        currentInput = "";
        operator = "";
        firstNumber = 0;
        isNewOperation = true;
    }

    private void deleteLast() {
        String current = display.getText().toString();
        if (!current.isEmpty()) {
            display.setText(current.substring(0, current.length() - 1));
        }
    }
}
