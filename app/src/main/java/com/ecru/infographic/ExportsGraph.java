package com.ecru.infographic;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.ecru.data.GetDataValues;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import org.json.JSONException;

/**
 * Created by Ayman on 06/12/2015.
 */
public class ExportsGraph {

    Activity activity;
    LineChart lineChart;
    GetDataValues dataValues;
    public ExportsGraph(Activity activity, GetDataValues dataValues) {
        this.activity = activity;
        this.dataValues = dataValues;
        this.lineChart = (LineChart) activity.findViewById(R.id.exportLineChart);

        try {
            lineChart.setData(dataValues.exportsChart());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        lineChart.getAxisRight().setEnabled(false);
        lineChart.setDrawGridBackground(false);
        Log.d("Chart focus", ""+lineChart.hasFocus());
        lineChart.setDescription("Employment In Different Sectors (% of total employment)");
        lineChart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d("Bar Focused", ""+ "This bar is in focus now" + hasFocus);
            }
        });

        XAxis x = lineChart.getXAxis();
        x.setDrawAxisLine(true);
        x.setDrawLabels(true);
        x.setEnabled(true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis y = lineChart.getAxisLeft();
        y.setDrawAxisLine(true);
        y.setAxisMaxValue(80);
        y.setAxisMinValue(33.4f);
        y.setDrawLabels(true);
        y.setEnabled(true);
        y.setDrawGridLines(true);
        y.setStartAtZero(false);
    }

    public LineChart getLineChart() {
        return lineChart;
    }
}