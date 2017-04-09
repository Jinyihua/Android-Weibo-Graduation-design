package com.jyh.sinaweibo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.jyh.sinaweibo.adapter.WeiboSelectImageAdapter;
import com.jyh.sinaweibo.ui.BaseActivity;
import com.jyh.sinaweibo.ui.SelectImageActivity;

/**
 * Created by cheng on 2016/10/4.
 */
public class WeiboPicturesPreviewer extends RecyclerView implements WeiboSelectImageAdapter.Callback, SelectImageActivity.Callback {
    private WeiboSelectImageAdapter mImageAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private RequestManager mCurImageLoader;

    public WeiboPicturesPreviewer(Context context) {
        super(context);
        init();
    }

    public WeiboPicturesPreviewer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WeiboPicturesPreviewer(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mImageAdapter = new WeiboSelectImageAdapter(this);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        this.setLayoutManager(layoutManager);
        this.setAdapter(mImageAdapter);
        this.setOverScrollMode(View.OVER_SCROLL_NEVER);

        ItemTouchHelper.Callback callback = new WeiboPicturesPreviewerItemTouchCallback(mImageAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(this);
    }

    public void set(String[] paths) {
        mImageAdapter.clear();
        for (String path : paths) {
            mImageAdapter.add(path);
        }
        mImageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreClick() {
        //启动SelectImageActivity
        //参数一：上下文
        //参数二：图片显示的最大值
        //参数三：是否使用camera
        //参数四: 已经选中的图片列表
        //参数五：回调接口
        //Callback
        //doSelectDone(String[] images)
        SelectImageActivity.showImage(getContext(), 9, true, mImageAdapter.getPaths(), this);
    }

    @Override
    public RequestManager getImgLoader() {
        if (mCurImageLoader == null) {
            Context context = getContext();
            if (context != null && context instanceof BaseActivity) {
                mCurImageLoader = ((BaseActivity) context).getImageLoader();
            } else {
                mCurImageLoader = Glide.with(getContext());
            }
        }
        return mCurImageLoader;
    }

    @Override
    public void onStartDrag(ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public String[] getPaths() {
        return mImageAdapter.getPaths();
    }

    public void destroy() {
        Context context = getContext();
        if (!(context != null && context instanceof BaseActivity)) {
            mCurImageLoader.onDestroy();
        }
        mCurImageLoader = null;
    }

    @Override
    public void doSelectDone(String[] images) {
        set(images);
    }
}
