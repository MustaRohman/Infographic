package com.ecru.data;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;

import com.ecru.infographic.Pie;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

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
    String exportsJson;

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
            exportsJson = new ApiHandler("exportsJson", "http://api.worldbank.org/countries/GBR/indicators/BM.GSR.CMCP.ZS?per_page=50&date=2005:2014&format=json", activity).execute().get();
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
        Pie.yr = years[year];
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
        for(int i=0; i< 21;++i) {
            //add the value to the array list for the year specified
            agriComp.add(new Entry(agricultureVals[21-i], i));
            servComp.add(new Entry(serviceVals[21-i], i));
            indComp.add(new Entry(industryVals[21-i], i));
            yearNumberLabels.add(i+ 1992 + "");
        }

        //Line Data Sets Are Created
        LineDataSet agriValues= new LineDataSet(agriComp,"AgriCulture");
        LineDataSet servicesValues = new LineDataSet(servComp,"Services");
        LineDataSet indValues = new LineDataSet(indComp,"Industry");

        /////////// Agriculture Styling
        int yellow = Color.parseColor("#f1c40f");
        agriValues.setAxisDependency(YAxis.AxisDependency.LEFT);
        agriValues.setColor(yellow);
        agriValues.setCircleColor(yellow);
        agriValues.setLineWidth(5f);
        agriValues.setCircleSize(10f);
        agriValues.setFillAlpha(65);
        agriValues.setFillColor(yellow);
        agriValues.setDrawCircleHole(false);
        agriValues.setHighLightColor(yellow);

        /////////// Services Styling
        int red = Color.parseColor("#e74c3c");
        servicesValues.setAxisDependency(YAxis.AxisDependency.LEFT);
        servicesValues.setColor(red);
        servicesValues.setCircleColor(red);
        servicesValues.setLineWidth(5f);
        servicesValues.setCircleSize(10f);
        servicesValues.setFillAlpha(65);
        servicesValues.setFillColor(red);
        servicesValues.setDrawCircleHole(false);
        servicesValues.setHighLightColor(red);

        /////////// Industry Styling
        int blue = Color.parseColor("#3498db");
        indValues.setAxisDependency(YAxis.AxisDependency.LEFT);
        indValues.setColor(blue);
        indValues.setCircleColor(blue);
        indValues.setLineWidth(5f);
        indValues.setCircleSize(10f);
        indValues.setFillAlpha(65);
        indValues.setFillColor(blue);
        indValues.setDrawCircleHole(false);
        indValues.setHighLightColor(blue);

        ArrayList<LineDataSet> LineDataArray = new ArrayList<>();
        LineDataArray.add(agriValues);
        LineDataArray.add(servicesValues);
        LineDataArray.add(indValues);
        LineData data = new LineData(yearNumberLabels, LineDataArray);


        //return the arrayList containing the values
        return data;
    }

    public LineData exportsChart() throws JSONException {


        int[] years = (int[]) parseData(exportsJson)[0];
        float[] ictExports = (float[]) parseData(exportsJson)[1];

        //Create a new arraylist to contain the dataset
        ArrayList<Entry> exportsArrList = new ArrayList<Entry>();
        ArrayList<String> yearNumberLabels = new ArrayList<>();
        for (int i = 0; i < years.length; ++i) {
            //add the value to the array list for the year specified

            exportsArrList.add(new Entry(ictExports[i], ((years.length - 1) - i)));
            yearNumberLabels.add(String.valueOf(years[((years.length - 1) - i)]));

        }
        int red = Color.parseColor("#e74c3c");
        //Line Data Sets Are Created
        LineDataSet values = new LineDataSet(exportsArrList, "Communication and computer exports % of service exports");
        values.setDrawCubic(true);
        values.setCubicIntensity(0.3f);
        values.setDrawFilled(true);
        values.setDrawCircles(false);
        values.setLineWidth(1.8f);

        values.setHighLightColor(red);
        values.setColor(red);
        values.setFillColor(red);
        values.setFillAlpha(100);
        values.setDrawHorizontalHighlightIndicator(false);

        ArrayList<LineDataSet> LineDataArray = new ArrayList<>();
        LineDataArray.add(values);
        LineData data = new LineData(yearNumberLabels, LineDataArray);

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

        int length = list.length();

        int[] year = new int[length];
        float[] value = new float[length];

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