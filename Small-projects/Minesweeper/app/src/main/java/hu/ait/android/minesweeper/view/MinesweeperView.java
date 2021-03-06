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
import android.widget.Toast;

import hu.ait.android.minesweeper.MainActivity;
import hu.ait.android.minesweeper.R;
import hu.ait.android.minesweeper.model.MinesweeperModel;

public class MinesweeperView extends View {
    private int SIZE;
    private static int flagsLeft;

    private Paint paintGridLine;
    private Paint paintText;
    private Bitmap bitmapFlag;
    private Bitmap bitmapBomb;

    private boolean hasLostGame = false;

    public MinesweeperView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SIZE = MinesweeperModel.SIZE;
        flagsLeft = MinesweeperModel.NUM_MINES;

        initPaintGridLine();
        initPaintText();
        decodeBitmaps();
    }

    private void decodeBitmaps() {
        bitmapFlag = BitmapFactory.decodeResource(getResources(), R.drawable.flag);
        bitmapBomb = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
    }

    private void initPaintText() {
        paintText = new Paint();
        paintText.setColor(Color.RED);
        paintGridLine.setStyle(Paint.Style.STROKE);
        paintText.setTextSize(getScaledSize(SIZE));
        paintText.setTypeface(Typeface.create(
                Typeface.createFromAsset(getContext().getAssets(), getContext().getString(R.string.path_to_font_munro)),
                Typeface.NORMAL));
    }

    private void initPaintGridLine() {
        paintGridLine = new Paint();
        paintGridLine.setColor(Color.BLACK);
        paintGridLine.setStyle(Paint.Style.STROKE);
        paintGridLine.setStrokeWidth(5);
    }

    private float getScaledSize(int size) {
        return ((float) 720 / size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bitmapFlag = Bitmap.createScaledBitmap(bitmapFlag,
                (int) (0.75 * getWidth() / SIZE), (int) (0.75 * getHeight() / SIZE), false);
        bitmapBomb = Bitmap.createScaledBitmap(bitmapBomb,
                getWidth() / SIZE, getHeight() / SIZE, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawVisibleGrid(canvas);
        drawLines(canvas);

        if (hasLostGame)
            hasLostGame = false;
        ((MainActivity) getContext()).updateFlagsLeft(flagsLeft);
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
                            (j + 0.4f) * getWidth() / SIZE, (i + 0.75f) * getHeight() / SIZE,
                            paintText);
                } else if (status == MinesweeperModel.FLAG) {
                    canvas.drawBitmap(bitmapFlag,
                            (j + 0.1f) * getWidth() / SIZE, (i + 0.1f) * getHeight() / SIZE,
                            null);
                } else if (hasLostGame && MinesweeperModel.getInstance().isMineAtLocation(i, j)) {
                    canvas.drawBitmap(bitmapBomb,
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
                        flagsLeft += 1;
                    } else {
                        if (!MinesweeperModel.getInstance().isMineAtLocation(point.x, point.y)) {
                            MinesweeperModel.getInstance().clickField(point.x, point.y);
                        } else {
                            gameLost();
                        }
                    }
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    Point point = getPoint(e);
                    if (MinesweeperModel.getInstance().getFieldStatus(
                            point.x, point.y) == MinesweeperModel.CLOSE && flagsLeft > 0) {
                        MinesweeperModel.getInstance().setFlag(point.x, point.y);
                        flagsLeft -= 1;
                    }
                }
            });

    private void gameLost() {
        hasLostGame = true;
        Toast.makeText(getContext(), R.string.toast_game_lost, Toast.LENGTH_SHORT).show();
        ((MainActivity) getContext()).gameOver(R.drawable.face_sad);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = gestureDetector.onTouchEvent(event);
        if (MinesweeperModel.getInstance().isFullGridVisible()
                && flagsLeft == 0) {
            gameWon();
        }
        invalidate();
        return result;
    }

    private void gameWon() {
        Toast.makeText(getContext(), R.string.toast_game_won, Toast.LENGTH_SHORT).show();
        ((MainActivity) getContext()).gameOver(R.drawable.face_happy);
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

    public static void resetFlagsLeft(int numMines) {
        flagsLeft = numMines;
    }
}
