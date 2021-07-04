package com.biyesheji.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.biyesheji.R;
import com.biyesheji.SQL.UserSQL;
import com.biyesheji.adapter.MyCourseAdapter;
import com.biyesheji.bean.CourseBean;
import com.biyesheji.utils.AnalysisUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyCourseActivity extends AppCompatActivity {

    private TextView tv_course_number;
    private ListView lv_list;
    private TextView tv_main_title;
    private TextView tv_back;
    private TextView tv_back_two;
    private List<List<CourseBean>> cb1;
    private MyCourseAdapter adapter;
    private String spUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);
        spUserName = AnalysisUtils.readLoginUserName(this);
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
    }

    private void initData() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        UserSQL userSQL=new UserSQL();
        String uservideotablename=userSQL.sea2(spUserName).get(0).getUsertablename();
        cb1= AnalysisUtils.getCourseInfowhereuser(uservideotablename);
    }

    private void initView() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("个人课程");
        tv_main_title.setTextColor(Color.BLACK);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setVisibility(View.GONE);
        tv_back_two = (TextView) findViewById(R.id.tv_back_two);
        tv_back_two.setVisibility(View.VISIBLE);
        tv_back_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCourseActivity.this.finish();
            }
        });
        tv_course_number = (TextView)findViewById(R.id.tv_course_number);
        lv_list = (ListView)findViewById(R.id.lv_list);
        adapter =new MyCourseAdapter(this);
        adapter.setData(cb1);
        lv_list.setAdapter(adapter);
    }
}
