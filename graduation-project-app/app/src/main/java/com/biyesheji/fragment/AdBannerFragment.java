package com.biyesheji.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.biyesheji.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2021/1/27.
 */

public class AdBannerFragment extends Fragment {
    private String ab; //广告
    private ImageView iv;//图片
    public static AdBannerFragment newInstance(Bundle args){
        final AdBannerFragment af=new AdBannerFragment();
        af.setArguments(args);
        return af;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg=getArguments();
        ab=arg.getString("ab");
    }

    @Override
    public void onResume() {
        super.onResume();
        switch (ab){
            case "banner_1" :
                iv.setImageResource(R.drawable.banner_1);
                break;
            case "banner_2" :
                iv.setImageResource(R.drawable.banner_2);
                break;
            case "banner_3" :
                iv.setImageResource(R.drawable.banner_3);
                break;
            case "banner_4" :
                iv.setImageResource(R.drawable.banner_4);
                break;
            case "banner_5" :
                iv.setImageResource(R.drawable.banner_5);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(iv!=null){
            iv.setImageDrawable(null);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //创建广告图片空间
        iv=new ImageView(getActivity());
        ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT);
        iv.setLayoutParams(lp);//图片的宽高参数
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        return iv;
    }
}
