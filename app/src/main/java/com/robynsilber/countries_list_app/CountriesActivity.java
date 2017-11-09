package com.robynsilber.countries_list_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

public class CountriesActivity extends AppCompatActivity implements CountryDataAsyncTask.IAsyncTaskResponder {

    private Country[] mCountryModel;
    private CountryDataAsyncTask mCountryDataAsyncTask;
    private ListView mListView;

    private final String LOG_TAG = CountriesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        mListView = (ListView)findViewById(R.id.list_view);
        Log.d(LOG_TAG, "Before AsyncTask");
        retrieveCountryData();

    }

    public void retrieveCountryData(){

        mCountryDataAsyncTask = new CountryDataAsyncTask(this); // instantiates CountryDataAsyncTask

        // execute the WeatherDataAsyncTask; pass in the lat and lon
        mCountryDataAsyncTask.execute();
    }

    @Override
    public void asyncTaskFinished(Country[] countries) {
        Log.d(LOG_TAG, "AsyncTask finished");
        mCountryModel = new Country[countries.length];
        int i=0;
        for(Country c : countries){
            mCountryModel[i] = c;
            Log.d(LOG_TAG, mCountryModel[i].getName());
            i++;
        }
        updateListUI();
    }

    public void updateListUI(){

        CountryAdapter countryAdapter = new CountryAdapter(this, mCountryModel);
        mListView.setAdapter(countryAdapter);
    }
}
