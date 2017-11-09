package com.robynsilber.countries_list_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class CountriesActivity extends AppCompatActivity implements CountryDataAsyncTask.IAsyncTaskResponder {

    private Country[] mCountryModel;
    private CountryDataAsyncTask mCountryDataAsyncTask;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        retrieveCountryData();
    }

    public void retrieveCountryData(){

        mCountryDataAsyncTask = new CountryDataAsyncTask(this); // instantiates CountryDataAsyncTask

        // execute the WeatherDataAsyncTask; pass in the lat and lon
        mCountryDataAsyncTask.execute();
    }

    @Override
    public void asyncTaskFinished(Country[] countries) {
        mCountryModel = new Country[countries.length];
        int i=0;
        for(Country c : countries){
            mCountryModel[i] = c;
            i++;
        }

    }
}
