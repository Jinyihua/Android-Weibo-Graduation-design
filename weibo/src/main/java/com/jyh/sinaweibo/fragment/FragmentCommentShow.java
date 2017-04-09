package com.jyh.sinaweibo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.jyh.sinaweibo.adapter.AdapterCommentRecycler;
import com.jyh.sinaweibo.adapter.MentionDetailAdapter;
import com.jyh.sinaweibo.adapter.base.BaseRecyclerAdapter;
import com.jyh.sinaweibo.adapter.base.OnDetailButtonClickListener;
import com.jyh.sinaweibo.common.StatusDetailModelImp;
import com.jyh.sinaweibo.fragment.base.BaseRecyclerViewFragment;
import com.jyh.sinaweibo.httputil.util.HttpRequest;
import com.jyh.sinaweibo.model.Comment;
import com.jyh.sinaweibo.model.CommentResultBean;
import com.jyh.sinaweibo.model.Entity;
import com.jyh.sinaweibo.model.IMobel;
import com.jyh.sinaweibo.model.ModelWeibo;
import com.jyh.sinaweibo.model.WeiBoResultBean;
import com.jyh.sinaweibo.util.AccessTokenKeeper;


public class FragmentCommentShow extends BaseRecyclerViewFragment<Entity> implements OnDetailButtonClickListener {

    private static final String ARG_PARAM1 = "weibo";
    private CommentResultBean<Comment> commentResultBean;
    private WeiBoResultBean<ModelWeibo> weiBoResultBean;
    private String token;
    private ModelWeibo modelWeibo;
    public LinearLayout mHeaderView;
    //针对评论的适配器
    private AdapterCommentRecycler adapterCommentRecycler;
    //针对转发的适配器
    private MentionDetailAdapter mentionDetailAdapter;
    //当前显示那个适配器的列表
    //StatusDetailModelImp.COMMENT_PAGE:显示评论的适配器
    //StatusDetailModelImp.REPOST_PAGE:显示转发的适配器
    private int mCurrentGroup = StatusDetailModelImp.COMMENT_PAGE;


    public static FragmentCommentShow newInstance(ModelWeibo modelWeibo) {
        FragmentCommentShow fragment = new FragmentCommentShow();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, modelWeibo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            modelWeibo = (ModelWeibo) getArguments().getSerializable(ARG_PARAM1);
        }

        CACHE_NAME = "OnComment";
        token = AccessTokenKeeper.readAccessToken(getActivity()).getToken();

        //创建评论的结果集对象
        commentResultBean = new CommentResultBean<>();
        //创建微博的结果集对象
        weiBoResultBean = new WeiBoResultBean<>();

        adapterCommentRecycler = new AdapterCommentRecycler(getActivity(), modelWeibo, this);
        mentionDetailAdapter = new MentionDetailAdapter(getActivity(), modelWeibo, this);
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
    public void initData() {
        Log.e("test", "initData");
        super.initData();
    }


    @Override
    public void onRefreshing() {
        super.onRefreshing();
    }

    @Override
    protected void setListData(IMobel<Entity> mobel) {
        super.setListData(mobel);
        if (mCurrentGroup == StatusDetailModelImp.COMMENT_PAGE)
            this.commentResultBean = (CommentResultBean) mobel;
        else if (mCurrentGroup == StatusDetailModelImp.REPOST_PAGE)
            this.weiBoResultBean = (WeiBoResultBean) mobel;

    }


    /*
    * 此方法获取数据
    * */
    @Override
    protected void requestData() {
        super.requestData();
        //当mIsRefresh等于true,上拉加载
        if (mIsRefresh) {
            if (mCurrentGroup == StatusDetailModelImp.COMMENT_PAGE) {
                Log.e("test", "requestData doGetCommentShow token=" + token);
                //当下拉刷新列表，显示评论列表
                HttpRequest.getInstance().doGetCommentShow(token, modelWeibo.getId(), mHandler);
            } else if (mCurrentGroup == StatusDetailModelImp.REPOST_PAGE) {
                Log.e("test", "requestData doGetMentionsWeiBo token=" + token);
                //当下拉刷新列表，显示转发列表
                HttpRequest.getInstance().doGetMentionsWeiBo(token, mHandler);
            }
        }
    }

    @Override
    protected BaseRecyclerAdapter<? extends Entity> getRecyclerAdapter() {
        if (mCurrentGroup == StatusDetailModelImp.COMMENT_PAGE)
            return adapterCommentRecycler;
        else if (mCurrentGroup == StatusDetailModelImp.REPOST_PAGE)
            return mentionDetailAdapter;
        else
            return null;
    }


    @Override
    public void onLoadMore() {

        if (mCurrentGroup == StatusDetailModelImp.COMMENT_PAGE) {
            if (commentResultBean.getComments() != null && commentResultBean.getComments().size() > 0) {
                String maxId = commentResultBean.getComments().get(commentResultBean.getComments().size() - 1).getId();
                HttpRequest.getInstance().doGetCommentShowNextPage(token, modelWeibo.getId(), Long.valueOf(maxId), mHandler);
            }
        } else if (mCurrentGroup == StatusDetailModelImp.REPOST_PAGE) {
            if (weiBoResultBean.getStatuses() != null && weiBoResultBean.getStatuses().size() > 0) {
                String maxId = weiBoResultBean.getStatuses().get(weiBoResultBean.getStatuses().size() - 1).getId();
                HttpRequest.getInstance().doGetMentionsWeiBoNextPage(token, Long.valueOf(maxId), mHandler);
            }
        }
    }


    @Override
    public void OnComment() {
        CACHE_NAME = "OnComment";
        mCurrentGroup = StatusDetailModelImp.COMMENT_PAGE;
        initData();

    }

    @Override
    public void OnRetweet() {
        CACHE_NAME = "OnRetweet";
        mCurrentGroup = StatusDetailModelImp.REPOST_PAGE;
        initData();

    }

    @Override
    protected void onRequestFinish() {
        super.onRequestFinish();
        Log.e("test", "onRequestFinish");

        if (mCurrentGroup == StatusDetailModelImp.COMMENT_PAGE) {
            adapterCommentRecycler.commentHighlight();
        } else if (mCurrentGroup == StatusDetailModelImp.REPOST_PAGE) {
            mentionDetailAdapter.repostHighlight();
        }

    }
}