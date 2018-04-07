package com.app.pking.bakingapp.model;


import com.google.gson.annotations.SerializedName;

@org.parceler.Parcel
public class Ingredient {

    @SerializedName("quantity")
    private double quantity;
    @SerializedName("measure")
    private String measure;
    @SerializedName("ingredient")
    private String ingredient;


    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
