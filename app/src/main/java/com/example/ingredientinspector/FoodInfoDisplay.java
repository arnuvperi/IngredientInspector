package com.example.ingredientinspector;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FoodInfoDisplay extends AppCompatActivity {

    //Fields

    private TextView txtFoodName, txtBrandName, txtFullListOfIngredeints, txtAllergy, txtFlag, txtGood;
    private DatabaseHelper mDatabaseHelper;


    //On startup
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info_display);

        //Set Fields to ID
        mDatabaseHelper = new DatabaseHelper(this);
        txtFoodName = (TextView) findViewById(R.id.textViewFoodName);
        txtBrandName = (TextView) findViewById(R.id.textViewBrandName);
        txtFullListOfIngredeints = (TextView) findViewById(R.id.textViewListOfIngredients);
        txtAllergy = (TextView) findViewById(R.id.textViewAllergyLabel);
        txtFlag = (TextView) findViewById(R.id.textViewLabelFlag);
        txtGood = (TextView) findViewById(R.id.textViewLabelGood);

        //Get extras
        Bundle extras = getIntent().getExtras();
        int fdcID = extras.getInt("indexOfBrandedFood");

        //Find food
        BrandedFood displayFood = new BrandedFood();
        for (BrandedFood f: InspectorSearch.searchResults) {
            if(f.getFdcID().equals(fdcID)){
                displayFood = f;
            }
        }

        //Set txt fields to information
        txtFoodName.setText(displayFood.getFoodName());
        txtBrandName.setText(displayFood.getBrandName());
        txtFullListOfIngredeints.setText(displayFood.getStrIngredients());

        //Set color to txt fields
        txtAllergy.setTextColor(Color.parseColor(MainActivity.allergies.getColor()));
        txtFlag.setTextColor(Color.parseColor(MainActivity.flag.getColor()));
        txtGood.setTextColor(Color.parseColor(MainActivity.good.getColor()));

        //Display flagged ingredients, if any
        String allergyString = "";
        String flagString = "";
        String goodString = "";
        if(displayFood.getListIngredient().isEmpty()){
            displayFood.generateIngredientArray(mDatabaseHelper);
        }
        for (Ingredient i:
        displayFood.getListIngredient()) {
            System.out.println(i.getName());
            if(i.getLabel().equals(MainActivity.allergies)){
                allergyString += i.getName() + ", ";
            }else if(i.getLabel().equals(MainActivity.flag)){
                flagString += i.getName() + ", ";
            }else if(i.getLabel().equals(MainActivity.good)){
                goodString += i.getName() + ", ";
            }
        }
        if(allergyString.equals("")){
            txtAllergy.setText("None");
        }else{
            txtAllergy.setText(allergyString.substring(0, allergyString.length()-2));
        }
        if(flagString.equals("")){
            txtFlag.setText("None");
        }else{
            txtFlag.setText(flagString.substring(0, flagString.length()-2));
        }
        if(goodString.equals("")){
            txtGood.setText("None");
        }else{
            txtGood.setText(goodString.substring(0, goodString.length()-2));
        }
    }
}