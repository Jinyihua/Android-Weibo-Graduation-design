package com.jyh.sinaweibo.model;

import java.io.Serializable;

/**
 * Created by cheng on 2016/9/29.
 */
public class PicUrls implements Serializable {
    private String thumbnail_pic; //缩略图
    private String bmiddle_pic;   //中等图
    private String original_pic;  //原始图


    public String getThumbnail_pic() {
        return thumbnail_pic;
    }

    public void setThumbnail_pic(String thumbnail_pic) {
        this.thumbnail_pic = thumbnail_pic;
    }

    public String getBmiddle_pic() {
        return bmiddle_pic;
    }

    public void setBmiddle_pic(String bmiddle_pic) {
        this.bmiddle_pic = bmiddle_pic;
    }

    public String getOriginal_pic() {
        return original_pic;
    }

    public void setOriginal_pic(String original_pic) {
        this.original_pic = original_pic;
    }

}
