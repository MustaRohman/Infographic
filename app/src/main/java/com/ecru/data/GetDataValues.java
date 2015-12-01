package com.ecru.data;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Ayman on 29/11/2015.
 */
public class GetDataValues {

    String empServJson;
    String empAgrJson;
    String empIndJson;

    /**
     * Method to get the data for various charts/graphs needed
     * @param activity
     */
    public GetDataValues(Activity activity){
        try {
            //to get data from url, append statement within try
            empServJson = new ApiHandler("empServJson", "http://api.worldbank.org/countries/GBR/indicators/SL.SRV.EMPL.ZS?per_page=100&date=1982:2012&format=json", activity).execute().get();
            empAgrJson = new ApiHandler("empAgrJson", "http://api.worldbank.org/countries/GBR/indicators/SL.AGR.EMPL.ZS?per_page=100&date=1982:2012&format=json", activity).execute().get();
            empIndJson = new ApiHandler("empIndJson", "http://api.worldbank.org/countries/GBR/indicators/SL.IND.EMPL.ZS?per_page=100&date=1982:2012&format=json", activity).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d("GetDataValues", "Data loading interrupted");
            e.printStackTrace();
        }
    }

    /**
     * Method to extract data for the employment pie chart
     * @param year the year to return data for
     * @return an array list containing the values for a specified year
     * @throws JSONException
     */
    public ArrayList employmentPieData(int year) throws JSONException {

        //an array containing all the years in the dataset for this chart (1982-2012)
        int[] years = (int[]) parseData(empAgrJson)[0];

        //arrays containing the actual values
        float[] agricultureVals = (float[]) parseData(empAgrJson)[1];
        float[] serviceVals = (float[]) parseData(empServJson)[1];
        float[] industryVals = (float[]) parseData(empIndJson)[1];


        //Create a new arraylist to contain the dataset
        ArrayList<Float> values = new ArrayList<>();

        //add the value to the array list for the year specified
        values.add(agricultureVals[year]);
        values.add(serviceVals[year]);
        values.add(industryVals[year]);


        //Temp, show values in log, used for debugging purposes
        Log.d("YEAR", years[year] + "");
        Log.d("VAL_AGRI", agricultureVals[year] + "");
        Log.d("VAL_SERV", serviceVals[year] + "");
        Log.d("VAL_INDS", industryVals[year] + "");

        //return the arrayList containing the values
        return values;
    }

<<<<<<< HEAD

    /**
     * Method to parse the data
     * @param jsonString the json string to parse
     * @return Returns an object of two arrays, call 0 for years, 1 for values
     * @throws JSONException
     */
=======
>>>>>>> origin/savingData
    public Object[] parseData(String jsonString) throws JSONException {

        JSONArray jsonArray = new JSONArray(jsonString);
        JSONArray list = jsonArray.getJSONArray(1);

        int x = list.length();

        int[] year = new int[x];
        float[] value = new float[x];

        //parsing code
        if (jsonArray != null) {
            try {
                JSONObject object;

                for (int i = 0; i < list.length(); ++i) {
                    object = list.getJSONObject(i);

                    year[i] = Integer.parseInt(object.getString("date"));
                    value[i] = Float.parseFloat(object.getString("value"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new Object[]{year, value};
    }

}
