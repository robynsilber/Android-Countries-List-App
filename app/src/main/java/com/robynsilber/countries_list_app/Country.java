package com.robynsilber.countries_list_app;


public class Country {

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

//    public static ArrayList<Country> getFilteredCountries(ArrayList<String> arrayList, String s){
//        ArrayList<Country> countries = new ArrayList<Country>();
//        for(String c : arrayList){
//            if(c.startsWith(s)){
//                countries.add(new Country(c));
//            }
//        }
//
//        return countries;
//    }
}




