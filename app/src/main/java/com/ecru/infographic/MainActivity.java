package com.ecru.infographic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.ecru.data.ApiHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       new ApiHandler("http://api.worldbank.org/countries/GBR/indicators/BG.GSR.NFSV.GD.ZS?per_page=100&date=2005:2015&format=json").execute();


    }
}
