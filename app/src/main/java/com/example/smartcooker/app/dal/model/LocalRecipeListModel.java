package com.example.smartcooker.app.dal.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ke on 2018/5/5.
 */
@Entity
public class LocalRecipeListModel {
    @Id
    private Long id;
    private String image_left;
    private String name;
    private int like;
    private String image_right;
    private String time;

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getImage_left() {
        return image_left;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLike() {
        return like;
    }

    public String getImage_right() {
        return image_right;
    }

    public void setImage_left(String image_left) {
        this.image_left = image_left;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void setImage_right(String image_right) {
        this.image_right = image_right;
    }

    @Keep
    public LocalRecipeListModel(Long id, String image_left, String name, int like, String image_right, String time) {
        this.id = id;
        this.image_left = image_left;
        this.name = name;
        this.like = like;
        this.image_right = image_right;
        this.time = time;
    }

    public LocalRecipeListModel(RecipeDetailModel detailModel) {
        this.id = detailModel.getRecipeId();
        this.image_left = detailModel.getImage();
        this.name = detailModel.getSource();
        this.time = detailModel.getTime() + "分钟";
    }

    @Generated(hash = 4031925)
    public LocalRecipeListModel() {
    }
}

