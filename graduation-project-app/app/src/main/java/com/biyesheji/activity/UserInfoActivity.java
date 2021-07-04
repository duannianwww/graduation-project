package com.biyesheji.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.biyesheji.R;
import com.biyesheji.SQL.UserSQL;
import com.biyesheji.bean.user;
import com.biyesheji.bean.userziliao;
import com.biyesheji.utils.AnalysisUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_main_title;
    private TextView tv_back;
    private RelativeLayout rl_title_bar;
    private RelativeLayout rl_nickname;
    private TextView tv_nickname;
    private RelativeLayout rl_account;
    private TextView tv_user_name;
    private RelativeLayout rl_sex;
    private TextView tv_sex;
    private RelativeLayout rl_signature;
    private TextView tv_signature;
    private String spUserName;

    private static final int CHANGE_NICKNAME=1;//修改昵称的自定义常量
    private static final int CHANGE_SIGNATURE=2;//修改签名的自定义常量
    private String newInfo;
    private List<user> userList;
    private String userziliaotable;
    private TextView tv_back_two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        spUserName= AnalysisUtils.readLoginUserName(this);
        init();
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setListener();
    }

    private void setListener() {
        tv_back.setOnClickListener(this);
        rl_nickname.setOnClickListener(this);
        rl_account.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_signature.setOnClickListener(this);
    }
    private void init() {
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("个人资料");
        tv_main_title.setTextColor(Color.BLACK);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setVisibility(View.GONE);
        tv_back_two = (TextView) findViewById(R.id.tv_back_two);
        tv_back_two.setVisibility(View.VISIBLE);
        tv_back_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.this.finish();
            }
        });
        rl_nickname = (RelativeLayout) findViewById(R.id.rl_nickname);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        rl_account = (RelativeLayout) findViewById(R.id.rl_account);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        rl_signature = (RelativeLayout) findViewById(R.id.rl_signature);
        tv_signature = (TextView) findViewById(R.id.tv_signature);
    }

    /**
     * 从数据库获取数据
     */
    private void initData() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        userziliao bean=null;
        userziliao userziliao=new userziliao();
        UserSQL userSQL=new UserSQL();

        userList = new ArrayList<>();
        userList =userSQL.sea2(spUserName);
        userziliaotable = userList.get(0).getUsergerentablename();
        List<userziliao> userziliaoList=new ArrayList<userziliao>();
        userziliaoList=userSQL.seauserziliao(userziliaotable);
        if(userziliaoList.size()>0)
        {
            bean=userziliaoList.get(0);
        }
        else {
            bean=new userziliao();
            bean.setNicheng(spUserName);
            bean.setSex("保密");
            bean.setIntro("这个人很懒，什么都没有写");
            //将信息插入数据库
            userSQL.adduserziliao(userziliaotable,spUserName,"保密","这个人很懒，什么都没有写");
        }
        setValue(bean);
    }
    /*
    * 为界面数据设置值
    * */
    private void setValue(userziliao bean) {
        tv_nickname.setText(bean.getNicheng());
        tv_user_name.setText(spUserName);
        tv_sex.setText(bean.getSex());
        tv_signature.setText(bean.getIntro());
    }
    public void enterActivityForResult(Class<?> to,int requestCode,Bundle b){
        Intent i=new Intent(this,to);
        i.putExtras(b);
        startActivityForResult(i,requestCode);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back_two:
                UserInfoActivity.this.finish();
                break;
            case R.id.rl_nickname:
                //昵称的点击事件
                String name=tv_nickname.getText().toString().trim();
                Bundle bdName=new Bundle();
                bdName.putString("content",name);//传递界面上的昵称
                bdName.putString("title","昵称");
                bdName.putInt("flag",1);//flag为1就是修改昵称
                enterActivityForResult(ChangeUserInfoActivity.class,CHANGE_NICKNAME,bdName);
                break;
            case R.id.rl_sex:
                String sex=tv_sex.getText().toString().trim();
                sexDialog(sex);
                //性别点击事件
                break;
            case R.id.rl_signature:
                //签名的点击事件

                String signature=tv_signature.getText().toString().trim();
                Bundle bdSignature=new Bundle();
                bdSignature.putString("content",signature);//传递界面上的签名
                bdSignature.putString("title","签名");
                bdSignature.putInt("flag",2);//flag为2就是修改签名
                enterActivityForResult(ChangeUserInfoActivity.class,CHANGE_SIGNATURE,bdSignature);
                break;
            default:break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UserSQL userSQL=new UserSQL();
        switch (requestCode){
            case CHANGE_NICKNAME:
                if(data!=null){
                    newInfo = data.getStringExtra("nickName");
                    if(TextUtils.isEmpty(newInfo)){
                        return;
                    }
                    tv_nickname.setText(newInfo);
                    //更新数据库的昵称
                    try {
                        userSQL.updatenickname(userziliaotable,newInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHANGE_SIGNATURE:
                if(data!=null){
                    newInfo = data.getStringExtra("signature");
                    if(TextUtils.isEmpty(newInfo)){
                        return;
                    }
                    tv_signature.setText(newInfo);
                    //更新数据库的签名
                    try {
                        userSQL.updateintro(userziliaotable,newInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;
        }
    }

    private void sexDialog(String sex) {
        int sexFlag=0;
        if("男".equals(sex)){
            sexFlag=0;
        }else if("女".equals(sex)){
            sexFlag=1;
        }
        final String items[]={"男","女"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("性别");
        builder.setSingleChoiceItems(items, sexFlag, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(UserInfoActivity.this,items[which],Toast.LENGTH_SHORT).show();
                setSex(items[which]);
            }
        });
        builder.show();
    }

    private void setSex(String sex) {
        UserSQL userSQL=new UserSQL();
        //更新数据库中的性别
        tv_sex.setText(sex);
        try {
            userSQL.updatesex(userziliaotable,sex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
