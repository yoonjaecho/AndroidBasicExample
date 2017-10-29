package com.example.yoonjaecho.ms_hw07_201202154;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private EditText addNameText, addMobileText, addBirthText;
    private Button addButton;
    private TextView numOfCustomerText;
    private ListView listView;
    private CustomerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNameText = (EditText) findViewById(R.id.addNameText);
        addMobileText = (EditText) findViewById(R.id.addMobileText);
        addBirthText = (EditText) findViewById(R.id.addBirthText);
        numOfCustomerText = (TextView) findViewById(R.id.numOfCustomerText);

        addNameText.setFocusable(true);

        listView = (ListView) findViewById(R.id.customerListView);
        adapter = new CustomerAdapter();
        listView.setAdapter(adapter);

        addButton = (Button) findViewById(R.id.addItemButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = addNameText.getText().toString();
                String mobile = addMobileText.getText().toString();
                String birth = addBirthText.getText().toString();

                if(name.equals("") | mobile.equals("") | birth.equals("")) {
                    Toast.makeText(getApplicationContext(), "정보를 정확하게 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.addItem(new CustomerItem(name, mobile, birth, R.drawable.customer));
                    adapter.notifyDataSetChanged();
                }
            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int itemIndex, long l) {
                CustomerItem item = (CustomerItem) adapter.getItem(itemIndex);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("안내");
                builder.setMessage("삭제하시겠습니까?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setNegativeButton("취소", null);
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int ii) {
                        adapter.removeItem(itemIndex);
                        adapter.notifyDataSetChanged();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private class CustomerAdapter extends BaseAdapter {
        LinkedList<CustomerItem> items = new LinkedList<CustomerItem>();

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

        public void addItem(CustomerItem item) {
            items.add(item);
        }

        public void removeItem(int i) {
            items.remove(i);
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            CustomerItemView view = new CustomerItemView(getApplicationContext());

            CustomerItem item = items.get(i);
            view.setName(item.getName());
            view.setMobile(item.getMobile());
            view.setBirth(item.getBirth());
            view.setImage(item.getRedId());

            return view;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            addNameText.setText("");
            addBirthText.setText("");
            addMobileText.setText("");
            numOfCustomerText.setText(String.valueOf(adapter.getCount()) + "명");
            addNameText.requestFocus();
        }
    }
}
