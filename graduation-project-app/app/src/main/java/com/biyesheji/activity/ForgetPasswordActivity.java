package com.biyesheji.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.biyesheji.R;
import com.biyesheji.SQL.UserSQL;
import com.biyesheji.bean.user;
import com.biyesheji.utils.AnalysisUtils;
import com.biyesheji.utils.MD5Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ForgetPasswordActivity extends AppCompatActivity {

    private TextView tv_back;
    private TextView tv_main_title;
    private EditText et_orginal_pwd;
    private EditText et_new_pwd;
    private EditText et_new_pwd_again;
    private Button btn_save;
    private String originalPwd;
    private String newPwd;
    private String newPwdAgain;
    private String userName;
    private UserSQL userSQL;
    private List<user> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        userName = AnalysisUtils.readLoginUserName(this);
        init();
    }

    private void init() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("修改密码");
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPasswordActivity.this.finish();
            }
        });
        et_orginal_pwd = (EditText) findViewById(R.id.et_original_pwd);
        et_new_pwd = (EditText) findViewById(R.id.et_new_pwd);
        et_new_pwd_again = (EditText) findViewById(R.id.et_new_pwd_again);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditString();
                if(TextUtils.isEmpty(originalPwd)) {
                    Toast.makeText(ForgetPasswordActivity.this, "请输入原始密码", Toast.LENGTH_SHORT).show();
                    return;
                }else try {
                    if(!originalPwd.equals(readPwd())){
                        Toast.makeText(ForgetPasswordActivity.this, "输入的密码和原始密码不一致", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(newPwd.equals(readPwd())){
                        Toast.makeText(ForgetPasswordActivity.this, "输入的新密码与原始密码不能一致", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(TextUtils.isEmpty(newPwd)){
                        Toast.makeText(ForgetPasswordActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(TextUtils.isEmpty(newPwdAgain)){
                        Toast.makeText(ForgetPasswordActivity.this, "请再次输入新密码", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(!newPwd.equals(newPwdAgain)){
                        Toast.makeText(ForgetPasswordActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        Toast.makeText(ForgetPasswordActivity.this, "新密码设置成功", Toast.LENGTH_SHORT).show();
                        try {
                            modifyPwd(newPwd);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent intent=new Intent(ForgetPasswordActivity.this,LoginActivity.class);
                        startActivity(intent);
                        SettingActivity.instance.finish();
                        ForgetPasswordActivity.this.finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void modifyPwd(String newPwd) throws IllegalAccessException, InstantiationException, ClassNotFoundException {//保存修改后密码
        userSQL = new UserSQL();
        userSQL.updatepassword(userName,newPwd);
        String Md5Pwd=MD5Utils.md5(newPwd);
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(userName,Md5Pwd);
        editor.commit();
    }

    private String readPwd() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {//读取密码
        userSQL = new UserSQL();
        userList = new ArrayList<user>();
        userList =userSQL.sea2(userName);
        return userList.get(0).getUserpassword().trim();
    }

    private void getEditString() {//获取输入框的值
        originalPwd = et_orginal_pwd.getText().toString().trim();
        newPwd = et_new_pwd.getText().toString().trim();
        newPwdAgain = et_new_pwd_again.getText().toString().trim();
    }
}
