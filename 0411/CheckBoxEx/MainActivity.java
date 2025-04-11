package com.example.checkboxex;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageView imageView1, imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        int id = view.getId();

        if (id == R.id.checkBox) {
            if (checked)
                imageView1.setImageResource(R.drawable.sand1);
            else
                imageView1.setImageDrawable(null);
        } else if (id == R.id.checkBox2) {
            if (checked)
                imageView2.setImageResource(R.drawable.sand2);
            else
                imageView2.setImageDrawable(null);
        }
    }

}
