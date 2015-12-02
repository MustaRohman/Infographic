package com.ecru.infographic;

/**
 * Created by nashwan on 12/1/2015.
 */
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;


import com.ecru.data.GetDataValues;


/**
 * Created by nashwan on 12/1/2015.
 * A pager adapter that represents Fragment objects, in sequence.
 */
public  class InfoPageAdapter extends FragmentStatePagerAdapter {


    private GetDataValues dataValues;

    public InfoPageAdapter(FragmentManager fm,GetDataValues dataValues){
        super(fm);
        this.dataValues =dataValues;
    }

    /**
     * The position of the page detrimines what is shown
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
      if(position==1){
          GraphSectorsFragment e = new GraphSectorsFragment(dataValues);
          return  e;
      }
        //position is the page (from 0 to last(specified in getCount))
        //TODO if statements for each option currently everything is this
        PieChartFragment da = new PieChartFragment(dataValues);
        return da;
    }

    @Override
    public Parcelable saveState()
    {
        return null;
    }
    @Override
    public int getCount() {
        return 5;
    }
    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }



}