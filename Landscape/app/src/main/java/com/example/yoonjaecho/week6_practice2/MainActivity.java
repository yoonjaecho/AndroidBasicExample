package com.example.yoonjaecho.week6_practice2;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
            Toast.makeText(this, "방향 : ORIENTATION_LANDSCAPE", Toast.LENGTH_SHORT).show();
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
            Toast.makeText(this, "방향 : ORIENTATION_PORTRAIT", Toast.LENGTH_SHORT).show();
    }
}
