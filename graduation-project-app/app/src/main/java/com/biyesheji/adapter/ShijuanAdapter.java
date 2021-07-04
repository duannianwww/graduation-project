package com.biyesheji.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.biyesheji.R;
import com.biyesheji.SQL.MySQL;
import com.biyesheji.activity.CourseListActivity;
import com.biyesheji.activity.ExercisesDetailActivity;
import com.biyesheji.bean.CourseBean;
import com.biyesheji.bean.shijuan;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2021/3/2.
 */

public class ShijuanAdapter extends BaseAdapter {
    private static Context mContext;
    private MySQL mySQL;

    public ShijuanAdapter(Context context) {
        this.mContext = context;
    }

    private List<shijuan> cad1;
    private String title;
    private int shijuannumber;

    public void setData(List<shijuan> cad1,String title){
        this.title=title;
        this.cad1=cad1;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cad1==null ? 0:cad1.size();
    }

    @Override
    public Object getItem(int position) {
        return cad1==null?null:cad1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ShijuanAdapter.ViewHolder vh;
        mySQL = new MySQL();
        //复用convertView
        if(convertView==null){
            vh=new ShijuanAdapter.ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.exercises_list_item,null);
            vh.xuhao=(TextView)convertView.findViewById(R.id.tv_order);
            vh.title= (TextView) convertView.findViewById(R.id.tv_title);
            vh.content= (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(vh);
        }else {
            vh=(ShijuanAdapter.ViewHolder)convertView.getTag();
        }
        final shijuan bean=(shijuan)getItem(position);
        try {
            shijuannumber=mySQL.seaquestioncourse(title,bean.getShiuanname()).size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(bean!=null){
            vh.xuhao.setText(bean.getShijuanid()+"");
            vh.title.setText(bean.getShiuanname());
            vh.content.setText("共有"+shijuannumber+"题");
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bean==null){
                    return;
                }
                //跳转到习题详情页面
                Intent intent=new Intent(mContext,ExercisesDetailActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("shijuanname",bean.getShiuanname());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHolder{
        public TextView xuhao,title,content;
    }
}
