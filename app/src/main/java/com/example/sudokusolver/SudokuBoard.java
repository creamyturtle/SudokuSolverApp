package com.example.sudokusolver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

class SudokuBoard extends View {
    private final int boardColor;
    private final int cellFillColor;
    private final int cellsHighlightColor;

    private final Paint boardColorPaint = new Paint();
    private final Paint cellFillColorPaint = new Paint();
    private final Paint cellsHighlightColorPaint = new Paint();

    private int cellSize;

    private final Solver solver = new Solver();

    public SudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SudokuBoard, 0, 0);

        try{
            boardColor = a.getInteger(R.styleable.SudokuBoard_boardColor, 0);
            cellFillColor = a.getInteger(R.styleable.SudokuBoard_cellFillColor, 0);
            cellsHighlightColor = a.getInteger(R.styleable.SudokuBoard_cellsHighlightColor, 0);

        }finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        int height2 = MeasureSpec.getSize(height);
        int width2 = MeasureSpec.getSize(width);

        int dimension = Math.min(width2, height2);
        cellSize = dimension / 9;

        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(16);
        boardColorPaint.setColor(boardColor);
        boardColorPaint.setAntiAlias(true);

        cellFillColorPaint.setStyle(Paint.Style.FILL);
        cellFillColorPaint.setAntiAlias(true);
        cellFillColorPaint.setColor(cellFillColor);

        cellsHighlightColorPaint.setStyle(Paint.Style.FILL);
        cellsHighlightColorPaint.setAntiAlias(true);
        cellsHighlightColorPaint.setColor(cellsHighlightColor);

        colorCell(canvas, solver.getSelectedRow(), solver.getSelectedColumn());

        canvas.drawRect(0, 0, getWidth(), getHeight(), boardColorPaint);
        drawBoard(canvas);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        boolean isValid;

        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            solver.setSelectedRow((int) Math.ceil(y/cellSize));
            solver.setSelectedColumn((int) Math.ceil(x/cellSize));
            isValid = true;
        }
        else{
            isValid = false;
        }


        return isValid;
    }

    private void colorCell(Canvas canvas, int r, int c){
        if (solver.getSelectedColumn() != -1 && solver.getSelectedRow() != -1){
            canvas.drawRect((c-1)*cellSize, 0, c*cellSize, cellSize*9, cellsHighlightColorPaint);
            canvas.drawRect(0, (r-1)*cellSize, cellSize*9, r*cellSize, cellsHighlightColorPaint);
            canvas.drawRect((c-1)*cellSize, (r-1)*cellSize, c*cellSize, r*cellSize, cellsHighlightColorPaint);
        }

        invalidate();
    }

    private void drawThickLine(){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(10);
        boardColorPaint.setColor(boardColor);

    }

    private void drawThinLine(){
        boardColorPaint.setStyle(Paint.Style.STROKE);
        boardColorPaint.setStrokeWidth(4);
        boardColorPaint.setColor(boardColor);

    }

    private void drawBoard(Canvas canvas){
        for (int c = 0; c<10; c++){
            if (c%3 == 0){
                drawThickLine();
            }
            else{
                drawThinLine();
            }
            canvas.drawLine(cellSize * c, 0, cellSize * c, getWidth(), boardColorPaint);

        }

        for (int r = 0; r<10; r++){
            if (r%3 == 0){
                drawThickLine();
            }
            else{
                drawThinLine();
            }

            canvas.drawLine(0, cellSize * r, getWidth(), cellSize * r, boardColorPaint);

        }
    }

}
