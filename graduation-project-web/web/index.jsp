<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 2020/05/25
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<html>
  <head>
    <title>闽科云课堂后台页面</title>

    <link rel="shortcut icon"  href="./img/mk.ico"/>
    <style type="text/css">
      .login-button { /* 按钮美化 */
        width: 270px; /* 宽度 */
        height: 40px; /* 高度 */
        border-width: 0px; /* 边框宽度 */
        border-radius: 3px; /* 边框半径 */
        background: #1E90FF; /* 背景颜色 */
        cursor: pointer; /* 鼠标移入按钮范围时出现手势 */
        outline: none; /* 不显示轮廓线 */
        font-family: Microsoft YaHei; /* 设置字体 */
        color: white; /* 字体颜色 */
        font-size: 17px; /* 字体大小 */
        margin-top: 10px;
      }
      .login-button:hover { /* 鼠标移入按钮范围时改变颜色 */
        background: #5599FF;
      }
      div,ul,li{
        margin: 0;
        padding: 0;
      }

      /*首先准备一个放图片的容器*/
      .container{
        width: 800px;
        height: 341px;
        position: relative;
        top: 150px;
        left: 10%;
        /*border: 1px solid #ccc;*/
      }

      /*图片样式*/
      .container img{
        position: absolute;        /*把所有图片放在同一个位置*/
        width: 100%;
        transition-duration: 1s;    /*设置过渡时间*/
        opacity: 0;                /*把所有图片变透明*/
      }
      /*图片显示开关*/
      .container img.on{
        opacity: 1;                /*用于显示图片*/
      }

      /*左右按钮 按钮用图片更好点,这里为了简便就用大于小于号*/
      .left, .right{
        position: absolute;
        top: 30%;
        width: 60px;
        height: 100px;
        line-height: 100px;
        background-color: #666;
        opacity: 0.5;
        text-align: center;
        font-size: 60px;
        color: #ccc;
        display: none;    /*先隐藏按钮*/
        cursor: pointer;    /*设置鼠标悬停时的样式*/
      }
      .left{
        left: 0;
      }
      .right{
        right: 0;
      }
      .container:hover .left, .container:hover .right{
        display: block;            /*鼠标悬停才容器范围内时显示按钮*/
      }
      .left:hover, .right:hover{
        color: #fff;
      }

      /*焦点*/
      .container ul{
        position: absolute;
        bottom: 0;
        max-width: 500px;
        padding: 5px 200px;
      }
      .container ul li{
        list-style: none;
        float: left;
        width: 10px;
        height: 10px;
        border-radius: 50%;
        margin-left: 10px;
        background-color: #ccc;
        cursor: pointer;
      }
      .container ul li.active{
        background-color: #fff;        /*焦点激活时的样式*/
      }
      body
      {
        background: url("./img/se.jpg") no-repeat;
        background-size: cover;
      }
    </style>
  </head>
  <body style="width: 100%;height: 100%;margin: 0px;">
  <div class="container">
    <!-- 图片 -->
    <!-- 先把第一张图片显示出来 -->
    <img class="on" src="./img/www.JPG" id="img1"/>
    <img src="./img/ss.JPG" id="img2"/>
    <img src="./img/sss.JPG" id="img3"/>

    <!-- 左右按钮 -->
    <div class="left"><</div>
    <div class="right">></div>

    <!-- 焦点 -->
    <ul style="margin-left: 150px;margin-top: 20px">
      <li class="active" id="li1"></li>
      <li id="li2"></li>
      <li id="li3"></li>
    </ul>
  </div>
    <div style="position: absolute;left:70%;bottom:50%;width:300px;height:180px">
      <h3 style="margin: 0px;font-family: 微软雅黑;color:#ffff">欢迎进入闽课云课堂后台页面！</h3>
      <a href="admin%20login.jsp">
        <br/>
        <button type="button" class="login-button">教师登录</button>
      </a>
      <a href="admin%20login.jsp">
        <br/>
        <button type="button" class="login-button">联系我们</button>
      </a>
    </div>
  </body>
  <script type="text/javascript">
    //1、找到container下的所有img标签,li标签,左右按钮
    var aImgs = document.querySelectorAll('.container img');
    var aLis = document.querySelectorAll('.container li');
    var btnLeft = document.querySelector('.container .left');
    var btnRight = document.querySelector('.container .right');
    var li1=document.getElementById('li1');
    var li2=document.getElementById('li2');
    var li3=document.getElementById('li3');
    var img1=document.getElementById('img1');
    var img2=document.getElementById('img2');
    var img3=document.getElementById('img3');
    // //检验是否找到
    // console.log(aImgs);
    // console.log(aLis);
    // console.log(btnLeft);
    // console.log(btnRight);

    //点击事件
    //点击按钮图片切换
    var index = 0;        //当前图片下标
    var lastIndex = 0;
    li1.onclick=function ()
    {
      index=0;
      lastIndex=0;
      li3.className = '';
      img3.className = '';
      li2.className = '';
      img2.className = '';
      li1.className='active';
      img1.className='on';
    }
    li2.onclick=function ()
    {
      index=1;
      lastIndex=1;
      li1.className = '';
      img1.className = '';
      li3.className = '';
      img3.className = '';
      li2.className='active';
      img2.className='on';
    }
    li3.onclick=function ()
    {
      index=2;
      lastIndex=2;
      li1.className = '';
      img1.className = '';
      li2.className = '';
      img2.className = '';
      li3.className='active';
      img3.className='on';
    }
    function ChangeImg() {
      //记录上一张图片的下标
      lastIndex = index;
      //清除上一张图片的样式
      aImgs[lastIndex].className = '';
      aLis[lastIndex].className = '';

      index++;
      index %= aImgs.length;    //实现周期性变化
      //设置当前图片的样式
      aImgs[index].className = 'on';
      aLis[index].className = 'active';
    }
    //设置定时器，每隔三秒切换一张图片
    setInterval(ChangeImg,3000);

    btnRight.onclick = function(){
      //记录上一张图片的下标
      lastIndex = index;
      //清除上一张图片的样式
      aImgs[lastIndex].className = '';
      aLis[lastIndex].className = '';

      index++;
      index %= aImgs.length;    //实现周期性变化
      //设置当前图片的样式
      aImgs[index].className = 'on';
      aLis[index].className = 'active';
    }
    //左边按钮类似
    btnLeft.onclick = function(){
      //记录上一张图片的下标
      lastIndex = index;
      //清除上一张图片的样式
      aImgs[lastIndex].className = '';
      aLis[lastIndex].className = '';

      index--;
      if (index < 0) {
        index = aImgs.length - 1;
      }
      //设置当前图片的样式
      aImgs[index].className = 'on';
      aLis[index].className = 'active';
    }
  </script>
  </html>
