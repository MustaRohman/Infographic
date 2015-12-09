package com.ecru.infographic;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by nashwan on 12/8/2015.
 */
public class LineGraphTests  extends ActivityInstrumentationTestCase2<MainActivity> {
    public LineGraphTests() {
        super(MainActivity.class);
    }
    public void testActivityExists() {
        MainActivity mainActivity = getActivity();
        assertNotNull(mainActivity);
    }

    public void testGraphExists(){
        MainActivity mainActivity = getActivity();
        assertNotNull(mainActivity.getGraph());
    }




}
