package com.ecru.infographic;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Rami on 04/12/2015.
 */
public class Pie implements SeekBar.OnSeekBarChangeListener {
    public  static int yr = 1980;
    public PieChart pieChart;
    private Activity activity;
    private SeekBar pieSeekBar;
    private GetDataValues dataValues;
    public int counter, delayTime;

    public Pie(final Activity activity) {
        this.activity = activity;
        this.pieChart = (PieChart) activity.findViewById(R.id.pieChart);
        dataValues = new GetDataValues(activity);
        pieSeekBar = (SeekBar) activity.findViewById(R.id.pieSeekBar);
        pieSeekBar.setOnSeekBarChangeListener(this);
        pieSeekBar.setMax(30);
        pieSeekBar.setProgress(0);
        pieChart.animateY(750, Easing.EasingOption.EaseInOutExpo);
        Button btn0 = (Button)activity.findViewById(R.id.button0);
        Button btn1 = (Button)activity.findViewById(R.id.button1);
        Button btn2 = (Button)activity.findViewById(R.id.button2);
        try {
            btn0.setText(getButtonString(0));
            btn1.setText(getButtonString(1));
            btn2.setText(getButtonString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            setData(new GetDataValues(activity).employmentPieData(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // SEEKBAR LISTENERS
        assignListeners();

    }

    public void assignListeners(){
        pieSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle Seekbar touch events.
                v.onTouchEvent(event);
                return true;
            }

        });

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                String temp[] = String.valueOf(entry).split(":");
                int buttonNum = Integer.parseInt(temp[1].replaceAll("[^\\d.]", ""));
                String buttonId = "button" + buttonNum;
                int resId = activity.getResources().getIdentifier(buttonId, "id", activity.getPackageName());
                Button pressBtn = (Button) activity.findViewById(resId);
                replace(pressBtn);


            }

            @Override
            public void onNothingSelected() {

            }
        });
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
        dataSet.setSelectionShift(8f);
        dataSet.setColors(new int[]{Color.parseColor("#f1c40f"), Color.parseColor("#e74c3c"), Color.parseColor("#3498db")});

        PieData data = new PieData(titles, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setHoleColorTransparent(true);
        pieChart.setDescription("");
        pieChart.setCenterText("2012");
        pieChart.setCenterTextSize(30.f);
        pieChart.setCenterTextTypeface(MainActivity.bigJoe);

        pieChart.setCenterTextColor(Color.BLACK);
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

    public void resetBtnSize(Button btn){

        ObjectAnimator buttonA = ViewPropertyObjectAnimator
                .animate(btn)
                .height(150)
                .setDuration(delayTime)
                .rotationX(360)
                .get();
        buttonA.start();


    }
    public void getAllBtns() throws  JSONException{
        delayTime = 250;
        counter = 0;
        while (counter < 3) {
            String buttonId = "button" + counter;
            int resId = activity.getResources().getIdentifier(buttonId, "id", activity.getPackageName());
            Button pressBtn = (Button) activity.findViewById(resId);
            pressBtn.setText(getButtonString(counter));
            resetBtnSize(pressBtn);
            counter++;
            delayTime += 250;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int year = pieSeekBar.getProgress();
        try {
            setData(dataValues.employmentPieData(year));
            pieChart.setCenterText("" + yr);
            Log.d("year", ""+year);
            pieChart.invalidate();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pieChart.animateY(750, Easing.EasingOption.EaseInOutExpo);
        try {
            getAllBtns();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getButtonString(int buttonIndex) throws JSONException {
        DecimalFormat decimalPoints = new DecimalFormat("0.###");
        int year = pieSeekBar.getProgress();
        String returnString;

        if(year < 30) {
            year +=1;

            if(buttonIndex == 0){
                //AGRICULTURE
                float valChange = (float) dataValues.employmentPieData(year-1).get(0)-
                        (float) dataValues.employmentPieData(year).get(0);

                if(valChange > 0){
                    returnString = decimalPoints.format(valChange) + "% increase from " + (Pie.yr);
                }else{
                    returnString = decimalPoints.format(valChange) + "% decrease from " + (Pie.yr);
                }

            }else if(buttonIndex == 1){
                //SERVICE
                float valChange = (float) dataValues.employmentPieData(year-1).get(1)-
                        (float) dataValues.employmentPieData(year).get(1);

                if(valChange > 0){
                    returnString = decimalPoints.format(valChange) + "% increase from " + (Pie.yr);
                }else{
                    returnString = decimalPoints.format(valChange) + "% decrease from " + (Pie.yr);
                }

            }else {
                //INDUSTRY
                float valChange = (float) dataValues.employmentPieData(year-1).get(2)-
                        (float) dataValues.employmentPieData(year).get(2);

                if(valChange > 0){
                    returnString = decimalPoints.format(valChange) + "% increase from " + (Pie.yr);
                }else{
                    returnString = decimalPoints.format(valChange) + "% decrease from " + (Pie.yr);
                }
            }

        }else{
            returnString = "N/A";
        }
        return returnString;
    }


}
