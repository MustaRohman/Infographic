package com.ecru.infographic;

import android.content.Context;
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
 * Created by Rami on 12/9/2015.
 */
public class ExportsValueGraph extends ArrayAdapter<LineData> {

    public ExportsValueGraph(Context context, List<LineData> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       LineData data = getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_rightchart, null);
            holder.lineChart = (LineChart) convertView.findViewById(R.id.rightSideLineChart);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        XAxis x = holder.lineChart.getXAxis();
        x.setDrawAxisLine(true);
        x.setDrawLabels(true);
        x.setEnabled(true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis y = holder.lineChart.getAxisLeft();
        y.setDrawAxisLine(true);
        y.setDrawLabels(true);
        y.setEnabled(true);
        y.setDrawGridLines(true);
        y.setStartAtZero(false);

        Legend l = holder.lineChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        l.setTextSize(15f);
        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        holder.lineChart.setDescription("");
        holder.lineChart.getAxisRight().setEnabled(true);
        holder.lineChart.setClickable(false);
        holder.lineChart.setDrawGridBackground(false);
        holder.lineChart.setData(data);
        holder.lineChart.invalidate();
        return convertView;

    }
}