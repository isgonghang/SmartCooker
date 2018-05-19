package com.example.smartcooker.app.dal.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

import java.util.HashMap;
import java.util.TreeMap;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by ke on 2018/5/7.
 */
@Entity
public class RecipeDetailModel {
    @Id
    @Unique
    private Long recipeId;
    private String image;
    private float score;
    private float time;
    private String source;
    private String other;
    @Convert(converter = MyObjectConverent.class, columnType = String.class)
    private TreeMap<Integer, Float> map = new TreeMap<>();

    @Keep
    public RecipeDetailModel(Long recipeId, String image, float score, float time, String source, String other, TreeMap<Integer, Float> map) {
        this.recipeId = recipeId;
        this.image = image;
        this.score = score;
        this.time = time;
        this.source = source;
        this.other = other;
        this.map = map;
    }



    @Generated(hash = 1028250336)
    public RecipeDetailModel() {
    }



    public Long getRecipeId() {
        return recipeId;
    }

    public String getImage() {
        return image;
    }

    public float getScore() {
        return score;
    }

    public float getTime() {
        return time;
    }

    public String getSource() {
        return source;
    }

    public String getOther() {
        return other;
    }

    public TreeMap<Integer, Float> getMap() {
        return map;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public void setMap(TreeMap<Integer, Float> map) {
        this.map = map;
    }
}
