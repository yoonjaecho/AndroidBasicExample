package com.example.yoonjaecho.ms_hw07_201202154;

/**
 * Created by yoonjaecho on 27/10/2017.
 */

public class CustomerItem {

    private String name;
    private String mobile;
    private String birth;
    private int redId;

    public CustomerItem(String name, String mobile, String birth, int redId) {
        this.name = name;
        this.mobile = mobile;
        this.birth = birth;
        this.redId = redId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getRedId() {
        return redId;
    }

    public void setRedId(int redId) {
        this.redId = redId;
    }
}
