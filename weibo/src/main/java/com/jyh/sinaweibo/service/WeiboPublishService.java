package com.jyh.sinaweibo.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.jyh.sinaweibo.R;

import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 2017/1/5.
 */
public class WeiboPublishService extends Service implements Contract.IService {
    private final static String TAG = WeiboPublishService.class.getName();


    private static final String ACTION_PUBLISH = "com.example.jyhweiboapp.weibo.service.action.PUBLISH";
    private static final String EXTRA_ACCESSTOKEN = "com.example.jyhweiboapp.weibo.service.extra.ACCESSTOKEN";
    private static final String EXTRA_CONTENT = "com.example.jyhweiboapp.weibo.service.extra.CONTENT";
    private static final String EXTRA_IMAGES = "com.example.jyhweiboapp.weibo.service.extra.IMAGES";
    private static final String EXTRA_ID = "com.example.jyhweiboapp.weibo.service.extra.ID";
    public static final String EXTRA_IDS = "com.example.jyhweiboapp.weibo.service.extra.IDS";

    private volatile Looper mServiceLooper;
    private volatile ServiceHandler mServiceHandler;
    private Map<String, Contract.IOperator> mRunTasks = new ArrayMap<>();

    private final class ServiceHandler extends Handler {
        ServiceHandler(Looper looper) {
            super(looper);
        }

        //接受消息
        //属于子线程
        @Override
        public void handleMessage(Message msg) {
            onHandleIntent((Intent) msg.obj, msg.arg1);
        }
    }


    /**
     * 发起微博发布服务
     * 第二个参数：微博内容
     * 第三个参数：上传图片
     * 第三个参数：访问令牌
     */
    public static void startActionPublish(Context context, String content, List<String> images, String accessToken) {
        Intent intent = new Intent(context, WeiboPublishService.class);
        intent.setAction(ACTION_PUBLISH);
        intent.putExtra(EXTRA_CONTENT, content);
        intent.putExtra(EXTRA_ACCESSTOKEN, accessToken);
        if (images != null && images.size() > 0) {
            String[] pubImages = new String[images.size()];
            images.toArray(pubImages);
            intent.putExtra(EXTRA_IMAGES, pubImages);
        }
        context.startService(intent);
    }



    public WeiboPublishService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //HandlerThread是线程类
        //HandlerThread创建messagequeue和looper
        //HandlerThread作用：
        HandlerThread thread = new HandlerThread(WeiboPublishService.class.getSimpleName());
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    /**
     * You should not override this method for your IntentService. Instead,
     * override {@link #onHandleIntent}, which the system calls when the IntentService
     * receives a start request.
     *
     * @see Service#onStartCommand
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //创建消息对象
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        mServiceHandler.sendMessage(msg);
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mServiceLooper.quit();
        log("onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 在线程中处理请求数据
     *
     * @param intent  请求的数据
     * @param startId 启动服务的Id
     */
    private void onHandleIntent(Intent intent, int startId) {
        if (intent != null) {
            final String action = intent.getAction();

            log(action);

            if (ACTION_PUBLISH.equals(action)) {
                //微博的内容
                final String content = intent.getStringExtra(EXTRA_CONTENT);
                //令牌
                final String accessToken = intent.getStringExtra(EXTRA_ACCESSTOKEN);
                //上传图片
                final String[] images = intent.getStringArrayExtra(EXTRA_IMAGES);
//              String content, String[] images,String lat, String lon, String accessToken,int startId
                handleActionPublish(content, images,"","",accessToken,startId);
            }
        }
    }

    /**
     * 发布,在后台服务中进行
     */
    private void handleActionPublish(String content, String[] images,String lat, String lon, String accessToken,int startId) {
        WeiboPublishModel model = new WeiboPublishModel(content, images,lat,lon,accessToken);
        WeiboPublishCache.save(getApplicationContext(), model.getId(), model);
        Contract.IOperator operator = new WeiboPublishOperator(model, this, startId);
        operator.run();
    }





    @Override
    public String getCachePath(String id) {
        return WeiboPublishCache.getImageCachePath(getApplicationContext(), id);
    }

    @Override
    public void start(String id, Contract.IOperator operator) {
        if (!mRunTasks.containsKey(id)) {
            mRunTasks.put(id, operator);
        }
    }

    @Override
    public void stop(String id, int startId) {
        if (mRunTasks.containsKey(id)) {
            mRunTasks.remove(id);
        }
        // stop self
        stopSelf(startId);
    }

    @Override
    public void updateModelCache(String id, WeiboPublishModel model) {
        if (model == null)
            WeiboPublishCache.remove(getApplicationContext(), id);
        else
            WeiboPublishCache.save(getApplicationContext(), id, model);
    }

    @Override
    public void notifyMsg(int notifyId, String modelId, boolean haveReDo, boolean haveDelete, int resId, Object... values) {

        String content = getString(resId, values);
        Log.e("test","notifyMsg content="+content);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this)
                .setTicker(content)
                .setContentTitle(getString(R.string.weibo_publish_title))
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_notification);

        Notification notification = builder.build();
        NotificationManagerCompat.from(this).notify(notifyId, notification);

        log(content);
    }

    @Override
    public void notifyCancel(int notifyId) {
        NotificationManagerCompat.from(this).cancel(notifyId);
    }

    public static void log(String str) {
        Log.e(TAG, str);
    }
}
