package com.ecru.infographic;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bartoszlipinski.viewpropertyobjectanimator.ViewPropertyObjectAnimator;
import com.github.mikephil.charting.charts.PieChart;

import org.json.JSONException;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static Typeface bigJoe;
    public PieChart sectors;
    public SeekBar selectYear;
    private TextView title;
    private ImageView seekbar_info;
    private ArrayList<String> mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

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
        try {
            new Pie(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new ExportsGraph(this);
        hideInfos();

        /**
         * LEFT SIDE SLIDER PANEL . WE ARE NOT USING FOR NOW
         */
        createSidePanel();
    }

    /**
     * Will use once we finish everything else. An extra feature.
     */
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

    public void createSidePanel() {
        mPlanetTitles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mPlanetTitles.add("Year 200" + i);
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.draw_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    public void hideInfos() {
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


}
