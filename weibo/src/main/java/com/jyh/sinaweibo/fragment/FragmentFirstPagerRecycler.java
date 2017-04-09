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


/*
 * 实现首页的功能
 */
public class FragmentFirstPagerRecycler extends BaseRecyclerViewFragment<ModelWeibo>
{
    private WeiBoResultBean<ModelWeibo> weiBoResultBean;
    private String token;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token= AccessTokenKeeper.readAccessToken(getActivity()).getToken();

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
            HttpRequest.getInstance().doGetHomeTimelineWeiBo(token,mHandler);
        }
    }

    @Override
    protected BaseRecyclerAdapter<ModelWeibo> getRecyclerAdapter() {
        return new AdapterWeiboRecycler(getActivity());
    }



    @Override
    public void onLoadMore() {
        //判断结果集是否有数据
        if(weiBoResultBean.getStatuses()!=null&& weiBoResultBean.getStatuses().size()>0) {
            String maxId = weiBoResultBean.getStatuses().get(weiBoResultBean.getStatuses().size() - 1).getId();
            HttpRequest.getInstance().doGetHomeTimelineWeiBoNextPage(token, Long.valueOf(maxId), mHandler);
        }
    }
}
