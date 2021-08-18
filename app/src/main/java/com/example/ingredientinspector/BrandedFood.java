package com.example.ingredientinspector;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BrandedFood extends Food{

    //Fields

    @SerializedName("brandOwner")
    private String brandName;
    private ArrayList<Ingredient> listIngredient = new ArrayList<>();

    //Constructors

    public BrandedFood(String brandName, String foodName, String strIngredients, Integer fdcID) {
        super(fdcID, foodName, strIngredients);
        this.brandName = brandName;
    }

    public BrandedFood(){
        super();
    }

    //Getters and Setters

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getFdcID() {
        return super.getFdcID();
    }

    public void setFdcID(Integer fdcID) {
        super.setFdcID(fdcID);
    }

    public String getFoodName() {
        return super.getFoodName();
    }

    public void setFoodName(String foodName) {
        super.setFoodName(foodName);
    }

    public String getStrIngredients() {
        return super.getStrIngredients();
    }

    public void setStrIngredients(String strIngredients) {
        super.setStrIngredients(strIngredients);
    }

    public ArrayList<Ingredient> getListIngredient(){
        return listIngredient;
    }

    //Functions

    public void generateIngredientArray(DatabaseHelper mDatabaseHelper){
        Cursor data = mDatabaseHelper.getData();
        while(data.moveToNext()){
            if(data.getString(1) != null){
                String name = data.getString(1);
                if(super.getStrIngredients().contains(name)){
                    Cursor dataLabel = mDatabaseHelper.getItemLabel(name);
                    String itemLabel = "";

                    while(dataLabel.moveToNext()){
                        itemLabel = dataLabel.getString(0);
                    }
                    Ingredient labelIngredient = new Ingredient(name);
                    labelIngredient.setLabel(labelFinder(itemLabel));
                    listIngredient.add(labelIngredient);
                }

            }
        }
    }

    public Label labelFinder(String labelName){
        Label label = new Label();
        switch (labelName){
            case "Allergies":
                label =  MainActivity.allergies;
                break;
            case "Flag":
                label = MainActivity.flag;
                break;
            case "Good":
                label = MainActivity.good;
                break;
        }
        return label;
    }


    //ToString method

    public String toString(){
        return getBrandName() + ": " + getFoodName();
    }

}
