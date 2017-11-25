package com.example.yoonjaecho.requesttoweb;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText input01;
    TextView textMessage;
    Button requestButton;

    public static String defaultUrl = "https://m.naver.com";

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input01 = (EditText) findViewById(R.id.input01);
        input01.setText(defaultUrl);

        textMessage = (TextView) findViewById(R.id.textMessage);

        requestButton = (Button) findViewById(R.id.requestButton);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlStr = input01.getText().toString();

                new ConnectTread(urlStr).start();
            }
        });
    }

    private class ConnectTread extends Thread {

        String urlStr;

        public ConnectTread(String urlStr) {
            this.urlStr = urlStr;
        }

        @Override
        public void run() {
            try {
                final String output = request(urlStr);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textMessage.setText(output);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private String request(String urlStr) {
            StringBuilder output = new StringBuilder();
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    while (true) {
                        line = reader.readLine();
                        if (line == null) break;
                        output.append(line + "\n");
                    }

                    reader.close();
                    conn.disconnect();
                }

            } catch (Exception e) {
                Log.e("HTTP", "Exceoption in processing response.", e);
                e.printStackTrace();
            }
            return output.toString();
        }
    }
}
