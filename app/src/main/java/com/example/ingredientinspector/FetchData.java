package com.example.ingredientinspector;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchData extends AsyncTask<String, Void, Void> {

    //Fields
    private String data = "";
    private BrandedFood brandedFood;

    //AsyncTask
    @Override
    protected Void doInBackground(String... urlToUse) {
        //Have to create a class for what the JSON will have
        try {
            //Open Url
            URL url = new URL(urlToUse[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            while (line != null) {
                data += line;
                line = bufferedReader.readLine();
            }
            //Get Food Data specifically from json String
            String foodData = data.substring(data.indexOf("foods") + 8, data.indexOf("aggregations") - 3);

            //Set data to Gson and BrandedFood class
            Gson gson = new Gson();
            brandedFood = gson.fromJson(foodData, BrandedFood.class);

            //Add back to searchResults
            InspectorSearch.searchResults.add(brandedFood);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
