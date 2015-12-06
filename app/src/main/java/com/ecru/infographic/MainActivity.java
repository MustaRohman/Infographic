package com.ecru.infographic;

import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bartoszlipinski.viewpropertyobjectanimator.ViewPropertyObjectAnimator;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{

    public PieChart sectors;
    public SeekBar selectYear;
    private TextView title;
    private ImageView seekbar_info;
    public static Typeface bigJoe;
    private ArrayList<String> mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CircleDisplay agriCir, indusCir, servCir;

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


        agrCircle();
        serviceCircle();
        industryCircle();
        hideInfos();

        /**
         * LEFT SIDE SLIDER PANEL . WE ARE NOT USING FOR NOW
         */
        createSidePanel();
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
        agriCir.setFormatDigits(1);
        agriCir.setUnit("%");
        agriCir.setStepSize(2f);
        agriCir.setTouchEnabled(false);
        // cd.setCustomText(...); // sets a custom array of text
        agriCir.showValue(75f, 100f, true);

    }

    public void serviceCircle(){
        int red = getResources().getColor(R.color.red);
        servCir = (CircleDisplay) findViewById(R.id.overallServ);
        servCir.setColor(red);
        servCir.setValueWidthPercent(10f);
        servCir.setTextSize(25f);
        servCir.setDrawText(true);
        servCir.setDrawInnerCircle(true);
        servCir.setFormatDigits(1);
        servCir.setUnit("%");
        servCir.setStepSize(2f);
        servCir.setTouchEnabled(false);
        // cd.setCustomText(...); // sets a custom array of text
        servCir.showValue(55f, 100f, true);
    }
    public void industryCircle(){
        int blue = getResources().getColor(R.color.blue);
        indusCir = (CircleDisplay) findViewById(R.id.overallInd);
        indusCir.setColor(blue);
        indusCir.setValueWidthPercent(10f);
        indusCir.setTextSize(25f);
        indusCir.setDrawText(true);
        indusCir.setDrawInnerCircle(true);
        indusCir.setFormatDigits(1);
        indusCir.setUnit("%");
        indusCir.setStepSize(2f);
        indusCir.setTouchEnabled(false);
        // cd.setCustomText(...); // sets a custom array of text
        indusCir.showValue(55f, 100f, true);
    }

}
