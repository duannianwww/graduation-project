package com.biyesheji.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.biyesheji.R;
import com.biyesheji.SQL.UserSQL;
import com.biyesheji.activity.LoginActivity;
import com.biyesheji.adapter.ExercisesAdapter;
import com.biyesheji.bean.CourseBean;
import com.biyesheji.bean.kecheng;
import com.biyesheji.bean.shiti;
import com.biyesheji.utils.AnalysisUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2021/1/26.
 */

public class ExercisesView {
    private String spUserName;
    private Activity mContext;
    private final LayoutInflater mInflater;
    private View mCurrentView;
    private List<CourseBean> cad1=new ArrayList<>();
    private ListView lv_list;
    private ExercisesAdapter adapter;
    PullToRefreshScrollView mPullRefreshScrollView;
    private ScrollView mScrollView;
    private TextView tv_course_number;
    private RelativeLayout rl_usernotlogin;
    private TextView tv_user_course;

    public ExercisesView(Activity context){
        mContext=context;
        mInflater = LayoutInflater.from(mContext);
    }
    public View getView(){
        if(mCurrentView==null){
            createView();
        }
        return mCurrentView;
    }
    private void createView() {
        initView();
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            // Do some stuff here

            // Call onRefreshComplete when the list has been refreshed.

            initData();
            AnalysisUtils.dengdai();
            adapter.setData(cad1);
            tv_course_number.setText(cad1.size()+"");
            lv_list.setAdapter(adapter);
            setListViewHeight(lv_list);
            mPullRefreshScrollView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }
    private void initView() {
        mCurrentView=mInflater.inflate(R.layout.main_view_exercises,null);

        tv_course_number = (TextView) mCurrentView.findViewById(R.id.tv_course_number);
        lv_list = (ListView) mCurrentView.findViewById(R.id.lv_list_exercises);
        rl_usernotlogin =(RelativeLayout) mCurrentView.findViewById(R.id.rl_usernotlogin);
        tv_user_course = (TextView) mCurrentView.findViewById(R.id.tv_user_course);
        if(readLoginStatus())
        {
            rl_usernotlogin.setVisibility(View.GONE);
            tv_course_number.setVisibility(View.VISIBLE);
            tv_user_course.setVisibility(View.VISIBLE);
            lv_list.setVisibility(View.VISIBLE);
        }
        else {
            rl_usernotlogin.setVisibility(View.VISIBLE);
            tv_course_number.setVisibility(View.GONE);
            tv_user_course.setVisibility(View.GONE);
            lv_list.setVisibility(View.GONE);
        }
        rl_usernotlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, LoginActivity.class);
                ((Activity)mContext).startActivityForResult(intent,1);
            }
        });
        adapter = new ExercisesAdapter(mContext);
        initData();
        adapter.setData(cad1);
        tv_course_number.setText(cad1.size()+"");
        lv_list.setAdapter(adapter);
        setListViewHeight(lv_list);
        mScrollView = (ScrollView) mCurrentView.findViewById(R.id.sl_exercises);
        mPullRefreshScrollView = (PullToRefreshScrollView) mCurrentView.findViewById(R.id.pull_refresh_exercises_scrollview);
        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                initData();
                new GetDataTask().execute();
            }
        });
        mScrollView = mPullRefreshScrollView.getRefreshableView();
    }
    private boolean readLoginStatus() {
        SharedPreferences sp=mContext.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin=sp.getBoolean("isLogin",false);
        return isLogin;
    }
    //为listview动态设置高度（有多少条目就显示多少条目）
    public void setListViewHeight(ListView listView) {
        //获取listView的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        //listAdapter.getCount()返回数据项的数目
        for (int i = 0,len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() *  (listAdapter .getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void initData() {
        spUserName = AnalysisUtils.readLoginUserName(mContext);
        try {
            UserSQL userSQL=new UserSQL();
            String usercoursetablename=userSQL.sea2(spUserName).get(0).getUsertablename();
            cad1= AnalysisUtils.getCourseInfowhereuser2(usercoursetablename);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showView(){
        if(mCurrentView==null){
            createView();
        }
        mCurrentView.setVisibility(View.VISIBLE);
    }
}
