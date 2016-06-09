package hu.ait.android.tictactoe.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.tictactoe.MainActivity;
import hu.ait.android.tictactoe.R;
import hu.ait.android.tictactoe.model.TicTacToeModel;

public class TicTacToeView extends View {

    private Bitmap bitmapBg;

    private Paint paintLine;
    private Paint paintCircle;

    public TicTacToeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        bitmapBg = BitmapFactory.decodeResource(getResources(), R.drawable.grass);

        paintLine = new Paint();
        paintLine.setColor(Color.GRAY);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bitmapBg = Bitmap.createScaledBitmap(bitmapBg,
                getWidth(), getHeight(), false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackground(canvas);
        drawGrid(canvas);
        drawPlayers(canvas);
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawBitmap(bitmapBg, 0, 0, null);
    }

    private void drawGrid(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);
        canvas.drawRect(0, getHeight() / 3, getWidth(), 2 * getHeight() / 3, paintLine);
        canvas.drawRect(getWidth() / 3, 0, 2 * getWidth() / 3, getHeight(), paintLine);
    }

    private void drawPlayers(Canvas canvas) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (TicTacToeModel.getInstance().getFieldContent(i, j) == TicTacToeModel.CIRCLE) {

                    // draw a circle at the center of the field

                    // X coordinate: left side of the square + half width of the square
                    float centerX = i * getWidth() / 3 + getWidth() / 6;
                    float centerY = j * getHeight() / 3 + getHeight() / 6;
                    int radius = getHeight() / 6 - 18;

                    canvas.drawCircle(centerX, centerY, radius, paintLine);

                } else if (TicTacToeModel.getInstance().getFieldContent(i, j) == TicTacToeModel.CROSS) {
                    canvas.drawLine(i * getWidth() / 3 + 24,
                            j * getHeight() / 3 + 24,
                            (i + 1) * getWidth() / 3 - 24,
                            (j + 1) * getHeight() / 3 - 24, paintLine);

                    canvas.drawLine((i + 1) * getWidth() / 3 - 24,
                            j * getHeight() / 3 + 24,
                            i * getWidth() / 3 + 24,
                            (j + 1) * getHeight() / 3 - 24, paintLine);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = ((int) event.getX()) / (getWidth() / 3);
            int y = ((int) event.getY()) / (getHeight() / 3);

            if (x < 3 && y < 3 && TicTacToeModel.getInstance().getFieldContent(x, y) == TicTacToeModel.EMPTY
                    && TicTacToeModel.getInstance().isGameOver() == TicTacToeModel.EMPTY) {
                TicTacToeModel.getInstance().setFieldContent(x, y,
                        TicTacToeModel.getInstance().getNextPlayer());
                TicTacToeModel.getInstance().changeNextPlayer();

                short currentState = TicTacToeModel.getInstance().isGameOver();
                if (currentState == TicTacToeModel.EMPTY) {
                    if (TicTacToeModel.getInstance().isGridFull()) {
                        ((MainActivity) getContext()).showToastMessage(
                                getResources().getString(R.string.toast_draw));
                    } else {
                        ((MainActivity) getContext()).showToastMessage(
                                getResources().getString(R.string.toast_next_player,
                                        TicTacToeModel.getInstance().getNextPlayer()));
                    }
                } else {
                    ((MainActivity) getContext()).showToastMessage(
                            getResources().getString(R.string.toast_winner,
                                    currentState));
                }
            }

            invalidate();
        }
        return true;
    }

    public void clearGameArea() {
        TicTacToeModel.getInstance().resetModel();
        invalidate();
    }

    /*
    Dynamically sets width and height of game board
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = (w == 0 ? h : (h == 0 ? w : (w < h ? w : h)));
        setMeasuredDimension(d, d);
    }
}