package com.ecru.infographic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.SeekBar;

import com.ecru.data.GetDataValues;
import com.ecru.data.Year;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.github.mikephil.charting.data.Entry;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private Year year1;                     //all the info for that year is stored here
    private SeekBar selectYear;             //seekbar to select year;
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new InfoPageAdapter(getSupportFragmentManager(), "faf");
        mPager.setAdapter(mPagerAdapter);

        year1 = new Year(1,45,45,10);

        selectYear = (SeekBar) findViewById(R.id.seekBar1);
        selectYear.setOnSeekBarChangeListener(changed);
        selectYear.setMax(30);
    }


    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);

        }

    }

    SeekBar.OnSeekBarChangeListener changed = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int year = seekBar.getProgress();
            mPager.setCurrentItem(mPager.getCurrentItem());
           //TODO this needs to be fixed
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
