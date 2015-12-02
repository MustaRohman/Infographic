package com.ecru.infographic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ecru.data.GetDataValues;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

/**
 * Created by nashwan on 12/2/2015.
 */
public class GraphSectorsFragment extends Fragment{

    private GetDataValues dd;
    private String position="8";
    private LineChart mChart;
    private float[] values = new float[3];

    public GraphSectorsFragment(){
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.graphsectorsfragment, container, false);

        return rootView;
    }



    public GraphSectorsFragment(GetDataValues dd){
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
        mChart = (LineChart) getView().findViewById(R.id.barChartSectors);
        //mChart.setOnChartGestureListener(change);
        mChart.setDrawGridBackground(false);
        mChart.setDescription("");
        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(true);

        try {
            mChart.setData(dd.LineGraphSectorData());
            xAxis.setDrawAxisLine(true);
            xAxis.setLabelsToSkip(0);
        } catch (Exception e) {
        }

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);

       mChart.getAxisRight().setEnabled(false);


        mChart.setClickable(false);


        mChart.invalidate();

    }


    OnChartGestureListener change = new OnChartGestureListener() {
        @Override
        public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            Log.i("Gesture", "START");
        }

        @Override
        public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            Log.i("Gesture", "END");
            mChart.highlightValues(null);
        }

        @Override
        public void onChartLongPressed(MotionEvent me) {
            Log.i("LongPress", "Chart longpressed.");
        }

        @Override
        public void onChartDoubleTapped(MotionEvent me) {
            Log.i("DoubleTap", "Chart double-tapped.");
        }

        @Override
        public void onChartSingleTapped(MotionEvent me) {
            Log.i("SingleTap", "Chart single-tapped.");
        }

        @Override
        public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
            Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
        }

        @Override
        public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
            Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
        }

        @Override
        public void onChartTranslate(MotionEvent me, float dX, float dY) {
            Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
        }
    };


}

