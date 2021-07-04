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

public class VideoListAdapter extends BaseAdapter {
    private Context context;
    private List<video> vb1;//视频列表数据
    private int selectedPosition=-1;//点击选中的位置
    private OnSelectListener onSelectListener;
    public VideoListAdapter(Context context, OnSelectListener onSelectListener) {
        this.context = context;
        this.onSelectListener = onSelectListener;
    }
    public VideoListAdapter(Context context) {
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
            convertView= LayoutInflater.from(context).inflate(R.layout.video_list_item,null);
            vh.tv_title= (TextView) convertView.findViewById(R.id.tv_video_title);
            vh.iv_icon= (ImageView) convertView.findViewById(R.id.iv_left_icon);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        final video bean=vb1.get(position);
        vh.iv_icon.setImageResource(R.drawable.course_bar_icon);
        vh.tv_title.setTextColor(Color.parseColor("#333333"));
        if(bean!=null){
            vh.tv_title.setText(bean.getDesc());
            if(selectedPosition==position){
                vh.iv_icon.setImageResource(R.drawable.course_intro_icon);
                vh.tv_title.setTextColor(Color.parseColor("#009958"));
            }else {
                vh.iv_icon.setImageResource(R.drawable.course_bar_icon);
                vh.tv_title.setTextColor(Color.parseColor("#333333"));
            }
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bean==null){
                    return;
                }
                //播放视频
                onSelectListener.onSelect(position,vh.iv_icon);
            }
        });
        return convertView;
    }
    class ViewHolder{
        public TextView tv_title;
        public ImageView iv_icon;
    }
    /*
        创建OnSelectListener接口把位置position和控件iv传递给界面
     */
    public interface OnSelectListener{
        void onSelect(int position,ImageView iv);
    }
}
