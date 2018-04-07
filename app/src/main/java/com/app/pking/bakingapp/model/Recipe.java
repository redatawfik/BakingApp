package com.app.pking.bakingapp.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

@org.parceler.Parcel
public class Recipe {


    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("ingredients")
    private List<Ingredient> ingeddientList;
    @SerializedName("steps")
    private List<Step> stepList;
    @SerializedName("servings")
    private int servings;
    @SerializedName("image")
    private String image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngeddientList() {
        return ingeddientList;
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }
}
