package com.view.ListView.OptimizationListView;

/**
 * Created by sjd on 2017/2/19.
 */

public class Bean {
    private String titile;
    private String desc;
    private String time;
    private String phone;

    public Bean(String titile, String desc, String time, String phone) {
        this.titile = titile;
        this.desc = desc;
        this.time = time;
        this.phone = phone;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
