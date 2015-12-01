package com.ecru.infographic;

/**
 * Created by nashwan on 12/1/2015.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by nashwan on 12/1/2015.
 * A pager adapter that represents Fragment objects, in sequence.
 */
public  class InfoPageAdapter extends FragmentStatePagerAdapter {

    String pageNumber;
    ArrayList mm;
    int position =0;
    int year =0;
    public InfoPageAdapter(FragmentManager fm,String ss){
        super(fm);
        pageNumber = ss;

    }

    /**
     * The position of the page detrimines what is shown
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        this.position=position;
        //TODO if statements for ech option
            PieChartFragment da = new PieChartFragment();
            Bundle ba = new Bundle();
            float[] f = new float[3];
            f[0] =5;
            f[1] =20;
            f[2] =75;
            ba.putString("Name",position + "");
            ba.putFloatArray("Hello", f);
            da.setArguments(ba);
            return da;
    }

    @Override
    public int getCount() {
        return 5;
    }

    public void setYear(int year) {
        this.year = year;
        getItem(position);
    }
}