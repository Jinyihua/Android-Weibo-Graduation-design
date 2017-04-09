package com.jyh.sinaweibo.util;

/**
 * Created by cheng on 2017/1/4.
 */
public interface WeiboPublishContract
{

    //定义操作行为方法
    interface Operator {
        //设置操作数据回调接口对象
        void setDataView(View view);
        //用于发布微博
        void publish();
        //回退操作事件
        void onBack();
        //加载数据
        void loadXmlData();
    }

    //操作数据回调方法
    interface View {
        //获取内容
        String getContent();

        void setContent(String content);
        //获取图片地址
        String[] getImages();

        void setImages(String[] paths);
    }
}
