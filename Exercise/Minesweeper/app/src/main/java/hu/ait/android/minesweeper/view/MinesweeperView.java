package hu.ait.android.minesweeper.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import hu.ait.android.minesweeper.MainActivity;
import hu.ait.android.minesweeper.model.MinesweeperModel;

public class MinesweeperView extends View {
    private short gridSize;
    private Paint paintGridLine;

    public MinesweeperView(Context context, AttributeSet attrs) {
        super(context, attrs);

        gridSize = MinesweeperModel.SIZE;

        paintGridLine = new Paint();
        paintGridLine.setColor(Color.BLACK);
        paintGridLine.setStyle(Paint.Style.STROKE);
        paintGridLine.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawGrid(canvas);
    }

    private void drawGrid(Canvas canvas) {
        for (int i = 0; i <= gridSize; ++i) {
            canvas.drawLine(0, i * getHeight() / gridSize,
                    getWidth(), i * getHeight() / gridSize, paintGridLine);
            canvas.drawLine(i * getWidth() / gridSize, 0,
                    i * getWidth() / gridSize, getHeight(), paintGridLine);
        }
    }

    final GestureDetector gestureDetector = new GestureDetector(getContext(),
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    return true;
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Log.d("Press", "onSingleTapUp");
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    Log.d("Press", "Long press!");
                }
            });

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = (w == 0 ? h : (h == 0 ? w : (w < h ? w : h)));
        setMeasuredDimension(d, d);
    }
}
