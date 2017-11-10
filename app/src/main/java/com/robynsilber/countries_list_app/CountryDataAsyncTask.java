package com.robynsilber.countries_list_app;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CountryDataAsyncTask extends AsyncTask<Void, Void, Country[]> {

    public interface IAsyncTaskResponder {
        void asyncTaskFinished(Country[] countries);
    }

    public IAsyncTaskResponder mIAsyncTaskListener = null;

    public CountryDataAsyncTask(IAsyncTaskResponder responder){
        this.mIAsyncTaskListener = responder;
    }

    private final String LOG_TAG = CountryDataAsyncTask.class.getSimpleName();


    @Override
    protected Country[] doInBackground(Void... voids) {
        Log.d(LOG_TAG, "line 35");
        final String BASE_URL = "https://restcountries.eu/rest/v2/all";
//        final String BASE_URL = "http://www.geognos.com/api/en/countries/info/all.json";
//        final String BASE_URL = "http://country.io/names.json";
        String jsonData = null; // stores the retrieved json data

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        try{

            // Declare the URL
            URL url = new URL(BASE_URL);
            // Begin query to server by opening httpURLConnection
            httpURLConnection = (HttpURLConnection) url.openConnection();

            // Set the request method of the httpURLConnection to "GET"
            httpURLConnection.setRequestMethod("GET");

            Log.d(LOG_TAG, "line 54");
            // Connect to server
            httpURLConnection.connect();
            Log.d(LOG_TAG, "line 57");
            // Next step: Retrieve the country data
            // Proceed by getting the input stream returned from the query
            InputStream inputStream = httpURLConnection.getInputStream();
            Log.d(LOG_TAG, "line 61");
            if(inputStream == null){ // Failed to retrieve data
                Log.d(LOG_TAG, "Failed to retrieve country data");
                return null; // no data retrieved from server
            }
            Log.d(LOG_TAG, "line 66");
            // Parse the retrieved data
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            // initialize bufferedReader and stringBuffer
            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            Log.d(LOG_TAG, "line 74");
            // Decalare variables for parsing, reading data line by line (using the bufferedReader)
            String line;
            final String NEW_LINE = "\n";

            while((line=bufferedReader.readLine()) != null){
                // append stringBuffer by adding new line escape char.
                // The following is for readability and debugging/testing purposes
                stringBuffer.append(line + NEW_LINE);
            } // all the data has been initially parsed and appended

            Log.d(LOG_TAG, "line 85");

            if(stringBuffer.length() == 0){ // nothing to parse
                Log.d(LOG_TAG, "No data to parse");
                return null;
            }

            Log.d(LOG_TAG, "line 92");

            // Successful retrieval of JSON country data
            jsonData = stringBuffer.toString();
            Log.d(LOG_TAG, jsonData);


        }catch(IOException e){
            Log.e(LOG_TAG, "Error: ", e);
            return null;
        }finally {
            // Disconnect the httpURLConnection
            if(httpURLConnection != null){
                httpURLConnection.disconnect();
            }

            // Close the buffered reader
            if(bufferedReader != null){
                try{
                    bufferedReader.close();
                } catch(final IOException e){
                    Log.e(LOG_TAG, "Error: BufferedReader failed to close: ", e);
                }
            }
        }

        try{
            Country[] countries = formatJsonArray(jsonData);
            Log.d(LOG_TAG, "returned countries");
            return countries;

        } catch (JSONException e){
            Log.e(LOG_TAG, "Error: failed to format JSON data: ", e);
        }


        return null; // fail
    }

    private Country[] formatJsonArray(String jsonData) throws JSONException {

        // Tag for JSON data extraction
        final String NAME = "name";

        JSONArray jsonArr = new JSONArray(jsonData);
        Log.d(LOG_TAG,Integer.toString(jsonArr.length()));

        Country[] countries = new Country[jsonArr.length()];

        for(int i=0; i<jsonArr.length(); i++){
            JSONObject jsonObject = jsonArr.getJSONObject(i);
//            Log.d(LOG_TAG, jsonObject.getString(NAME));
            countries[i] = new Country(jsonObject.getString(NAME));
        }

        return countries;
    }


    @Override
    protected void onPostExecute(Country[] data) {
        if (data != null) {
            mIAsyncTaskListener.asyncTaskFinished(data);
        }
    }

}
