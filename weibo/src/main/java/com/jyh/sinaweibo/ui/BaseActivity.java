package com.jyh.sinaweibo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.jyh.sinaweibo.R;
import com.jyh.sinaweibo.app.AppManager;


/**
 * Created by cheng on 2016/12/17.
 */
public abstract class BaseActivity extends AppCompatActivity
        implements View.OnClickListener,BaseViewInterface
{

    protected RequestManager mImageLoader;
    protected LayoutInflater mInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //将活动实例存储到堆栈管理
        AppManager.getAppManager().addActivity(this);

        if (initBundle(getIntent().getExtras())) {
            onBeforeSetContentLayout();
            if (getLayoutId() != 0) {
                setContentView(getLayoutId());
            }

            mInflater = getLayoutInflater();
            initToolbar((Toolbar) findViewById(R.id.toolbar));

            init(savedInstanceState);
            initView();
            initData();
        } else {
            finish();
        }
    }

    protected void onBeforeSetContentLayout() {
    }



    protected int getLayoutId() {
        return 0;
    }

    protected View inflateView(int resId) {
        return mInflater.inflate(resId, null);
    }

    protected boolean initBundle(Bundle bundle) {
        return true;
    }

    protected void init(Bundle savedInstanceState) {
    }

    protected void initToolbar(Toolbar  toolbar) {

        if (toolbar == null)
            return;


        int titleRes = getToolBarTitle();
        if (titleRes != 0) {
            toolbar.setTitle(titleRes);
            toolbar.setTitleTextColor(getResources().getColor(R.color.toobalTitlecolor));
        }

        setSupportActionBar(toolbar);

        if (hasBackButton()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }


    protected abstract int getToolBarTitle();

    protected boolean hasBackButton() {
        return false;
    }

    public synchronized RequestManager getImageLoader() {
        if (mImageLoader == null)
            mImageLoader = Glide.with(this);
        return mImageLoader;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //从堆栈删除窗体实例
        AppManager.getAppManager().finishActivity(this);
    }





}
