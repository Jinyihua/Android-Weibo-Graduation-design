package com.jyh.sinaweibo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;

import com.jyh.sinaweibo.model.ViewPageInfo;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * ViewPager的适配器
 * 1、Fragment缓存处理
 * 2、对外提供addTab更新页面
 */
public class ViewPageFragmentAdapter extends FragmentStatePagerAdapter
{
    private final Context mContext;
    private final ViewPager mViewPager;
    //存储ViewPagerInfo对象
    //mTabs就是适配器的数据源
    public ArrayList<ViewPageInfo> mTabs = new ArrayList<ViewPageInfo>();
    //存储Fragment页面
    private Map<String, Fragment> mFragments = new ArrayMap<>();

    public ViewPageFragmentAdapter(FragmentManager fm, ViewPager pager) {
        super(fm);
        mContext = pager.getContext();
        mViewPager = pager;
        //指明当前适配器对象就是ViewPager的适配器
        mViewPager.setAdapter(this);
    }

    //用于添加标签页
    public void addTab(String title, String tag, Class<?> clss, Bundle args)
    {
        ViewPageInfo viewPageInfo = new ViewPageInfo(title, tag, clss, args);
        addFragment(viewPageInfo);
    }

    private void addFragment(ViewPageInfo info) {
        if (info == null) {
            return;
        }

        mTabs.add(info);
        notifyDataSetChanged();
    }


    @Override
    public Fragment getItem(int position) {
        ViewPageInfo info = mTabs.get(position);

        Fragment fragment = mFragments.get(info.tag);
        if (fragment == null) {
            //实例化Fragment（创建Fragment实例）
            fragment = Fragment.instantiate(mContext, info.clss.getName(), info.args);
            // 避免重复创建而进行缓存
            mFragments.put(info.tag, fragment);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).title;
    }
}
