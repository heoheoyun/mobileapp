package com.example.drawingboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawingView extends View {

    private Path drawPath;
    private Paint drawPaint, canvasPaint;
    private int paintColor = 0xFF000000;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private boolean erase = false;
    private float brushSize = 10;

    // Undo/Redo 리스트
    private final List<Bitmap> undoList = new ArrayList<>();
    private final List<Bitmap> redoList = new ArrayList<>();
    private static final int MAX_UNDO_SIZE = 10; // 최대 Undo 횟수 제한

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing() {
        drawPath = new Path();
        drawPaint = new Paint();

        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);

        saveStateForUndo(); // 초기 상태 저장
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                saveStateForUndo();
                redoList.clear(); // Redo 스택 초기화
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void setColor(String newColor) {
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);

        if (erase) {
            drawPaint.setXfermode(null);
            erase = false;
        }
    }

    public void setErase(boolean isErase) {
        erase = isErase;
        if (erase) {
            drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        } else {
            drawPaint.setXfermode(null);
            drawPaint.setColor(paintColor);
        }
    }

    public void startNew() {
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        saveStateForUndo();
        redoList.clear();
        invalidate();
    }

    public void setStrokeWidth(float width) {
        brushSize = width;
        drawPaint.setStrokeWidth(brushSize);
    }

    public void undo() {
        if (undoList.size() > 1) { // 초기 상태 외에 undo 할 상태가 있어야 함
            redoList.add(0, getCurrentBitmap()); // 현재 상태를 redo 리스트에 추가 (가장 앞에)
            undoList.remove(undoList.size() - 1); // 가장 최근 상태 제거
            canvasBitmap = Bitmap.createBitmap(undoList.get(undoList.size() - 1)); // 이전 상태로 복원
            drawCanvas = new Canvas(canvasBitmap);
            invalidate();
        }
    }

    public void redo() {
        if (!redoList.isEmpty()) {
            undoList.add(getCurrentBitmap()); // 현재 상태를 undo 리스트에 추가
            canvasBitmap = Bitmap.createBitmap(redoList.remove(0)); // 가장 앞의 redo 상태를 가져옴
            drawCanvas = new Canvas(canvasBitmap);
            invalidate();
        }
    }

    // 현재 캔버스 상태를 Bitmap으로 반환
    private Bitmap getCurrentBitmap() {
        Bitmap copy = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(copy);
        draw(tempCanvas);
        return copy;
    }

    private void saveStateForUndo() {
        undoList.add(getCurrentBitmap());
        if (undoList.size() > MAX_UNDO_SIZE) {
            undoList.remove(0); // 최대 크기 초과 시 가장 오래된 상태 제거
        }
    }
}