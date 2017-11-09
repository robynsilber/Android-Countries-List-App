package com.robynsilber.countries_list_app;


import android.os.AsyncTask;
import android.util.Log;

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

        final String BASE_URL = "https://restcountries.eu/rest/v2/all";

        String jsonData = null; // stores the retrieved json data

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        try{
//            Uri uri = Uri.parse(BASE_URL).buildUpon().build();

            // Declare the URL, using the uri String as arg
//            URL url = new URL(uri.toString()); // requires an IOException as the catch block param
            URL url = new URL(BASE_URL);
            // Begin query to server by opening httpURLConnection
            httpURLConnection = (HttpURLConnection) url.openConnection();

            // Set the request method of the httpURLConnection to "GET"
            httpURLConnection.setRequestMethod("GET");


            // Connect to server
            httpURLConnection.connect();

            // Next step: Retrieve the country data
            // Proceed by getting the input stream returned from the query
            InputStream inputStream = httpURLConnection.getInputStream();

            if(inputStream == null){ // Failed to retrieve data
                Log.d(LOG_TAG, "Failed to retrieve country data");
                return null; // no data retrieved from server
            }

            // Parse the retrieved data
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            // initialize bufferedReader and stringBuffer
            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            // Decalare variables for parsing, reading data line by line (using the bufferedReader)
            String line;
            final String NEW_LINE = "\n";

            while((line=bufferedReader.readLine()) != null){
                // append stringBuffer by adding new line escape char.
                // The following is for readability and debugging/testing purposes
                stringBuffer.append(line + NEW_LINE);
            } // all the data has been initially parsed and appended

            if(stringBuffer.length() == 0){ // nothing to parse
                Log.d(LOG_TAG, "No data to parse");
                return null;
            }

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


        return new Country[0];
    }
}
