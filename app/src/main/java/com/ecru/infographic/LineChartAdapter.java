package com.ecru.infographic;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;

import java.util.List;

/**
 * Created by Rami on 07/12/2015.
 */
public class LineChartAdapter extends ArrayAdapter<LineData> {


    public LineChartAdapter(Context context, List<LineData> objects) {
        super(context,0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LineData data = getItem(position);

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_linechart, null);
            holder.lineChart = (LineChart) convertView.findViewById(R.id.futureLineChart);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // xAxis
        XAxis xAxis = holder.lineChart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setLabelsToSkip(0);
        xAxis.setTextSize(10.f);
        xAxis.setGridColor(Color.parseColor("#95a5a6"));


        // yAxis
        YAxis leftAxis = holder.lineChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setEnabled(false);
        leftAxis.setAxisMaxValue(220f);
        leftAxis.setAxisMinValue(-20f);
        leftAxis.setStartAtZero(false);
        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        // Legend
        Legend l = holder.lineChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        holder.lineChart.getAxisRight().setEnabled(false);
        holder.lineChart.setDescription("");
        holder.lineChart.getAxisRight().setEnabled(false);
        holder.lineChart.setClickable(false);
        holder.lineChart.setDrawGridBackground(false);
        holder.lineChart.setData(data);
        holder.lineChart.invalidate();
        return convertView;

    }




}
