package com.ecru.infographic;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bartoszlipinski.viewpropertyobjectanimator.ViewPropertyObjectAnimator;
import com.ecru.data.GetDataValues;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements SeekBar.OnSeekBarChangeListener {

    private PieChart sectors;
    private SeekBar selectYear;
    private TextView title;
    private Typeface bigJoe;
    private ArrayList<String> mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bigJoe = Typeface.createFromAsset(getAssets(), "fonts/Slim Joe.otf");
        title = (TextView) findViewById(R.id.title);
        title.setTypeface(bigJoe);
        sectors = (PieChart) findViewById(R.id.pieChart);
//        alert();
//        selectYear = (SeekBar) findViewById(R.id.seekBar1);

//        selectYear.setMax(30);
//        selectYear.setOnSeekBarChangeListener(this);

        /**
         * LEFT SIDE SLIDER PANEL . WE ARE NOT USING FOR NOW
         */
        createSidePanel();


        try {
            setData(new GetDataValues().employmentPieData(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sectors.setUsePercentValues(false);
        sectors.setDrawHoleEnabled(true);
        sectors.setDescription("Sectors % of UK employment");
        sectors.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        sectors.setHoleColorTransparent(true);

        /**
         * PIE CHART LISTENER
         */
        sectors.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                String temp[] = String.valueOf(entry).split(":");
                int buttonNum = Integer.parseInt(temp[1].replaceAll("[^\\d.]", ""));
                String buttonId = "button"+buttonNum;
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                Button pressBtn = (Button)findViewById(resId);
//                pressBtn.bringToFront();
                replace(pressBtn);
                Log.d("chart value", "");

            }

            @Override
            public void onNothingSelected() {

            }
        });


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

    public void checkAnimation(View v) {
        Button clickBtn = (Button) v;
        replace(clickBtn);


    }

    public void replace(Button btn) {
        ObjectAnimator buttonAni = ViewPropertyObjectAnimator
                .animate(btn)
                .height(400)
                .setDuration(500)
                .rotationX(360)
                .get();
        buttonAni.start();
    }

    public void alert() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        // set dialog message
        alertDialogBuilder
                .setMessage("Swipe left to select a different year.")
                .setCancelable(false)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    public void createSidePanel(){
        mPlanetTitles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mPlanetTitles.add("Year 200" + i);
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.draw_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

}
