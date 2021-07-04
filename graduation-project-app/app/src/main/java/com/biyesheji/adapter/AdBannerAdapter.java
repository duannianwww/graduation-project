package com.biyesheji.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.MotionEvent;
import android.view.View;

import com.biyesheji.bean.CourseBean;
import com.biyesheji.fragment.AdBannerFragment;
import com.biyesheji.view.CourseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2021/1/27.
 */

public class AdBannerAdapter extends FragmentStatePagerAdapter implements View.OnTouchListener{
    private List<CourseBean> cad1;
    private Handler mHandler;
    public AdBannerAdapter(FragmentManager fm) {
        super(fm);
        cad1=new ArrayList<CourseBean>();
    }
    public AdBannerAdapter(FragmentManager fm,Handler handler) {
        super(fm);
        cad1=new ArrayList<CourseBean>();
        mHandler=handler;
    }

    public void setDatas(List<CourseBean> cad1){
        this.cad1=cad1;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args=new Bundle();
        if(cad1.size()>0){
            args.putString("ab",cad1.get(position%cad1.size()).icon);
        }
        return AdBannerFragment.newInstance(args);
    }
    public int getSize(){
        return cad1==null?0:cad1.size();
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mHandler.removeMessages(CourseView.MSG_AD_SLID);
        return false;
    }
}
