package com.jyh.sinaweibo.httputil.util;


import com.jyh.sinaweibo.common.NewFeature;
import com.jyh.sinaweibo.httputil.listener.OnEntityResultBackListener;
import com.jyh.sinaweibo.httputil.listener.OnResultBackListener;
import com.jyh.sinaweibo.httputil.retrofitservice.WeiboInterface;
import com.jyh.sinaweibo.model.Comment;
import com.jyh.sinaweibo.model.CommentResultBean;
import com.jyh.sinaweibo.model.Entity;
import com.jyh.sinaweibo.model.ModelUserResultBean;
import com.jyh.sinaweibo.model.ModelWeibo;
import com.jyh.sinaweibo.model.RepostResultBean;
import com.jyh.sinaweibo.model.WeiBoResultBean;
import com.jyh.sinaweibo.util.SinaWeiboApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import rx.Observable;

/**
 * 实例化HttpHepler对象
 * 发送请求
 */
public class HttpRequest {

    private HttpHelper httpHelper;

    private HttpRequest(String baseUrl)
    {
        httpHelper=HttpHelper.getInstance();
        httpHelper.init(baseUrl);
    }

    private static final class SingleTonHttpRequestHolder
    {
        private final static HttpRequest instance=new HttpRequest(SinaWeiboApi.BASE_URL);
    }

    public static HttpRequest getInstance()
    {
        return SingleTonHttpRequestHolder.instance;
    }


    //获取最新的公共微博数据
    public <E extends Entity>  void doGetPublicTimeWeiBo(String token, OnResultBackListener<E> listener)
    {
        Map<String,String> params=new HashMap<>();
        params.put("access_token",token);
        params.put("count",String.valueOf(NewFeature.GET_WEIBO_NUMS));
        Observable<WeiBoResultBean<ModelWeibo>> observable=httpHelper.createRetrofitService(WeiboInterface.class).getPublicTimeWeiBoByRx(params);
        httpHelper.doRequest(observable,listener);
    }

    //获取主页微博数据
    public <E extends Entity> void doGetHomeTimelineWeiBo(String token, OnResultBackListener<E> listener)
    {
        Map<String,String> params=new HashMap<>();
        params.put("access_token",token);
        params.put("count", String.valueOf(NewFeature.GET_WEIBO_NUMS));

        Observable<WeiBoResultBean<ModelWeibo>> observable=httpHelper.createRetrofitService(WeiboInterface.class).getHomeTimeLineWeiBo(params);

        httpHelper.doRequest(observable,listener);
    }


    //获取主页微博数据
    public <E extends Entity> void doGetHomeTimelineWeiBoNextPage(String token,long max_id, OnResultBackListener<E> listener)
    {

        Map<String,String> params=new HashMap<>();
        params.put("access_token",token);
        params.put("max_id", String.valueOf(max_id-1));
        params.put("count", String.valueOf(NewFeature.GET_WEIBO_NUMS));

        Observable<WeiBoResultBean<ModelWeibo>> observable=httpHelper.createRetrofitService(WeiboInterface.class).getHomeTimeLineWeiBo(params);

        httpHelper.doRequest(observable,listener);
    }

    //获取用户信息
    public <E extends Entity> void doGetUserInfoShow(String token,String uid,OnResultBackListener<E> listener)
    {
        Map<String,String> params=new HashMap<>();
        params.put("access_token",token);
        params.put("uid",uid);

        Observable<ModelUserResultBean<ModelWeibo>> observable
                =httpHelper.createRetrofitService(WeiboInterface.class).getUserShow(params);
        httpHelper.doRequest(observable,listener);
    }


    //获取@我的微博
    public <E extends Entity> void doGetMentionsWeiBo(String token, OnResultBackListener<E> listener)
    {
        Map<String,String> params=new HashMap<>();
        params.put("access_token",token);
        params.put("count", String.valueOf(NewFeature.GET_WEIBO_NUMS));


        Observable<RepostResultBean<ModelWeibo>> observable
                =httpHelper.createRetrofitService(WeiboInterface.class).getReportTimeLine(params);

        httpHelper.doRequest(observable,listener);
    }


    //获取@我的微博
    public <E extends Entity> void doGetMentionsWeiBoNextPage(String token,long max_id, OnResultBackListener<E> listener)
    {

        Map<String,String> params=new HashMap<>();
        params.put("access_token",token);
        params.put("max_id", String.valueOf(max_id-1));
        params.put("count", String.valueOf(NewFeature.GET_WEIBO_NUMS));

        Observable<WeiBoResultBean<ModelWeibo>> observable=httpHelper.createRetrofitService(WeiboInterface.class).getMentions(params);

        httpHelper.doRequest(observable,listener);
    }



    //获取评语数据
    public <E extends Entity> void doGetCommentShowNextPage(String token,String id,long max_id,OnResultBackListener<E> listener)
    {
        Map<String,String> params=new HashMap<>();
        params.put("access_token",token);
        params.put("id",id);
        params.put("max_id", String.valueOf(max_id-1));
        params.put("count", String.valueOf(NewFeature.GET_WEIBO_NUMS));


        Observable<CommentResultBean<Comment>> observable=httpHelper.createRetrofitService(WeiboInterface.class).getCommentShow(params);

        httpHelper.doRequest(observable,listener);
    }



    //获取评语数据
    public <E extends Entity> void doGetCommentShow(String token,String id,OnResultBackListener<E> listener)
    {
        Map<String,String> params=new HashMap<>();
        params.put("access_token",token);
        params.put("id",id);
        params.put("count", String.valueOf(NewFeature.GET_WEIBO_NUMS));

        Observable<CommentResultBean<Comment>> observable=httpHelper.createRetrofitService(WeiboInterface.class).getCommentShow(params);

        httpHelper.doRequest(observable,listener);
    }

    //发送微博
    public void doPostWeibo(String access_token,String status, OnEntityResultBackListener listener)
    {
        Observable observable=httpHelper.createRetrofitService(WeiboInterface.class).postWeibo(access_token,status);
        httpHelper.doRequest(observable,listener);
    }

    //上传图片
    public void uploadPic(List<MultipartBody.Part> partList, OnEntityResultBackListener listener)
    {
        Observable<ModelWeibo> observable=httpHelper.createRetrofitService(WeiboInterface.class).uploadPic(partList);
        httpHelper.doRequest(observable,listener);
    }
}
