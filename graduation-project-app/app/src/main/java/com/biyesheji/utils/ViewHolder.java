package com.biyesheji.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.biyesheji.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by yetwish on 2015-05-11
 */

public class ViewHolder {

    private SparseArray<View> mViews;
    private static Context mContext;
    private View mConvertView;
    private int mPosition;
    private View.OnClickListener onClickListener;

    /**
     * init holder
     */
    public ViewHolder(Context context, int layoutId, ViewGroup parent, int position) {
        mContext = context;
        mConvertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        mViews = new SparseArray<>();
        mPosition = position;
        mConvertView.setTag(this);
    }

    /**
     *  获取viewHolder
     */
    public static ViewHolder getHolder(Context context, View convertView,
                                       int layoutId, ViewGroup parent, int position) {
        if(convertView == null){
            return new ViewHolder(context,layoutId,parent,position);
        }else{
            ViewHolder holder = (ViewHolder)convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    public View getConvertView(){
        return mConvertView;
    }

    /**
     * get view
     */
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if(view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }

    /**
     * set text
     */
    public ViewHolder setText(int viewId, String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     *  set image res
     */
    public ViewHolder setImageResource(int viewId,String resId){
        ImageView iv = getView(viewId);
        asyncloadImage(iv,"http://10.0.2.2:8080/Login_war_exploded/img/"+resId);
        return this;
    }

    /**
     *  set image bitmap
     */
    public ViewHolder setImageBitmap(int viewId,Bitmap bitmap){
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
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


    public void setOnClickListener(int exercises_learn_list_item, View.OnClickListener onClickListener) {
        LinearLayout view=getView(exercises_learn_list_item);
        view.setOnClickListener(onClickListener);
    }
}
