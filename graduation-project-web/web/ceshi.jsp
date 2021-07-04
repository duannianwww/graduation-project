<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/11/21
  Time: 19:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>获取图片base64地址</title>

</head>
<body>
    <div class="index">
        <input accept="image/gif,image/jpeg,image/jpg,image/png" onchange="changImg(this,event)" type="file" id="up" />
        <img id="edit-image">
    </div>
    </body>
<script>
    function changImg(context,e){

        //判断是否支持FileReader
        if (window.FileReader) {
            var reader = new FileReader();
        } else {
            alert("您的设备不支持图片预览功能，如需该功能请升级您的设备！");
        }

        //获取文件
        var file = context.files[0];
        var imageType = /^image\//;
        //是否是图片
        if (!imageType.test(file.type)) {
            alert("请选择图片！");
            return;
        }
        //读取完成
        reader.onload = function(e) {
            var editImg=document.getElementById('edit-image');
            editImg.style.display='block';
            editImg.setAttribute('src', e.target.result);
            var img = editImg.src;

            function getBase64Image(img) {
                var canvas = document.createElement("canvas");
                canvas.width = img.width;
                canvas.height = img.height;
                var ctx = canvas.getContext("2d");
                ctx.drawImage(img, 0, 0, img.width, img.height);
                var ext = img.src.substring(img.src.lastIndexOf(".")+1).toLowerCase();
                var dataURL = canvas.toDataURL("image/"+ext);
                return dataURL;
            }
            function dataURItoBlob(dataURI) {
                var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]; // mime类型
                var byteString = atob(dataURI.split(',')[1]); //base64 解码
                var arrayBuffer = new ArrayBuffer(byteString.length); //创建缓冲数组
                var intArray = new Uint8Array(arrayBuffer); //创建视图

                for (var i = 0; i < byteString.length; i++) {
                    intArray[i] = byteString.charCodeAt(i);
                }
                return new Blob([intArray], {type: mimeString});
            }

            var image = new Image();
            image.src = img;
            image.onload = function(){
                console.clear();
                var base64 = getBase64Image(image);
                var blob=dataURItoBlob(base64);
            }
        };

        reader.readAsDataURL(file);
    }

</script>
</html>
