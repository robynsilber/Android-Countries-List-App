package com.robynsilber.countries_list_app;


import android.os.AsyncTask;

public class CountryDataAsyncTask extends AsyncTask<String, Void, Country[]> {


    @Override
    protected Country[] doInBackground(String... strings) {
        return new Country[0];
    }
}
