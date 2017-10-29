package com.example.yoonjaecho.week7_practice3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private GridView gridView;
    SingerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new SingerAdapter();
        adapter.addItem(new SingerItem("소녀시대", "010-1000-1000", 2007, R.drawable.girlsgeneration));
        adapter.addItem(new SingerItem("에이핑크", "010-2000-2000", 2011, R.drawable.apink));
        adapter.addItem(new SingerItem("여자친구", "010-3000-3000", 2015, R.drawable.girlfriend));
        adapter.addItem(new SingerItem("레드벨벳", "010-4000-4000", 2014, R.drawable.redvelvet));
        adapter.addItem(new SingerItem("AOA", "010-5000-5000", 2012, R.drawable.aoa));
        adapter.addItem(new SingerItem("트와이스", "010-6000-6000", 2015, R.drawable.twice));

        gridView.setAdapter(adapter);

        editText = (EditText) findViewById(R.id.editText);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SingerItem item = (SingerItem) adapter.getItem(i);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class SingerAdapter extends BaseAdapter {
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public void addItem(SingerItem item) {
            items.add(item);
        }

        @Override
        public View getView(int i, View converView, ViewGroup viewGroup) {
            SingerItemView view = new SingerItemView(getApplicationContext());

            SingerItem item = items.get(i);
            view.setName(item.getName());
            view.setMobile(item.getMobile());
            view.setYear(item.getYear());
            view.setImage(item.getResId());

            int numColumns = gridView.getNumColumns();
            int rowIndex = i / numColumns;
            int columnIndex = i % numColumns;
            Log.d("SingerAdapter", "index : " + rowIndex + ", " + columnIndex);

            return view;
        }
    }
}
