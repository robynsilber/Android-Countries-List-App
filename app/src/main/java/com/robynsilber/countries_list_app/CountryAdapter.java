package com.robynsilber.countries_list_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class CountryAdapter extends BaseAdapter {

    private Context mContext;
    private Country[] mCountryArray;

    public CountryAdapter(Context context, Country[] countries){
        mContext = context;
        mCountryArray = new Country[countries.length];
        for(int i=0; i<countries.length; i++){
            mCountryArray[i] = countries[i];
        }
    }

    @Override
    public int getCount() {
        return mCountryArray.length;
    }

    @Override
    public Object getItem(int i) {
        return mCountryArray[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;  // not implementing this at the moment
    }
    
    /* Method is called for each item in the list. This method is called when
     * each view is being prepared to be shown on the screen. */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Gets the views that will be provided to the adapter.
        /* convertView is the object that we want to reuse. First time called:
         * convertView will be null. If null, create the view and set it up;
         * otherwise, reuse and reset the data. */

        // Declare ViewHolder. Used for efficient scrolling of views.
        ViewHolder holder;

        if(convertView == null){ // new view; create it using LayoutInflater
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
            // null is a ViewGroup root, which we can leave out here (hence null)

            holder = new ViewHolder(); // instantiates ViewHolder
            holder.countryName = (TextView) convertView.findViewById(R.id.country_name_textview);

            // Sets a tag for the view that will be reused below
            convertView.setTag(holder);

        }else{ // holder already associated with a view
            // call getTag() to reuse
            holder = (ViewHolder) convertView.getTag();
        }

        // Set the Person data
        Country country = mCountryArray[position];

        holder.countryName.setText(country.getName());

        return convertView;
    }


    public static class ViewHolder{
        TextView countryName;
    }
}
