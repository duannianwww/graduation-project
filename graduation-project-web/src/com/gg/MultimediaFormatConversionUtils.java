package com.gg;

import it.sauronsoftware.jave.*;

import java.io.File;

public class MultimediaFormatConversionUtils {


    /**
     * @Title: convertMultimediaFormat
     * @Description: ��Ƶ��ʽת��
     * ���ĵ���http://www.sauronsoftware.it/projects/jave/manual.php?PHPSESSID=eu6upk3l6okcd3h6sj6b9egqp4��
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
			/*File source = new File("C:\\Users\\Administrator\\Pictures\\�½��ļ���\\3-6 webpack������У�.avi");
			File target = new File("C:\\Users\\Administrator\\Pictures\\�½��ļ���\\4-3.mp4");*/
            File source = new File(sourcePath);
            File target = new File(targetPath);
            /*������Ƶ����*/
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec(audioEncoder); // libmp3lame  ��  flac
            audio.setBitRate(new Integer(400000)); // ����Խ������Խ�ӽ�ԭ����ת��ʱ��Խ�ã��ײ������ֵ�ȽϺã�
            audio.setChannels(new Integer(1));
            // audio.setSamplingRate(new Integer(22050));
            /*������Ƶ����*/
            VideoAttributes video = new VideoAttributes();
            video.setCodec(videoEncoder);  // msmpeg4v2
            video.setBitRate(new Integer(1600000)); // ����Խ����Խ������ת��ʱ��Խ�ã��ײ������ֵ�ȽϺã�
            video.setFrameRate(new Integer(15));
            // video.setSize(new VideoSize(400, 300));
            /*���ñ�������*/
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat(targetFormat); //mp4  ��Ҫת���ɵĸ�ʽ
            attrs.setAudioAttributes(audio);
            attrs.setVideoAttributes(video);
            /*ִ��ת��*/
            Encoder encoder = new Encoder();
            encoder.encode(source, target, attrs);
            System.out.println("ת��ɹ���������������");
        } catch (IllegalArgumentException e) {
            System.out.println("ת��ʧ�ܣ���IllegalArgumentException��");
            e.printStackTrace();
        } catch (InputFormatException e) {
            System.out.println("ת��ʧ�ܣ���InputFormatException��");
            e.printStackTrace();
        } catch (EncoderException e) {
            System.out.println("ת��ʧ�ܣ���EncoderException��");
            e.printStackTrace();
        }
    }

}
