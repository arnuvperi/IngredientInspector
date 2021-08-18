package com.example.ingredientinspector;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class IngredientLabeler extends AppCompatActivity {

    //Fields
    private Button btnAddIngredients, btnViewData;
    DatabaseHelper mDatabaseHelper;
    private AutoCompleteTextView ingredientSearch;
    private Switch switchAllergy, switchFlag, switchGood;
    public ListOfIngredients ingredients;


    //On startup
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_labeler);

        //Open txt file
        try {
            ingredients = new ListOfIngredients(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Set fields to ID
        btnViewData = (Button) findViewById(R.id.buttonViewData);
        btnAddIngredients = (Button) findViewById(R.id.buttonAddIngredient);
        ingredientSearch= findViewById(R.id.ACIngredientSearch);
        switchAllergy = (Switch) findViewById(R.id.switchAllergy);
        switchFlag = (Switch) findViewById(R.id.switchFlag);
        switchGood = (Switch) findViewById(R.id.switchGood);
        mDatabaseHelper = new DatabaseHelper(this);

        //autocompleter for ingredients dropdown
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, ingredients.returnList());
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.ACIngredientSearch);
        ingredientSearch.setAdapter(adapter);

        //Set color for switch txt
        switchAllergy.setTextColor(Color.parseColor(MainActivity.allergies.getColor()));
        switchFlag.setTextColor(Color.parseColor(MainActivity.flag.getColor()));
        switchGood.setTextColor(Color.parseColor(MainActivity.good.getColor()));

        btnAddIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = ingredientSearch.getText().toString();

                //checking to see if input is a valid ingredient
                boolean isIngredient = false;
                for(String ingredient : ingredients.returnList()){
                    if(ingredient.equals(newEntry)){
                        isIngredient = true;
                    }
                }

                //adding ingredient to data storage
                if(newEntry.length() != 0 && isIngredient){
                    AddData(newEntry, switchAllergy, switchFlag, switchGood);
                    ingredientSearch.setText("");
                    switchAllergy.setChecked(false);
                    switchFlag.setChecked(false);
                    switchGood.setChecked(false);
                }else if(!isIngredient){
                    toastMessage("That is not a valid ingredient, please select from the available ingredients");
                }
                else{
                    toastMessage("You must put something in the text field");
                }
            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIngredientListViewActivity();
            }
        });
    }

    //Functions

    //adds data to list view
    public void AddData(String newEntry, Switch sAllergy, Switch sFlag, Switch sGood) {
        Ingredient ing = new Ingredient(newEntry);
        Cursor data = mDatabaseHelper.getData();

        //checks if data is already in
        boolean repeat = false;
        while(data.moveToNext()){
            if(newEntry.equals(data.getString(1))){
                toastMessage("This ingredient has already been added. Edit in the View Data Tab");
                repeat = true;
            }
        }
        boolean insertData = false;
        if(!repeat) {
            if (sAllergy.isChecked()) {
                if (sFlag.isChecked() || sGood.isChecked()) {
                    toastMessage("Please only select one label");
                } else {
                    ing.setLabel(MainActivity.allergies);
                    insertData = mDatabaseHelper.addData(ing);
                }
            } else if (sFlag.isChecked()) {
                if (sAllergy.isChecked() || sGood.isChecked()) {
                    toastMessage("Please only select one label");
                } else {
                    ing.setLabel(MainActivity.flag);
                    insertData = mDatabaseHelper.addData(ing);
                }
            } else if (sGood.isChecked()) {
                if (sAllergy.isChecked() || sFlag.isChecked()) {
                    toastMessage("Please only select one label");
                } else {

                    ing.setLabel(MainActivity.good);
                    insertData = mDatabaseHelper.addData(ing);
                }
            } else {
                toastMessage("Please select a label");
                insertData = false;
            }
            if (insertData) {
                toastMessage("Data Successfully Inserted");

            } else {
                toastMessage("Something went Wrong");
            }
        }
        }

    //opens activity
    public void openIngredientListViewActivity(){
        Intent intentOpenIL = new Intent(this, IngredientListView.class);
        startActivity(intentOpenIL);
    }


    //toastMessage
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}