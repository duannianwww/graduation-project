package com.biyesheji;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.biyesheji.SQL.UserSQL;
import com.biyesheji.activity.LoginActivity;
import com.biyesheji.activity.SearchCourseActivity;
import com.biyesheji.adapter.ExercisesAdapter;
import com.biyesheji.bean.CourseBean;
import com.biyesheji.utils.AnalysisUtils;
import com.biyesheji.view.CourseView;
import com.biyesheji.view.ExercisesView;
import com.biyesheji.view.MyInfoView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private MyInfoView mMyInfoView;
    private ExercisesView mExercisesView;
    private CourseView mCourseView;
    private RelativeLayout rl_course_history;
    private RelativeLayout rl_course_my;
    private RelativeLayout rl_usernotlogin;
    private TextView tv_course_number;
    private ListView lv_list;
    private TextView tv_user_course;
    private ExercisesAdapter adapter;
    private String spUserName;
    private List<CourseBean> cb1_exercises=new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        UserSQL userSQL=new UserSQL();
        spUserName = AnalysisUtils.readLoginUserName(this);
        String usercoursetablename= null;
        try {
            usercoursetablename = userSQL.sea2(spUserName).get(0).getUsertablename();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cb1_exercises = AnalysisUtils.getCourseInfowhereuser2(usercoursetablename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(data!=null){//设置登录状态
            boolean isLogin = data.getBooleanExtra("isLogin", false);
            //登录后3个页面都需要重置，不然会空指针
            if(mCourseView==null){
                mCourseView=new CourseView(this);
                mBobyLayout.addView(mCourseView.getView());
            }else {
                mCourseView.getView();
            }
            if(mExercisesView==null){
                mExercisesView=new ExercisesView(this);
                mBobyLayout.addView(mExercisesView.getView());
            }else {
                mExercisesView.getView();
            }
            if(mMyInfoView==null){
                mMyInfoView=new MyInfoView(this);
                mBobyLayout.addView(mMyInfoView.getView());
            }else {
                mMyInfoView.getView();
            }
            rl_course_history = (RelativeLayout)findViewById(R.id.rl_course_history);
            rl_course_my =(RelativeLayout)findViewById(R.id.rl_course_my);
            rl_usernotlogin =(RelativeLayout)findViewById(R.id.rl_usernotlogin);
            tv_course_number = (TextView) findViewById(R.id.tv_course_number);
            tv_user_course = (TextView) findViewById(R.id.tv_user_course);
            lv_list = (ListView) findViewById(R.id.lv_list_exercises);
            if(isLogin)//登录后各界面的变化
            {
                clearBottomImageState();
                selectDisplayView(2);

                rl_course_history.setVisibility(View.VISIBLE);
                rl_course_my.setVisibility(View.VISIBLE);
                rl_usernotlogin.setVisibility(View.GONE);
                tv_course_number.setVisibility(View.VISIBLE);
                tv_user_course.setVisibility(View.VISIBLE);
                lv_list.setVisibility(View.VISIBLE);
                tv_course_number.setText(cb1_exercises.size()+"");
                adapter = new ExercisesAdapter(this);
                adapter.setData(cb1_exercises);
                lv_list.setAdapter(adapter);
                setListViewHeight(lv_list);
            }else
            {
                tv_course_number.setVisibility(View.GONE);
                lv_list.setVisibility(View.GONE);
                rl_usernotlogin.setVisibility(View.VISIBLE);
                rl_course_history.setVisibility(View.GONE);
                tv_user_course.setVisibility(View.GONE);
                rl_course_my.setVisibility(View.GONE);
            }
            if(mMyInfoView!=null)
            {
                mMyInfoView.setLoginParams(isLogin);
            }
        }
    }

    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout rl_title_bar;
    private FrameLayout mBobyLayout;
    private LinearLayout mBottomLayout;
    private RelativeLayout mCourseBtn;
    private RelativeLayout mExercisesBtn;
    private RelativeLayout mMyinfoBtn;
    private TextView tv_course;
    private TextView tv_exercises;
    private TextView tv_myinfo;
    private ImageView iv_course;
    private ImageView iv_exercises;
    private ImageView iv_myinfo;
    private ImageView iv_search_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initBottomBar();
        setListener();
        setInitStatus();
    }

    private void setInitStatus() {//设置底部导航栏状态
        clearBottomImageState();
        setSelectedStatus(0);//选择第一个按钮
        createView(0);
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

    /*
    底部三按钮监听
     */
    private void setListener() {
        for(int i = 0; i< mBottomLayout.getChildCount(); i++) {
            mBottomLayout.getChildAt(i).setOnClickListener(this);
        }
    }

    private void initBottomBar() {//按钮初始化
        iv_search_button =(ImageView)findViewById(R.id.iv_search_button);
        mBottomLayout = (LinearLayout) findViewById(R.id.main_bottom_bar);
        mCourseBtn = (RelativeLayout) findViewById(R.id.bottom_bar_course_btn);
        mExercisesBtn = (RelativeLayout) findViewById(R.id.bottom_bar_exercises_btn);
        mMyinfoBtn = (RelativeLayout) findViewById(R.id.bottom_bar_myinfo_btn);
        tv_course = (TextView) findViewById(R.id.bottom_bar_text_course);
        tv_exercises = (TextView) findViewById(R.id.bottom_bar_text_exercises);
        tv_myinfo = (TextView) findViewById(R.id.bottom_bar_text_myinfo);
        iv_course =(ImageView)findViewById(R.id.bottom_bar_image_course);
        iv_exercises =(ImageView)findViewById(R.id.bottom_bar_image_exercises);
        iv_myinfo =(ImageView)findViewById(R.id.bottom_bar_image_myinfo);
        iv_search_button.setOnClickListener(this);
    }

    private void init() {//顶部标题栏初始化
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        iv_search_button = (ImageView) findViewById(R.id.iv_search_button);
        tv_main_title.setText("首页");
        rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#ffffff"));
        tv_back.setVisibility(View.GONE);
        iv_search_button.setVisibility(View.VISIBLE);
        initBodyLayout();
    }

    private void initBodyLayout() {//整体碎片布局初始化
        mBobyLayout = (FrameLayout) findViewById(R.id.main_body);
    }
    /*
    控件的点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //课程的点击事件
            case R.id.bottom_bar_course_btn:
                clearBottomImageState();
                selectDisplayView(0);
                break;
            //习题的点击事件
            case R.id.bottom_bar_exercises_btn:
                clearBottomImageState();
                selectDisplayView(1);
                break;
            //我的点击事件
            case R.id.bottom_bar_myinfo_btn:
                clearBottomImageState();
                selectDisplayView(2);
                break;
            case R.id.iv_search_button:
                Intent intent=new Intent(MainActivity.this,SearchCourseActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void selectDisplayView(int index) {
        removeAllView();
        createView(index);
        setSelectedStatus(index);
    }

    private void removeAllView() {
        for(int i=0;i<mBobyLayout.getChildCount();i++){
            mBobyLayout.getChildAt(i).setVisibility(View.GONE);
        }
    }

    /**
     * 所选界面
     * @param viewIndex
     */

    private void createView(int viewIndex) {
        switch (viewIndex)
        {
            case 0:
                //首页
                if(mCourseView==null){
                    mCourseView=new CourseView(this);
                    mBobyLayout.addView(mCourseView.getView());
                }else {
                    mCourseView.getView();
                }
                mCourseView.showView();
                break;
            case 1:
                //学习界面
                if(mExercisesView==null){
                    mExercisesView=new ExercisesView(this);
                    mBobyLayout.addView(mExercisesView.getView());
                }else {
                    mExercisesView.getView();
                }
                mExercisesView.showView();
                break;
            case 2:
                //我的界面
                if(mMyInfoView==null){
                    mMyInfoView=new MyInfoView(this);
                    mBobyLayout.addView(mMyInfoView.getView());
                }else {
                    mMyInfoView.getView();
                }
                mMyInfoView.showView();
                break;
        }
    }

    protected long exitTime;//记录第一次
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime)>2000){
                Toast.makeText(this,"再按一次退出闽科云",Toast.LENGTH_SHORT).show();
                exitTime= System.currentTimeMillis();
            }else {
                MainActivity.this.finish();
                if(readLoginStatus()){
                    clearLoginStatus();
                }
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void clearLoginStatus() {
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isLogin",false);
        editor.putString("loginUserName","");
        editor.commit();
    }
    private boolean readLoginStatus() {
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        boolean isLogin=sp.getBoolean("isLogin",false);
        return false;
    }
    public void Click3(View v)
    {
        Intent intent=new Intent(this, LoginActivity.class);
        startActivityForResult(intent,1);
    }
    /**
     * 选择后的标题栏状态
     * @param index
     */
    private void setSelectedStatus(int index) {
        switch (index){
            case 0:
                mCourseBtn.setSelected(true);
                iv_course.setImageResource(R.drawable.main_course_icon_selected);
                tv_course.setTextColor(Color.parseColor("#0097f7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                iv_search_button.setVisibility(View.VISIBLE);
                tv_main_title.setText("首页");
                break;
            case 1:
                mCourseBtn.setSelected(true);
                iv_exercises.setImageResource(R.drawable.main_exercises_icon_selected);
                tv_exercises.setTextColor(Color.parseColor("#0097f7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                iv_search_button.setVisibility(View.GONE);
                tv_main_title.setText("我的学习");
                break;
            case 2:
                mCourseBtn.setSelected(true);
                iv_myinfo.setImageResource(R.drawable.main_user_icon_selected);
                tv_myinfo.setTextColor(Color.parseColor("#0097f7"));
                rl_title_bar.setVisibility(View.GONE);
                iv_search_button.setVisibility(View.GONE);
                break;
        }
    }

    private void clearBottomImageState() {//清除按钮选中状态,使所有按钮初始化
        tv_course.setTextColor(Color.parseColor("#666666"));
        tv_exercises.setTextColor(Color.parseColor("#666666"));
        tv_myinfo.setTextColor(Color.parseColor("#666666"));
        iv_course.setImageResource(R.drawable.main_course_icon);
        iv_exercises.setImageResource(R.drawable.main_exercises_icon);
        iv_myinfo.setImageResource(R.drawable.main_user_icon);
        for(int i=0;i<mBottomLayout.getChildCount();i++) {
            mBottomLayout.getChildAt(i).setSelected(false);
        }
    }
}
