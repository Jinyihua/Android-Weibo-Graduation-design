package com.jyh.sinaweibo.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.SharedPreferencesCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.jyh.sinaweibo.R;
import com.jyh.sinaweibo.app.AppContext;
import com.jyh.sinaweibo.app.AppManager;
import com.jyh.sinaweibo.fragment.WeiboPublishFragment;
import com.jyh.sinaweibo.service.WeiboPublishService;
import com.jyh.sinaweibo.util.AccessTokenKeeper;
import com.jyh.sinaweibo.util.CollectionUtil;
import com.jyh.sinaweibo.util.TDevice;
import com.jyh.sinaweibo.util.WeiboPublishContract;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.List;
import java.util.Set;

/*
* 用于实现发送微博界面
* */
public class WeiboPublishActivity extends BaseActivity implements WeiboPublishContract.Operator {

    private final static String SHARE_FILE_NAME = WeiboPublishActivity.class.getName();
    private final static String SHARE_VALUES_CONTENT = "content";
    private final static String SHARE_VALUES_IMAGES = "images";

    private WeiboPublishContract.View mView;
    private boolean mSaveOnDestroy = true;

    @Override
    protected int getToolBarTitle() {
        return R.string.toolbar_title_add;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weibo_publish;
    }

    @Override
    public void initView() {
        //创建Fragment对象
        WeiboPublishFragment fragment = new WeiboPublishFragment();
        FragmentTransaction trans = getSupportFragmentManager()
                .beginTransaction();
        trans.replace(R.id.activity_weibo_publish, fragment);
        trans.commitNow();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    public static void show(Context context) {
        Intent intent = new Intent(context, WeiboPublishActivity.class);
        context.startActivity(intent);
    }

    //
    @Override
    public void setDataView(WeiboPublishContract.View view) {
        mView = view;
    }

    /*
    * 实现发布微博
    * */
    @Override
    public void publish() {

        if (!TDevice.hasInternet()) {
            AppContext.showToastShort(R.string.tip_network_error);
            return;
        }

        Oauth2AccessToken accessToken = AccessTokenKeeper
                .readAccessToken(this);

        //获取Fragment输入框架的数据
        String content = mView.getContent();
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(content.trim())) {
            AppContext.showToastShort(R.string.tip_content_empty);
            return;
        }

        if (content.length() > WeiboPublishFragment.MAX_TEXT_LENGTH) {
            AppContext.showToastShort(R.string.tip_content_too_long);
            return;
        }

        //获取上传图片数据路径
        final List<String> paths = CollectionUtil.toArrayList(mView.getImages());
        ;

        // To service publish
        content = content.replaceAll("[\n\\s]+", " ");
        //后台服务实现发送微博功能

        WeiboPublishService.startActionPublish(this, content, paths, accessToken.getToken());
        // Toast
        AppContext.showToast(R.string.weibo_publishing_toast);
        // clear the tweet data
        clearAndFinish();
    }

    @Override
    public void onBack() {
        AppManager.getAppManager().finishActivity();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        final String content = mView.getContent();
        final String[] paths = mView.getImages();
        if (content != null)
            outState.putString(SHARE_VALUES_CONTENT, content);
        if (paths != null && paths.length > 0)
            outState.putStringArray(SHARE_VALUES_IMAGES, paths);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String content = savedInstanceState.getString(SHARE_VALUES_CONTENT, null);
        String[] images = savedInstanceState.getStringArray(SHARE_VALUES_IMAGES);
        if (content != null) {
            mView.setContent(content);
        }
        if (images != null && images.length > 0) {
            mView.setImages(images);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSaveOnDestroy) {
            saveXmlData();
        }

    }

    @Override
    public void loadXmlData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_FILE_NAME, Activity.MODE_PRIVATE);
        String content = sharedPreferences.getString(SHARE_VALUES_CONTENT, null);
        Set<String> set = sharedPreferences.getStringSet(SHARE_VALUES_IMAGES, null);
        if (content != null) {
            mView.setContent(content);
        }
        if (set != null && set.size() > 0) {
            mView.setImages(CollectionUtil.toArray(set, String.class));

        }
        // hide the software
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    private void saveXmlData() {
        final String content = mView.getContent();
        final String[] paths = mView.getImages();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARE_VALUES_CONTENT, content);
        if (paths != null && paths.length > 0) {
            editor.putStringSet(SHARE_VALUES_IMAGES, CollectionUtil.toHashSet(paths));
        } else {
            editor.putStringSet(SHARE_VALUES_IMAGES, null);
        }
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }


    private void clearAndFinish() {
        mSaveOnDestroy = false;
        SharedPreferences sharedPreferences = getSharedPreferences(SHARE_FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARE_VALUES_CONTENT, null);
        editor.putStringSet(SHARE_VALUES_IMAGES, null);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
        finish();
    }
}
