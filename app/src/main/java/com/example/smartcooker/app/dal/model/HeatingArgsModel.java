package com.example.smartcooker.app.dal.model;

/**
 * Created by ke on 2018/4/17.
 */

public class HeatingArgsModel {
    private long time;
    private int pre;
    private int temp;

    public HeatingArgsModel() {

    }

    public HeatingArgsModel(long time, int pre, int temp) {
        this.time = time;
        this.pre = pre;
        this.temp = temp;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setPre(int pre) {
        this.pre = pre;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
