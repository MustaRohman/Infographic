package com.ecru.infographic;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecru.data.GetDataValues;
import com.ecru.data.Year;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by nashwan on 12/1/2015.
 */
public class PieChartFragment extends Fragment{

    private String position="8";
    private PieChart  sectors;
    private float[] values = new float[3];
    public PieChartFragment(){
        super();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragmentone, container, false);

        position = getArguments().getString("Name");
        values =getArguments().getFloatArray("Hello");
        return rootView;
    }

    /**
     * Changes to the view are to be done here
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sectors = (PieChart) getView().findViewById(R.id.pieChart);
        setData(values);
        sectors.animateY(750, Easing.EasingOption.EaseInOutExpo);
    }

    public void setData(float[] values){
        ArrayList<Entry> yVals1 = new ArrayList<>();

        //get values in arrayList and pass them to here
        for (int i = 0; i < values.length; i++) {
            float temp = (float) values[i];
            yVals1.add(new Entry((Float) temp, i));
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
    public void pieChartHander(){
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}