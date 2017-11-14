package com.example.yoonjaecho.ms_hw08_201202154;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {

    private EditText companyText, productText, priceText, descriptionText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        companyText = (EditText) findViewById(R.id.add_company_editText);
        productText = (EditText) findViewById(R.id.add_product_editText);
        priceText = (EditText) findViewById(R.id.add_price_editText);
        descriptionText = (EditText) findViewById(R.id.add_descriptiong_editText);

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingItem item = new ShoppingItem(R.drawable.clothes5,
                        companyText.getText().toString(),
                        productText.getText().toString(),
                        priceText.getText().toString(),
                        descriptionText.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("data", item);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
