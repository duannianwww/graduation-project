package com.biyesheji.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.biyesheji.R;
import com.biyesheji.activity.CoursePayActivity;
import com.biyesheji.activity.SearchCourseActivity;
import com.biyesheji.bean.SearchBean;
import com.biyesheji.utils.CommonAdapter;
import com.biyesheji.utils.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2021/3/2.
 */

public class SearchAdapter extends CommonAdapter<SearchBean> {

    public SearchAdapter(Context context, List<SearchBean> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, final int position) {
        holder.setImageResource(R.id.iv_order,mData.get(position).getComments())
                .setText(R.id.tv_title,mData.get(position).getTitle())
                .setText(R.id.tv_content,mData.get(position).getContent())
                .setOnClickListener(R.id.ln_exercises_learn,new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,CoursePayActivity.class);
                intent.putExtra("id",mData.get(position).getIconId());
                intent.putExtra("title",mData.get(position).getTitle());
                intent.putExtra("intro",mData.get(position).getContent());
                intent.putExtra("icon",mData.get(position).getComments());
                mContext.startActivity(intent);
                }
            });
    }
}
