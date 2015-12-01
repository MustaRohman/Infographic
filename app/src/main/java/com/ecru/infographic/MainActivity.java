package com.ecru.infographic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;

import com.ecru.data.GetDataValues;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements SeekBar.OnSeekBarChangeListener{

    public PieChart sectors;
    public SeekBar selectYear;
    public ArrayList values;
    public GetDataValues dataValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataValues = new GetDataValues(this);

        sectors = (PieChart) findViewById(R.id.pieChart);
        selectYear = (SeekBar) findViewById(R.id.seekBar1);

        selectYear.setMax(30);
        selectYear.setOnSeekBarChangeListener(this);

        values = null;

        sectors.setUsePercentValues(false);
        sectors.setDrawHoleEnabled(true);
        sectors.setCenterText("Sectors % of UK employment");
        sectors.animateY(1500, Easing.EasingOption.EaseInOutQuad);

        try{
            setData(dataValues.employmentPieData(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void setData(ArrayList values) {


        ArrayList<Entry> yVals1 = new ArrayList<>();

        //get values in arrayList and pass them to here
        for (int i = 0; i < values.size(); i++) {
            yVals1.add(new Entry((Float) values.get(i), i));
        }

        ArrayList<String> titles = new ArrayList<>();
        titles.add("Agriculture");
        titles.add("Services");
        titles.add("Industry");

        PieDataSet dataSet = new PieDataSet(yVals1, "Employment sectors");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        // add colors
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(titles, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        sectors.setData(data);

        // undo all highlights
        sectors.highlightValues(null);

        sectors.invalidate();
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        int year = seekBar.getProgress();

        if (values !=null) {
            setData(values);
        }else {
            try {
                setData(dataValues.employmentPieData(year));
            } catch (JSONException e) {
                Log.d("onpProgressChanged", "Failed to setData");
            }
        }
        sectors.animateY(750, Easing.EasingOption.EaseInOutExpo);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
