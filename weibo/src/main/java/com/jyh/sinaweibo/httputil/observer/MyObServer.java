package com.jyh.sinaweibo.httputil.observer;

import android.util.Log;

import com.jyh.sinaweibo.httputil.listener.OnResultBackListener;
import com.jyh.sinaweibo.model.IMobel;

import rx.Observer;

/**
 *
 * 针对结果集（列表）的观察者
 */
public class MyObServer implements Observer<IMobel>
{
    private OnResultBackListener listener;

    public MyObServer() {
    }


    public void setListener(OnResultBackListener listener) {
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
    public void onNext(IMobel iMobel) {
        Log.e("test","onNext end");
        listener.onSuccess(iMobel);
    }
}
