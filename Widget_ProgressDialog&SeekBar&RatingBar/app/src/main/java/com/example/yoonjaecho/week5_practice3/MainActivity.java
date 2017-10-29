package com.example.yoonjaecho.week5_practice3;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private RatingBar ratingBar;
    private SeekBar seekBar;
    private Button startButton;

    private int defaultBrightness = 100;
    private TextView seekBarText, ratingBarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        seekBarText = (TextView) findViewById(R.id.seekbar_text);

        ratingBar = (RatingBar) findViewById(R.id.ratingbar);
        ratingBarText = (TextView) findViewById(R.id.ratingbar_text);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingBarText.setText("결과 : " + v);
            }
        });

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setBrightness(i);
                seekBarText.setText(i + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(view);
            }
        });

    }

    private void setBrightness(int value) {
        if (value < 10) value = 10;
        else if (value > 100) value = 100;

        defaultBrightness = value;

        WindowManager.LayoutParams parmas = getWindow().getAttributes();
        parmas.screenBrightness = (float) value / 100;
        getWindow().setAttributes(parmas);
    }

    public void start(View view) {
        progressDialog.setCancelable(true);
        progressDialog.setMessage("다운로드 중입니다.");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.show();

        final Thread t = new Thread() {
            @Override
            public void run() {
                int time = 0;
                while (time < 100) {
                    try {
                        sleep(200);
                        time += 5;
                        progressDialog.setProgress(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(time == 100)
                    progressDialog.dismiss();
            }
        };
        t.start();
    }
}
