package com.ecru.infographic;

import android.app.Activity;

import com.ecru.data.GetDataValues;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import org.json.JSONException;

/**
 * Created by nashwan on 12/9/2015.
 */
public class ExportsValueGraph {
    Activity activity;
    LineChart lineChart;
    GetDataValues dataValues;
    public ExportsValueGraph(Activity activity, GetDataValues dataValues) {
        this.activity = activity;
        this.dataValues = dataValues;
        this.lineChart = (LineChart) activity.findViewById(R.id.exportLineChart);

        try {
            lineChart.setData(dataValues.exportsGdpChart());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        lineChart.getAxisRight().setEnabled(false);
        lineChart.setDescription("");
        lineChart.setDrawGridBackground(false);

        XAxis x = lineChart.getXAxis();
        x.setDrawAxisLine(true);
        x.setDrawLabels(true);
        x.setEnabled(true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis y = lineChart.getAxisLeft();
        y.setDrawAxisLine(true);
        y.setDrawLabels(true);
        y.setEnabled(true);
        y.setDrawGridLines(true);
        y.setStartAtZero(false);
    }
}
