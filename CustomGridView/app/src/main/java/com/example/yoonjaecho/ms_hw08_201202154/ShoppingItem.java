package com.example.yoonjaecho.ms_hw08_201202154;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yoonjaecho on 08/11/2017.
 */

public class ShoppingItem implements Parcelable {

    private int resId;
    private String companyName;
    private String productName;
    private String price;
    private String description;

    public ShoppingItem(int redId, String companyName, String productName, String price, String description) {
        this.resId = redId;
        this.companyName = companyName;
        this.productName = productName;
        this.price = price;
        this.description = description;
    }

    public ShoppingItem(Parcel in) {
        this.resId = in.readInt();
        this.companyName = in.readString();
        this.productName = in.readString();
        this.price = in.readString();
        this.description = in.readString();
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int redId) {
        this.resId = redId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static final Creator<ShoppingItem> CREATOR = new Creator<ShoppingItem>() {
        @Override
        public ShoppingItem createFromParcel(Parcel parcel) {
            return new ShoppingItem(parcel);
        }

        @Override
        public ShoppingItem[] newArray(int i) {
            return new ShoppingItem[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.resId);
        parcel.writeString(this.companyName);
        parcel.writeString(this.productName);
        parcel.writeString(this.price);
        parcel.writeString(this.description);
    }
}
