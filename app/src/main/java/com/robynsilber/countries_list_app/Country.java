package com.robynsilber.countries_list_app;


import android.support.annotation.NonNull;

import java.util.Comparator;

public class Country implements Comparable<Country> {

    private String mName;

    public Country(String name){
        mName = name;
    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }


    @Override
    public int compareTo(@NonNull Country country) {
        return this.mName.compareTo(country.getName());
    }

    public static Comparator<Country> CountryComparator
            = new Comparator<Country>() {
        @Override
        public int compare(Country country1, Country country2) {
            return country1.getName().compareTo(country2.getName());
        }
    };
}




