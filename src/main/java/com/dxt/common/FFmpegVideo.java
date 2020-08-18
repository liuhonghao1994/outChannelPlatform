package com.dxt.common;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.MultimediaInfo;
import it.sauronsoftware.jave.VideoAttributes;

import java.io.File;

public class FFmpegVideo {
    /**
     * 压缩视频
     * @param convertFile  待转换的文件
     * @param targetFile  转换后的目标文件
     */
    public static void toCompressFile_java(String convertFile,String targetFile){
        File source = new File(convertFile);
        File target = new File(targetFile);
        try {
            System.out.println("begin");
            VideoAttributes video=new VideoAttributes();
            video.setCodec("mpeg4");
            video.setBitRate(new Integer(512000));
            video.setFrameRate(new Integer(15));
            EncodingAttributes attr=new EncodingAttributes();
            attr.setFormat("mp4");
            attr.setVideoAttributes(video);
            Encoder encoder=new Encoder();
            // 获取时长
            MultimediaInfo m = encoder.getInfo(source);
            System.out.println(m.getDuration());
            long beginTime = System.currentTimeMillis();
            System.out.println("获取时长花费时间是：" + (System.currentTimeMillis() - beginTime));
            beginTime = System.currentTimeMillis();
            encoder.encode(source, target, attr);
            System.out.println("视频转码花费时间是：" + (System.currentTimeMillis() - beginTime));
            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩视频
     * @param convertFile  待转换的文件
     * @param targetFile  转换后的目标文件
     */
    public static void toCompressFile(String convertFile,String targetFile){
        try{
            Runtime runtime = Runtime.getRuntime();
            /**将视频压缩为 每秒15帧 平均码率600k 画面的宽与高 为1280*720*/
//            String cutCmd="ffmpeg -i " + convertFile + " -r 15 -b:v 600k  -s 1280x720 "+ targetFile;
            String cutCmd=" ffmpeg -i " + convertFile + " -r 15 -b:v 300k "+ targetFile;
            System.out.println("文件："+convertFile+" 正在转换中。。。");
            Process process = runtime.exec(cutCmd);
            process.waitFor();
            System.out.println("文件："+convertFile+" 转换完毕");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("压缩文件出现异常："+e.getMessage());
        }
    }

    /**
    *通过图片base64流判断图片等于多少字节
    *image 图片流
    */
    public static Integer imageSize(String image){
        String str=image.substring(22);// 1.需要计算文件流大小，首先把头部的data:image/png;base64,（注意有逗号）去掉。
        Integer equalIndex= str.indexOf("=");//2.找到等号，把等号也去掉
        if(str.indexOf("=")>0) {
            str=str.substring(0, equalIndex);
        }
        Integer strLength=str.length();//3.原来的字符流大小，单位为字节
        Integer size=strLength-(strLength/8)*2;//4.计算后得到的文件流大小，单位为字节
        return size;
    }

    public static void main(String[] ars ){
        final String convertFile="E:\\media\\";
        File f=new File(convertFile);
        File[] fs=f.listFiles();
        for(File ff:fs){
            if(ff.toPath().toString().toLowerCase().endsWith(".mp4")){
                String f1=convertFile+ff.getName();
                String f2=convertFile+"压缩后_"+ff.getName();
                toCompressFile_java(f1,f2);
            }
        }
    }

}
