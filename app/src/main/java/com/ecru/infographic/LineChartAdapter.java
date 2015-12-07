package com.ecru.infographic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.charts.LineChart;
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

        // Chart Style

        holder.lineChart.setDescription("");
        holder.lineChart.setDrawGridBackground(false);

        XAxis xAxis = holder.lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = holder.lineChart.getAxisLeft();

        leftAxis.setLabelCount(5, false);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = holder.lineChart.getAxisRight();
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(15f);

        holder.lineChart.setData(data);
        holder.lineChart.invalidate();
        return convertView;

    }




}
