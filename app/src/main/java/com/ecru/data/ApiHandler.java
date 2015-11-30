package com.ecru.data;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ayman on 24/11/2015.
 */
public class ApiHandler extends AsyncTask<String, Void, String> {


    private String response;
    private String urlName;

    public String getResponse() {
        return response;
    }

    //Constructor which takes a url to retrieve data from
    public ApiHandler(String urlName) {
        this.urlName = urlName;
    }


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
        } catch (IOException e) {

            e.printStackTrace();
        }

        //temp, used to see response from server in logCat
        Log.d("returnSting", returnString);

        return returnString;
    }

    @Override
    protected void onPostExecute(String returnString) {
        //store string

    }

}
