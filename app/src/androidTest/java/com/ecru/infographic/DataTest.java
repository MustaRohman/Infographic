package com.ecru.infographic;

import android.app.Application;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.ecru.data.ApiHandler;
import com.ecru.data.GetDataValues;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by nashwan on 12/6/2015.
 */
public class DataTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public DataTest() {
        super(MainActivity.class);
    }
    public void testActivityExists() {
        MainActivity mainActivity = getActivity();
        assertNotNull(mainActivity);
    }
    public void testGetDataValuesExists() {
        MainActivity mainActivity = getActivity();
        assertNotNull(mainActivity.getDataValues());
    }
    public void testCorrectDataSavedPieChart1982(){
        MainActivity mainActivity =getActivity();
        GetDataValues  dd = mainActivity.getDataValues();
        //checks 1982 data is properly applied to pie chart
        try {
            ArrayList<Float> pieData = dd.employmentPieData(0);
            //data from the worlddata website
            if(pieData.get(0)==1.2f&&pieData.get(1)==78.9f&&pieData.get(2)==18.9f){
                assertTrue(true);
            }
            else {
                assertTrue(false);

            }
        }
        catch(JSONException e){
            assertTrue(false);
        }
    }

    public void testCorrectDataSavedPieChart1992(){
        MainActivity mainActivity =getActivity();
        GetDataValues  dd = mainActivity.getDataValues();
        //checks 1992 data is properly applied to pie chart
        try {
            //year 10
            ArrayList<Float> pieData = dd.employmentPieData(10);
            //data from the worlddata website
            if(pieData.get(0)==1.4f&&pieData.get(1)==74.7f&&pieData.get(2)==23.7f){
                assertTrue(true);
            }
            else {
                assertTrue(false);

            }
        }
        catch(JSONException e){
            assertTrue(false);
        }
    }

    public void testApiHandlerExists(){
        MainActivity activity =getActivity();
        String testFileString ="";
        ApiHandler apiHandler = new ApiHandler(testFileString,"http://api.worldbank.org/countries/GBR/indicators/SL.SRV.EMPL.ZS?per_page=100&date=1982:2012&format=json", activity);
        assertNotNull(apiHandler);
    }
    public void testStringUpdatedFunction(){

        MainActivity activity =getActivity();
        ApiHandler apiHandler = new ApiHandler("testFileString","http://api.worldbank.org/countries/GBR/indicators/SL.SRV.EMPL.ZS?per_page=100&date=1982:2012&format=json", activity);
        try {
            String  testFileString = apiHandler.execute().get();
            if(testFileString ==null ||testFileString.equals("")){
                assertTrue(false);
            }
            else{
                assertTrue(true);
            }
        }
        catch (InterruptedException | ExecutionException e) {
            assertTrue(false);
        }
    }
    public void testLoadFunction(){

        MainActivity activity =getActivity();
        String testFileStringLoad ="";
        ApiHandler apiHandler = new ApiHandler("testFileStringLoad","http://api.worldbank.org/countries/GBR/indicators/SL.SRV.EMPL.ZS?per_page=100&date=1982:2012&format=json", activity);
        try {
            testFileStringLoad = apiHandler.execute().get();
        }
        catch (InterruptedException | ExecutionException e) {
            assertTrue(false);
        }
        try {
            String loadedFile = apiHandler.loadCachedData();
            assertEquals(testFileStringLoad,loadedFile);
        }
        catch (Exception e){
            assertTrue(false);
        }

    }
    public void testSaveFunction(){

        MainActivity activity =getActivity();

        ApiHandler apiHandler = new ApiHandler("testFileStringSave","http://api.worldbank.org/countries/GBR/indicators/SL.SRV.EMPL.ZS?per_page=100&date=1982:2012&format=json", activity);
        //will call from URL, tests fail if unable to do so
        try {
            apiHandler.execute().get();

        }
        catch (InterruptedException | ExecutionException e) {
            assertTrue(false);
        }
        //compares the saved file to website
        try {
            String loadedFile = apiHandler.loadCachedData();
            //copied from the url link used for apiHandler
            assertEquals("[{\"page\":1,\"pages\":1,\"per_page\":\"100\",\"total\":31},[{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"78.9000015258789\",\"decimal\":\"0\",\"date\":\"2012\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"79\",\"decimal\":\"0\",\"date\":\"2011\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"78.9000015258789\",\"decimal\":\"0\",\"date\":\"2010\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"78.6999969482422\",\"decimal\":\"0\",\"date\":\"2009\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"76.5999984741211\",\"decimal\":\"0\",\"date\":\"2008\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"76\",\"decimal\":\"0\",\"date\":\"2007\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"76.4000015258789\",\"decimal\":\"0\",\"date\":\"2006\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"76.3000030517578\",\"decimal\":\"0\",\"date\":\"2005\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"76.3000030517578\",\"decimal\":\"0\",\"date\":\"2004\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"75.4000015258789\",\"decimal\":\"0\",\"date\":\"2003\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"74.6999969482422\",\"decimal\":\"0\",\"date\":\"2002\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"73.8000030517578\",\"decimal\":\"0\",\"date\":\"2001\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"73.0999984741211\",\"decimal\":\"0\",\"date\":\"2000\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"72.5999984741211\",\"decimal\":\"0\",\"date\":\"1999\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"71.5\",\"decimal\":\"0\",\"date\":\"1998\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"71.0999984741211\",\"decimal\":\"0\",\"date\":\"1997\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"70.3000030517578\",\"decimal\":\"0\",\"date\":\"1996\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"70.1999969482422\",\"decimal\":\"0\",\"date\":\"1995\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"69.8000030517578\",\"decimal\":\"0\",\"date\":\"1994\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"68\",\"decimal\":\"0\",\"date\":\"1993\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"67.0999984741211\",\"decimal\":\"0\",\"date\":\"1992\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"65.6999969482422\",\"decimal\":\"0\",\"date\":\"1991\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"64.9000015258789\",\"decimal\":\"0\",\"date\":\"1990\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"64.5\",\"decimal\":\"0\",\"date\":\"1989\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"64.3000030517578\",\"decimal\":\"0\",\"date\":\"1988\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"66.5999984741211\",\"decimal\":\"0\",\"date\":\"1987\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"65.8000030517578\",\"decimal\":\"0\",\"date\":\"1986\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"64.9000015258789\",\"decimal\":\"0\",\"date\":\"1985\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"64.3000030517578\",\"decimal\":\"0\",\"date\":\"1984\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"63\",\"decimal\":\"0\",\"date\":\"1983\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"61.7999992370605\",\"decimal\":\"0\",\"date\":\"1982\"}]]",loadedFile);
        }
        catch (Exception e){
            assertTrue(false);
        }
    }

    //TODO compare data
    public void testParseData(){
        MainActivity activity =getActivity();
        GetDataValues dataValues =activity.getDataValues();
        String jSonTestString ="";
        try {
            Object[] ff=dataValues.parseData(jSonTestString);
            //if length of data is 0, test fails
            if(ff.length==0){
                assertTrue(false);
            }

            int[] yearArray =(int[]) ff[0];
            //if array for years is empty then test fails
            if(yearArray==null ||yearArray.length==0){
                assertTrue(false);
            }

            float[] floatArray = (float[]) ff[1];
            //if array for years is empty then test fails
            if(floatArray==null ||floatArray.length==0){
                assertTrue(false);
            }
            //checks to see if year is correct
            //if(!(ff[1]==3f &&))
            //checks to see if data is correct for first 5

        }catch (JSONException e){
            assertTrue(false);
        }
    }

}