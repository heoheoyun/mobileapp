package com.example.radiobuttonex;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (LinearLayout) findViewById(R.id.layout);
    }
    public void onRadioButtonClicked(View view){
        boolean checked =((RadioButton) view).isChecked();

        int id = view.getId();

        if (id == R.id.radio_red) {
            if (checked)
                layout.setBackgroundColor(Color.RED);
        } else if (id == R.id.radio_blue) {
            if (checked)
                layout.setBackgroundColor(Color.BLUE);
        }
    }
}