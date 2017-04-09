package com.jyh.sinaweibo.httputil.util;


import com.jyh.sinaweibo.httputil.listener.OnEntityResultBackListener;
import com.jyh.sinaweibo.httputil.listener.OnResultBackListener;
import com.jyh.sinaweibo.httputil.observer.MyEntityObServer;
import com.jyh.sinaweibo.httputil.observer.MyObServer;
import com.jyh.sinaweibo.model.Entity;
import com.jyh.sinaweibo.model.IMobel;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 单例模式
 */
public class HttpHelper {
    private Retrofit retrofit;

    private final static long TIMES = 5000;

    private HttpHelper() {

    }

    private static final class SingleTonHttpHelperHolder {
        private final static HttpHelper instance = new HttpHelper();
    }

    public static HttpHelper getInstance() {
        return SingleTonHttpHelperHolder.instance;
    }

    public void init(String baseUrl) {
        //创建Okhttp
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .connectTimeout(HttpHelper.TIMES, TimeUnit.MILLISECONDS)
                .readTimeout(HttpHelper.TIMES, TimeUnit.MILLISECONDS)
                .writeTimeout(HttpHelper.TIMES, TimeUnit.MILLISECONDS).build();

        //创建Retrofit对象
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                //自动解释json数据--->javabean数据
                .addConverterFactory(GsonConverterFactory.create())
                //支持rxjava的适配转换
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    /*
    * retrofit的服务接口
    * 泛型方法
    * */
    public <I> I createRetrofitService(Class<I> retrofitInterface) {
        return retrofit.create(retrofitInterface);
    }


    /*
    * T：结果集类型
    * E：实体类类型
    * observable：被观察者
    * resultBackListener：回调接口
    * */
    public <T extends IMobel, E extends Entity> void doRequest(Observable<T> observable, OnResultBackListener<E> resultBackListener) {
        //创建观察者
        MyObServer myObServer = new MyObServer();
        //将回调监听对象注册给观察者
        myObServer.setListener(resultBackListener);
        observable.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(myObServer);
    }


    public <E extends Entity> void doRequest(Observable<E> observable, OnEntityResultBackListener resultBackListener) {
        //创建观察者
        MyEntityObServer myObServer = new MyEntityObServer();
        //将回调监听对象注册给观察者
        myObServer.setListener(resultBackListener);
        observable.
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(myObServer);
    }
}
