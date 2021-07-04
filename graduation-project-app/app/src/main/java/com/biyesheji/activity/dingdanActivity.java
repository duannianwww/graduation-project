package com.biyesheji.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.biyesheji.R;
import com.biyesheji.SQL.MySQL;
import com.biyesheji.SQL.UserSQL;
import com.biyesheji.adapter.CourseButtonAdapter;
import com.biyesheji.adapter.DingdanAdapter;
import com.biyesheji.bean.CourseBean;
import com.biyesheji.utils.AnalysisUtils;

import java.sql.SQLException;
import java.util.List;

public class dingdanActivity extends AppCompatActivity implements View.OnClickListener{

    private String title;
    private TextView tv_main_title;
    private TextView tv_back;
    private TextView tv_back_two;
    private ListView lv_list_course_dingdang;
    private DingdanAdapter adapter;
    private List<CourseBean> cad1;
    private TextView price_all;
    private TextView price_shifu;
    private int chapterId;
    private String intro;
    private String icon;
    private String spUserName;
    private String usercoursetablename;
    private Button btn_dingdanconfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingdan);
        //从课程页面传过来的章节id
        chapterId =getIntent().getIntExtra("id",0);
        //从课程传过来的章节简介
        intro =getIntent().getStringExtra("intro");
        title =getIntent().getStringExtra("title");
        icon =getIntent().getStringExtra("icon");
        spUserName = AnalysisUtils.readLoginUserName(this);
        initView();
    }

    private void initView() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("购买页面");
        tv_main_title.setTextColor(Color.BLACK);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setVisibility(View.GONE);
        tv_back_two = (TextView) findViewById(R.id.tv_back_two);
        tv_back_two.setVisibility(View.VISIBLE);
        tv_back_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dingdanActivity.this.finish();
            }
        });
        price_all =(TextView)findViewById(R.id.price_all);
        price_shifu =(TextView)findViewById(R.id.price_shifu);
        btn_dingdanconfirm =(Button)findViewById(R.id.btn_dingdanconfirm);
        lv_list_course_dingdang = (ListView)findViewById(R.id.lv_list_course_dingdang);
        btn_dingdanconfirm.setOnClickListener(this);
        adapter = new DingdanAdapter(this);
        initData();
        adapter.setData(cad1);
        price_all.setText("￥"+cad1.get(0).price);
        price_shifu.setText("￥"+cad1.get(0).price);
        lv_list_course_dingdang.setAdapter(adapter);
        setListViewHeight(lv_list_course_dingdang);
    }
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
        try {

            cad1 = AnalysisUtils.getCourseInfowherename(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dingdanconfirm:
                UserSQL userSQL=new UserSQL();
                try {
                    usercoursetablename =userSQL.sea2(spUserName).get(0).getUsertablename();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    userSQL.addusercourse(usercoursetablename,title);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(this,"课程购买成功！",Toast.LENGTH_SHORT).show();

                this.finish();

                break;
            default:
                break;
        }
    }
}
