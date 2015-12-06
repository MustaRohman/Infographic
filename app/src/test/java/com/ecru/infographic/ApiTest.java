package com.ecru.infographic;

import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestRunner;

import com.ecru.data.ApiHandler;
import com.ecru.data.GetDataValues;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by nashwan on 12/6/2015.
 */
public class ApiTest extends ActivityInstrumentationTestCase2<MainActivity> {



    public ApiTest() {
        super(MainActivity.class);
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

     MainActivity activity = new MainActivity();

     @Test
     public void testNotNullActivity(){
     assertNotNull(activity);
     }

    @Test
    public void testDataValues(){
        assertNotNull(activity.getDataValues());
    }
    ApiHandler handler = new ApiHandler("TestFile","http://api.worldbank.org/countries/GBR/indicators/SL.SRV.EMPL.ZS?per_page=100&date=1982:2012&format=json",activity);
    @Test
    public void testApiHandler(){
        assertNotNull(handler);
    }
    /**
     public void testGetDataCalls(){

     }
     **/

    @Test
    public void testdada(){
        assertTrue((2 + 2) == 4);
    }

}
