package hu.ait.android.minesweeper.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import hu.ait.android.minesweeper.model.MinesweeperModel;

public class MinesweeperView extends View {
    public static final String LOG_TAG = "TAG";
    private int gridSize;
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

        drawLines(canvas);
        drawVisibleGrid(canvas);
    }

    private void drawLines(Canvas canvas) {
        for (int i = 0; i <= gridSize; ++i) {
            canvas.drawLine(0, i * getHeight() / gridSize,
                    getWidth(), i * getHeight() / gridSize, paintGridLine);
            canvas.drawLine(i * getWidth() / gridSize, 0,
                    i * getWidth() / gridSize, getHeight(), paintGridLine);
        }
    }

    private void drawVisibleGrid(Canvas canvas) {
        for (int i = 0; i < gridSize; ++i) {
            for (int j = 0; j < gridSize; ++j) {
                short status = MinesweeperModel.getInstance().getFieldStatus(i, j);
                if (status == MinesweeperModel.OPEN) {
                    // draw number of neighboring mines
                    canvas.drawText(Integer.toString(MinesweeperModel.getInstance().getNumOfNeighboringMines(i, j)),
                            50, 50, paintGridLine);
                }
                else if (status == MinesweeperModel.FLAG) {
                    // draw a flag
                }
            }
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
                    Point point = getPoint(e);
                    MinesweeperModel.getInstance().clickField(point.x, point.y);
                    invalidate();
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    Point point = getPoint(e);
                    MinesweeperModel.getInstance().setFlag(point.x, point.y);
                    invalidate();
                }
            });

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private Point getPoint(MotionEvent e) {
        int x = ((int) e.getX()) / (getWidth() / gridSize);
        int y = ((int) e.getY()) / (getHeight() / gridSize);
        return new Point(y, x);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = (w == 0 ? h : (h == 0 ? w : (w < h ? w : h)));
        setMeasuredDimension(d, d);
    }
}
