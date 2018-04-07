package com.app.pking.bakingapp.model;


import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Recipe implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("ingredients")
    private List<Ingredient> ingredientList;
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

    public List<Ingredient> getIngredientList() {
        return ingredientList;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.ingredientList);
        dest.writeTypedList(this.stepList);
        dest.writeInt(this.servings);
        dest.writeString(this.image);
    }

    public Recipe() {
    }

    protected Recipe(android.os.Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.ingredientList = in.createTypedArrayList(Ingredient.CREATOR);
        this.stepList = in.createTypedArrayList(Step.CREATOR);
        this.servings = in.readInt();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(android.os.Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
