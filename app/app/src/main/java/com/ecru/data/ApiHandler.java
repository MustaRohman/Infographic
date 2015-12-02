package com.ecru.data;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ayman on 24/11/2015.
 */
public class ApiHandler extends AsyncTask<String, Void, String> {

    private String filename;
    private Activity activity;
    private String urlName;


    /**
     * Constuctor for ApiHandler class
     * @param filename used to reference where the string has been stored
     * @param urlName the url from which the data should be fetched
     * @param activity
     */
    public ApiHandler(String filename, String urlName, Activity activity) {
        this.urlName = urlName;
        this.activity = activity;
        this.filename = filename;
    }


    /**
     * Called by .execute, in this method fetch json string
     * @param Params
     * @return string containing json from url
     */
    @Override
    protected String doInBackground(String... Params) {

        String returnString = "";

        try {
            //gets url
            URL urlName = new URL(this.urlName);

            HttpURLConnection connection = (HttpURLConnection) urlName.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    urlName.openStream()));

            //appends the response to the request to the return string
            //N.B. no need for loop here as only one line will be returned
            returnString += in.readLine();

            //closes the connection
            in.close();
            connection.disconnect();
            //calls the saveData method to store the string
            saveData(returnString);
        } catch (IOException e) {
            Log.d("doInBackground", "Failed to retrieve online data, retrieving stored data");
            returnString = loadCachedData();
            e.printStackTrace();
        }
        //temp, used to see response from server in logCat
        Log.d("returnSting", returnString);

        return returnString;
    }

    @Override
    protected void onPostExecute(String returnString) {
    }


    /**
     * Method to store the string persistently
     * @param jsonString the json string to save
     * @return
     */
    public boolean saveData(String jsonString) {
        try {
            FileOutputStream fos = activity.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(jsonString.getBytes());
            fos.close();
            Log.d("saveData", "Data has been saved to: " + filename);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * method to load the cached string
     * @return string containing json data
     */
    public String loadCachedData() {
        String returnString = null;
        try {
            FileInputStream fis = activity.openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            returnString = reader.readLine();
            Log.d("loadCachedData", "Data has been read");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnString;
    }

}
