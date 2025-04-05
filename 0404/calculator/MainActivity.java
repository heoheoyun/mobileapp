package com.example.aicalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    TextView tvInput;
    StringBuilder expression = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInput = findViewById(R.id.tvInput);
    }

    public void onClick(View v) {
        Button b = (Button) v;
        String text = b.getText().toString();

        switch (text) {
            case "C":
                expression.setLength(0);
                tvInput.setText("");
                break;
            case "←":
                if (expression.length() > 0) {
                    expression.setLength(expression.length() - 1);
                    tvInput.setText(expression.toString());
                }
                break;
            case "=":
                try {
                    double result = evaluate(expression.toString());
                    tvInput.setText(String.valueOf(result));
                    expression = new StringBuilder(String.valueOf(result));
                } catch (Exception e) {
                    tvInput.setText("Error");
                    expression.setLength(0);
                }
                break;
            default:
                expression.append(text);
                tvInput.setText(expression.toString());
                break;
        }
    }

    // 간단한 사칙연산 평가 함수 (중위 표현식 계산)
    private double evaluate(String expr) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expr.length()) ? expr.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expr.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expr.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                return x;
            }
        }.parse();
    }
}
