package com.robynsilber.countries_list_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CountriesActivity extends AppCompatActivity
        implements CountryDataAsyncTask.IAsyncTaskResponder {

    private Country[] mCountryModel;
    private CountryDataAsyncTask mCountryDataAsyncTask;
    private ListView mListView;
    private List<Country> mAllCountriesList;
    private Button mSearchButton;
    private Button mClearButton;
    private EditText mEditText;
    private List<Country> mFilteredCountries;
    private boolean filteredList = false;

    private final String LOG_TAG = CountriesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        mListView = (ListView)findViewById(R.id.list_view);
        mEditText = (EditText)findViewById(R.id.search_edittext);
        mSearchButton = (Button)findViewById(R.id.search_btn);
        mClearButton = (Button)findViewById(R.id.clear_btn);
        Log.d(LOG_TAG, "Before AsyncTask");
        retrieveCountryData();
    }

    public void retrieveCountryData(){
        mCountryDataAsyncTask = new CountryDataAsyncTask(this); // instantiates asynctask
        mCountryDataAsyncTask.execute(); // executes asynctask
    }

    @Override
    public void asyncTaskFinished(Country[] countries) {
        Log.d(LOG_TAG, "AsyncTask finished");
        mCountryModel = new Country[countries.length];
        mAllCountriesList = new ArrayList<Country>();
        int i=0;
        for(Country c : countries){
            mCountryModel[i] = c;
            mAllCountriesList.add(c);
            Log.d(LOG_TAG, mCountryModel[i].getName());
            i++;
        }
        Collections.sort(mAllCountriesList, Country.CountryComparator);
        updateListUI();
    }

    public void updateListUI(){
        filteredList = false;
        mClearButton.setVisibility(View.INVISIBLE);
        CountryAdapter countryAdapter = new CountryAdapter(this, mCountryModel);
        mListView.setAdapter(countryAdapter);
    }


    public void filterCountriesList(View view) {
        if(mEditText.getText().toString().length() > 0){
            mClearButton.setVisibility(View.VISIBLE);
//            mFilteredCountries = new ArrayList<>();
            getFilteredCountriesList(mEditText.getText().toString());
            filteredList = true;
            CountryAdapter filteredCountryAdapter = new CountryAdapter(this, mFilteredCountries);
            mListView.setAdapter(filteredCountryAdapter);
        }else if(filteredList){
            updateListUI();
        }
    }

    public void clearFilteredCountriesList(View view) {
        mEditText.setText("");
        mEditText.setHint(R.string.type_a_country_name_label);
        updateListUI();
    }

    public void getFilteredCountriesList(String str){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            return mAllCountriesList.stream()
//                    .filter(item -> item.getName().toLowerCase().startsWith(str.toLowerCase()))
//                    .collect(Collectors.toList());
//        }else{
            mFilteredCountries = new ArrayList<>();
            boolean startsWithTrue = false;
            for(int i = 0; i< mAllCountriesList.size(); i++){
                if(mAllCountriesList.get(i).getName().toLowerCase().startsWith(str.toLowerCase())){
                    startsWithTrue = true;
                    mFilteredCountries.add(mAllCountriesList.get(i));
                }else if(startsWithTrue){
                    break;
                }
            }
//            return filteredCountries;
//        }
    }
}
