package com.example.ingredientinspector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InspectorSearch extends AppCompatActivity {

    //Fields

    public Button btnSearch;
    public EditText foodQuery, brandQuery;
    public static ArrayList<BrandedFood> searchResults = new ArrayList<>();

    //On startup
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspector_search);

        //Set fields to ID
        btnSearch = (Button) findViewById(R.id.buttonSearchFood);
        foodQuery = (EditText) findViewById(R.id.editTextFoodQuery);
        brandQuery = (EditText) findViewById(R.id.editTextBrandName);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get user search query
                String querySearch = foodQuery.getText().toString();
                String brandName = brandQuery.getText().toString();

                if(!querySearch.equals("")) {
                    searchResults.clear();
                    //run search 10 times to get top 10 food results
                    for (int i = 1; i <= 10; i++) {
                        String urlToUse = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key=JItV73pPfPqRMP0fC8MytU328VwxXj6MLJsbuWkl&query=" + querySearch + "&pageSize=1&pageNumber=" + i + "&brandOwner=" +brandName ;
                        FetchData process = new FetchData();
                        process.execute(urlToUse);
                    }

                    //Wait for HTML requests to go through
                    while(searchResults.size() != 10){
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    //If brand name is not valid
                    if(searchResults.get(0) == null){
                        toastMessage("That was not a valid brand name, please try again");
                    }else{
                        openFoodSearchListViewActivity();
                    }
                }else{
                    toastMessage("Please enter a food name");
                }
            }
        });

    }

    //Functions

    //open activity
    public void openFoodSearchListViewActivity(){
        Intent intentOpenIL = new Intent(this, FoodSearchListView.class);
        startActivity(intentOpenIL);
    }

    //toastMessage
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}