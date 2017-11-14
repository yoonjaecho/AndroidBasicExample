package com.example.yoonjaecho.ms_hw09_201202154;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RectangleView rectangleView = new RectangleView(this);
        setContentView(rectangleView);
    }
}
