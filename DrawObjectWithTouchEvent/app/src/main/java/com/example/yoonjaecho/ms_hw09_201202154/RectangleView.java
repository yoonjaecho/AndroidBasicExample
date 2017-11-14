package com.example.yoonjaecho.ms_hw09_201202154;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by yoonjaecho on 14/11/2017.
 */

public class RectangleView extends View {

    private static int rectWidth = 100;
    private static int rectHeight = 100;

    private int rectTopX, rectTopY;

    private static int lowerBoundX;
    private static int upperBoundX;
    private static int lowerBoundY;
    private static int upperBoundY;

    private Paint rect;


    public RectangleView(Context context) {
        super(context);

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();

        Point displaySize = new Point();
        display.getSize(displaySize);

        rect = new Paint();
        rect.setColor(Color.RED);

        lowerBoundX = (rectWidth / 2);
        upperBoundX = displaySize.x - (rectWidth / 2);
        lowerBoundY = (rectHeight / 2);
        upperBoundY = displaySize.y - (rectHeight / 2);

        rectTopX = getRandomPosition(lowerBoundX, upperBoundX);
        rectTopY = getRandomPosition(lowerBoundY, upperBoundY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(rectTopX, rectTopY, rectTopX + rectWidth, rectTopY + rectHeight, rect);
        Log.d("POS", "X : " + rectTopX + ", Y : " + rectTopY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_DOWN:
                float curX = event.getX();
                float curY = event.getY();
                curX = curX < lowerBoundX ? lowerBoundX :
                        curX > upperBoundX ? upperBoundX : curX;
                curY = curY < lowerBoundY ? lowerBoundY :
                        curY > upperBoundY ? upperBoundY : curY;
                rectTopX = (int) curX - (rectWidth / 2);
                rectTopY = (int) curY - (rectHeight / 2);
                invalidate();
                return true;
            default:
                return false;
        }
    }

    private int getRandomPosition(int lowerBound, int upperBound) {
        return (int) (Math.random() * (upperBound - lowerBound)) + lowerBound;
    }
}
