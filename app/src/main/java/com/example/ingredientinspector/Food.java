package com.example.ingredientinspector;

import com.google.gson.annotations.SerializedName;

public class Food {

    //Fields

    @SerializedName("fdcId")
    private Integer fdcID;

    @SerializedName("description")
    private String foodName;

    @SerializedName("ingredients")
    private String strIngredients;

    //Constructors
    public Food(Integer fdcID, String foodName, String strIngredients) {
        this.fdcID = fdcID;
        this.foodName = foodName;
        this.strIngredients = strIngredients.toUpperCase();
    }

    public Food(){
    }

    //Getters and Setters

    public Integer getFdcID() {
        return fdcID;
    }

    public void setFdcID(Integer fdcID) {
        this.fdcID = fdcID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getStrIngredients() {
        return strIngredients;
    }

    public void setStrIngredients(String strIngredients) {
        this.strIngredients = strIngredients;
    }
}
