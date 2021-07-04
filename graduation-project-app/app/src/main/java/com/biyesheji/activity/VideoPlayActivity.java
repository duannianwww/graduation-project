package com.biyesheji.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.biyesheji.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class VideoPlayActivity extends AppCompatActivity{
    private VideoView videoView;
    private String videoPath;
    private int position;
    private String url;
    private int videohei;
    private int videow;
    private boolean flag=true;
    private LinearLayout activity_video_play;
    float densityRatio = 1.0f; // 密度比值系数（密度比值：一英寸中像素点除以160）
    private TextView videoMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_play);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        videoPath=getIntent().getStringExtra("videoPath");
        position=getIntent().getIntExtra("position",0);
        init();
    }

    private void init() {
        activity_video_play =(LinearLayout)findViewById(R.id.activity_video_play);
        videoView= (VideoView) findViewById(R.id.VideoView);
        videoMessage =(TextView)findViewById(R.id.videoMessage);
        videohei =videoView.getHeight();
        videow =videoView.getWidth();
        MediaController controller=new MediaController(this);
        videoView.setMediaController(controller);
        play();
    }

    private void play() {
        if(TextUtils.isEmpty(videoPath)){
            Toast.makeText(this, "视频读取错误，暂时无法播放", Toast.LENGTH_SHORT).show();
            return;
        }
        //修改视频信息
        url=videoPath;
        videoView.setVideoPath(url);
        videoView.requestFocus();
        videoView.start();
        handler.postDelayed(runnable, 0);
    }
    final Handler handler = new Handler();
    private int old_duration;
    Runnable runnable = new Runnable() {
        public void run() {
            int duration = videoView.getCurrentPosition();
            if (old_duration == duration && videoView.isPlaying()) {
                videoMessage.setVisibility(View.VISIBLE);
                videoView.pause();
            } else {
                videoMessage.setVisibility(View.GONE);
                videoView.start();
            }
            old_duration = duration;

            handler.postDelayed(runnable, 1000);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent data=new Intent();
        data.putExtra("position",position);
        setResult(RESULT_OK,data);
        return super.onKeyDown(keyCode, event);

    }
}
