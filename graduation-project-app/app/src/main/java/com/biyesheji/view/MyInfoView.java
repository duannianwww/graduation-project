package com.biyesheji.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.biyesheji.R;
import com.biyesheji.activity.LoginActivity;
import com.biyesheji.activity.MyCourseActivity;
import com.biyesheji.activity.PlayHistoryActivity;
import com.biyesheji.activity.SettingActivity;
import com.biyesheji.activity.UserInfoActivity;
import com.biyesheji.utils.AnalysisUtils;

/**
 * Created by Administrator on 2021/1/26.
 */

public class MyInfoView {
    Context mContext;
    private final LayoutInflater mInflater;
    private View mCurrentView;
    private TextView tv_user_name;
    private RelativeLayout rl_setting;
    private RelativeLayout rl_course_history;
    private ImageView ll_head_icon;
    private LinearLayout ll_head;
    private RelativeLayout rl_course_my;

    public MyInfoView(Context mContext) {
        this.mContext = mContext;
        mInflater=LayoutInflater.from(mContext);
    }

    public View getView()
    {
        if(mCurrentView==null){
            createView();
        }
        return mCurrentView;
    }

    private void createView() {
        initView();
    }

    private void initView(){
        mCurrentView = mInflater.inflate(R.layout.main_view_myinfo, null);
        ll_head = (LinearLayout)mCurrentView.findViewById(R.id.ll_head);
        ll_head_icon = (ImageView) mCurrentView.findViewById(R.id.iv_head_icon);
        rl_course_history = (RelativeLayout)mCurrentView.findViewById(R.id.rl_course_history);
        rl_course_my =(RelativeLayout)mCurrentView.findViewById(R.id.rl_course_my);
        rl_setting = (RelativeLayout)mCurrentView.findViewById(R.id.rl_setting);
        tv_user_name = (TextView) mCurrentView.findViewById(R.id.tv_user_name);
        mCurrentView.setVisibility(View.VISIBLE);
        if(!readLoginStatus())
        {
            rl_course_history.setVisibility(View.GONE);
            rl_course_my.setVisibility(View.GONE);
        }
        setLoginParams(readLoginStatus());
        ll_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否已经登录
                if(readLoginStatus()){
                    //跳转到个人资料界面
                    Intent intent=new Intent(mContext, UserInfoActivity.class);
                    ((Activity)mContext).startActivity(intent);
                }else {
                    Intent intent=new Intent(mContext, LoginActivity.class);
                    ((Activity)mContext).startActivityForResult(intent,1);
                }
            }
        });
        rl_course_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readLoginStatus()){
                    //跳转到个人课程界面
                    Intent intent=new Intent(mContext, MyCourseActivity.class);
                    mContext.startActivity(intent);
                }else {
                    Toast.makeText(mContext,"您还未登录，请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });
        rl_course_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readLoginStatus()){
                    //跳转到播放记录界面
                    Intent intent=new Intent(mContext, PlayHistoryActivity.class);
                    mContext.startActivity(intent);
                }else {
                    Toast.makeText(mContext,"您还未登录，请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });
        rl_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readLoginStatus()){
                    //跳转到设置界面
                    Intent intent=new Intent(mContext, SettingActivity.class);
                    ((Activity)mContext).startActivityForResult(intent,1);
                }else {
                    Toast.makeText(mContext,"您还未登录，请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setLoginParams(boolean isLogin) {
        if(isLogin){
            tv_user_name.setText(AnalysisUtils.readLoginUserName(mContext));
        }else {
            tv_user_name.setText("点击登录");
        }
    }

    private boolean readLoginStatus() {
        SharedPreferences sp=mContext.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        boolean isLogin=sp.getBoolean("isLogin",false);
        return isLogin;
    }


    public void showView() {
        if(mCurrentView==null){
            createView();
        }
        mCurrentView.setVisibility(View.VISIBLE);
    }
}
