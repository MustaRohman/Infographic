package com.ecru.infographic;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Rami on 02/12/2015.
 */
public class DrawerItemClickListener implements  ListView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("ID",""+position);
    }



}


