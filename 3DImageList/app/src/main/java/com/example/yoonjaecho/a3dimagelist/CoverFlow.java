package com.example.yoonjaecho.a3dimagelist;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * Created by yoonjaecho on 17/11/2017.
 */

public class CoverFlow extends Gallery {

    private Camera camera = new Camera();
    public static int maxRotationAngle = 55;
    public static int maxZoom = -60;
    private int centerPoint;

    public CoverFlow(Context context) {
        super(context);
        init();
    }

    public CoverFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CoverFlow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setStaticTransformationsEnabled(true);
    }

    private int getCenterOfCoverFlow() {
        return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
    }

    private static int getCenterOfView(View view) {
        return view.getLeft() + view.getWidth() / 2;
    }

    protected boolean getChildStaticTransformation(View child, Transformation t) {
        final int childCenter = getCenterOfView(child);
        final int childWidth = child.getWidth();
        int rotationAngle = 0;
        t.clear();
        t.setTransformationType(Transformation.TYPE_MATRIX);

        if (childCenter == centerPoint)
            transformImageBitmap((ImageView) child, t, 0);
        else {
            rotationAngle = (int) (((float) (centerPoint - childCenter) / childWidth) * maxRotationAngle);
            if (Math.abs(rotationAngle) > maxRotationAngle)
                rotationAngle = (rotationAngle < 0)? -maxRotationAngle : maxRotationAngle;
            transformImageBitmap((ImageView) child, t, rotationAngle);
        }

        return true;
    }

    private void transformImageBitmap(ImageView child, Transformation t, int rotationAngle) {
        camera.save();

        final Matrix imageMatrix = t.getMatrix();
        final int imageHeight = child.getLayoutParams().height;
        final int imageWidth = child.getLayoutParams().width;
        final int rotation = Math.abs(rotationAngle);

        camera.translate(0.0f, 0.0f, 100.0f);

        if (rotation < maxRotationAngle) {
            float zoomAmonut = (float) (maxZoom + (rotationAngle * 1.5));
            camera.translate(0.0f, 0.0f, zoomAmonut);
        }

        camera.rotateY(rotationAngle);
        camera.getMatrix(imageMatrix);

        imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));
        imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2));

        camera.restore();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        centerPoint = getCenterOfCoverFlow();
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
