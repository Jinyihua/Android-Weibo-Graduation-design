package com.jyh.sinaweibo.httputil.listener;


import com.jyh.sinaweibo.model.IMobel;

/**
 *
 *
 * 针对结果集（列表）的回调接口
 */
public interface OnResultBackListener<E> {

    public void onSuccess(IMobel<E> mobile);
    public void onError(String error);
}
