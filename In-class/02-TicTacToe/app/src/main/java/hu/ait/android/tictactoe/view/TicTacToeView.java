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

/**
 * Created by Yash Mittal on 2016. 06. 08..
 */
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

        // draw background here
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBG);
        canvas.drawLine(0, 0, getWidth(), getHeight(), paintLine);

        // draw circles
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
}
