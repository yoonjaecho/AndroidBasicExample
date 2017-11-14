package com.example.yoonjaecho.ms_hw08_201202154;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by yoonjaecho on 08/11/2017.
 */

public class ShoppingItemView extends LinearLayout {

    private ImageView itemImg;
    private TextView companyNameText, productNameText, priceText, descriptionText;

    public ShoppingItemView(Context context) {
        super(context);
        init(context);
    }

    public ShoppingItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.shopping_item, this, true);

        itemImg = (ImageView) findViewById(R.id.imageView);
        companyNameText = (TextView) findViewById(R.id.company_textView);
        productNameText = (TextView) findViewById(R.id.product_textView);
        priceText = (TextView) findViewById(R.id.price_textView);
        descriptionText = (TextView) findViewById(R.id.description_textView);
    }

    public void setItemImg(int resId) {
        itemImg.setImageResource(resId);
    }

    public void setCompanyNameText(String company) {
        companyNameText.setText("[" + company + "]");
    }

    public void setProductNameText(String product) {
        productNameText.setText(product);
    }

    public void setPriceText(String price) {
        priceText.setText(price);
    }

    public void setDescriptionText(String description) {
        descriptionText.setText(description);
    }
}
