package com.ecru.infographic;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by nashwan on 12/8/2015.
 */
public class GUIPieChartTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public GUIPieChartTest() {
        super(MainActivity.class);
    }
    public void testActivityExists() {
        MainActivity mainActivity = getActivity();
        assertNotNull(mainActivity);
    }

    public void testPieSeekBarExists() {
        MainActivity mainactivity = getActivity();
        assertNotNull(mainactivity.getPieSeekBar());
    }

    public void testPieChartExists() {
        MainActivity mainactivity = getActivity();
        assertNotNull(mainactivity.getPie());
    }

    //Testing SeekBar
    public void testSeekBarLimits() {
        MainActivity mainactivity = getActivity();
        int n = 30;
        assertEquals(n, mainactivity.getPieSeekBar().getMax());
    }

    public void testSetProgress() {
        MainActivity mainactivity = getActivity();
        int m = 30;
        assertSame(m, mainactivity.getPieSeekBar().getProgress());
    }

}


