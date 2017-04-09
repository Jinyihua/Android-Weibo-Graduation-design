package com.jyh.sinaweibo.media.config;

import android.widget.ImageView;

/**
 * 暴露一个图片加载器
 * on 2016/7/13.
 */
public interface ImageLoaderListener {
    void displayImage(ImageView iv, String path);
}
