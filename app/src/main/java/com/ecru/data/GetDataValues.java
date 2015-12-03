package com.ecru.data;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

/**
 * Created by Ayman on 29/11/2015.
 */
public class GetDataValues {

    String empServJson;
    String empAgrJson;
    String empIndJson;
    int  selected =0;
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


    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
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

    public LineData LineGraphSectorData() throws JSONException{
        //an array containing all the years in the dataset for this chart (1982-2012)
        int[] years = (int[]) parseData(empAgrJson)[0];

        //arrays containing the actual values
        float[] agricultureVals = (float[]) parseData(empAgrJson)[1];
        float[] serviceVals = (float[]) parseData(empServJson)[1];
        float[] industryVals = (float[]) parseData(empIndJson)[1];


        //Create a new arraylist to contain the dataset
        ArrayList<Entry> agriComp = new ArrayList<Entry>();
        ArrayList<Entry> servComp = new ArrayList<Entry>();
        ArrayList<Entry> indComp = new ArrayList<Entry>();
        ArrayList<String> yearNumberLabels = new ArrayList<>();
        for(int i=0; i<years.length;++i) {
            //add the value to the array list for the year specified
            agriComp.add(new Entry(agricultureVals[i], i));
            servComp.add(new Entry(serviceVals[i], i));
            indComp.add(new Entry(industryVals[i], i));
            yearNumberLabels.add(i+ 1982 + "");

            //Temp, show values in log, used for debugging purposes
            Log.d("YEAR", yearNumberLabels.get(i)+"");
           // Log.d("VAL_AGRI", agricultureVals[i] + "");
           // Log.d("VAL_SERV", serviceVals[i] + "");
            //Log.d("VAL_INDS", industryVals[i] + "");
        }

        //Line Data Sets Are Created
        LineDataSet agriValues= new LineDataSet(agriComp,"AgriCulture");
        LineDataSet servicesValues = new LineDataSet(servComp,"Services");
        LineDataSet indValues = new LineDataSet(indComp,"Industry");

        agriValues.setAxisDependency(YAxis.AxisDependency.LEFT);
        servicesValues.setAxisDependency(YAxis.AxisDependency.LEFT);
        indValues.setAxisDependency(YAxis.AxisDependency.LEFT);
        agriValues.setColor(Color.argb(255, 120, 30, 30));
        servicesValues.setColor(Color.argb(255,30,120,20));
        indValues.setColor(Color.argb(255,30,240,130));

        ArrayList<LineDataSet> LineDataArray = new ArrayList<>();
        LineDataArray.add(agriValues);
        LineDataArray.add(servicesValues);
        LineDataArray.add(indValues);
        LineData data = new LineData(yearNumberLabels, LineDataArray);


        //return the arrayList containing the values
        return data;
    }

    /**
     * Method to parse the data
     * @param jsonString the json string to parse
     * @return Returns an object of two arrays, call 0 for years, 1 for values
     * @throws JSONException
     */
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
