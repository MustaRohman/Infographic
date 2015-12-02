package com.ecru.infographic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.ecru.data.GetDataValues;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity  implements SeekBar.OnSeekBarChangeListener{

    public PieChart sectors;
    public SeekBar selectYear;
    public BarChart chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sectors = (PieChart) findViewById(R.id.pieChart);
        selectYear = (SeekBar) findViewById(R.id.seekBar1);

        selectYear.setMax(30);
        selectYear.setOnSeekBarChangeListener(this);


        chart = (BarChart) findViewById(R.id.chart);




        try {
            setData(new GetDataValues().employmentPieData(0));
            setBarData(new GetDataValues().barchart(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sectors.setUsePercentValues(false);
        sectors.setDrawHoleEnabled(true);
        sectors.setDescription("Sectors % of UK employment");
        sectors.animateY(1500, Easing.EasingOption.EaseInOutQuad);

        chart.setDescription("# of times Alice called Bob");
        XAxis x = chart.getXAxis();
        x.setDrawAxisLine(true);
        x.setDrawLabels(true);
        x.setEnabled(true);

        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis y = chart.getAxisLeft();
        y.setDrawAxisLine(true);
        y.setDrawLabels(true);
        y.setEnabled(true);
        y.setDrawGridLines(true);
        y.setStartAtZero(true);
        y.setAxisMaxValue(100);

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

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(titles, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        sectors.setData(data);

        // undo all highlights
        sectors.highlightValues(null);

        sectors.invalidate();
    }
    public void setBarData(ArrayList barvalues) {


        ArrayList<BarEntry> percValues = new ArrayList<>();

        //get values in arrayList and pass them to here
        for (int i = 0; i < barvalues.size(); i++) {
            percValues.add(new BarEntry((Float) barvalues.get(i), i));
        }



        BarDataSet dataSet = new BarDataSet(percValues, "Communication and computer exports % of service exports");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            ArrayList<String> labels = new ArrayList<String>();
        labels.add("2014");
        labels.add("2013");
        labels.add("2012");
        labels.add("2011");
        labels.add("2010");
        labels.add("2009");
        labels.add("2008");
        labels.add("2007");
        labels.add("2006");
        labels.add("2005");

        BarData data = new BarData(labels, dataSet);

        chart.setData(data);

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


        int year = seekBar.getProgress();


        try {
            setData(new GetDataValues().employmentPieData(year));
        } catch (JSONException e) {
            e.printStackTrace();
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
