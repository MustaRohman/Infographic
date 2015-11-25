package com.ecru.data;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ayman on 24/11/2015.
 */
public class ApiHandler extends AsyncTask<String, Integer, JSONArray> {


    private String result;
    private String urlName;
    private JSONArray jsonArray;

    public String getResult() {
        return result;
    }

    //Constructor which takes a url to retrieve data from
    public ApiHandler(String urlName) {
        this.urlName = urlName;
    }


    @Override
    protected JSONArray doInBackground(String... params) {

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
        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        //temp, used to see response from server in logCat
        Log.d("returnSting", returnString);
        try {
            return new JSONArray(returnString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(JSONArray result) {
        //parsing code
        if (result != null){
            try {
                JSONArray list = result.getJSONArray(1);
                JSONObject object;

                for (int i = 0; i < list.length(); ++i){
                    object = list.getJSONObject(i);
                    Log.d("onPostExecute", object.getString("date"));
                    Log.d("onPostExecute", object.getString("value"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        this.result = result;

    }
}
