package hu.ait.android.tictactoe.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeView extends View {

    private Paint paintBG;
    private Paint paintLine;
    private Paint paintCircle;

    private List<Point> circleCoordinates = new ArrayList<Point>();

    public TicTacToeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintBG = new Paint();
        paintBG.setColor(Color.BLACK);
        paintBG.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.GRAY);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);

        paintCircle = new Paint();
        paintCircle.setColor(Color.BLUE);
        paintCircle.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackground(canvas);
        drawGrid(canvas);
        drawPlayers(canvas);
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBG);
    }

    private void drawGrid(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);
        canvas.drawRect(0, getHeight()/3, getWidth(), 2*getHeight()/3, paintLine);
        canvas.drawRect(getWidth()/3, 0, 2*getWidth()/3, getHeight(), paintLine);
    }

    private void drawPlayers(Canvas canvas) {
        for (Point circleCoordinate : circleCoordinates) {
            canvas.drawCircle(circleCoordinate.x, circleCoordinate.y, 40, paintCircle);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            circleCoordinates.add(new Point((int) event.getX(), (int) event.getY()));

            invalidate(); // tells Android that current view is not valid, so view has to be redrawn
        }
        return true;
    }

    public void clearGameArea() {
        circleCoordinates.clear();
        invalidate();
    }
}