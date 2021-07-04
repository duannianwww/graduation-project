package com.biyesheji.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.biyesheji.R;
import com.biyesheji.SQL.BDmanage_search;
import com.biyesheji.SQL.MySQL;
import com.biyesheji.adapter.SearchAdapter;
import com.biyesheji.adapter.TagAdapter;
import com.biyesheji.bean.SearchBean;
import com.biyesheji.bean.SearchItem;
import com.biyesheji.utils.AnalysisUtils;
import com.biyesheji.view.FlowLayout;
import com.biyesheji.view.SearchView;
import com.biyesheji.view.TagFlowLayout;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchCourseActivity extends AppCompatActivity implements SearchView.SearchViewListener ,View.OnClickListener{
    private Button search_btn_back;
    /**
     * 搜索结果列表view
     */
    private ListView lvResults;

    /**
     * 搜索view
     */
    private SearchView searchView;


    /**
     * 热搜框列表adapter
     */
    private ArrayAdapter<String> hintAdapter;

    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter;

    /**
     * 搜索结果列表adapter
     */
    private SearchAdapter resultAdapter;

    /**
     * 数据库数据，总数据
     */
    private List<SearchBean> dbData;

    /**
     * 热搜版数据
     */
    private List<String> hintData;

    /**
     * 搜索过程中自动补全数据
     */
    private List<String> autoCompleteData;

    /**
     * 搜索结果的数据
     */
    private List<SearchBean> resultData;

    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE = 4;

    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;
    private LinearLayout ln_searchjilu;
    private ScrollView lv_searchjilu;
    private TagFlowLayout mFlowLayout;
    private List<String> strings;
    //布局管理器
    private LayoutInflater mInflater;
    //流式布局的子布局
    private TextView tv;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mFlowLayout.setAdapter(new TagAdapter<String>(strings) {
                        @Override
                        public View getView(FlowLayout parent, int position, String s) {
                            //添加搜索记录到搜索记录表

                            tv = (TextView) mInflater.inflate(R.layout.tv,
                                    mFlowLayout, false);
                            tv.setText(s);
                            return tv;
                        }
                    });
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private EditText etInput;
    private String spUserName;
    private TextView search_qinchu;
    private BDmanage_search db;
    List<SearchItem> data;
    private ListView lvTips;

    /**
     * 设置提示框显示项的个数
     *
     * @param hintSize 提示框显示个数
     */
    public static void setHintSize(int hintSize) {
        SearchCourseActivity.hintSize = hintSize;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);
        spUserName = AnalysisUtils.readLoginUserName(this);

        initjiluData();
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
    }

    private void initjiluData() {
        //从数据库获取该用户的搜索记录，添加到strings
        strings = new ArrayList<>();
        db =new BDmanage_search(this);
        data=db.queryAll(spUserName);

        if(data!=null&&data.size()!=0)
        {
            for(int i=0;i<data.size();i++)
            {
                strings.add(data.get(i).getSearchtext());
            }
        }
        handler.sendEmptyMessageDelayed(1, 0);
    }

    private void initView() {
        lvResults = (ListView) findViewById(R.id.lv_search);
        lv_searchjilu = (ScrollView) findViewById(R.id.lv_searchjilu);
        ln_searchjilu =(LinearLayout) findViewById(R.id.ln_searchjilu);
        searchView = (SearchView) findViewById(R.id.main_search_layout);
        search_qinchu =(TextView)findViewById(R.id.search_qinchu);
        lvTips = (ListView) findViewById(R.id.search_lv_tips);
        search_qinchu.setOnClickListener(this);
        //设置监听
        searchView.setSearchViewListener(this);
        //设置adapter
        searchView.setAutoCompleteAdapter(autoCompleteAdapter);
        mInflater = LayoutInflater.from(this);
        mFlowLayout = (TagFlowLayout) findViewById(R.id.id_flowlayout);
        etInput = (EditText) findViewById(R.id.search_et_input);

        //流式布局tag的点击方法
        mFlowLayout.setMaxSelectCount(3);
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                //点击后进入到这个词条的搜索界面
                etInput.setText(strings.get(position));
                onSearch(strings.get(position));
                //请求焦点，将光标移到最后
                etInput.requestFocus();
                etInput.setSelection(etInput.getText().length());
                lvTips.setVisibility(View.GONE);
                return true;
            }
        });
    }

    private void initData() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //从数据库获取数据
        getDbData();
        //初始化自动补全数据
        getAutoCompleteData(null);
        //初始化搜索结果数据
        getResultData(null);
    }
    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
        if (resultData == null) {
            // 初始化
            resultData = new ArrayList<>();
        } else {
            resultData.clear();
            for (int i = 0; i < dbData.size(); i++) {
                if (dbData.get(i).getTitle().contains(text.trim())) {
                    resultData.add(dbData.get(i));
                }
            }
        }
        if (resultAdapter == null) {
            resultAdapter = new SearchAdapter(this, resultData, R.layout.exercises_learn_list_item);
        } else {
            resultAdapter.notifyDataSetChanged();
        }
    }
    /**
     * 获取自动补全data 和adapter
     */
    private void getAutoCompleteData(String text) {
        if (autoCompleteData == null) {
            //初始化
            autoCompleteData = new ArrayList<>(hintSize);
        } else {
            // 根据text 获取auto data
            autoCompleteData.clear();
            for (int i = 0, count = 0; i < dbData.size()
                    && count < hintSize; i++) {
                if (dbData.get(i).getTitle().contains(text.trim())) {
                    autoCompleteData.add(dbData.get(i).getTitle());
                    count++;
                }
            }
        }
        if (autoCompleteAdapter == null) {
            autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autoCompleteData);
        } else {
            autoCompleteAdapter.notifyDataSetChanged();
        }
    }
    /**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     * @param text
     */
    @Override
    public void onRefreshAutoComplete(String text) {
        //更新数据
        getAutoCompleteData(text);
    }
    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text
     */
    @Override
    public void onSearch(String text) {
        //更新result数据
        getResultData(text);
        lvResults.setVisibility(View.VISIBLE);
        //第一次获取结果 还未配置适配器
        if (lvResults.getAdapter() == null) {
            //获取搜索数据 设置适配器
            lv_searchjilu.setVisibility(View.GONE);
            ln_searchjilu.setVisibility(View.GONE);
            lvResults.setVisibility(View.VISIBLE);
            lvResults.setAdapter(resultAdapter);
            String aa = etInput.getText().toString().trim();
            //判断有没有重复的搜索记录，没有则通知handler更新UI
            if(!strings.contains(aa))
            {
                strings.add(aa);
                db.insert(spUserName,aa);
            }
            handler.sendEmptyMessageDelayed(1, 0);
        } else {
            //更新搜索数据
            lv_searchjilu.setVisibility(View.GONE);
            ln_searchjilu.setVisibility(View.GONE);
            lvResults.setVisibility(View.VISIBLE);
            resultAdapter.notifyDataSetChanged();
            String aa = etInput.getText().toString().trim();
            //判断有没有重复的搜索记录，没有则通知handler更新UI
            if(!strings.contains(aa))
            {
                strings.add(aa);
                db.insert(spUserName,aa);
            }
            handler.sendEmptyMessageDelayed(1, 0);
        }
    }
    /**
     * 获取db 数据
     */
    public void getDbData() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //获取mysql数据
        dbData= AnalysisUtils.getSearchInfo();
    }
    private boolean readLoginStatus() {
        SharedPreferences sp=this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin=sp.getBoolean("isLogin",false);
        return isLogin;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_qinchu:

                AlertDialog dialog;
                dialog=new AlertDialog.Builder(this)
                        .setMessage("确认删除全部历史记录吗？")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(readLoginStatus())
                                {
                                    strings.clear();
                                    handler.sendEmptyMessageDelayed(1, 0);
                                    //删除数据库历史记录
                                    db.deleteall(spUserName);
                                }
                                {
                                    Toast.makeText(SearchCourseActivity.this,"对不起，您还未登录,登录后即可管理搜索记录",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setPositiveButton("取消",new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                break;
        }
    }
}
