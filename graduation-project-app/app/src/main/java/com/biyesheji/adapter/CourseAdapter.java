package com.biyesheji.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.biyesheji.R;
import com.biyesheji.SQL.MySQL;
import com.biyesheji.activity.CoursePayActivity;
import com.biyesheji.bean.CourseBean;
import com.biyesheji.bean.kecheng;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2021/1/26.
 */

public class CourseAdapter extends BaseAdapter{
    private static Context context;
    private List<List<CourseBean>> cb1;
    private CourseBean leftImg;
    private Bitmap bm;
    static boolean flag=false;
    public CourseAdapter(Context context) {
        this.context = context;
    }

    /**
     * 数据更新方法
     * @param cb1
     */
    public void setData(List<List<CourseBean>> cb1) {
        this.cb1 = cb1;
    }

    @Override
    public int getCount() {
        Log.i("adaptersize",cb1.size()+"");
        return cb1==null?0:cb1.size();
    }

    @Override
    public Object getItem(int position) {
        Log.i("adapterlimian",position+"");
        return cb1==null?null:cb1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.course_list_item, null);
            vh.iv_left_img = (ImageView) convertView.findViewById(R.id.iv_left_img);
            vh.iv_right_img = (ImageView) convertView.findViewById(R.id.iv_right_img);
            vh.tv_left_img_title = (TextView) convertView.findViewById(R.id.tv_left_img_title);
            vh.tv_right_img_title = (TextView) convertView.findViewById(R.id.tv_right_img_title);
            vh.tv_left_title = (TextView) convertView.findViewById(R.id.tv_left_title);
            vh.tv_right_title = (TextView) convertView.findViewById(R.id.tv_right_title);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        List<CourseBean> list = (List<CourseBean>) getItem(position);
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {

                final CourseBean bean = list.get(i);
                switch (i) {
                    case 0://左边
                        vh.tv_left_img_title.setText(bean.title);
                        vh.tv_left_title.setText(bean.title);
                        asyncloadImage(vh.iv_left_img,"http://10.0.2.2:8080/Login_war_exploded/img/"+bean.icon);
                        vh.iv_left_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //跳转到课程详情页面
                                Intent intent=new Intent(context, CoursePayActivity.class);
                                intent.putExtra("id",bean.id);
                                intent.putExtra("title",bean.title);
                                intent.putExtra("intro",bean.intro);
                                context.startActivity(intent);
                            }
                        });
                        break;
                    case 1://右边
                        vh.tv_right_img_title.setText(bean.title);
                        vh.tv_right_title.setText(bean.title);
                        asyncloadImage(vh.iv_right_img,"http://10.0.2.2:8080/Login_war_exploded/img/"+bean.icon);
                        vh.iv_right_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //跳转到课程详情页面
                                Intent intent=new Intent(context, CoursePayActivity.class);
                                intent.putExtra("id",bean.id);
                                intent.putExtra("title",bean.title);
                                intent.putExtra("intro",bean.intro);
                                context.startActivity(intent);
                            }
                        });
                        break;
                    default:break;
                }
            }
        }
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
        Bitmap  bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_img);
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
        public TextView tv_left_img_title,tv_left_title,tv_right_img_title,tv_right_title;
        public ImageView iv_left_img,iv_right_img;
    }
}
