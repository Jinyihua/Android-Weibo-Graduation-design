package com.jyh.sinaweibo.httputil.observer;

import android.util.Log;

import com.jyh.sinaweibo.httputil.listener.OnEntityResultBackListener;
import com.jyh.sinaweibo.model.Entity;

import rx.Observer;

/**
 * Created by cheng on 2016/12/7.
 * 针对实体类的观察者
 */
public class MyEntityObServer implements Observer<Entity>
{
    private OnEntityResultBackListener listener;

    public MyEntityObServer() {
    }


    public void setListener(OnEntityResultBackListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

        listener.onError(e.getMessage());
    }

    /*
    * 当数据请求成功，回调onNext方法
    * IMoble参数：代表结果集数据
    * */
    @Override
    public void onNext(Entity entity) {
        Log.e("test","onNext end");
        listener.onSuccess(entity);
    }
}
