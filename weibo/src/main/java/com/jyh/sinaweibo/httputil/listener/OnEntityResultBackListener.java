package com.jyh.sinaweibo.httputil.listener;


import com.jyh.sinaweibo.model.Entity;

/**
 * Created by cheng on 2016/12/7.
 *
 * 针对entity的回调接口
 */
public interface OnEntityResultBackListener {

    public void onSuccess(Entity entity);
    public void onError(String error);
}
