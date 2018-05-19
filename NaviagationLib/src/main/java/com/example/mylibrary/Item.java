package com.example.mylibrary;



/**
 * Created by ke on 2018/4/23.
 */

public class Item {
    private int id,id2;
    private String text;

    public Item() {
    }

    public Item(int id, int id2,String text) {
        this.id = id;
        this.id2=id2;
        this.text = text;
    }


    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getId2() {
        return id2;
    }
}

