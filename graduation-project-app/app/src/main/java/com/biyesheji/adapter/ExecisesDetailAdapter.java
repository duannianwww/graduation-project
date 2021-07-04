package com.biyesheji.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Administrator on 2021/3/3.
 */

public class ExecisesDetailAdapter extends PagerAdapter {
    private static ArrayList<View> viewpagelist;
    public void setData(ArrayList<View> viewpagelist){
        this.viewpagelist=viewpagelist;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return viewpagelist.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager) container).addView(viewpagelist.get(position));
        return viewpagelist.get(position);
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView(viewpagelist.get(position));
    }
}
