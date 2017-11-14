package com.example.yoonjaecho.drawobject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        CustomViewDrawables myView = new CustomViewDrawables(this);
        setContentView(myView);
    }
}
