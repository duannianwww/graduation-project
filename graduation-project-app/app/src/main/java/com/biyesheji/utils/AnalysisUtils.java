package com.biyesheji.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Xml;
import android.widget.ImageView;

import com.biyesheji.SQL.MySQL;
import com.biyesheji.SQL.UserSQL;
import com.biyesheji.bean.CourseBean;
import com.biyesheji.bean.SearchBean;
import com.biyesheji.bean.kecheng;
import com.biyesheji.bean.pinlun;
import com.biyesheji.bean.shijuan;
import com.biyesheji.bean.shiti;
import com.biyesheji.bean.uservideo;
import com.biyesheji.bean.video;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2021/1/28.
 */

public class AnalysisUtils {
    static boolean flag;
    public static String readLoginUserName(Context context)
    {
        SharedPreferences sp=context.getSharedPreferences("loginInfo",context.MODE_PRIVATE);
        String username =  sp.getString("loginUserName", "");//获取登录时的用户名
        return username;
    }
    /**
     * 解析每章的习题
     */
//    public static List<ExercisesBean> getExercisesInfos(InputStream is)throws Exception{
//        XmlPullParser parser= Xml.newPullParser();
//        parser.setInput(is,"utf-8");
//        List<ExercisesBean>exercisesInfos=null;
//        ExercisesBean exercisesInfo=null;
//        int type=parser.getEventType();
//        while (type!=XmlPullParser.END_DOCUMENT){
//            switch (type){
//                case XmlPullParser.START_TAG:
//                    if("infos".equals(parser.getName())){
//                        exercisesInfos=new ArrayList<ExercisesBean>();
//                    }else if("exercises".equals(parser.getName())){
//                        exercisesInfo=new ExercisesBean();
//                        String ids=parser.getAttributeValue(0);
//                        exercisesInfo.subjectId=Integer.parseInt(ids);
//                    }else if("subject".equals(parser.getName())){
//                        String subject=parser.nextText();
//                        exercisesInfo.subject=subject;
//                    }else if("a".equals(parser.getName())){
//                        String a=parser.nextText();
//                        exercisesInfo.a=a;
//                    }else if("b".equals(parser.getName())){
//                        String b=parser.nextText();
//                        exercisesInfo.b=b;
//                    }else if("c".equals(parser.getName())){
//                        String c=parser.nextText();
//                        exercisesInfo.c=c;
//                    }else if("d".equals(parser.getName())){
//                        String d=parser.nextText();
//                        exercisesInfo.d=d;
//                    }else if("answer".equals(parser.getName())){
//                        String answer=parser.nextText();
//                        exercisesInfo.answer=Integer.parseInt(answer);
//                    }
//                    break;
//                case XmlPullParser.END_TAG:
//                    if("exercises".equals(parser.getName())){
//                        exercisesInfos.add(exercisesInfo);
//                        exercisesInfo=null;
//                    }
//                    break;
//            }
//            type=parser.next();
//        }
//        return exercisesInfos;
//    }

//    /**
//     * 设置A/B/C/D选项是否可以点击
//     * @param value
//     * @param iv_a
//     * @param iv_b
//     * @param iv_c
//     * @param iv_d
//     */
//    public static void setABCDEnable(boolean value, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d) {
//        iv_a.setEnabled(value);
//        iv_b.setEnabled(value);
//        iv_c.setEnabled(value);
//        iv_d.setEnabled(value);
//    }

//
    public static List<shijuan> getShijuanInfo(String name) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        flag=false;
        MySQL sql=new MySQL();
        List<shijuan> CourseList=new ArrayList<shijuan>();
        List<shijuan> SQLHelper=null;
        SQLHelper= sql.seashijuan(name);
        int count=0;
        for(shijuan Course1:SQLHelper){
            shijuan CourseInfo=new shijuan();
            CourseInfo.setShijuanid(Course1.getShijuanid());
            Log.i("YEMIAN", Course1.getShijuanid()+"");
            CourseInfo.setShiuanname(Course1.getShiuanname());
            Log.i("YEMIAN", Course1.getShiuanname()+"");
            count++;
            CourseList.add(CourseInfo);
        }
        Log.i("YEMIAN", CourseList.size()+"");
        callback();
        return CourseList;
    }
    public static List<pinlun> getPinlunInfo(String name) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        flag=false;
        MySQL sql=new MySQL();
        List<pinlun> CourseList=new ArrayList<pinlun>();
        List<pinlun> SQLHelper=null;
        SQLHelper= sql.seapinlun(name);
        int count=0;
        for(pinlun Course1:SQLHelper){
            pinlun CourseInfo=new pinlun();
            CourseInfo.setPinlunid(Course1.getPinlunid());
            Log.i("YEMIAN", Course1.getPinlunid()+"");
            CourseInfo.setComment(Course1.getComment());
            Log.i("YEMIAN", Course1.getComment()+"");
            CourseInfo.setSendtime(Course1.getSendtime());
            CourseInfo.setUsername(Course1.getUsername());
            count++;
            CourseList.add(CourseInfo);
        }
        Log.i("YEMIAN", CourseList.size()+"");
        callback();
        return CourseList;
    }
    public static List<video> getVideoInfo(String name) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
            flag=false;
            MySQL sql=new MySQL();
            List<video> CourseList=new ArrayList<video>();
            List<video> SQLHelper=null;
            SQLHelper= sql.seavideo(name);
            int count=0;
            for(video Course1:SQLHelper){
                video CourseInfo=new video();
                CourseInfo.setId(Course1.getId());
                Log.i("YEMIAN", Course1.getId()+"");
                CourseInfo.setDesc(Course1.getDesc());
                Log.i("YEMIAN", Course1.getDesc()+"");
                CourseInfo.setVideoname(Course1.getVideoname());
                count++;
                CourseList.add(CourseInfo);
            }
            Log.i("YEMIAN", CourseList.size()+"");
            callback();
            return CourseList;
    }
    public static List<CourseBean> getCourseInfowherename(String coursename) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        flag=false;
        MySQL sql=new MySQL();
        List<CourseBean> CourseList=CourseList=new ArrayList<CourseBean>();
        List<kecheng> SQLHelper=null;
        SQLHelper= sql.selectusercourse(coursename);
        int count=0;
        for(kecheng Course1:SQLHelper){
            CourseBean CourseInfo=new CourseBean();
            CourseInfo.id=Course1.getId();
            Log.i("YEMIAN", Course1.getId()+"");
            CourseInfo.title=Course1.getBookname();
            Log.i("YEMIAN", Course1.getBookname()+"");
            CourseInfo.intro=Course1.getIntroduct();
            CourseInfo.icon=Course1.getImagename();
            CourseInfo.imgTitle=Course1.getImagename();
            CourseInfo.stock=Course1.getStock();
            CourseInfo.price=Course1.getPrice();
            count++;
            CourseList.add(CourseInfo);
        }
        Log.i("YEMIAN", CourseList.size()+"");
        callback();
        return CourseList;
    }
    public static List<CourseBean> getCourseInfowheretype(String type) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        flag=false;
        MySQL sql=new MySQL();
        List<CourseBean> CourseList=CourseList=new ArrayList<CourseBean>();
        List<kecheng> SQLHelper=null;
        SQLHelper= sql.selectbutton(type);
        int count=0;
        for(kecheng Course1:SQLHelper){
            CourseBean CourseInfo=new CourseBean();
            CourseInfo.id=Course1.getId();
            Log.i("YEMIAN", Course1.getId()+"");
            CourseInfo.title=Course1.getBookname();
            Log.i("YEMIAN", Course1.getBookname()+"");
            CourseInfo.intro=Course1.getIntroduct();
            CourseInfo.icon=Course1.getImagename();
            CourseInfo.imgTitle=Course1.getImagename();
            CourseInfo.stock=Course1.getStock();
            CourseInfo.price=Course1.getPrice();
            count++;
            CourseList.add(CourseInfo);
        }
        Log.i("YEMIAN", CourseList.size()+"");
        callback();
        return CourseList;
    }
    public static List<SearchBean> getSearchInfo() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        flag=false;
        MySQL sql=new MySQL();
        List<SearchBean> CourseList=new ArrayList<SearchBean>();
        List<kecheng> SQLHelper=null;
        SQLHelper= sql.select();
        int count=0;
        for(kecheng Course1:SQLHelper){
            SearchBean CourseInfo=new SearchBean();
            CourseInfo.setIconId(Course1.getId());
            Log.i("YEMIAN", Course1.getId()+"");
            CourseInfo.setTitle(Course1.getBookname());
            Log.i("YEMIAN", Course1.getBookname()+"");
            CourseInfo.setContent(Course1.getIntroduct());
            CourseInfo.setComments(Course1.getImagename());
            count++;
            CourseList.add(CourseInfo);
        }
        Log.i("YEMIAN", CourseList.size()+"");
        callback();
        return CourseList;
    }
    public static List<CourseBean> getCourseInfo() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        flag=false;
        MySQL sql=new MySQL();
        List<CourseBean> CourseList=CourseList=new ArrayList<CourseBean>();
        List<kecheng> SQLHelper=null;
        SQLHelper= sql.select();
        int count=0;
        for(kecheng Course1:SQLHelper){
            CourseBean CourseInfo=new CourseBean();
            CourseInfo.id=Course1.getId();
            Log.i("YEMIAN", Course1.getId()+"");
            CourseInfo.title=Course1.getBookname();
            Log.i("YEMIAN", Course1.getBookname()+"");
            CourseInfo.intro=Course1.getIntroduct();
            CourseInfo.icon=Course1.getImagename();
            CourseInfo.imgTitle=Course1.getImagename();
            CourseInfo.stock=Course1.getStock();
            CourseInfo.price=Course1.getPrice();
            count++;
            CourseList.add(CourseInfo);
        }
        Log.i("YEMIAN", CourseList.size()+"");
        callback();
        return CourseList;
    }
    public static List<List<CourseBean>> getCourseInfos() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        flag=false;
        MySQL sql=new MySQL();
        List<List<CourseBean>> CourseInfos=new ArrayList<List<CourseBean>>();
        List<CourseBean> CourseList=CourseList=new ArrayList<CourseBean>();
        List<kecheng> SQLHelper=null;
        SQLHelper= sql.select();
        int count=0;
        for(kecheng Course1:SQLHelper){
            CourseBean CourseInfo=new CourseBean();
            CourseInfo.id=Course1.getId();
            Log.i("YEMIAN", Course1.getId()+"");
            CourseInfo.title=Course1.getBookname();
            Log.i("YEMIAN", Course1.getBookname()+"");
            CourseInfo.intro=Course1.getIntroduct();
            CourseInfo.icon=Course1.getImagename();
            CourseInfo.imgTitle=Course1.getImagename();
            CourseInfo.stock=Course1.getStock();
            CourseInfo.price=Course1.getPrice();
            count++;
            CourseList.add(CourseInfo);
            if(count%2==0){
                Log.i("YEMIANmian",CourseList.size()+"");
                CourseInfos.add(CourseList);
                Log.i("YEMIANmian", CourseInfos.size()+"");
                CourseList=null;
                CourseList=new ArrayList<CourseBean>();
            }
            CourseInfo=null;
        }
        if(count%2==1)
        {
            CourseInfos.add(CourseList);
        }
        Log.i("YEMIAN", CourseInfos.size()+"");
        callback();
        return CourseInfos;
    }
    public static List<List<CourseBean>> getCourseInfowhereuser(String username) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        flag=false;
        MySQL sql=new MySQL();
        UserSQL userSQL=new UserSQL();
        List<String> usercourseList=new ArrayList<String>();
        usercourseList=userSQL.seausercourse(username);
        Log.i("Usercoursesize", usercourseList.size()+"");
        List<List<CourseBean>> CourseInfos=new ArrayList<List<CourseBean>>();
        List<CourseBean> CourseList=new ArrayList<CourseBean>();
        List<kecheng> SQLHelper=null;
        int count=0;
        for(String coursename:usercourseList)
        {
            Log.i("coursename", coursename);
            SQLHelper= sql.selectusercourse(coursename);
            Log.i("SQLHelper", SQLHelper.size()+"");
            for(kecheng Course1:SQLHelper){
                CourseBean CourseInfo=new CourseBean();
                CourseInfo.id=Course1.getId();
                Log.i("YEMIAN", Course1.getId()+"");
                CourseInfo.title=Course1.getBookname();
                Log.i("YEMIAN", Course1.getBookname()+"");
                CourseInfo.intro=Course1.getIntroduct();
                CourseInfo.icon=Course1.getImagename();
                CourseInfo.imgTitle=Course1.getImagename();
                CourseInfo.stock=Course1.getStock();
                CourseInfo.price=Course1.getPrice();
                count++;
                CourseList.add(CourseInfo);
                if(count%2==0){
                    Log.i("YEMIANmian",CourseList.size()+"");
                    CourseInfos.add(CourseList);
                    Log.i("YEMIANmian", CourseInfos.size()+"");
                    CourseList=null;
                    CourseList=new ArrayList<CourseBean>();
                }
                CourseInfo=null;
            }
        }
        if(count%2==1)
        {
            CourseInfos.add(CourseList);
        }
        Log.i("YEMIAN", CourseInfos.size()+"");
        callback();
        return CourseInfos;
    }
    public static List<CourseBean> getCourseInfowhereuser2(String username) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        flag=false;
        MySQL sql=new MySQL();
        UserSQL userSQL=new UserSQL();
        List<String> usercourseList=new ArrayList<String>();
        usercourseList=userSQL.seausercourse(username);
        List<CourseBean> CourseList=new ArrayList<CourseBean>();
        List<kecheng> SQLHelper=null;
        int count=0;
        for(String coursename:usercourseList)
        {
            Log.i("coursename", coursename);
            SQLHelper= sql.selectusercourse(coursename);
            Log.i("SQLHelper", SQLHelper.size()+"");
            for(kecheng Course1:SQLHelper){
                CourseBean CourseInfo=new CourseBean();
                CourseInfo.id=Course1.getId();
                Log.i("YEMIAN", Course1.getId()+"");
                CourseInfo.title=Course1.getBookname();
                Log.i("YEMIAN", Course1.getBookname()+"");
                CourseInfo.intro=Course1.getIntroduct();
                CourseInfo.icon=Course1.getImagename();
                CourseInfo.imgTitle=Course1.getImagename();
                CourseInfo.stock=Course1.getStock();
                CourseInfo.price=Course1.getPrice();
                count++;
                CourseList.add(CourseInfo);
            }
        }
        callback();
        return CourseList;
    }
    public static void callback()
    {
        System.out.println("子线程执行结束");
        flag=true;
    }
    public static void dengdai()
    {
        while (!flag);
    }
}
