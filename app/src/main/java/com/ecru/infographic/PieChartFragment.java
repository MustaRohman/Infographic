package com.ecru.infographic;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by nashwan on 12/1/2015.
 */
public class PieChartFragment extends Fragment{

    private  GetDataValues dd;
   private TextView pieChartTV;
    private PieChart  sectors;
    private SeekBar seek;
    private float[] values = new float[3];
    public PieChartFragment(){
        super();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragmentone, container, false);

        return rootView;
    }
    public PieChartFragment(GetDataValues dd){
        super();
        this.dd=dd;
    }

    /**
     * Changes to the view are to be done here
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pieChartTV =(TextView) getView().findViewById(R.id.textViewPieChart);
        sectors = (PieChart) getView().findViewById(R.id.pieChart);
        seek =(SeekBar) getView().findViewById(R.id.seekBar123);

        try {
            setData(dd.employmentPieData(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        pieChartTV.setText(seek.getProgress()+1982+"");
        seek.setOnSeekBarChangeListener(changed);
        seek.setMax(30);
        seek.setProgress(0);
        sectors.animateY(750, Easing.EasingOption.EaseInOutExpo);
    }

    SeekBar.OnSeekBarChangeListener changed = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int year = seek.getProgress();


            try {
                setData(dd.employmentPieData(year));
                Log.d("hello", year+"");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            sectors.animateY(750, Easing.EasingOption.EaseInOutExpo);
            pieChartTV.setText(seek.getProgress() + 1982 + "");

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

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

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(titles, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
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