package com.biyesheji.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.biyesheji.R;
import com.biyesheji.adapter.CourseButtonAdapter;
import com.biyesheji.bean.CourseBean;
import com.biyesheji.utils.AnalysisUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.List;

public class CourseButtonActivity extends AppCompatActivity {
    private List<CourseBean> cad1;
    private ListView lv_list_course_button;
    private CourseButtonAdapter adapter;
    PullToRefreshScrollView mPullRefreshScrollView;
    String type;
    private ScrollView mScrollView;
    private TextView tv_main_title;
    private TextView tv_back;
    private TextView tv_back_two;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_button);
        type=getIntent().getStringExtra("type");
        initView();
    }
//    private class GetDataTask extends AsyncTask<Void, Void, String[]> {
//
//        @Override
//        protected String[] doInBackground(Void... params) {
//            // Simulates a background job.
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String[] result) {
//            // Do some stuff here
//
//            // Call onRefreshComplete when the list has been refreshed.
//            initData();
//            AnalysisUtils.dengdai();
//            adapter.setData(cad1);
//            lv_list_course_button.setAdapter(adapter);
//            setListViewHeight(lv_list_course_button);
//            mPullRefreshScrollView.onRefreshComplete();
//
//            super.onPostExecute(result);
//        }
//    }
    private void initView() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText(type);
        tv_main_title.setTextColor(Color.BLACK);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setVisibility(View.GONE);
        tv_back_two = (TextView) findViewById(R.id.tv_back_two);
        tv_back_two.setVisibility(View.VISIBLE);
        tv_back_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseButtonActivity.this.finish();
            }
        });
//        mScrollView = (ScrollView)findViewById(R.id.sl_exercises);
//        mPullRefreshScrollView = (PullToRefreshScrollView)findViewById(R.id.pull_refresh_exercises_scrollview);
//        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
//
//            @Override
//            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                initData();
//                new CourseButtonActivity.GetDataTask().execute();
//            }
//        });
//        mScrollView = mPullRefreshScrollView.getRefreshableView();
        lv_list_course_button = (ListView)findViewById(R.id.lv_list_course_button);
        adapter = new CourseButtonAdapter(this);
        initData();
        adapter.setData(cad1);
        lv_list_course_button.setAdapter(adapter);
    }
    //???listview????????????????????????????????????????????????????????????
    public void setListViewHeight(ListView listView) {
        //??????listView???adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        //listAdapter.getCount()????????????????????????
        for (int i = 0,len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        // listView.getDividerHeight()???????????????????????????????????????
        // params.height??????????????????ListView???????????????????????????
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() *  (listAdapter .getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void initData() {
        try {
            if(type.equals("????????????")){
                cad1= AnalysisUtils.getCourseInfo();
            }
            else {
                cad1=AnalysisUtils.getCourseInfowheretype(type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
