package com.jyh.sinaweibo.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jyh.sinaweibo.R;
import com.jyh.sinaweibo.app.AppManager;
import com.jyh.sinaweibo.fragment.FragmentFirstPagerRecycler;
import com.jyh.sinaweibo.fragment.FragmentPublicPager;
import com.jyh.sinaweibo.httputil.listener.OnResultBackListener;
import com.jyh.sinaweibo.httputil.util.HttpRequest;
import com.jyh.sinaweibo.model.IMobel;
import com.jyh.sinaweibo.model.ModelUserResultBean;
import com.jyh.sinaweibo.model.ModelWeibo;
import com.jyh.sinaweibo.util.AccessTokenKeeper;


/*
* 主页
* */
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnResultBackListener<ModelWeibo> {

    private final static String TAG = MainActivity.class.getSimpleName();

    private CharSequence mTitle;

    private DrawerLayout drawer;
    private Fragment mContent;
    private ImageView weibo_head;
    private TextView weibo_name;
    private TextView weibo_jianjie;
    private TextView weibo;
    private TextView guanzhu;
    private TextView fensi;
    private Toolbar toolbar;


    //配置工具栏的标题
    @Override
    protected int getToolBarTitle() {
        return R.string.sinamenu01;
    }


    //配置窗体布局
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_drawer);
        weibo_head = (ImageView) headerLayout.findViewById(R.id.weibo_head);
        weibo_name = (TextView) headerLayout.findViewById(R.id.weibo_name);
        weibo_jianjie = (TextView) headerLayout.findViewById(R.id.weibo_jianjie);
        weibo = (TextView) headerLayout.findViewById(R.id.weibo);
        guanzhu = (TextView) headerLayout.findViewById(R.id.guanzhu);
        fensi = (TextView) headerLayout.findViewById(R.id.fensi);
    }

    @Override
    public void initData() {

        String token = AccessTokenKeeper.readAccessToken(this).getToken();
        String uid = AccessTokenKeeper.readAccessToken(this).getUid();
        HttpRequest.getInstance().doGetUserInfoShow(token, uid, this);
        switchContent(new FragmentFirstPagerRecycler());
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected boolean hasBackButton() {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            mTitle = "首页";
            toolbar.setTitle(mTitle);
            switchContent(new FragmentFirstPagerRecycler());

        } else if (id == R.id.public_weibo) {
            mTitle = "公共微博";
            toolbar.setTitle(mTitle);
            switchContent(new FragmentPublicPager());
        } else if (id == R.id.send_weibo) {
            WeiboPublishActivity.show(MainActivity.this);
        } else if (id == R.id.exit_system) {
            AppManager.getAppManager().finishAllActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * 切换Fragment，也是切换视图的内容
     */
    public void switchContent(Fragment fragment) {
        mContent = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mContent).commit();

    }

    @Override
    public void onSuccess(IMobel<ModelWeibo> mobile) {
        ModelUserResultBean<ModelWeibo> modelUserResultBean = (ModelUserResultBean<ModelWeibo>) mobile;

        Log.e("tag", "name=" + modelUserResultBean.getName());
        Glide.with(this).load(modelUserResultBean.getAvatar_hd()).into(weibo_head);
        weibo_name.setText(modelUserResultBean.getName());
        weibo_jianjie.setText("简介：" + modelUserResultBean.getDescription());
        weibo.setText("微博：" + modelUserResultBean.getStatuses_count());
        guanzhu.setText("关注：" + modelUserResultBean.getFriends_count());
        fensi.setText("粉丝：" + modelUserResultBean.getFollowers_count());

    }

    @Override
    public void onError(String error) {
        Log.e("tag", "error=" + error);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add:
                //启动WeiboPublishActivity窗体
                WeiboPublishActivity.show(MainActivity.this);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
