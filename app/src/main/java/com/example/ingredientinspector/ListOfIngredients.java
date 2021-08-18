package com.example.ingredientinspector;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListOfIngredients {

    //Fields
    public ArrayList<String> ingredients;

    //Constructor
    public ListOfIngredients(Context context) throws IOException {
        InputStream input = null;
        try{
           input = context.getAssets().open("ingredientList.txt");
        }catch(IOException e){
            e.getLocalizedMessage();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        ingredients= new ArrayList<>();
        String st;
        while((st = br.readLine()) != null){
            ingredients.add(st.trim().toUpperCase());
        }
    }

    //Getter
    public ArrayList<String> returnList(){
        return ingredients;
    }
}
