package com.example.smartcooker.app.dal.model;

/**
 * Created by ke on 2018/4/17.
 */

public class CloudRecipeListModel {
    private int id;
    private String count_image;
    private String circle_image;
    private String name;
    private int like_count;

    public CloudRecipeListModel(int id, String count_image, String circle_image, String name, int like_count) {
        this.id = id;
        this.count_image = count_image;
        this.circle_image = circle_image;
        this.name = name;
        this.like_count = like_count;
    }

    public int getId() {
        return id;
    }

    public String getCount_image() {
        return count_image;
    }

    public String getCircle_image() {
        return circle_image;
    }

    public String getName() {
        return name;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCount_image(String count_image) {
        this.count_image = count_image;
    }

    public void setCircle_image(String circle_image) {
        this.circle_image = circle_image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }
}
