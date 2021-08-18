package com.example.ingredientinspector;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class IngredientListView extends AppCompatActivity {

    //Fields
    DatabaseHelper mDatabaseHelper;
    private ListView mListView;

    //On startup
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list_view);
        mListView = (ListView) findViewById(R.id.listViewFoodSearch);
        mDatabaseHelper = new DatabaseHelper(this);
        populateListView();

    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        populateListView();
    }

    //Functions

    //populates List View
    private void populateListView() {
        //get data and add to a list
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            if(data.getString(1) != null){
                listData.add(data.getString(1));
            }
        }

        //create adapter and set it
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

            //set an onItemClickListener to ListView
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name = parent.getItemAtPosition(position).toString();
                    Cursor dataID = mDatabaseHelper.getItemID(name);
                    Cursor dataLabel = mDatabaseHelper.getItemLabel(name);

                    int itemID = -1;
                    String itemLabel = "";

                    while(dataID.moveToNext()){
                        itemID = dataID.getInt(0);
                    }

                    while(dataLabel.moveToNext()){
                        itemLabel = dataLabel.getString(0);
                    }

                    //if not an error, which is -1
                    if(itemID > -1){
                        Intent editIngredientLabelIntent = new Intent(IngredientListView.this, EditIngredientLabel.class);
                        editIngredientLabelIntent.putExtra("id", itemID);
                        editIngredientLabelIntent.putExtra("name", name);
                        editIngredientLabelIntent.putExtra("label", itemLabel);
                        startActivity(editIngredientLabelIntent);

                    }else{
                        toastMessage("No ID associated with that name");
                    }
                }
            });
    }



    //toastMessage
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}