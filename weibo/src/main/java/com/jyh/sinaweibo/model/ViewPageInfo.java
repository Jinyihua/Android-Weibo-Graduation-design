package com.jyh.sinaweibo.model;

import android.os.Bundle;

/**
 * Created by cheng on 2016/12/17.
 */
public class ViewPageInfo {

    //页面的标记
    public final String tag;
    //页面的类
    public final Class<?> clss;
    //页面的参数
    public final Bundle args;
    //页面的标题
    public final String title;

    public ViewPageInfo(String _title, String _tag, Class<?> _class, Bundle _args) {
        title = _title;
        tag = _tag;
        clss = _class;
        args = _args;
    }
}
