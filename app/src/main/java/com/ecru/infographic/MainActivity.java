package com.ecru.infographic;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bartoszlipinski.viewpropertyobjectanimator.ViewPropertyObjectAnimator;
import com.ecru.data.GetDataValues;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{


    private TextView title;
    private ImageView seekbar_info;
    public static Typeface bigJoe;
    private ArrayList<LineData> lineDatas;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CircleDisplay agriCir, indusCir, servCir;
    private ImageView fall, rise0, rise1;
    private GetDataValues dataValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bigJoe = Typeface.createFromAsset(getAssets(), "fonts/Track.otf");
        // TEXTVIEWS
        title = (TextView) findViewById(R.id.title);
        title.setTypeface(bigJoe);

        // IMAGE VIEW
        seekbar_info = (ImageView) findViewById(R.id.seekbar_info);
        new Graph(this);
        new Pie(this);
        new ExportsGraph(this);


        // THREE CIRCLES
        agrCircle();
        serviceCircle();
        industryCircle();
        hideInfos();

        dataValues = new GetDataValues(this);

        animateArrows();

        /**
         * LEFT SIDE SLIDER PANEL . WE ARE NOT USING FOR NOW
         */
        createSidePanel();

    }

    public void createSidePanel(){

        lineDatas = new ArrayList<>();
        lineDatas.add(generateData());
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        LineChartAdapter lineChartAdapter = new LineChartAdapter(getApplicationContext(), lineDatas);
        mDrawerList.setAdapter(lineChartAdapter);

    }

    public void hideInfos(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator seekbarInfo = ViewPropertyObjectAnimator
                        .animate(seekbar_info)
                        .setDuration(500)
                        .alpha(0)
                        .get();
                seekbarInfo.start();
            }
        }, 10000);
    }

    public void agrCircle(){
        int yellow = getResources().getColor(R.color.yellow);
        agriCir = (CircleDisplay) findViewById(R.id.overallAgri);
        agriCir.setColor(yellow);
        agriCir.setValueWidthPercent(10f);
        agriCir.setTextSize(25f);
        agriCir.setDrawText(true);
        agriCir.setDrawInnerCircle(true);
        agriCir.setFormatDigits(3);
        agriCir.setUnit("%");
        agriCir.setStepSize(2f);
        agriCir.setTouchEnabled(false);
        // cd.setCustomText(...); // sets a custom array of text
        agriCir.showValue(0.006f, 1f, true);

    }

    public void serviceCircle(){
        int red = getResources().getColor(R.color.red);
        servCir = (CircleDisplay) findViewById(R.id.overallServ);
        servCir.setColor(red);
        servCir.setValueWidthPercent(10f);
        servCir.setTextSize(25f);
        servCir.setDrawText(true);
        servCir.setDrawInnerCircle(true);
        servCir.setFormatDigits(3);
        servCir.setUnit("%");
        servCir.setStepSize(2f);
        servCir.setTouchEnabled(false);
        // cd.setCustomText(...); // sets a custom array of text
        servCir.showValue(-0.054f, 1f, true);
    }
    public void industryCircle(){
        int blue = getResources().getColor(R.color.blue);
        indusCir = (CircleDisplay) findViewById(R.id.overallInd);
        indusCir.setColor(blue);
        indusCir.setValueWidthPercent(10f);
        indusCir.setTextSize(25f);
        indusCir.setDrawText(true);
        indusCir.setDrawInnerCircle(true);
        indusCir.setFormatDigits(3);
        indusCir.setUnit("%");
        indusCir.setStepSize(2f);
        indusCir.setTouchEnabled(false);
        // cd.setCustomText(...); // sets a custom array of text
        indusCir.showValue(0.055f, 1f, true);
    }
    public void animateArrows(){
        fall = (ImageView) findViewById(R.id.fall);
        rise0 = (ImageView) findViewById(R.id.rise0);
        rise1 = (ImageView) findViewById(R.id.rise1);
        ObjectAnimator arrow = ViewPropertyObjectAnimator
                .animate(fall)
                .setDuration(1200)
                .alpha(0)
                .translationY(9f)
                .get();
        arrow.start();
        arrow.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator arrow1 = ViewPropertyObjectAnimator
                .animate(rise0)
                .setDuration(1200)
                .alpha(0)
                .translationY(-8f)
                .get();
        arrow1.start();
        arrow1.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator arrow2 = ViewPropertyObjectAnimator
                .animate(rise1)
                .setDuration(1200)
                .alpha(0)
                .translationY(-8f)
                .get();
        arrow2.start();
        arrow2.setRepeatCount(ValueAnimator.INFINITE);
    }

    private LineData generateData(){
//        ArrayList<Entry> entries = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++){
//            entries.add(new Entry((int) (Math.random() * 70) + 30, i));
//        }
//
//        LineDataSet dataSet = new LineDataSet(entries, "Test");
//        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
//
//        ArrayList<LineDataSet> sets = new ArrayList<>();
//        sets.add(dataSet);
//
//        LineData data = new LineData(getYears(), sets);
//        return data;

        //arrays containing the actual values
        float[] serviceVals = new float[10];
        for (int i = 0; i < 10; i++){
            float temp = (float) (67.1 * (1 + -0.054));
            float predValue = (float) Math.pow(temp, i);
            serviceVals[i] = predValue;
            Log.d("generateData", "Service Value: " + predValue);
        }

        float[] industryVals = new float[10];
        for (int i = 0; i < 10; i++){
            float temp = (float) (30.0 * (1 + 0.055));
            float predValue = (float) Math.pow(temp, i);
            industryVals[i] = predValue;
            Log.d("generateData","Industry Value: " + predValue);
        }

        float[] agricultureVals = new float[10];
        for (int i = 0; i < 10; i++){
            float temp = (float) (30.0 * (1 + 0.055));
            float predValue = (float) Math.pow(temp, i);
            agricultureVals[i] = predValue;
            Log.d("generateData","Agriculture Value: " + predValue);
        }

        //Create a new arraylist to contain the dataset
        ArrayList<Entry> agriComp = new ArrayList<Entry>();
        ArrayList<Entry> servComp = new ArrayList<Entry>();
        ArrayList<Entry> indComp = new ArrayList<Entry>();
        ArrayList<String> yearNumberLabels = new ArrayList<>();
        for(int i=0; i< 10;++i) {
            //add the value to the array list for the year specified
            agriComp.add(new Entry(agricultureVals[i], i));
            servComp.add(new Entry(serviceVals[i], i));
            indComp.add(new Entry(industryVals[i], i));
            yearNumberLabels.add(i+ 2015 + "");
        }

        //Line Data Sets Are Created
        LineDataSet agriValues= new LineDataSet(agriComp,"AgriCulture");
        LineDataSet servicesValues = new LineDataSet(servComp,"Services");
        LineDataSet indValues = new LineDataSet(indComp,"Industry");

        /////////// Agriculture Styling
        int yellow = Color.parseColor("#f1c40f");
        agriValues.setAxisDependency(YAxis.AxisDependency.LEFT);
        agriValues.setColor(yellow);
        agriValues.setCircleColor(yellow);
        agriValues.setLineWidth(5f);
        agriValues.setCircleSize(10f);
        agriValues.setFillAlpha(65);
        agriValues.setFillColor(yellow);
        agriValues.setDrawCircleHole(false);
        agriValues.setHighLightColor(yellow);

        /////////// Services Styling
        int red = Color.parseColor("#e74c3c");
        servicesValues.setAxisDependency(YAxis.AxisDependency.LEFT);
        servicesValues.setColor(red);
        servicesValues.setCircleColor(red);
        servicesValues.setLineWidth(5f);
        servicesValues.setCircleSize(10f);
        servicesValues.setFillAlpha(65);
        servicesValues.setFillColor(red);
        servicesValues.setDrawCircleHole(false);
        servicesValues.setHighLightColor(red);

        /////////// Industry Styling
        int blue = Color.parseColor("#3498db");
        indValues.setAxisDependency(YAxis.AxisDependency.LEFT);
        indValues.setColor(blue);
        indValues.setCircleColor(blue);
        indValues.setLineWidth(5f);
        indValues.setCircleSize(10f);
        indValues.setFillAlpha(65);
        indValues.setFillColor(blue);
        indValues.setDrawCircleHole(false);
        indValues.setHighLightColor(blue);

        ArrayList<LineDataSet> LineDataArray = new ArrayList<>();
        LineDataArray.add(agriValues);
        LineDataArray.add(servicesValues);
        LineDataArray.add(indValues);
        LineData data = new LineData(yearNumberLabels, LineDataArray);


        //return the arrayList containing the values
        return data;

    }


    private ArrayList<String> getYears() {

        ArrayList<String> year = new ArrayList<String>();
        for (int i = 0;i < 11; i++){
            year.add(i + 2015 + "");
        }

        return year;
    }

}
