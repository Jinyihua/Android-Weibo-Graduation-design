package com.jyh.sinaweibo.fragment.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jyh.sinaweibo.R;
import com.jyh.sinaweibo.adapter.base.BaseRecyclerAdapter;
import com.jyh.sinaweibo.cache.CacheManager;
import com.jyh.sinaweibo.common.GetDataStatus;
import com.jyh.sinaweibo.common.NewFeature;
import com.jyh.sinaweibo.httputil.listener.OnResultBackListener;
import com.jyh.sinaweibo.model.Entity;
import com.jyh.sinaweibo.model.IMobel;
import com.jyh.sinaweibo.model.PageBean;
import com.jyh.sinaweibo.util.AppOperator;
import com.jyh.sinaweibo.widget.RecyclerRefreshLayout;

import java.util.ArrayList;



public abstract class BaseRecyclerViewFragment <T extends Entity> extends BaseFragment implements RecyclerRefreshLayout.SuperRefreshLayoutListener,
        BaseRecyclerAdapter.OnItemClickListener {

    protected BaseRecyclerAdapter<T> mAdapter;

    protected RecyclerView mRecyclerView;
    protected RecyclerRefreshLayout mRefreshLayout;
    protected boolean mIsRefresh;
    protected OnResultBackListener<T> mHandler;
    protected PageBean<T> mBean;
    protected String CACHE_NAME = getClass().getName();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_recycler_view;
    }

    @Override
    protected void initWidget(View root) {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        mRefreshLayout = (RecyclerRefreshLayout) root.findViewById(R.id.refreshLayout);
    }

    @Override
    public void initData() {
        mBean = null;
        //获取适配器
        mAdapter = getRecyclerAdapter();
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, false);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mRefreshLayout.setSuperRefreshLayoutListener(this);
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, false);
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

        //定义一个回调接口对象
        mHandler=new OnResultBackListener<T>() {
            @Override
            public void onSuccess(IMobel<T> mobel) {
                try {
                    mBean.setItems(mobel.getList());
                    if (mBean.getItems() != null) {
                        setListData(mobel);
                        onRequestSuccess(GetDataStatus.GET_DATA_SUCCESS);
                    } else {
                        mAdapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
                    }
                    onRequestFinish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                onRequestFinish();
            }
        };


        //通过线程池，启动子线程
        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                //判断缓存是否有数据
                mBean = (PageBean<T>) CacheManager.readObject(getActivity(), CACHE_NAME);
                //if is the first loading
                if (mBean == null) {
                    //页面数据的bean对象
                    mBean = new PageBean<>();
                    mBean.setItems(new ArrayList<T>());
//                    mRefreshLayout.setRefreshing(true);
                    onRefreshing();
                } else {
                    mRoot.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.addAll(mBean.getItems());
//                            mRefreshLayout.setRefreshing(true);
                            mRefreshLayout.setVisibility(View.VISIBLE);
                            onRefreshing();
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onItemClick(int position, long itemId) {

    }



    @Override
    public void onRefreshing() {
        mIsRefresh = true;
        requestData();
    }


    protected void requestData() {
    }

    protected void onRequestStart() {

    }

    protected void onRequestSuccess(int code) {

    }

    protected void onRequestFinish() {
        onComplete();
    }

    protected void onComplete() {
        mRefreshLayout.onComplete();
        mIsRefresh = false;
    }


    protected void setListData(final IMobel<T> mobel) {
        //is refresh
        if (mIsRefresh) {
            //cache the time
            mAdapter.clear();
            mAdapter.addAll(mobel.getList());
            mRefreshLayout.setCanLoadMore(true);

            AppOperator.runOnThread(new Runnable() {
                @Override
                public void run() {
                    //将页面数据保存到缓存
                    CacheManager.saveObject(getActivity(), mBean, CACHE_NAME);
                }
            });
        } else {
            mAdapter.addAll(mobel.getList());
        }
        if (mobel.getList().size() < NewFeature.GET_WEIBO_NUMS) {
            mAdapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
        }
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }


    protected abstract <T extends Entity>  BaseRecyclerAdapter<T>  getRecyclerAdapter();

}