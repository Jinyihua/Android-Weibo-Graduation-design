package com.jyh.sinaweibo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

import com.jyh.sinaweibo.adapter.AdapterWeibo;
import com.jyh.sinaweibo.adapter.base.BaseListAdapter;
import com.jyh.sinaweibo.fragment.base.BaseListFragment;
import com.jyh.sinaweibo.httputil.util.HttpRequest;
import com.jyh.sinaweibo.model.ModelWeibo;
import com.jyh.sinaweibo.model.WeiBoResultBean;
import com.jyh.sinaweibo.util.AccessTokenKeeper;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentFirstPagerMy} factory method to
 * create an instance of this fragment.
 */
public class FragmentFirstPagerMy extends BaseListFragment<ModelWeibo> {

    private WeiBoResultBean<ModelWeibo> weiBoResultBean;


    @Override
    protected void onRestartInstance(Bundle bundle) {
        super.onRestartInstance(bundle);
        mIsRefresh = false;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onRefreshing() {
        super.onRefreshing();
    }

    /*
    * 此方法获取数据
    * */
    @Override
    protected void requestData() {
        super.requestData();
        //当mIsRefresh等于true，下拉刷新，否则上拉加载
        String token= AccessTokenKeeper.readAccessToken(getActivity()).getToken();
        if(mIsRefresh)
        {
            HttpRequest.getInstance().doGetHomeTimelineWeiBo(token,mHandler);
        }else{

            if(weiBoResultBean.getStatuses()!=null&& weiBoResultBean.getStatuses().size()>0) {
                String maxId = weiBoResultBean.getStatuses().get(weiBoResultBean.getStatuses().size() - 1).getId();
                HttpRequest.getInstance().doGetHomeTimelineWeiBoNextPage(token, Long.valueOf(maxId), mHandler);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected BaseListAdapter<ModelWeibo> getListAdapter() {
        return new AdapterWeibo(this);
    }



    @Override
    protected void setListData(WeiBoResultBean<ModelWeibo> weiBoResultBean) {
        super.setListData(weiBoResultBean);
        this.weiBoResultBean = weiBoResultBean;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}