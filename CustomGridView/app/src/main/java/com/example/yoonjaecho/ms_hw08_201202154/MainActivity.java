package com.example.yoonjaecho.ms_hw08_201202154;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private ActionBar actionBar;
    private ShoppingItemAdapter shoppingItemAdapter;

    public static final int REQEUST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = this.getSupportActionBar();
        actionBar.show();

        gridView = (GridView) findViewById(R.id.item_gridView);
        shoppingItemAdapter = new ShoppingItemAdapter();
        shoppingItemAdapter.addItem(new ShoppingItem(R.drawable.clothes1, "빈폴", "롱 코트", "300,000", "기획상품"));
        shoppingItemAdapter.addItem(new ShoppingItem(R.drawable.clothes2, "나이키", "운동화", "70,000", "특가상품"));
        shoppingItemAdapter.addItem(new ShoppingItem(R.drawable.clothes3, "폴로", "남방", "150,000", "기획상품"));
        shoppingItemAdapter.addItem(new ShoppingItem(R.drawable.clothes4, "리바이스", "모자", "40,000", "기획상품"));
        gridView.setAdapter(shoppingItemAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ShoppingItem item = (ShoppingItem) shoppingItemAdapter.getItem(i);
                Toast.makeText(MainActivity.this, "선택된 제품 : " + item.getProductName() + "\n가격 : " + item.getPrice(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addItem:
                Intent intent = new Intent(this, AddItemActivity.class);
                startActivityForResult(intent, REQEUST_CODE);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQEUST_CODE) {
                if (data != null) {
                    shoppingItemAdapter.addItem((ShoppingItem) data.getExtras().getParcelable("data"));
                }
            }
        }
    }

    private class ShoppingItemAdapter extends BaseAdapter {
        LinkedList<ShoppingItem> items = new LinkedList<ShoppingItem>();

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

        public void addItem(ShoppingItem item) {
            items.add(item);
            notifyDataSetChanged();
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ShoppingItemView view = new ShoppingItemView(getApplicationContext());

            ShoppingItem item = items.get(i);
            view.setItemImg(item.getResId());
            view.setCompanyNameText(item.getCompanyName());
            view.setProductNameText(item.getProductName());
            view.setPriceText(item.getPrice());
            view.setDescriptionText(item.getDescription());

            return view;
        }
    }
}
