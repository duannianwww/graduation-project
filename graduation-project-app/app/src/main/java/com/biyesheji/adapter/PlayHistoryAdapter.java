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
import com.biyesheji.activity.VideoPlayActivity;
import com.biyesheji.bean.uservideo;
import com.biyesheji.bean.video;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by Administrator on 2021/2/28.
 */

public class PlayHistoryAdapter extends BaseAdapter {
    private Context context;
    private List<uservideo> vb1;

    public PlayHistoryAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<uservideo> vb1){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if(convertView==null){
            vh=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.play_history_list_item,null);
            vh.tv_adapter_title= (TextView) convertView.findViewById(R.id.tv_adapter_title);
            vh.tv_video_title=(TextView)convertView.findViewById(R.id.tv_video_title);
            vh.iv_icon= (ImageView) convertView.findViewById(R.id.iv_video_icon);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        final uservideo bean= (uservideo) getItem(position);
        if(bean!=null){
            vh.tv_adapter_title.setText(bean.getVideoname());
            vh.tv_video_title.setText(bean.getTitle());
            loadCover(vh.iv_icon,"http://10.0.2.2:8080/Login_war_exploded/video/"+bean.getVideopath(),context);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bean==null)return;
                Intent intent=new Intent(context, VideoPlayActivity.class);
                intent.putExtra("videoPath","http://10.0.2.2:8080/Login_war_exploded/video/"+bean.getVideopath());
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    public static void loadCover(ImageView imageView, String url, Context context) {

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(4000000)
                                .centerCrop()
                                .error(R.drawable.default_img)//可以忽略
                                .placeholder(R.drawable.default_img)//可以忽略
                )
                .load(url)
                .into(imageView);
    }
    class ViewHolder{
        TextView tv_adapter_title;
        TextView tv_video_title;
        ImageView iv_icon;
    }
}
