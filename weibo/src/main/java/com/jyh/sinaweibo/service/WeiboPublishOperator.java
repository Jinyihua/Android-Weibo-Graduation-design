package com.jyh.sinaweibo.service;

import android.graphics.BitmapFactory;
import android.util.Log;

import com.jyh.sinaweibo.R;
import com.jyh.sinaweibo.httputil.listener.OnEntityResultBackListener;
import com.jyh.sinaweibo.httputil.util.HttpRequest;
import com.jyh.sinaweibo.model.Entity;
import com.jyh.sinaweibo.util.PicturesCompress;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by cheng on 2017/1/5.
 */
public class WeiboPublishOperator implements Runnable, Contract.IOperator,OnEntityResultBackListener {
    private final int serviceStartId;
    private final int notificationId;
    private Contract.IService service;
    private WeiboPublishModel model;

    interface UploadImageCallback {
        void onUploadImageDone();

        void onUploadImage(int index, String token);
    }

    WeiboPublishOperator(WeiboPublishModel model, Contract.IService service, int startId) {
        this.model = model;
        this.notificationId = model.getId().hashCode();
        this.serviceStartId = startId;
        this.service = service;
    }

    /**
     * 执行动弹发布操作
     */
    @Override
    public void run() {
        // call to service
        this.service.start(model.getId(), this);
        // notify
        notifyMsg(R.string.weibo_publishing);

        final WeiboPublishModel model = this.model;

        if (model.getSrcImages() == null && model.getCacheImages() == null) {
            // 当没有图片的时候,直接进行发布微博
            publish();
        } else {
            if (model.getCacheImages() == null) {
                notifyMsg(R.string.weibo_image_wait);
                final String cacheDir = service.getCachePath(model.getId());
                // change the model
                model.setCacheImages(saveImageToCache(cacheDir, model.getSrcImages()));
                // update to cache file
                service.updateModelCache(model.getId(), model);
                //上传图片
                uploadImages();
            }
        }
    }

    /**
     * 上传图片
     *
     */
    private void uploadImages() {

        String accessToken=model.getAccessToken();
        String weiboContent=model.getStatus();

        /*
        * 拼接普通参数
        * */
        List<MultipartBody.Part> partList=new ArrayList<>();
        partList.add(MultipartBody.Part.createFormData("access_token",accessToken));
        try {
            partList.add(MultipartBody.Part.createFormData("status", URLEncoder.encode(weiboContent,"UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /*
        *  处理图片文件
        * */
        for(String imagePath:model.getSrcImages())
        {
            File imageFile=new File(imagePath);
            RequestBody requestBody=RequestBody.create(MediaType.parse(guessMimeType(imagePath)),imageFile);
            partList.add(MultipartBody.Part.createFormData("pic",imageFile.getName(),requestBody));
        }

        //提交请求
        HttpRequest.getInstance().uploadPic(partList,this);

    }

    @Override
    public void stop() {
        final Contract.IService service = this.service;
        if (service != null) {
            this.service = null;
            service.stop(model.getId(), serviceStartId);
        }
    }

    /**
     * 发布微博
     */
    private void publish() {
        HttpRequest.getInstance().doPostWeibo(model.getAccessToken(),model.getStatus(),this);
    }

    private void notifyMsg(int resId, Object... values) {
        notifyMsg(false, resId, values);
    }

    private void notifyMsg(boolean done, int resId, Object... values) {
        Contract.IService service = this.service;
        if (service != null) {
            service.notifyMsg(notificationId, model.getId(), done, done, resId, values);
        }
    }

    private void setSuccess() {
        notifyMsg(R.string.weibo_publish_success);
/*        try {
            Thread.sleep(1600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        Contract.IService service = this.service;
        if (service != null) {
            // clear the cache
            service.updateModelCache(model.getId(), null);
            // hide the notify
            service.notifyCancel(notificationId);
        }
        stop();
    }

    private void setError(int resId, Object... values) {
        notifyMsg(true, resId, values);
        stop();
    }


    private static final long MAX_UPLOAD_LENGTH = 512 * 1024;

    /**
     * 保存文件到缓存中
     *
     * @param cacheDir 缓存文件夹
     * @param paths    原始路径
     * @return 转存后的路径
     */
    private static String[] saveImageToCache(String cacheDir, String[] paths) {
        List<String> ret = new ArrayList<>();
        byte[] buffer = new byte[PicturesCompress.DEFAULT_BUFFER_SIZE];
        BitmapFactory.Options options = PicturesCompress.createOptions();
        for (String path : paths) {
            File sourcePath = new File(path);
            if (!sourcePath.exists())
                continue;
            try {
                String name = sourcePath.getName();
                String ext = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
                String tempFile = String.format("%s/IMG_%s.%s", cacheDir, System.currentTimeMillis(), ext);
                if (PicturesCompress.compressImage(path, tempFile,
                        MAX_UPLOAD_LENGTH, 80,
                        1280, 1280 * 2,
                        buffer, options, true)) {
                    Log.e("OPERATOR", "doImage " + tempFile + " " + new File(tempFile).length());
                    ret.add(tempFile);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (ret.size() > 0) {
            String[] images = new String[ret.size()];
            ret.toArray(images);
            return images;
        }
        return null;
    }


    @Override
    public void onSuccess(Entity mobile) {

        Log.e("test","onSuccess");

        notifyMsg(R.string.weibo_publish_success);
/*        try {
            Thread.sleep(1600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        Contract.IService service = this.service;
        if (service != null) {
            // clear the cache
            service.updateModelCache(model.getId(), null);
            // hide the notify
            service.notifyCancel(notificationId);
        }
        stop();

    }

    @Override
    public void onError(String error) {

        stop();
    }


    private String guessMimeType(String path)
    {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null)
        {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
