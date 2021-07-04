package com.biyesheji.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.biyesheji.R;
import com.biyesheji.SQL.MySQL;
import com.biyesheji.SQL.UserSQL;
import com.biyesheji.adapter.PinlunListAdapter;
import com.biyesheji.adapter.dagangAdapter;
import com.biyesheji.bean.CourseBean;
import com.biyesheji.bean.pinlun;
import com.biyesheji.bean.video;
import com.biyesheji.utils.AnalysisUtils;

import java.sql.SQLException;
import java.util.List;

public class CoursePayActivity extends AppCompatActivity implements View.OnClickListener{
    private int chapterId;
    private String intro;
    private String title;
    private String icon;
    private TextView tv_title_course;
    private TextView tv_video;
    private TextView tv_intro;
    private ListView lv_video_list;
    private TextView tv_chapter_intro;
    private List<video> videoList;
    private List<pinlun> pinlunList;
    private String videotablename;
    private String pinluntablename;
    private TextView tv_pinlun;
    private ScrollView sv_chapter;
    private TextView tv_main_title;
    private TextView tv_back;
    private Button btn_confirm;
    private String spUserName;
    private PinlunListAdapter pinlunadapter;
    private ListView lv_pinlun_list_pay;
    private View in_bottm;
    private dagangAdapter adapter;
    private String usercoursetablename;
    private List<CourseBean> usercourseList;
    private boolean useryongyou=false;
    private View weicanjia;
    private View yicanjia;
    private Button btn_yicanjiaconfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_pay);
        //从课程页面传过来的章节id
        chapterId=getIntent().getIntExtra("id",0);
        //从课程传过来的章节简介
        intro=getIntent().getStringExtra("intro");
        title =getIntent().getStringExtra("title");
        icon=getIntent().getStringExtra("icon");
        spUserName = AnalysisUtils.readLoginUserName(this);
        initData();
        initView();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            int position=data.getIntExtra("position",0);
            lv_video_list.setVisibility(View.VISIBLE);
            sv_chapter.setVisibility(View.GONE);
            tv_intro.setBackgroundColor(Color.parseColor("#FFFFFF"));
            tv_video.setBackgroundColor(Color.parseColor("#3084FF"));
            tv_intro.setTextColor(Color.parseColor("#000000"));
            tv_video.setTextColor(Color.parseColor("#FFFFFF"));
        }
        if(requestCode==0xA)
        {
            UserSQL userSQL=new UserSQL();
            if(readLoginStatus()) try {
                usercoursetablename=userSQL.sea2(spUserName).get(0).getUsertablename();
                usercourseList =AnalysisUtils.getCourseInfowhereuser2(usercoursetablename);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for(CourseBean course:usercourseList)
            {
                if(course.title.equals(title)) useryongyou =true;
            }
            if(useryongyou){
                weicanjia.setVisibility(View.GONE);
                yicanjia.setVisibility(View.VISIBLE);
            }else {
                weicanjia.setVisibility(View.VISIBLE);
                yicanjia.setVisibility(View.GONE);
            }
        }
    }
    private void initView() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText(title);
        tv_main_title.setTextColor(Color.WHITE);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoursePayActivity.this.finish();
            }
        });
        tv_intro = (TextView) findViewById(R.id.tv_intro);
        tv_video = (TextView) findViewById(R.id.tv_video);
        lv_video_list = (ListView) findViewById(R.id.lv_video_list);
        tv_chapter_intro = (TextView) findViewById(R.id.tv_chapter_intro);
        sv_chapter = (ScrollView) findViewById(R.id.sv_chapter_intro);
        tv_pinlun =(TextView)findViewById(R.id.tv_pinlun);
        btn_confirm =(Button)findViewById(R.id.btn_confirm);
        btn_yicanjiaconfirm =(Button)findViewById(R.id.btn_yicanjiaconfirm);
        in_bottm =(View)findViewById(R.id.in_bottm);
        btn_confirm.setOnClickListener(this);
        btn_yicanjiaconfirm.setOnClickListener(this);
        tv_pinlun.setOnClickListener(this);
        tv_intro.setOnClickListener(this);
        tv_video.setOnClickListener(this);

        tv_chapter_intro.setText(intro);
        weicanjia =(View)findViewById(R.id.weicanjia);
        yicanjia =(View)findViewById(R.id.yicanjia);
        tv_intro.setBackgroundColor(Color.parseColor("#3084FF"));
        tv_pinlun.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tv_video.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tv_video.setTextColor(Color.parseColor("#000000"));
        tv_pinlun.setTextColor(Color.parseColor("#000000"));
        lv_pinlun_list_pay = (ListView) findViewById(R.id.lv_pinlun_list_pay);
        pinlunadapter =new PinlunListAdapter(this);
        adapter =new dagangAdapter(this);
        lv_video_list.setAdapter(adapter);
        adapter.setData(videoList);
        lv_pinlun_list_pay.setAdapter(pinlunadapter);
        pinlunadapter.setData(pinlunList);
        if(useryongyou){
            weicanjia.setVisibility(View.GONE);
            yicanjia.setVisibility(View.VISIBLE);
        }else {
            weicanjia.setVisibility(View.VISIBLE);
            yicanjia.setVisibility(View.GONE);
        }
    }

    private void initData() {
        MySQL mySQL=new MySQL();
        UserSQL userSQL=new UserSQL();
        try {
            if(readLoginStatus())usercoursetablename=userSQL.sea2(spUserName).get(0).getUsertablename();
            videotablename = mySQL.sea2(title).getFilename();
            pinluntablename=mySQL.sea2(title).getFilenamepinlun();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //判断用户是否登录，有登录则查找该用户所拥有的课程
            useryongyou=false;
            if(readLoginStatus()){usercourseList =AnalysisUtils.getCourseInfowhereuser2(usercoursetablename);
            for(CourseBean course:usercourseList)
            {
                if(course.title.equals(title)) useryongyou =true;
            }}
            pinlunList= AnalysisUtils.getPinlunInfo(pinluntablename);
            videoList= AnalysisUtils.getVideoInfo(videotablename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean readLoginStatus() {
        SharedPreferences sp=this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin=sp.getBoolean("isLogin",false);
        return isLogin;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
                if(readLoginStatus())
                {
                    Intent intent=new Intent(CoursePayActivity.this,dingdanActivity.class);
                    intent.putExtra("id",chapterId);
                    intent.putExtra("title",title);
                    intent.putExtra("intro",intro);
                    intent.putExtra("icon",icon);
                    startActivityForResult(intent,0xA);
                }else {
                    Toast.makeText(this,"您还未登录，请先登录",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_yicanjiaconfirm:
                Intent intent=new Intent(CoursePayActivity.this,CourseListActivity.class);
                intent.putExtra("id",chapterId);
                intent.putExtra("title",title);
                intent.putExtra("intro",intro);
                intent.putExtra("icon",icon);
                startActivity(intent);
            case R.id.tv_intro:
                lv_video_list.setVisibility(View.GONE);
                in_bottm.setVisibility(View.GONE);
                sv_chapter.setVisibility(View.VISIBLE);
                tv_intro.setBackgroundColor(Color.parseColor("#3084FF"));
                tv_video.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_pinlun.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_intro.setTextColor(Color.parseColor("#FFFFFF"));
                tv_video.setTextColor(Color.parseColor("#000000"));
                tv_pinlun.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv_video:
                lv_video_list.setVisibility(View.VISIBLE);
                in_bottm.setVisibility(View.GONE);
                sv_chapter.setVisibility(View.GONE);
                tv_intro.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_video.setBackgroundColor(Color.parseColor("#3084FF"));
                tv_pinlun.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_intro.setTextColor(Color.parseColor("#000000"));
                tv_video.setTextColor(Color.parseColor("#FFFFFF"));
                tv_pinlun.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv_pinlun:
                lv_video_list.setVisibility(View.GONE);
                in_bottm.setVisibility(View.VISIBLE);
                sv_chapter.setVisibility(View.GONE);
                tv_intro.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_video.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_pinlun.setBackgroundColor(Color.parseColor("#3084FF"));
                tv_intro.setTextColor(Color.parseColor("#000000"));
                tv_video.setTextColor(Color.parseColor("#000000"));
                tv_pinlun.setTextColor(Color.parseColor("#FFFFFF"));
                break;
            default:
                break;
        }
    }
}
