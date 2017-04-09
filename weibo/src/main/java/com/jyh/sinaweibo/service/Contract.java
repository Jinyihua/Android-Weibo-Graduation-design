package com.jyh.sinaweibo.service;

/**
 * Created by cheng on 2017/1/5.
 */
public class Contract {

    interface IService {
        String getCachePath(String id);

        void start(String modelId, IOperator operator);
        void stop(String id, int startId);


        void notifyMsg(int notifyId, String modelId, boolean haveReDo, boolean haveDelete, int resId, Object... values);

        void notifyCancel(int notifyId);

        void updateModelCache(String id, WeiboPublishModel model);
    }

    interface IOperator extends Runnable {
        void stop();
    }

}
