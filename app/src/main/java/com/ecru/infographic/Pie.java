package com.ecru.infographic;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;

import com.bartoszlipinski.viewpropertyobjectanimator.ViewPropertyObjectAnimator;
import com.ecru.data.GetDataValues;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Rami on 04/12/2015.
 */
public class Pie {

    PieChart pieChart;
    Activity activity;
    SeekBar pieSeekBar;
    GetDataValues dataValues;

    public Pie(final Activity activity,GetDataValues dataValues) {
        this.activity = activity;
        this.pieChart = (PieChart) activity.findViewById(R.id.pieChart);
        this.dataValues = dataValues;
        pieSeekBar = (SeekBar) activity.findViewById(R.id.pieSeekBar);
        pieSeekBar.setOnSeekBarChangeListener(change);
        pieSeekBar.setMax(30);
        pieSeekBar.setProgress(0);
        pieChart.animateY(750, Easing.EasingOption.EaseInOutExpo);
        try {
            setData(dataValues.employmentPieData(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                String temp[] = String.valueOf(entry).split(":");
                int buttonNum = Integer.parseInt(temp[1].replaceAll("[^\\d.]", ""));
                String buttonId = "button" + buttonNum;
                int resId = activity.getResources().getIdentifier(buttonId, "id", activity.getPackageName());
                Button pressBtn = (Button) activity.findViewById(resId);
                replace(pressBtn);
                Log.d("chart value", "");

            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    SeekBar.OnSeekBarChangeListener change = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int year = pieSeekBar.getProgress();
            try {
                setData(dataValues.employmentPieData(year));
                Log.d("hello", year+"");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pieChart.animateY(750, Easing.EasingOption.EaseInOutExpo);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int year = pieSeekBar.getProgress();
            try {
                setData(dataValues.employmentPieData(year));
                Log.d("hello", year+"");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pieChart.animateY(750, Easing.EasingOption.EaseInOutExpo);
        }
    };

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



        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(titles, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    /**
     * Animation for Buttons
     * @param btn
     */
    public void replace(Button btn) {
        ObjectAnimator buttonAni = ViewPropertyObjectAnimator
                .animate(btn)
                .height(400)
                .setDuration(500)
                .rotationX(360)
                .get();
        buttonAni.start();
    }


}
