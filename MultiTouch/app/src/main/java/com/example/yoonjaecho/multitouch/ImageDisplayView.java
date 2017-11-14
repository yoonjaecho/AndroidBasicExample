package com.example.yoonjaecho.multitouch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yoonjaecho on 07/11/2017.
 */

public class ImageDisplayView extends View implements View.OnTouchListener {

    public static final String TAG = "ImageDisplayView";

    private Context mContext;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mPaint;

    private int lastX, lastY;

    private Bitmap sourceBitmap;
    private Matrix mMatrix;

    private float sourceWidth = 0.0f;
    private float sourceHeight = 0.0f;

    private float bitmapCenterX, bitmapCenterY;
    private float scaleRatio, totalScaleRatio;

    private float displayWidth = 0.0f;
    private float displayHeight = 0.0f;

    private int displayCenterX = 0;
    private int displayCenterY = 0;

    public float startX, startY;

    public static float MAX_SCALE_RATIO = 5.0F;
    public static float MIN_SCALE_RATIO = 0.1F;

    private float oldDistance = 0.0F;

    private int oldPointerCount = 0;
    private boolean isScrolling = false;
    private float distanceThreshold = 3.0F;

    public ImageDisplayView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public ImageDisplayView(Context context, @Nullable AttributeSet attrs, Context mContext) {
        super(context, attrs);
        this.mContext = mContext;
        init();
    }

    private void init() {
        this.mPaint = new Paint();
        this.mMatrix = new Matrix();

        this.lastX = -1;
        this.lastY = -1;

        setOnTouchListener(this);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w > 0 && h > 0) {
            newImage(w, h);
            redraw();
        }
    }

    private void newImage(int width, int height) {
        Bitmap img = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(img);

        this.mBitmap = img;
        this.mCanvas = canvas;

        this.displayWidth = (float) width;
        this.displayHeight = (float) height;

        this.displayCenterX = width / 2;
        this.displayCenterY = height / 2;
    }

    public void drawBackground(Canvas canvas) {
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
        }
    }

    protected void onDraw(Canvas canvas) {
        if (this.mBitmap != null) {
            canvas.drawBitmap(this.mBitmap, 0, 0, null);
        }
    }

    public void setImageData(Bitmap image) {
        recycle();

        this.sourceBitmap = image;

        this.sourceWidth = sourceBitmap.getWidth();
        this.sourceHeight = sourceBitmap.getHeight();

        this.bitmapCenterX = sourceBitmap.getWidth() / 2;
        this.bitmapCenterY = sourceBitmap.getHeight() / 2;

        this.scaleRatio = 1.0F;
        this.totalScaleRatio = 1.0F;
    }

    private void recycle() {
        if (this.sourceBitmap != null) {
            this.sourceBitmap.recycle();
        }
    }

    private void redraw() {
        if (this.sourceBitmap == null) {
            Log.d(TAG, "sourceBitmap is null in redraw().");
            return;
        }

        drawBackground(this.mCanvas);

        float originX = (this.displayWidth - (float) sourceBitmap.getWidth()) / 2.0F;
        float originY = (this.displayHeight - (float) sourceBitmap.getHeight()) / 2.0F;

        this.mCanvas.translate(originX, originY);
        this.mCanvas.drawBitmap(this.sourceBitmap, this.mMatrix, this.mPaint);
        this.mCanvas.translate(-originX, -originY);

        invalidate();
    }

    @Override
    public boolean onTouch(View v, MotionEvent ev) {
        final int action = ev.getAction();

        int pointerCount = ev.getPointerCount();
        Log.d(TAG, "Pointer Count : " + pointerCount);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (pointerCount == 1) {
                    float curX = ev.getX();
                    float curY = ev.getY();
                    this.startX = curX;
                    this.startY = curY;
                } else if (pointerCount == 2) {
                    this.oldDistance = 0.0F;
                    this.isScrolling = true;
                }

                return true;

            case MotionEvent.ACTION_MOVE:
                if (pointerCount == 1) {
                    if (this.isScrolling) return true;

                    float curX = ev.getX();
                    float curY = ev.getY();

                    if (this.startX == 0.0F) {
                        this.startX = curX;
                        this.startY = curY;
                        return true;
                    }

                    float offsetX = this.startX - curX;
                    float offsetY = this.startY - curY;
                    
                    if (this.oldPointerCount == 2) {}
                    else {
                        Log.d(TAG, "ACTION_MOVE : " + offsetX + ", " + offsetY);
                        if (this.totalScaleRatio > 1.0F) {
                            moveImage(-offsetX, -offsetY);
                        }
                        
                        this.startX = curX;
                        this.startY = curY;
                    }
                } else if (pointerCount == 2) {
                    float x1 = ev.getX(0);
                    float y1 = ev.getY(0);
                    float x2 = ev.getX(1);
                    float y2 = ev.getY(1);
                    float dx = x1 - x2;
                    float dy = y1 - y2;
                    float distance = new Double(Math.sqrt(new Float(dx * dx + dy * dy).doubleValue())).floatValue();

                    float outScaleRatio = 0.0F;
                    if (this.oldDistance == 0.0F) {
                        this.oldDistance = distance;
                        break;
                    }

                    if (distance > this.oldDistance) {
                        if ((distance - this.oldDistance) < this.distanceThreshold) {
                            return true;
                        }
                        outScaleRatio = this.scaleRatio + (this.oldDistance / distance * 0.05F);
                    } else if (distance < this.oldDistance) {
                        if ((this.oldDistance - distance) < this.distanceThreshold) {
                            return true;
                        }
                        outScaleRatio = this.scaleRatio - (distance / this.oldDistance * 0.05F);
                    }

                    if (outScaleRatio < MIN_SCALE_RATIO || outScaleRatio > MAX_SCALE_RATIO) {
                        Log.d(TAG, "Invalid scaleRatio : " + outScaleRatio);
                    } else {
                        Log.d(TAG, "Distance : " + distance + ", ScaleRatio : " + outScaleRatio);
                        scaleImage(outScaleRatio);
                    }

                    this.oldDistance = distance;
                }
                this.oldPointerCount = pointerCount;
                break;

            case MotionEvent.ACTION_UP:
                if (pointerCount == 1) {
                    float curX = ev.getX();
                    float curY = ev.getY();

                    float offsetX = this.startX - curX;
                    float offsetY = this.startY - curY;

                    if (this.oldPointerCount == 2) {}
                    else {
                        moveImage(-offsetX, -offsetY);
                    }
                } else {
                    this.isScrolling = false;
                }

                return true;
        }

        return true;
    }

    private void scaleImage(float inScaleRatio) {
        Log.d(TAG, "scaleImage() called : " + inScaleRatio);

        this.mMatrix.postScale(inScaleRatio, inScaleRatio, bitmapCenterX, bitmapCenterY);
        this.mMatrix.postRotate(0);

        this.totalScaleRatio *= inScaleRatio;

        redraw();
    }

    private void moveImage(float offsetX, float offsetY) {
        Log.d(TAG, "moveImage() called : " + offsetX + ", " + offsetY);
        this.mMatrix.postTranslate(offsetX, offsetY);
        redraw();
    }


}
