package com.ecru.infographic;

import android.app.Activity;

import com.ecru.data.GetDataValues;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;

/**
 * Created by Rami on 04/12/2015.
 */
public class Graph {
    Activity activity;
    LineChart lineChart;

    public Graph(Activity activity) {
        this.activity = activity;
        this.lineChart = (LineChart)activity.findViewById(R.id.lineChart);

        lineChart.setDrawGridBackground(false);
        lineChart.setDescription("");
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setEnabled(true);

        try {
            lineChart.setData(new GetDataValues(activity).LineGraphSectorData());
            xAxis.setDrawAxisLine(true);
            xAxis.setLabelsToSkip(0);
        } catch (Exception e) {

        }

        Legend l = lineChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setClickable(false);
        lineChart.invalidate();


    }
}
