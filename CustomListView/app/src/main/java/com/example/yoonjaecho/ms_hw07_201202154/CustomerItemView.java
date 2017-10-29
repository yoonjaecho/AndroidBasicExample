package com.example.yoonjaecho.ms_hw07_201202154;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by yoonjaecho on 27/10/2017.
 */

public class CustomerItemView extends LinearLayout {

    private TextView nameText, mobileText, birthText;
    private ImageView imageView;

    public CustomerItemView(Context context) {
        super(context);
        init(context);
    }

    public CustomerItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.customer_item, this, true);

        nameText = (TextView) findViewById(R.id.nameText);
        mobileText = (TextView) findViewById(R.id.mobileText);
        birthText = (TextView) findViewById(R.id.birthText);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void setName(String name) {
        nameText.setText(name);
    }

    public void setMobile(String mobile) {
        mobileText.setText(mobile);
    }

    public void setBirth(String birth) {
        birthText.setText(birth);
    }

    public void setImage(int resId) {
        imageView.setImageResource(resId);
    }
}
