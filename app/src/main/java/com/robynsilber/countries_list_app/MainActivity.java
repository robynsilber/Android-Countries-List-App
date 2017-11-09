package com.robynsilber.countries_list_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getAllTheCountries(View view) {
        Intent intent = new Intent(this, CountriesActivity.class);
        /* Consider executing AsyncTask here */
        startActivity(intent);
    }
}
