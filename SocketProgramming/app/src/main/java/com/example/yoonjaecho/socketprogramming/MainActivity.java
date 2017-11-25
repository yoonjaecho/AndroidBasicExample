package com.example.yoonjaecho.socketprogramming;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    EditText input01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input01 = (EditText) findViewById(R.id.input01);

        Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addr = input01.getText().toString().trim();
                new ConnectThread(addr).start();
            }
        });
    }

    private class ConnectThread extends Thread {
        String hostName;

        public ConnectThread(String hostName) {
            this.hostName = hostName;
        }

        @Override
        public void run() {
            try {
                int port = 5001;

                Socket socket = new Socket(hostName, port);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject("모바일 소프트웨어 설계");
                outputStream.flush();

                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                String obj = (String) inputStream.readObject();

                Log.d("MainActivity", "서버에서 받은 메시지 : " + obj);

                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
