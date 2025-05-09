package com.example.drawingboard;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private DrawingView drawingView;
    private ImageButton currentPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingView = findViewById(R.id.drawing_view);

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress(10);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                drawingView.setStrokeWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        ImageButton drawBtn = findViewById(R.id.draw_btn);
        drawBtn.setOnClickListener(v -> drawingView.setErase(false));

        ImageButton eraseBtn = findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(v -> drawingView.setErase(true));

        ImageButton newBtn = findViewById(R.id.new_btn);
        newBtn.setOnClickListener(v -> drawingView.startNew());

        ImageButton undoBtn = findViewById(R.id.undo_btn);
        undoBtn.setOnClickListener(v -> drawingView.undo());

        ImageButton redoBtn = findViewById(R.id.redo_btn);
        redoBtn.setOnClickListener(v -> drawingView.redo());

        LinearLayout paintLayout = findViewById(R.id.paint_colors);
        currentPaint = (ImageButton) paintLayout.getChildAt(0);
        currentPaint.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.paint_pressed));
    }

    public void paintClicked(View view) {
        if (view != currentPaint) {
            ImageButton imgView = (ImageButton) view;
            String color = view.getTag().toString();
            drawingView.setColor(color);

            imgView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.paint_pressed));
            currentPaint.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.paint));
            currentPaint = (ImageButton) view;

            drawingView.setErase(false);
        }
    }
}