package com.jyh.sinaweibo.httputil.retrofitservice;


import com.jyh.sinaweibo.model.Comment;
import com.jyh.sinaweibo.model.CommentResultBean;
import com.jyh.sinaweibo.model.ModelUserResultBean;
import com.jyh.sinaweibo.model.ModelWeibo;
import com.jyh.sinaweibo.model.RepostResultBean;
import com.jyh.sinaweibo.model.WeiBoResultBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import rx.Observable;


public interface WeiboInterface {

    @GET("2/statuses/public_timeline.json")
    Observable<WeiBoResultBean<ModelWeibo>> getPublicTimeWeiBo(@QueryMap Map<String, String> params);

    @GET("2/statuses/public_timeline.json")
    Observable<WeiBoResultBean<ModelWeibo>> getPublicTimeWeiBoByRx(@QueryMap Map<String, String> params);

    @GET("2/statuses/home_timeline.json")
    Observable<WeiBoResultBean<ModelWeibo>> getHomeTimeLineWeiBo(@QueryMap Map<String, String> params);

    @GET("2/comments/show.json")
    Observable<CommentResultBean<Comment>> getCommentShow(@QueryMap Map<String, String> params);

    @GET("2/statuses/repost_timeline.json")
    Observable<RepostResultBean<ModelWeibo>> getReportTimeLine(@QueryMap Map<String, String> params);

    @GET("2/statuses/mentions.json")
    Observable<WeiBoResultBean<ModelWeibo>> getMentions(@QueryMap Map<String, String> params);

    @GET("2/users/show.json")
    Observable<ModelUserResultBean<ModelWeibo>> getUserShow(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("2/statuses/update.json")
    Observable<ModelWeibo> postWeibo(@Field("access_token") String access_token, @Field("status") String status);

    @Multipart
    @POST("2/statuses/upload.json")
    Observable<ModelWeibo> uploadPic(@Part() List<MultipartBody.Part> parts);

}
