package hu.ait.android.minesweeper.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import hu.ait.android.minesweeper.R;
import hu.ait.android.minesweeper.model.MinesweeperModel;

public class MinesweeperView extends View {
    public static final String LOG_TAG = "TAG";

    private int SIZE;

    private Paint paintGridLine;
    private Paint paintText;
    private Bitmap bitmapFlag;

    public MinesweeperView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SIZE = MinesweeperModel.SIZE;

        paintGridLine = new Paint();
        paintGridLine.setColor(Color.BLACK);
        paintGridLine.setStyle(Paint.Style.STROKE);
        paintGridLine.setStrokeWidth(5);

        paintText = new Paint();
        paintText.setColor(Color.RED);
        paintGridLine.setStyle(Paint.Style.STROKE);
        paintText.setTextSize(getFontSize(SIZE));
        paintText.setTypeface(Typeface.create(
                Typeface.createFromAsset(getContext().getAssets(), "fonts/munro.ttf"),
                Typeface.NORMAL));

        bitmapFlag = BitmapFactory.decodeResource(getResources(), R.drawable.flag);
    }

    private float getFontSize(int size) {
        return 108;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bitmapFlag = Bitmap.createScaledBitmap(bitmapFlag,
                getWidth() / SIZE, getHeight() / SIZE, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawLines(canvas);
        drawVisibleGrid(canvas);
    }

    private void drawLines(Canvas canvas) {
        for (int i = 0; i <= SIZE; ++i) {
            canvas.drawLine(0, i * getHeight() / SIZE,
                    getWidth(), i * getHeight() / SIZE, paintGridLine);
            canvas.drawLine(i * getWidth() / SIZE, 0,
                    i * getWidth() / SIZE, getHeight(), paintGridLine);
        }
    }

    private void drawVisibleGrid(Canvas canvas) {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                short status = MinesweeperModel.getInstance().getFieldStatus(i, j);

                if (status == MinesweeperModel.OPEN) {
                    canvas.drawText(Integer.toString(MinesweeperModel.getInstance().getNumOfNeighboringMines(i, j)),
                            j * getWidth() / SIZE, (i + 1) * getHeight() / SIZE,
                            paintText);
                } else if (status == MinesweeperModel.FLAG) {
                    canvas.drawBitmap(bitmapFlag,
                            j * getWidth() / SIZE, i * getHeight() / SIZE,
                            null);
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
                    if (MinesweeperModel.getInstance().getFieldStatus(
                            point.x, point.y) == MinesweeperModel.FLAG) {
                        MinesweeperModel.getInstance().removeFlag(point.x, point.y);
                    } else {
                        if (!MinesweeperModel.getInstance().isMineAtLocation(point.x, point.y)) {
                            MinesweeperModel.getInstance().clickField(point.x, point.y);
                        } else {
                            // game over (lost)
                            Log.d("Hello", "You lost!");
                        }
                    }
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    Point point = getPoint(e);
                    if (MinesweeperModel.getInstance().getFieldStatus(
                            point.x, point.y) == MinesweeperModel.CLOSE) {
                        MinesweeperModel.getInstance().setFlag(point.x, point.y);
                    }
                }
            });

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = gestureDetector.onTouchEvent(event);
        invalidate();
        return result;
    }

    private Point getPoint(MotionEvent e) {
        int x = (int) (e.getX()) / (getWidth() / SIZE);
        int y = (int) (e.getY()) / (getHeight() / SIZE);
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
