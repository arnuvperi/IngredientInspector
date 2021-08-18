package com.example.ingredientinspector;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditIngredientLabel extends AppCompatActivity {

    //Fields
    private Button btnSave, btnDelete;
    private TextView TextName;
    private Switch switchAllergy2, switchFlag2, switchGood2;
    DatabaseHelper mDatabaseHelper;
    private String selectedName, labelName;
    private int selectedID;

    //Startup

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ingredient_label);

        //Assign IDs
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        TextName = (TextView) findViewById(R.id.textViewIngredientName);
        switchAllergy2 = (Switch) findViewById(R.id.switchAllergy2);
        switchFlag2 = (Switch) findViewById(R.id.switchFlag2);
        switchGood2 = (Switch) findViewById(R.id.switchGood2);
        mDatabaseHelper = new DatabaseHelper(this);

        //Set Color
        switchAllergy2.setTextColor(Color.parseColor(MainActivity.allergies.getColor()));
        switchFlag2.setTextColor(Color.parseColor(MainActivity.flag.getColor()));
        switchGood2.setTextColor(Color.parseColor(MainActivity.good.getColor()));

        //get the intent extra
        Bundle extras = getIntent().getExtras();
        selectedID = extras.getInt("id", -1);
        selectedName = extras.getString("name");

        //Get Data
        Cursor label = mDatabaseHelper.getItemLabel(selectedName);

        //Check for Labels
        while(label.moveToNext()){
            labelName = label.getString(0);
        }
        switch(labelName){
            case "Allergies":
                switchAllergy2.setChecked(true);
                break;
            case "Flag":
                switchFlag2.setChecked(true);
                break;
            case "Good":
                switchGood2.setChecked(true);
        }

        //Set text to name
        TextName.setText(selectedName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AddData(switchAllergy2, switchFlag2, switchGood2, labelName);
            }
        });

    btnDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mDatabaseHelper.deleteName(selectedID, selectedName);
            TextName.setText("");
            switchAllergy2.setChecked(false);
            switchFlag2.setChecked(false);
            switchGood2.setChecked(false);
            toastMessage("Removed from database");
        }
    });


    }

    //Add data back into database
    public void AddData(Switch sAllergy, Switch sFlag, Switch sGood, String oldLabel) {
        boolean insertData = false;
            if (sAllergy.isChecked()) {
                if (sFlag.isChecked() || sGood.isChecked()) {
                    toastMessage("Please only select one label");
                } else {
                    mDatabaseHelper.updateLabel("Allergies", selectedID,oldLabel);
                    insertData = true;
                }
            } else if (sFlag.isChecked()) {
                if (sAllergy.isChecked() || sGood.isChecked()) {
                    toastMessage("Please only select one label");
                } else {
                    mDatabaseHelper.updateLabel("Flag", selectedID,oldLabel);
                    insertData = true;
                }
            } else if (sGood.isChecked()) {
                if (sAllergy.isChecked() || sFlag.isChecked()) {
                    toastMessage("Please only select one label");
                } else {
                    mDatabaseHelper.updateLabel("Good", selectedID,oldLabel);
                    insertData = true;
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

    //toastMessage
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}