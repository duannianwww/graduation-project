package com.biyesheji.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.biyesheji.R;
import com.biyesheji.SQL.MySQL;
import com.biyesheji.SQL.UserSQL;
import com.biyesheji.adapter.PinlunListAdapter;
import com.biyesheji.adapter.ShijuanAdapter;
import com.biyesheji.adapter.VideoListAdapter;
import com.biyesheji.bean.pinlun;
import com.biyesheji.bean.shijuan;
import com.biyesheji.bean.shiti;
import com.biyesheji.bean.video;
import com.biyesheji.utils.AnalysisUtils;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseListActivity extends AppCompatActivity implements View.OnClickListener{

    private int chapterId;
    private String intro;
    private String title;
    private String icon;
    private List<video> videoList;
    private List<shijuan> shitiList;
    private List<pinlun> pinlunList;
    private TextView tv_intro;
    private TextView tv_video;
    private ListView lv_video_list;
    private TextView tv_chapter_intro;
    private ScrollView sv_chapter;
    private VideoListAdapter adapter;
    private ListView lv_exercises_list;
    private TextView tv_exercises;
    private String videotablename;
    private String pinluntablename;
    private TextView tv_pinlun;
    private View in_bottm;
    private TextView tv_title_course;
    private TextView tv_back;
    private PinlunListAdapter pinlunadapter;
    private ListView lv_pinlun_list;
    private Button btn_confirm;
    private EditText et_discuss;
    private MySQL mySQL;
    private String shijuantablename;
    private ShijuanAdapter shijuanadapter;
    private String spUserName;
    private String uservideotablename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
            adapter.SetSelectedPosition(position);
            //视频选项卡选择的时候删除所有图标的颜色
            lv_video_list.setVisibility(View.VISIBLE);
            sv_chapter.setVisibility(View.GONE);
            tv_intro.setBackgroundColor(Color.parseColor("#FFFFFF"));
            tv_video.setBackgroundColor(Color.parseColor("#3084FF"));
            tv_exercises.setBackgroundColor(Color.parseColor("#FFFFFF"));
            tv_intro.setTextColor(Color.parseColor("#000000"));
            tv_video.setTextColor(Color.parseColor("#FFFFFF"));
            tv_exercises.setTextColor(Color.parseColor("#000000"));
        }
    }

    private boolean readLoginStatus() {
        SharedPreferences sp=getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin=sp.getBoolean("isLogin",false);
        return isLogin;
    }
    private void initView() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseListActivity.this.finish();
            }
        });
        tv_title_course =(TextView)findViewById(R.id.tv_title_course);
        tv_title_course.setText(title);
        tv_exercises = (TextView) findViewById(R.id.tv_exercises);
        tv_intro= (TextView) findViewById(R.id.tv_intro);
        tv_video = (TextView) findViewById(R.id.tv_video);
        btn_confirm =(Button)findViewById(R.id.btn_confirm);
        et_discuss =(EditText)findViewById(R.id.et_discuss);
        lv_exercises_list =(ListView) findViewById(R.id.lv_exercises_list);
        lv_pinlun_list = (ListView) findViewById(R.id.lv_pinlun_list);
        lv_video_list= (ListView) findViewById(R.id.lv_video_list);
        tv_chapter_intro= (TextView) findViewById(R.id.tv_chapter_intro);
        sv_chapter = (ScrollView) findViewById(R.id.sv_chapter_intro);
        tv_pinlun =(TextView)findViewById(R.id.tv_pinlun);
        in_bottm =(View)findViewById(R.id.in_bottm);
        tv_pinlun.setOnClickListener(this);
        tv_exercises.setOnClickListener(this);
        tv_intro.setOnClickListener(this);
        tv_video.setOnClickListener(this);
        tv_chapter_intro.setText(intro);

        tv_intro.setBackgroundColor(Color.parseColor("#3084FF"));
        tv_pinlun.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tv_exercises.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tv_video.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tv_video.setTextColor(Color.parseColor("#000000"));
        tv_pinlun.setTextColor(Color.parseColor("#000000"));
        tv_exercises.setTextColor(Color.parseColor("#000000"));
        adapter=new VideoListAdapter(this, new VideoListAdapter.OnSelectListener() {
            @Override
            public void onSelect(int position, ImageView iv) {
                adapter.SetSelectedPosition(position);
                video bean=videoList.get(position);
                UserSQL userSQL=new UserSQL();
                String videoPath="http://10.0.2.2:8080/Login_war_exploded/video/"+bean.getVideoname();
                adapter.notifyDataSetChanged();
                if(TextUtils.isEmpty(videoPath)){
                    Toast.makeText(CourseListActivity.this, "本地没有此视频，暂时无法播放", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    //判断用户是否登录，若登录则把此视频加到数据库
                    if(readLoginStatus()){
                        String userName= AnalysisUtils.readLoginUserName(CourseListActivity.this);
                        try {
                            uservideotablename = userSQL.sea2(spUserName).get(0).getUservideotablename();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            userSQL.adduservideo(uservideotablename,title,bean.getVideoname(),bean.getDesc());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //跳转到课程播放页面
                    Intent intent=new Intent(CourseListActivity.this,VideoPlayActivity.class);
                    intent.putExtra("videoPath",videoPath);
                    intent.putExtra("position",position);
                    startActivityForResult(intent,1);
                }
            }
        });

        lv_video_list.setAdapter(adapter);
        adapter.setData(videoList);
        pinlunadapter =new PinlunListAdapter(this);
        lv_pinlun_list.setAdapter(pinlunadapter);
        pinlunadapter.setData(pinlunList);
        shijuanadapter =new ShijuanAdapter(this);
        lv_exercises_list.setAdapter(shijuanadapter);
        shijuanadapter.setData(shitiList,title);
    }
    /*
    获取对应课程的试题与视频表
     */
    private void initData() {
        mySQL = new MySQL();
        try {
            videotablename = mySQL.sea2(title).getFilename();
            pinluntablename= mySQL.sea2(title).getFilenamepinlun();
            shijuantablename =mySQL.sea2(title).getShijuantablename();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            videoList= AnalysisUtils.getVideoInfo(videotablename);
            pinlunList= AnalysisUtils.getPinlunInfo(pinluntablename);
            shitiList=AnalysisUtils.getShijuanInfo(shijuantablename);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void Click4(View v) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if(!et_discuss.getText().toString().trim().equals("")){
            //添加评论
            MySQL mySQL=new MySQL();
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
            String sendtime=df.format(new Date());

            mySQL.addpinlun(spUserName,et_discuss.getText().toString().trim(),sendtime,pinluntablename);
            pinlunList= AnalysisUtils.getPinlunInfo(pinluntablename);
            pinlunadapter.setData(pinlunList);
            lv_pinlun_list.setAdapter(pinlunadapter);
            et_discuss.setText("");
            InputMethodManager imm = ( InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow( et_discuss.getWindowToken(), 0 );
            }
        }else
        {
            Toast.makeText(this,"请输入评论",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_intro:
                lv_video_list.setVisibility(View.GONE);
                lv_exercises_list.setVisibility(View.GONE);
                in_bottm.setVisibility(View.GONE);
                sv_chapter.setVisibility(View.VISIBLE);
                tv_intro.setBackgroundColor(Color.parseColor("#3084FF"));
                tv_video.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_exercises.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_pinlun.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_intro.setTextColor(Color.parseColor("#FFFFFF"));
                tv_video.setTextColor(Color.parseColor("#000000"));
                tv_pinlun.setTextColor(Color.parseColor("#000000"));
                tv_exercises.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv_exercises:
                lv_video_list.setVisibility(View.GONE);
                lv_exercises_list.setVisibility(View.VISIBLE);
                sv_chapter.setVisibility(View.GONE);
                in_bottm.setVisibility(View.GONE);
                tv_intro.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_video.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_exercises.setBackgroundColor(Color.parseColor("#3084FF"));
                tv_pinlun.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_intro.setTextColor(Color.parseColor("#000000"));
                tv_video.setTextColor(Color.parseColor("#000000"));
                tv_pinlun.setTextColor(Color.parseColor("#000000"));
                tv_exercises.setTextColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.tv_video:
                lv_video_list.setVisibility(View.VISIBLE);
                lv_exercises_list.setVisibility(View.GONE);
                in_bottm.setVisibility(View.GONE);
                sv_chapter.setVisibility(View.GONE);
                tv_intro.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_video.setBackgroundColor(Color.parseColor("#3084FF"));
                tv_exercises.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_pinlun.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_intro.setTextColor(Color.parseColor("#000000"));
                tv_video.setTextColor(Color.parseColor("#FFFFFF"));
                tv_pinlun.setTextColor(Color.parseColor("#000000"));
                tv_exercises.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.tv_pinlun:
                lv_video_list.setVisibility(View.GONE);
                in_bottm.setVisibility(View.VISIBLE);
                lv_exercises_list.setVisibility(View.GONE);
                sv_chapter.setVisibility(View.GONE);
                tv_intro.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_video.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_pinlun.setBackgroundColor(Color.parseColor("#3084FF"));
                tv_exercises.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_intro.setTextColor(Color.parseColor("#000000"));
                tv_video.setTextColor(Color.parseColor("#000000"));
                tv_pinlun.setTextColor(Color.parseColor("#FFFFFF"));
                tv_exercises.setTextColor(Color.parseColor("#000000"));
                break;
            case R.id.btn_confirm:
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 年月日 时分秒
                Date date = new Date(System.currentTimeMillis());//获取当前时间
                //做完用户部分才可继续
                break;
            default:
                break;
        }
    }
}
