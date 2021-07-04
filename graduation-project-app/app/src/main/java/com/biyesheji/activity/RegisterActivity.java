package com.biyesheji.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.biyesheji.R;
import com.biyesheji.SQL.UserSQL;
import com.biyesheji.utils.MD5Utils;

import java.io.FileNotFoundException;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    private TextView tv_main_title;
    private TextView tv_back;
    private RelativeLayout ri_title_bar;
    private Button btn_register;
    private EditText et_user_name;
    private EditText et_pwd;
    private EditText et_pwd_again;
    private String userName;
    private String pwd;
    private String pwd_agagin;
    private EditText et_pwdsafe;
    private String pwdsafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        tv_back = (TextView) findViewById(R.id.tv_back);
        ri_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        ri_title_bar.setBackgroundColor(Color.TRANSPARENT);
        btn_register = (Button) findViewById(R.id.btn_register);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_pwdsafe = (EditText)findViewById(R.id.et_pwdsafe);
        et_pwd_again = (EditText) findViewById(R.id.et_pwd_again);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditString();
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(pwd)){
                    Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(pwd_agagin)){
                    Toast.makeText(RegisterActivity.this,"请再次输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(pwdsafe))
                {
                    Toast.makeText(RegisterActivity.this,"请输入邮箱",Toast.LENGTH_SHORT).show();
                    return;
                }else if(!pwd.equals(pwd_agagin)){
                    Toast.makeText(RegisterActivity.this,"输入两次的密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }else if(isExistUserName(userName)){
                    Toast.makeText(RegisterActivity.this,"此用户名已存在",Toast.LENGTH_SHORT).show();
                    return;
                }else
                {
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    //把用户名保存至SharedPreferences
                    try {
                        saveRegisterInfo(userName,pwd,pwdsafe);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //注册成功后把用户名传递到loginactivity.java
                    Intent data=new Intent();
                    data.putExtra("userName",userName);
                    setResult(RESULT_OK,data);
                    RegisterActivity.this.finish();
                }
            }
        });
    }
    private void saveRegisterInfo(String userName, String pwd,String pwdsafe) throws FileNotFoundException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        UserSQL userSQL=new UserSQL();
        String usertyblename= UUID.randomUUID().toString();
        String uservideotablename=UUID.randomUUID().toString();
        String usergerentablename=UUID.randomUUID().toString();
        userSQL.add(userName,userName,pwd,"user",usertyblename,uservideotablename,usergerentablename,pwdsafe);
        String md5pwd= MD5Utils.md5(pwd);//把密码用md5加密
        //loginInfo是sp的文件名
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        //userName为key,md5pwd为value
        editor.putString(userName,md5pwd);
        editor.commit();
    }

    private boolean isExistUserName(String userName) {
        boolean has_userName=false;
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        String spPwd=sp.getString(userName,"");
        if(!TextUtils.isEmpty(spPwd))
        {
            has_userName=true;
        }
        return has_userName;
    }

    private void getEditString() {
        userName = et_user_name.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();
        pwd_agagin = et_pwd_again.getText().toString().trim();
        pwdsafe =et_pwdsafe.getText().toString().trim();
    }
}
