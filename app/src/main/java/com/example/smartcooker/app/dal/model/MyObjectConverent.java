package com.example.smartcooker.app.dal.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.lang.reflect.Type;
import java.util.TreeMap;

/**
 * Created by ke on 2018/5/8.
 */

public class MyObjectConverent implements PropertyConverter<TreeMap<Integer, Float>, String> {

    @Override
    public TreeMap<Integer, Float> convertToEntityProperty(String databaseValue) {
        Type type = new TypeToken<TreeMap<Integer, Float>>() {
        }.getType();
        TreeMap<Integer, Float> items = new Gson().fromJson(databaseValue, type);
        return items;
    }

    @Override
    public String convertToDatabaseValue(TreeMap<Integer, Float> entityProperty) {
        String dbString = new Gson().toJson(entityProperty);
        return dbString;
    }
}