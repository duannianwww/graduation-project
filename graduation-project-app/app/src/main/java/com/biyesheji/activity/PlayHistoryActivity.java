package com.biyesheji.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.biyesheji.R;
import com.biyesheji.SQL.UserSQL;
import com.biyesheji.adapter.PlayHistoryAdapter;
import com.biyesheji.bean.uservideo;
import com.biyesheji.utils.AnalysisUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayHistoryActivity extends AppCompatActivity {

    private List<uservideo> vb1;
    private TextView tv_main_title;
    private TextView tv_back;
    private TextView tv_back_two;
    private ListView lv_list;
    private TextView tv_none;
    private PlayHistoryAdapter adapter;
    private String spUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_history);
        spUserName = AnalysisUtils.readLoginUserName(this);
        vb1 =new ArrayList<uservideo>();

        //查询数据库用户视频表
//        vb1 =db.getVideoHistory(AnalysisUtils.readLoginUserName(this));
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
    }

    private void initData() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        UserSQL userSQL=new UserSQL();
        String uservideotablename=userSQL.sea2(spUserName).get(0).getUservideotablename();
        vb1 =userSQL.seauservideo(uservideotablename);
    }

    private void initView() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("播放记录");
        tv_main_title.setTextColor(Color.BLACK);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setVisibility(View.GONE);
        tv_back_two = (TextView) findViewById(R.id.tv_back_two);
        tv_back_two.setVisibility(View.VISIBLE);
        tv_back_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayHistoryActivity.this.finish();
            }
        });
        lv_list = (ListView) findViewById(R.id.lv_list);
        tv_none = (TextView) findViewById(R.id.tv_none);
        if(vb1.size()==0){
            tv_none.setVisibility(View.VISIBLE);
        }
        adapter =new PlayHistoryAdapter(this);
        adapter.setData(vb1);
        lv_list.setAdapter(adapter);
    }
}
