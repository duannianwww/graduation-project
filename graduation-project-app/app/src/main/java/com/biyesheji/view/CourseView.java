package com.biyesheji.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.biyesheji.MainActivity;
import com.biyesheji.R;
import com.biyesheji.activity.CourseButtonActivity;
import com.biyesheji.activity.SearchCourseActivity;
import com.biyesheji.adapter.AdBannerAdapter;
import com.biyesheji.adapter.CourseAdapter;
import com.biyesheji.bean.CourseBean;
import com.biyesheji.utils.AnalysisUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2021/1/26.
 */

public class CourseView implements View.OnClickListener{
    public static final int MSG_AD_SLID =002 ;//广告自动滚动
    private LayoutInflater mInflater;
    public FragmentActivity mContext;
    private ArrayList<CourseBean> cad1;
    private List<List<CourseBean>> cb1=new ArrayList<>();
    private View mCurrentView;
    private CourseAdapter adapter;
    private ViewPager adPager;
    private AdBannerAdapter ada;
    private ViewPagerIndicator vpi;
    private RelativeLayout adBannerLay;
    private MHandler mHandler=new MHandler();
    private ListView lv_list;
    PullToRefreshScrollView mPullRefreshScrollView;
    private ScrollView mScrollView;
    private ListView lv_list2;
    private ImageView iv_search_button;
    private LinearLayout ln_qimo;
    private LinearLayout ln_kaoyan;
    private LinearLayout ln_jisuanji;
    private LinearLayout ln_jinji;
    private LinearLayout ln_xinli;
    private LinearLayout ln_qizho;
    private LinearLayout ln_yingyu;
    private LinearLayout ln_kaozheng;
    private LinearLayout ln_jingping;
    private LinearLayout ln_quanbu;
    private List<List<CourseBean>> cb2;
    private List<List<CourseBean>> cb3;
    private List<List<CourseBean>> cb4=new ArrayList<>();
    private CourseAdapter adapter2;
    private List<List<CourseBean>> cb5;

    public CourseView(FragmentActivity context) {
        mContext=context;
        mInflater= LayoutInflater.from(context);

    }
    private void createView() {
        initAdData();
        getCourseData();
        initView();
        new AdAutoSlidThread().start();
    }
    /**
     * 动态计算控件大小
     */
    private void resetSize() {
        int sw=getScreenWidth(mContext);
        int adiHeight=sw/2;//广告条的宽度
        ViewGroup.LayoutParams adlp=adBannerLay.getLayoutParams();
        adlp.width=sw;
        adlp.height=adiHeight;
        adBannerLay.setLayoutParams(adlp);
    }
    private int getScreenWidth(Activity context) {
        DisplayMetrics displayMetrics=new DisplayMetrics();
        Display display=context.getWindowManager().getDefaultDisplay();
        display.getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(mContext,CourseButtonActivity.class);
        switch (v.getId()){
            //期末不挂的点击事件
            case R.id.ln_qimo:
                intent.putExtra("type","期末不挂");
                mContext.startActivity(intent);
                break;
            //22考研的点击事件
            case R.id.ln_kaoyan:
                intent.putExtra("type","22考研");
                mContext.startActivity(intent);
                break;
            //计算机的点击事件
            case R.id.ln_jisuanji:
                intent.putExtra("type","计算机");
                mContext.startActivity(intent);
                break;
            //金融学的点击事件
            case R.id.ln_jinji:
                intent.putExtra("type","金融学");
                mContext.startActivity(intent);
                break;
            //心理学的点击事件
            case R.id.ln_xinli:
                intent.putExtra("type","心理学");
                mContext.startActivity(intent);
                break;
            //期中满分的点击事件
            case R.id.ln_qizho:
                intent.putExtra("type","期中满分");
                mContext.startActivity(intent);
                break;
            //大学英语的点击事件
            case R.id.ln_yingyu:
                intent.putExtra("type","大学英语");
                mContext.startActivity(intent);
                break;
            //就业考证的点击事件
            case R.id.ln_kaozheng:
                intent.putExtra("type","就业考证");
                mContext.startActivity(intent);
                break;
            //精品课程的点击事件
            case R.id.ln_jingping:
                intent.putExtra("type","精品课程");
                mContext.startActivity(intent);
                break;
            //全部课程的点击事件
            case R.id.ln_quanbu:
                intent.putExtra("type","全部课程");
                mContext.startActivity(intent);
                break;
        }
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
            getCourseData();
            AnalysisUtils.dengdai();
            //根据某个值排序热门
            adapter.setData(cb1);
            lv_list.setAdapter(adapter);
            setListViewHeight(lv_list);
            //根据某个值排序好课
            adapter2.setData(cb4);
            lv_list2.setAdapter(adapter2);
            setListViewHeight(lv_list2);
            mPullRefreshScrollView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }

    private void initView() {

        mCurrentView=mInflater.inflate(R.layout.main_view_course,null);
        lv_list = (ListView) mCurrentView.findViewById(R.id.lv_list);
        adapter=new CourseAdapter(mContext);
        adapter.setData(cb1);
        lv_list.setAdapter(adapter);
        setListViewHeight(lv_list);
        lv_list2 =(ListView) mCurrentView.findViewById(R.id.lv_list2);
        adapter2 =new CourseAdapter(mContext);
        adapter2.setData(cb4);
        lv_list2.setAdapter(adapter2);
        setListViewHeight(lv_list2);
        mScrollView = (ScrollView) mCurrentView.findViewById(R.id.sl_course);
        mPullRefreshScrollView = (PullToRefreshScrollView) mCurrentView.findViewById(R.id.pull_refresh_scrollview);
        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getCourseData();
                new GetDataTask().execute();
            }
        });
        ln_qimo =(LinearLayout)mCurrentView.findViewById(R.id.ln_qimo);
        ln_kaoyan =(LinearLayout)mCurrentView.findViewById(R.id.ln_kaoyan);
        ln_jisuanji =(LinearLayout)mCurrentView.findViewById(R.id.ln_jisuanji);
        ln_jinji =(LinearLayout)mCurrentView.findViewById(R.id.ln_jinji);
        ln_xinli =(LinearLayout)mCurrentView.findViewById(R.id.ln_xinli);
        ln_qizho =(LinearLayout)mCurrentView.findViewById(R.id.ln_qizho);
        ln_yingyu =(LinearLayout)mCurrentView.findViewById(R.id.ln_yingyu);
        ln_kaozheng =(LinearLayout)mCurrentView.findViewById(R.id.ln_kaozheng);
        ln_jingping =(LinearLayout)mCurrentView.findViewById(R.id.ln_jingping);
        ln_quanbu =(LinearLayout)mCurrentView.findViewById(R.id.ln_quanbu);
        ln_qimo.setOnClickListener(this);
        ln_kaoyan.setOnClickListener(this);
        ln_jisuanji.setOnClickListener(this);
        ln_jinji.setOnClickListener(this);
        ln_xinli.setOnClickListener(this);
        ln_qizho.setOnClickListener(this);
        ln_yingyu.setOnClickListener(this);
        ln_kaozheng.setOnClickListener(this);
        ln_jingping.setOnClickListener(this);
        ln_quanbu.setOnClickListener(this);
        mScrollView = mPullRefreshScrollView.getRefreshableView();
        adPager = (ViewPager) mCurrentView.findViewById(R.id.vp_advertBanner);
        adPager.setOffscreenPageLimit(0);
        adPager.setLongClickable(false);
        ada=new AdBannerAdapter(mContext.getSupportFragmentManager(),mHandler);
        adPager.setAdapter(ada);
        adPager.setOnTouchListener(ada);
        vpi = (ViewPagerIndicator) mCurrentView.findViewById(R.id.vpi_advert_indicator);
        vpi.setCount(ada.getSize());
        adBannerLay= (RelativeLayout) mCurrentView.findViewById(R.id.rl_adBanner);
        adPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(ada.getSize()>0){
                    vpi.setCurrentPosition(position%ada.getSize());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        resetSize();
        if(cad1!=null){
            if(cad1.size()>0){
                vpi.setCount(cad1.size());
                vpi.setCurrentPosition(0);
            }
            ada.setDatas(cad1);
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
    private void initAdData() {
        cad1=new ArrayList<CourseBean>();
        for(int i=0;i<5;i++){
            CourseBean bean=new CourseBean();
            bean.id=(i+1);
            switch (i){
                case 0:
                    bean.icon="banner_1";
                    break;
                case 1:
                    bean.icon="banner_2";
                    break;
                case 2:
                    bean.icon="banner_3";
                    break;
                case 3:
                    bean.icon="banner_4";
                    break;
                case 4:
                    bean.icon="banner_5";
                    break;
                default:break;
            }
            cad1.add(bean);
        }
    }
    /**
     * 获取课程信息
     */
    public void getCourseData() {

        try {
            //根据某个值随机排序热门
            cb2 = AnalysisUtils.getCourseInfos();
            cb5 =getListrandom(cb2);
            cb1=new ArrayList<>();
            if(cb5.size()>2)
            {
                if(cb1.size()<1) {
                for(int i=0;i<2;i++)
                    {
                        cb1.add(cb5.get(i));
                    }
                }
            }
            else {
                cb1=cb5;
            }
            //根据某个值排序好课，随机推荐
            cb3 =getListrandom(cb2);
            cb4=new ArrayList<>();
            if(cb3.size()>2)
            {
                if(cb4.size()<1)
                {
                for(int i=0;i<2;i++)
                    {
                        cb4.add(cb3.get(i));
                    }
                }
            }else
            {
                cb4 =cb3;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private List<List<CourseBean>> getListrandom( List<List<CourseBean>> list)
    {

        Random random = new Random();
        for (int i = 0; i <list.size(); i++)
        {
            List<CourseBean>  temp;
            int j = random.nextInt(list.size());
            //交换两个数据的值
            temp = list.get(j);
            list.set(j, list.get(i));
            list.set(i, temp);
        }
        return list;
    }
    private class AdAutoSlidThread extends Thread {
        public AdAutoSlidThread(FragmentManager fragmentManager, Handler mHandler) {

        }

        public AdAutoSlidThread() {

        }

        @Override
        public void run() {
            super.run();
            while (true){
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(mHandler!=null){
                    mHandler.sendEmptyMessage(MSG_AD_SLID);
                }
            }
        }
    }
    private class MHandler extends Handler{
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what){
                case MSG_AD_SLID:
                    if(ada.getCount()>0){
                        adPager.setCurrentItem(adPager.getCurrentItem()+1);
                    }

                    break;
            }
        }
    }
    public View getView(){
        if(mCurrentView==null){
            createView();
        }
        return mCurrentView;
    }
    public void showView(){
        if(mCurrentView==null){
            createView();
        }
        mCurrentView.setVisibility(View.VISIBLE);
    }
}
