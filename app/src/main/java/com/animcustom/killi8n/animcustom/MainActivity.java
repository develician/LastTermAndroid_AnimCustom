package com.animcustom.killi8n.animcustom;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button btnskew, btncamera;
    ImageView animTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btncamera = (Button) findViewById(R.id.btncamera);
        btnskew = (Button) findViewById(R.id.btnskew);

        animTarget = (ImageView) findViewById(R.id.animtarget);


    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.btnskew:
                animTarget.startAnimation(new SkewAnim());
                break;
            case R.id.btncamera:
                animTarget.startAnimation(new CameraAnim());
                break;

        }
    }

    private class SkewAnim extends Animation {
        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            setDuration(1000);
            setInterpolator(new LinearInterpolator());
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            Matrix matrix = t.getMatrix();
            matrix.setSkew(1.0f * interpolatedTime, 0);
        }
    }

    private class CameraAnim extends Animation {
        float cx, cy;
        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            cx = width / 2;
            cy = height / 2;
            setDuration(1000);
            setInterpolator(new LinearInterpolator());
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            Camera cam = new Camera();
            cam.rotateY(360 * interpolatedTime);
            Matrix matrix = t.getMatrix();
            cam.getMatrix(matrix);
            matrix.preTranslate(-cx, -cy);//회전하기 전에 회전 중심을  원점으로 옮김.
            matrix.postTranslate(cx, cy);
        }
    }
}
