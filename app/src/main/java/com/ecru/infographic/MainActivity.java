package com.ecru.infographic;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ecru.data.GetDataValues;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements SeekBar.OnSeekBarChangeListener{

    public PieChart sectors;
    public SeekBar selectYear;
    private TextView title;
    private Typeface bigJoe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bigJoe = Typeface.createFromAsset(getAssets(), "fonts/Slim Joe.otf");
        title = (TextView) findViewById(R.id.title);
        title.setTypeface(bigJoe);
        sectors = (PieChart) findViewById(R.id.pieChart);
//        selectYear = (SeekBar) findViewById(R.id.seekBar1);
//
//        selectYear.setMax(30);
//        selectYear.setOnSeekBarChangeListener(this);


        try {
            setData(new GetDataValues().employmentPieData(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sectors.setUsePercentValues(false);
        sectors.setDrawHoleEnabled(true);
        sectors.setDescription("Sectors % of UK employment");
        sectors.animateY(1500, Easing.EasingOption.EaseInOutQuad);

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
        data.setValueTextColor(Color.BLACK);
        sectors.setData(data);

        // undo all highlights
        sectors.highlightValues(null);

        sectors.invalidate();
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
