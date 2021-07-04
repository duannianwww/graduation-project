package com.gg;

import it.sauronsoftware.jave.*;

import java.io.File;

public class MultimediaFormatConversionUtils {


    /**
     * @Title: convertMultimediaFormat
     * @Description: 视频格式转换
     * 【文档：http://www.sauronsoftware.it/projects/jave/manual.php?PHPSESSID=eu6upk3l6okcd3h6sj6b9egqp4】
     * @author: ZXM
     * @param sourcePath
     * @param targetPath
     * @param audioEncoder
     * @param videoEncoder
     * @param targetFormat
     */
    public void convertMultimediaFormat(String sourcePath, String targetPath, String audioEncoder, String videoEncoder,
                                        String targetFormat) {
        try {
			/*File source = new File("C:\\Users\\Administrator\\Pictures\\新建文件夹\\3-6 webpack打包（中）.avi");
			File target = new File("C:\\Users\\Administrator\\Pictures\\新建文件夹\\4-3.mp4");*/
            File source = new File(sourcePath);
            File target = new File(targetPath);
            /*设置音频属性*/
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec(audioEncoder); // libmp3lame  或  flac
            audio.setBitRate(new Integer(400000)); // 数字越大声音越接近原声，转换时间越久（亲测这个数值比较好）
            audio.setChannels(new Integer(1));
            // audio.setSamplingRate(new Integer(22050));
            /*设置视频属性*/
            VideoAttributes video = new VideoAttributes();
            video.setCodec(videoEncoder);  // msmpeg4v2
            video.setBitRate(new Integer(1600000)); // 数字越大画面越清晰，转换时间越久（亲测这个数值比较好）
            video.setFrameRate(new Integer(15));
            // video.setSize(new VideoSize(400, 300));
            /*设置编码属性*/
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat(targetFormat); //mp4  需要转换成的格式
            attrs.setAudioAttributes(audio);
            attrs.setVideoAttributes(video);
            /*执行转码*/
            Encoder encoder = new Encoder();
            encoder.encode(source, target, attrs);
            System.out.println("转码成功！！！！！！！");
        } catch (IllegalArgumentException e) {
            System.out.println("转码失败！（IllegalArgumentException）");
            e.printStackTrace();
        } catch (InputFormatException e) {
            System.out.println("转码失败！（InputFormatException）");
            e.printStackTrace();
        } catch (EncoderException e) {
            System.out.println("转码失败！（EncoderException）");
            e.printStackTrace();
        }
    }

}
