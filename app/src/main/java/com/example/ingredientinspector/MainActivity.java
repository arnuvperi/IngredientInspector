package com.example.ingredientinspector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //Label Instantiation
    static Label allergies = new Label("Allergies", "#c91c0c");
    static Label flag = new Label("Flag", "#ccb612");
    static Label good = new Label("Good", "#124dcc");

    //Fields
    private Button buttonIngredients, buttonInspector;

    //ON startup
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set buttons and ids

        buttonIngredients = (Button) findViewById(R.id.buttonIngredients);
        buttonIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIngredientActivity();
            }
        });

        buttonInspector = (Button) findViewById(R.id.buttonSearch);
        buttonInspector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInspectorActivity();
            }
        });
    }

    //Functions
    public void openIngredientActivity(){
        Intent intentOpenIL = new Intent(this, IngredientLabeler.class);
        startActivity(intentOpenIL);
    }

    public void openInspectorActivity(){
        Intent intentOpenIL = new Intent(this, InspectorSearch.class);
        startActivity(intentOpenIL);
    }

}