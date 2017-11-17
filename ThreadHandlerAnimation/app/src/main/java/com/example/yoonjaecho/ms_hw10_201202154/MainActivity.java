package com.example.yoonjaecho.ms_hw10_201202154;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button startButton, stopButton;
    private TextView nameText, phoneText, regionText;

    boolean isRunning = false;
    Handler handler = new Handler();

    private int position = 0;

    private String[] names = {"김준수", "이희선", "최민수"};
    private String[] phones = {"010-2000-2000", "010-3000-3000", "010-4000-4000"};
    private String[] regions = {"서울시", "부산시", "대전시"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = (TextView) findViewById(R.id.nameText);
        phoneText = (TextView) findViewById(R.id.phoneText);
        regionText = (TextView) findViewById(R.id.regionText);

        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRunning = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (isRunning) {
                                Thread.sleep(2000);
                                position = (position + 1) == names.length? 0 : position + 1;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        nameText.setText(names[position]);
                                        phoneText.setText(phones[position]);
                                        regionText.setText(regions[position]);
                                    }
                                });
                            }
                        } catch (Exception ex) {
                            Log.e("MainActivity", "Exception in processing message.", ex);
                        }
                    }
                }).start();
            }
        });

        stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRunning = false;
            }
        });
    }
}
