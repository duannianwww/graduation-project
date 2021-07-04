package com.biyesheji.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.biyesheji.R;
import com.biyesheji.bean.video;

import java.util.List;

/**
 * Created by Administrator on 2021/2/28.
 */

public class dagangAdapter extends BaseAdapter {
    private Context context;
    private List<video> vb1;//视频列表数据
    private int selectedPosition=-1;//点击选中的位置
    public dagangAdapter(Context context) {
        this.context = context;
    }
    public void SetSelectedPosition(int position){
        this.selectedPosition=position;
    }
    /**
     * 设置数据，更新页面
     */
    public void setData(List<video> vb1){
        this.vb1=vb1;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return vb1==null?0:vb1.size();
    }

    @Override
    public Object getItem(int position) {
        return vb1==null?null:vb1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if(convertView==null){
            vh=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.dagang_list_item,null);
            vh.tv_title= (TextView) convertView.findViewById(R.id.tv_video_title);
            vh.iv_icon= (ImageView) convertView.findViewById(R.id.iv_left_icon);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        final video bean=vb1.get(position);
        vh.tv_title.setTextColor(Color.parseColor("#333333"));
        if(bean!=null){
            vh.tv_title.setText(bean.getDesc());
        }

        return convertView;
    }
    class ViewHolder{
        public TextView tv_title;
        public ImageView iv_icon;
    }
}
