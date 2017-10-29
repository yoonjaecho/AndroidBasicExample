package com.example.yoonjaecho.week5_practice2;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView menuText, listText;
    private Button menuButton, listButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuText = (TextView) findViewById(R.id.menu_text);
        listText = (TextView) findViewById(R.id.list_text);

        menuButton = (Button) findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage();
            }
        });

        listButton = (Button) findViewById(R.id.list_button);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showList();
            }
        });

    }

    private void showList() {
        final String[] fruit = new String[] {"사과", "바나나", "포도", "귤"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("과일 선택");
        dialog.setItems(fruit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listText.setText(fruit[i]);
            }
        });
        dialog.setPositiveButton("OK", null);
        dialog.show();
    }

    private void showMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("종료하시겠습니까");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                menuText.setText("예 버튼이 눌렸습니다.");
            }
        });

        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                menuText.setText("아니오 버튼이 눌렸습니다.");
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
