package com.biyesheji.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.biyesheji.R;
import com.biyesheji.activity.CourseListActivity;
import com.biyesheji.bean.CourseBean;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2021/2/28.
 */

public class ExercisesAdapter extends BaseAdapter {
    private static Context mContext;
    public ExercisesAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private List<CourseBean> cad1;

    public void setData(List<CourseBean> cad1){
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
        final ViewHolder vh;
        //复用convertView
        if(convertView==null){
            vh=new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.exercises_learn_list_item,null);
            vh.title= (TextView) convertView.findViewById(R.id.tv_title);
            vh.content= (TextView) convertView.findViewById(R.id.tv_content);
            vh.order= (ImageView) convertView.findViewById(R.id.iv_order);
            convertView.setTag(vh);
        }else {
            vh=(ViewHolder)convertView.getTag();
        }
        final CourseBean bean=(CourseBean)getItem(position);
        if(bean!=null){
            vh.title.setText(bean.title);
            vh.content.setText("已更新"+bean.stock+"课时");
            asyncloadImage(vh.order,"http://10.0.2.2:8080/Login_war_exploded/img/"+bean.icon);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bean==null){
                    return;
                }
                //跳转到习题详情页面
                Intent intent=new Intent(mContext,CourseListActivity.class);
                intent.putExtra("id",bean.id);
                intent.putExtra("title",bean.title);
                intent.putExtra("intro",bean.intro);
                intent.putExtra("icon",bean.icon);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
    private void asyncloadImage(final ImageView imageView, final String uri) {
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    Bitmap bitmap = (Bitmap) msg.obj;
                    if (imageView != null && uri != null) {
                        imageView.setImageBitmap(bitmap);
                    }

                }
            }
        };
        // 子线程，开启子线程去下载或者去缓存目录找图片，并且返回图片在缓存目录的地址
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    //这个URI是图片下载到本地后的缓存目录中的URI
                    if (uri != null ) {
                        Bitmap bitmap = getLocalOrNetBitmap(uri);
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = bitmap;
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }
    public static Bitmap getLocalOrNetBitmap(String url)
    {
        Bitmap  bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.default_img);
        if (url != null) {
            InputStream in = null;
            BufferedOutputStream out = null;
            try
            {
                //读取图片输入流
                in = new BufferedInputStream(new URL(url).openStream(), 2 * 1024);
                //准备输出流
                final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                out = new BufferedOutputStream(dataStream, 2 * 1024);
                byte[] b = new byte[1024];
                int read;
                //将输入流变为输出流
                while ((read = in.read(b)) != -1) {
                    out.write(b, 0, read);
                }
                out.flush();
                //将输出流转换为bitmap
                byte[] data = dataStream.toByteArray();
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                data = null;
                return bitmap;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return bitmap;
            }
        }
        return bitmap;
    }
    class ViewHolder{
        public TextView title,content;
        public ImageView order;
    }
}
