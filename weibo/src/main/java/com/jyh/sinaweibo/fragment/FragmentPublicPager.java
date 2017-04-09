package com.jyh.sinaweibo.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jyh.sinaweibo.adapter.AdapterWeiboRecycler;
import com.jyh.sinaweibo.adapter.base.BaseRecyclerAdapter;
import com.jyh.sinaweibo.fragment.base.BaseRecyclerViewFragment;
import com.jyh.sinaweibo.httputil.util.HttpRequest;
import com.jyh.sinaweibo.model.IMobel;
import com.jyh.sinaweibo.model.ModelWeibo;
import com.jyh.sinaweibo.model.WeiBoResultBean;
import com.jyh.sinaweibo.util.AccessTokenKeeper;


/**
 * 实现公共微博
 */
public class FragmentPublicPager extends BaseRecyclerViewFragment<ModelWeibo>
{

    //定义数据结果集的javabean
    private WeiBoResultBean<ModelWeibo> weiBoResultBean;
    //访问令牌
    private String token;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token= AccessTokenKeeper.readAccessToken(getActivity()).getToken();
        //缓存文件名
        CACHE_NAME = getClass().getName();
    }

    @Override
    protected void onRestartInstance(Bundle bundle) {
        super.onRestartInstance(bundle);
        mIsRefresh = false;
    }

    @Override
    public void initWidget(View root) {
        super.initWidget(root);
    }

    @Override
    public void initData()
    {
        super.initData();
    }

    @Override
    public void onRefreshing() {
        super.onRefreshing();
    }

    @Override
    protected void setListData(IMobel<ModelWeibo> mobel) {
        super.setListData(mobel);

        this.weiBoResultBean = (WeiBoResultBean<ModelWeibo>) mobel;
    }


    /*
    * 此方法获取数据
    * */
    @Override
    protected void requestData() {
        super.requestData();
        //当mIsRefresh等于true,下拉刷新
        if(mIsRefresh)
        {
            //获取数据
            HttpRequest.getInstance().doGetPublicTimeWeiBo(token,mHandler);
        }
    }

    /*
    * 设置recyclerView的适配器
    * */
    @Override
    protected BaseRecyclerAdapter<ModelWeibo> getRecyclerAdapter() {
        return new AdapterWeiboRecycler(getActivity());
    }

    /*
    * 上拉刷新更多
    * */
    @Override
    public void onLoadMore() {

    }
}
