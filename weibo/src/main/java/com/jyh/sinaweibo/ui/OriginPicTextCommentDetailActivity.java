package com.jyh.sinaweibo.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.jyh.sinaweibo.R;
import com.jyh.sinaweibo.fragment.FragmentCommentShow;
import com.jyh.sinaweibo.model.ModelWeibo;


/*
* 显示原创的微博正文
* */
public class OriginPicTextCommentDetailActivity extends BaseActivity
{

    private ModelWeibo modelWeibo;

    @Override
    protected int getToolBarTitle() {
        return R.string.weibo_detail_title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_origin_pic_text_comment_detail;
    }

    @Override
    protected boolean initBundle(Bundle bundle) {
        modelWeibo = (ModelWeibo) bundle.getSerializable("weiboitem");

        return super.initBundle(bundle);
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    public void initView() {

        FragmentTransaction trans = getSupportFragmentManager()
                .beginTransaction();
        trans.replace(R.id.main_frame_id, FragmentCommentShow.newInstance(modelWeibo));
        trans.commit();

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }


}
