package com.example.yoonjaecho.sqliteexample;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String databaseName, tableName;
    TextView status;
    boolean databaseCreated = false, tableCreated = false;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText databaseNameInput = (EditText) findViewById(R.id.databaseNameInput);
        final EditText tableNameInput = (EditText) findViewById(R.id.tableNameInput);

        Button createDatabaseButton = (Button) findViewById(R.id.createDatabaseButton);
        createDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseName = databaseNameInput.getText().toString();
                createDatabase(databaseName);
            }
        });

        Button createTableButton = (Button) findViewById(R.id.createTableButton);
        createTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableName = tableNameInput.getText().toString();
                createTable(tableName);
                int count = insertRecord(tableName);
                println(count + " records inserted.");

                try {
//                    String[] columns = {"name", "age", "phone"};
//                    String whereStr = " age > ?";
//                    String[] whereParams = {"19"};
//                    Cursor c1 = db.query(tableName, columns, whereStr, whereParams, null, null, null);
//                    int recordCount = c1.getCount();
//                    println("Cursor count : " + recordCount + "\n");
//                    for (int i = 0; i < recordCount; i++) {
//                        c1.moveToNext();
//                        String name = c1.getString(0);
//                        int age = c1.getInt(1);
//                        String phone = c1.getString(2);
//
//                        println("Record #" + i + " : " + name + ", " + age + ", " + phone);
//                    }
//                    c1.close();
//
                    String[] args = {"0"};
                    Cursor c1 = db.rawQuery("select name, age, phone from " + tableName + " where age > ?", args);
                    int num = c1.getCount();
                    for (int i = 0; i < num; i++) {
                        c1.moveToNext();

                        println(i + " : " + String.valueOf(c1.getString(0)));
                        println(i + " : " + String.valueOf(c1.getInt(1)));
                        println(i + " : " + String.valueOf(c1.getString(2)));
                    }

                    c1.close();
                } catch (Exception ex) {
                    println(ex.getMessage());
                }
            }
        });

        status = (TextView) findViewById(R.id.status);
    }


    private void println(String msg) {
        Log.d("Mainactivity", msg);
        status.append("\n" + msg);
    }

    private int insertRecord(String name) {
        println("Inserting records into table " + name + ".");

        int count = 3;
        db.execSQL("insert into " + name + "(name, age, phone) values ('John', 20, '010-7788-1234');");
        db.execSQL("insert into " + name + "(name, age, phone) values ('Mike', 35, '010-8888-1111');");
        db.execSQL("insert into " + name + "(name, age, phone) values ('Sean', 26, '010-6677-4321');");

        return count;
    }

    private void createTable(String name) {
        String DROP_SQL = "drop table if exists " + name;
        db.execSQL(DROP_SQL);

        println("Creating table [ " + name + "].");

        db.execSQL("create table if not exists " + name + "("
                + " _id integer PRIMARY KEY autoincrement, "
                + " name text, "
                + " age integer, "
                + " phone text);");

        tableCreated = true;
    }

    private void createDatabase(String name) {
        println("Creating database [" + name + "].");

        try {
            db = openOrCreateDatabase(
                    name,
                    Activity.MODE_PRIVATE,
                    null
            );
            databaseCreated = true;
            println("Database is created.");
        } catch (Exception ex) {
            ex.printStackTrace();
            println("Database is not created.");
        }
    }

    private int insertRecordParam(String name) {
        println("Inserting records using parameters.");

        ContentValues recordValues = new ContentValues();
        recordValues.put("name", "Rice");
        recordValues.put("age", 43);
        recordValues.put("phone", "010-3322-9811");

        int rowPosition = (int) db.insert(name, null, recordValues);
        return rowPosition;
    }

    private int updateRecordParam(String name) {
        println("Updating records using parameters.");

        ContentValues recordValues = new ContentValues();
        recordValues.put("age", 43);
        String[] whereArgs = {"Rice"};

        int rowAffected = db.update(
                name,
                recordValues,
                "name = ?",
                whereArgs
        );
        return rowAffected;
    }

    private int deleteRecordParam(String name) {
        println("Deleting records using parameters.");

        String[] whereArgs = {"Rice"};

        int rowAffected = db.delete(
                name,
                "name = ?",
                whereArgs
        );

        return rowAffected;
    }

    private void executeRawQueryParam2(String tableName) {
        println("\nexecuteRawQueryParam2 called\n");

        String[] columns = {"name", "age", "phone"};
        String whereStr = " age > ?";
        String[] whereParams = {"30"};
        Cursor c1 = db.query(tableName, columns, whereStr, whereParams, null, null, null);

        int recordCount = c1.getCount();
        println("Cursor count : " + recordCount + "\n");

        for (int i = 0; i < recordCount; i++) {
            c1.moveToNext();
            String name = c1.getString(0);
            int age = c1.getInt(1);
            String phone = c1.getString(2);

            println("Record #" + i + " : " + name + ", " + age + ", " + phone);

            c1.close();
        }

    }
}
