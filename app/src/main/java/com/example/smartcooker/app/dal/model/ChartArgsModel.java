package com.example.smartcooker.app.dal.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ke on 2018/4/17.
 */

public class ChartArgsModel {
    private int grow_heat_time;
    private int heating_time;
    private float temp;

    public ChartArgsModel() {
    }

    public int getGrow_heat_time() {
        return grow_heat_time;
    }

    public int getHeating_time() {
        return heating_time;
    }

    public float getTemp() {
        return temp;
    }

    public void setGrow_heat_time(int grow_heat_time) {
        this.grow_heat_time = grow_heat_time;
    }

    public void setHeating_time(int heating_time) {
        this.heating_time = heating_time;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }
}
