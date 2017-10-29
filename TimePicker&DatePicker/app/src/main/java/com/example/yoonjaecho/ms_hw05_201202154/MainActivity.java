package com.example.yoonjaecho.ms_hw05_201202154;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    private EditText nameText, phoneText;
    private Button dateButtton, timeButton, saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = (EditText) findViewById(R.id.name_editText);
        phoneText = (EditText) findViewById(R.id.phone_editText);

        Calendar calendar = Calendar.getInstance();
        final int currentYear = calendar.get(Calendar.YEAR);
        final int currentMonth = calendar.get(Calendar.MONTH);
        final int currnetDay = calendar.get(Calendar.DAY_OF_MONTH);
        final int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        final int currentMinute = calendar.get(Calendar.MINUTE);

        dateButtton = (Button) findViewById(R.id.date_button);
        dateButtton.setText(String.format("%d년 %d월 %d일", currentYear, currentMonth, currnetDay));
        dateButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        dateButtton.setText(String.format("%d년 %d월 %d일", year, month, day));
                    }
                }, currentYear, currentMonth, currnetDay).show();
            }
        });

        timeButton = (Button) findViewById(R.id.time_button);
        timeButton.setText(String.format("%d시 %d분", currentHour, currentMinute));
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        timeButton.setText(String.format("%d시 %d분", hour, minute));
                    }
                }, currentHour, currentMinute, false).show();
            }
        });

        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameText.getText().equals("") || phoneText.getText().equals(""))
                    Toast.makeText(getApplicationContext(), "이름과 전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                else {
                    String message = String.format("이름 : %s\n전화번호 : %s\n날짜 : %s\n시간 : %s",
                            nameText.getText().toString(),
                            phoneText.getText().toString(),
                            dateButtton.getText().toString(),
                            timeButton.getText().toString());
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
