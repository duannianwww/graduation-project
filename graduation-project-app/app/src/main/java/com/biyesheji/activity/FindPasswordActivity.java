package com.biyesheji.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class FindPasswordActivity extends AppCompatActivity {

    private String from;
    private TextView tv_main_title;
    private TextView tv_back;
    private EditText et_validate_name;
    private Button btn_validate;
    private TextView tv_reset_pwd;
    private EditText et_user_name;
    private TextView tv_user_name;
    private EditText et_user_password;
    private EditText et_user_password_again;
    private UserSQL userSQL;
    private List<user> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        from=getIntent().getStringExtra("from");
        init();
    }

    private void init() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindPasswordActivity.this.finish();
            }
        });
        et_validate_name = (EditText) findViewById(R.id.et_validate_name);
        btn_validate = (Button) findViewById(R.id.btn_validate);
        tv_reset_pwd = (TextView) findViewById(R.id.tv_reset_pwd);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        et_user_password = (EditText) findViewById(R.id.et_user_password);
        et_user_password_again = (EditText) findViewById(R.id.et_user_password_again);
        if("security".equals(from)){
            tv_main_title.setText("设置密保");
        }else {
            tv_main_title.setText("找回密码");
            tv_user_name.setVisibility(View.VISIBLE);
            et_user_name.setVisibility(View.VISIBLE);
            et_user_password.setVisibility(View.VISIBLE);
            et_user_password_again.setVisibility(View.VISIBLE);
        }
        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String validateName=et_validate_name.getText().toString().trim();
                SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
                if("security".equals(from)){
                    if(TextUtils.isEmpty(validateName)){
                        Toast.makeText(FindPasswordActivity.this, "请输入您的邮箱", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        Toast.makeText(FindPasswordActivity.this, "密保设置成功", Toast.LENGTH_SHORT).show();
                        //保存密保到数据库
                        try {
                            saveSecurity(sp.getString("loginUserName",""),validateName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FindPasswordActivity.this.finish();
                    }
                }else {
                    //找回密码
                    String userName=et_user_name.getText().toString().trim();
                    String newPwd=et_user_password.getText().toString().trim();
                    String newPwdAgain=et_user_password_again.getText().toString().trim();
                    String sp_security= null;
                    try {
                        sp_security = readSecurity(userName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(TextUtils.isEmpty(userName)){
                        Toast.makeText(FindPasswordActivity.this, "请输入您的用户名", Toast.LENGTH_SHORT).show();
                        return;
                    }else try {
                        if(!isExistUserName(userName)){
                            Toast.makeText(FindPasswordActivity.this, "您输入的用户名不存在", Toast.LENGTH_SHORT).show();
                            return;
                        }else if(TextUtils.isEmpty(newPwd)){
                            Toast.makeText(FindPasswordActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                            return;
                        }else if(TextUtils.isEmpty(newPwdAgain)){
                            Toast.makeText(FindPasswordActivity.this, "请再次输入新密码", Toast.LENGTH_SHORT).show();
                            return;
                        }else if(!newPwd.equals(newPwdAgain)){
                            Toast.makeText(FindPasswordActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                            return;
                        } else if(newPwd.equals(readPwd())){
                            Toast.makeText(FindPasswordActivity.this, "输入的新密码与原始密码不能一致", Toast.LENGTH_SHORT).show();
                            return;
                        } else if(TextUtils.isEmpty(validateName)){
                            Toast.makeText(FindPasswordActivity.this, "请输入要验证的邮箱", Toast.LENGTH_SHORT).show();
                            return;
                        }else if(!validateName.equals(sp_security)) {
                            Toast.makeText(FindPasswordActivity.this, "输入的邮箱不正确", Toast.LENGTH_SHORT).show();
                            Log.i(validateName,sp_security);
                            return;
                        }else {
                            //用户输入的密保正确，重新给用户设置一个密码
                            tv_reset_pwd.setVisibility(View.VISIBLE);
                            Toast.makeText(FindPasswordActivity.this,"您的密码已修改成功", Toast.LENGTH_SHORT).show();
                            savePwd(userName,newPwd);
                            FindPasswordActivity.this.finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    /*
    * 保存初始化密码
    * @param userName
    */
    private void savePwd(String userName,String newPwd) throws IllegalAccessException, InstantiationException, ClassNotFoundException {//保存密码
        boolean savesuccess=false;
        userSQL = new UserSQL();
        savesuccess=userSQL.updatepassword(userName,newPwd);
        String md5Pwd= MD5Utils.md5(newPwd);
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(userName,md5Pwd);
        editor.commit();
    }

    private boolean isExistUserName(String userName) throws IllegalAccessException, InstantiationException, ClassNotFoundException {//用户名存在
        boolean hasUserName=false;
        userSQL = new UserSQL();
        hasUserName=userSQL.sea22(userName);
        return hasUserName;
    }

    private String readSecurity(String userName) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {//读取密保
        userSQL = new UserSQL();
        userList = new ArrayList<user>();
        userList=userSQL.sea2(userName);
        return userList.get(0).getUserpasswordsafe().trim();
    }
    private String readPwd() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {//读取密码
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        userSQL = new UserSQL();
        userList = new ArrayList<user>();
        userList =userSQL.sea2(sp.getString("loginUserName",""));
        return userList.get(0).getUserpassword().trim();
    }
    private void saveSecurity(String account,String validateName) throws IllegalAccessException, InstantiationException, ClassNotFoundException {//保存密保
        boolean savesuccess=false;
        userSQL = new UserSQL();
        savesuccess=userSQL.updatesafe(account,validateName);
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(AnalysisUtils.readLoginUserName(this)+"_security",validateName);//存入用户
        editor.commit();
    }
}
