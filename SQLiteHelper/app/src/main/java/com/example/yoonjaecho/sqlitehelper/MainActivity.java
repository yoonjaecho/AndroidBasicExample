package com.example.yoonjaecho.sqlitehelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView status;

    public static final String TAG = "MainActivity";

    private static String DATABASE_NAME = null;
    private static final String TABLE_NAME = "employee";
    private static final int DATABASE_VERSION = 1;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = (TextView) findViewById(R.id.status);
        final EditText input01 = (EditText) findViewById(R.id.input01);

        Button queryButton = (Button) findViewById(R.id.queryButton);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DATABASE_NAME = input01.getText().toString();
                boolean isOpen = openDatabse();
                if (isOpen) {
                    executeRawQuery();
                    executeRawQueryParam();
                }
            }
        });
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            getWritableDatabase();
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            println("Creating table [" + TABLE_NAME + "].");

            try {
                String DROP_SQL = "drop table if exists " + TABLE_NAME;
                sqLiteDatabase.execSQL(DROP_SQL);
            } catch (Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            try {
                String CREATE_SQL = "create table " + TABLE_NAME
                        + " ( _id integer PRIMARY KEY autoincrement, "
                        + "name text, "
                        + "age integer, "
                        + "phone text)";
                sqLiteDatabase.execSQL(CREATE_SQL);
            } catch (Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }

            println("Inserting records.");

            try {
                sqLiteDatabase.execSQL("insert into " + TABLE_NAME + "(name, age, phone) values ('John', 20, '010'7788-1234');");
                sqLiteDatabase.execSQL("insert into " + TABLE_NAME + "(name, age, phone) values ('Mike', 35, '010'8888-1111');");
                sqLiteDatabase.execSQL("insert into " + TABLE_NAME + "(name, age, phone) values ('Sean', 26, '010'6677-4321');");
            } catch (Exception ex) {
                Log.e(TAG, "Exception in insert SQL", ex);
            }
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            println("Opened database [" + DATABASE_NAME + "].");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + "to " + newVersion + ".");
        }
    }

    private boolean openDatabse() {
        println("Opening database [" + DATABASE_NAME + "].");

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        return true;
    }

    private void executeRawQuery() {
        println("\nexcuteRawQuery called.\n");
        println("TABLE NAME : " + TABLE_NAME);

        Cursor c1 = db.rawQuery("select count(*) as Total from " + TABLE_NAME, null);
        println("cursor count : " + c1.getCount());

        c1.moveToNext();
        println("record count : " + c1.getInt(0));

        c1.close();
    }

    private void executeRawQueryParam() {
        println("\nexecuteRawQueryParam called.\n");

        String SQL = "select name, age, phone "
                + " from " + TABLE_NAME
                + " where age > ?";
        String[] args = {"30"};

        Cursor c1 = db.rawQuery(SQL, args);
        int recordCount = c1.getCount();
        println("cursor count : " + recordCount + "\n");

        for (int i = 0; i < recordCount; i++) {
            c1.moveToNext();
            String name = c1.getString(0);
            int age = c1.getInt(1);
            String phone = c1.getString(2);

            println("Record #" + i + " : " + name + ", " + age + ", " + phone);
        }

        c1.close();
    }

    private void println(String msg) {
        Log.d("MainActivity", msg);
        status.append("\n" + msg);
    }
}
