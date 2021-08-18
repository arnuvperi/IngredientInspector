package com.example.ingredientinspector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FoodSearchListView extends AppCompatActivity {

    //Fields
    private ListView mListView;

    //On startup
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search_list_view);
        mListView = (ListView) findViewById(R.id.listViewFoodSearch);
        populateListView();

    }

    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        populateListView();
    }


    //Functions

    //populates List View
    private void populateListView() {

        //get data and add to a list
        ArrayList<String> displayData = new ArrayList<>();
        for (BrandedFood f: InspectorSearch.searchResults) {
            displayData.add(f.getBrandName() + ": " + f.getFoodName());
        }
        //create adapter and set it
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, displayData);
        mListView.setAdapter(adapter);

        //set an onItemClickListener to ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();
                String brandName = name.substring(0, name.indexOf(":"));
                String foodName = name.substring(name.indexOf(":") + 2);
                BrandedFood foodClicked = new BrandedFood();
                for (BrandedFood f: InspectorSearch.searchResults) {
                    if(f.getBrandName().equals(brandName) && f.getFoodName().equals(foodName)){
                        foodClicked = f;
                    }
                }

                Intent foodInfoDisplayintent = new Intent(FoodSearchListView.this, FoodInfoDisplay.class);
                foodInfoDisplayintent.putExtra("indexOfBrandedFood", foodClicked.getFdcID());
                startActivity(foodInfoDisplayintent);
            }
        });
    }


    //toastMessage
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}