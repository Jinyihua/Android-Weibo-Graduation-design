package com.jyh.sinaweibo.service;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by cheng on 2017/1/5.
 */
public class WeiboPublishModel implements Serializable
{
    private String id;
    private String status;
    private Bitmap bitmap;
    private String[] srcImages;
    private String[] cacheImages;
    private String lat;
    private String lon;
    private String accessToken;
    private long date;

    public WeiboPublishModel() {
        id = UUID.randomUUID().toString();
        date = System.currentTimeMillis();
    }

    public WeiboPublishModel(String status, String[] srcImages, String lat, String lon, String accessToken) {
        this();
        this.status = status;
        this.srcImages = srcImages;
        this.lat = lat;
        this.lon = lon;
        this.accessToken = accessToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String[] getSrcImages() {
        return srcImages;
    }

    public void setSrcImages(String[] srcImages) {
        this.srcImages = srcImages;
    }

    public String[] getCacheImages() {
        return cacheImages;
    }

    public void setCacheImages(String[] cacheImages) {
        this.cacheImages = cacheImages;
    }
}
