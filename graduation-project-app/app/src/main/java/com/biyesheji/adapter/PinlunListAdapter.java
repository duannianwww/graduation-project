package com.biyesheji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.biyesheji.R;
import com.biyesheji.bean.pinlun;

import java.util.List;

/**
 * Created by Administrator on 2021/3/2.
 */

public class PinlunListAdapter extends BaseAdapter {
    private static Context mContext;
    public PinlunListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private List<pinlun> pd1;

    public void setData(List<pinlun> pd1){
        this.pd1=pd1;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return pd1==null ? 0:pd1.size();
    }

    @Override
    public Object getItem(int position) {
        return pd1==null?null:pd1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PinlunListAdapter.ViewHolder vh;
        //复用convertView
        if(convertView==null){
            vh=new PinlunListAdapter.ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.pinlun_list_item,null);
            vh.username= (TextView) convertView.findViewById(R.id.tv_pinlun_username);
            vh.sendtime= (TextView) convertView.findViewById(R.id.tv_pinlun_sendtime);
            vh.comment= (TextView) convertView.findViewById(R.id.tv_comment);
            convertView.setTag(vh);
        }else {
            vh=(PinlunListAdapter.ViewHolder)convertView.getTag();
        }
        final pinlun bean=(pinlun)getItem(position);
        if(bean!=null){
            vh.username.setText(bean.getUsername());
            vh.sendtime.setText("发表于"+bean.getSendtime());
            vh.comment.setText(bean.getComment());
        }
        return convertView;
    }
    class ViewHolder{
        public TextView username,sendtime,comment;
        public ImageView order;
    }
}
