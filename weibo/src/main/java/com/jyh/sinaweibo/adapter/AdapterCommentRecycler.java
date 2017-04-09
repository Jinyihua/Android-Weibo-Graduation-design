package com.jyh.sinaweibo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyh.sinaweibo.R;
import com.jyh.sinaweibo.adapter.base.BaseRecyclerAdapter;
import com.jyh.sinaweibo.adapter.base.OnDetailButtonClickListener;
import com.jyh.sinaweibo.common.FillContent;
import com.jyh.sinaweibo.common.StatusDetailModelImp;
import com.jyh.sinaweibo.model.Comment;
import com.jyh.sinaweibo.model.ModelUser;
import com.jyh.sinaweibo.model.ModelWeibo;
import com.jyh.sinaweibo.ui.ImageGalleryActivity;
import com.jyh.sinaweibo.widget.EmojiTextView;
import com.jyh.sinaweibo.widget.FlowLayout;


public class AdapterCommentRecycler extends BaseRecyclerAdapter<Comment> implements BaseRecyclerAdapter.OnLoadingHeaderCallBack
{

    private ModelWeibo modelWeibo;
    private OnDetailButtonClickListener onDetailButtonClickListener;

    public TextView commentView;
    public ImageView mCommentIndicator;

    public TextView retweetView;
    public ImageView mRetweetIndicator;
    private OnWeiboImageClickListener imageClickListener;



    public AdapterCommentRecycler(Context context,ModelWeibo modelWeibo,OnDetailButtonClickListener onDetailButtonClickListener) {
        super(context,BOTH_HEADER_FOOTER);
        this.modelWeibo=modelWeibo;
        setOnLoadingHeaderCallBack(this);
        this.onDetailButtonClickListener=onDetailButtonClickListener;

        initListener();
    }


    private void initListener() {
        imageClickListener = new OnWeiboImageClickListener() {
            //imagePostition：图片序号
            //itemPosition：列表item的索引值
            @Override
            public void onClick(View v, int imagePostition, int itemPosition) {
                //获取微博原图的url
                String[] images = ModelWeibo.Image.getLargeImagePath(modelWeibo.getPic_urls());
                ImageGalleryActivity.show(mContext, images, imagePostition);

            }
        };

    }


    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {

        return new CommentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.mainfragment_weiboitem_detail_commentbar_comment_item, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, final Comment item, int position) {

        ModelUser user = item.getUser();
        String content = item.getText();
        FillContent.fillProfileImg(mContext, user, ((CommentViewHolder) holder).profile_img);
        FillContent.fillWeiBoContent(mContext,content,((CommentViewHolder) holder).content);
        FillContent.setWeiBoName(((CommentViewHolder) holder).profile_name, user);
        FillContent.setWeiBoTime(mContext, ((CommentViewHolder) holder).profile_time, item.getCreated_at());
    }



    public class CommentViewHolder extends RecyclerView.ViewHolder {
        //微博列表的控件
        public ImageView profile_img;
        public ImageView profile_verified;
        public TextView profile_name;
        public TextView profile_time;
        public EmojiTextView content;

        public CommentViewHolder(View v) {
            super(v);
            profile_img = (ImageView) v.findViewById(R.id.profile_img);
            profile_verified = (ImageView) v.findViewById(R.id.profile_verified);
            profile_name = (TextView) v.findViewById(R.id.comment_profile_name);
            profile_time = (TextView) v.findViewById(R.id.comment_profile_time);
            content = (EmojiTextView) v.findViewById(R.id.comment_content);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent) {

        return new WeiboDetailHeadviewViewHolder(LayoutInflater.from(mContext).inflate(R.layout.mainfragment_weiboitem_detail_commentbar_origin_pictext_headview, parent, false));
    }

    @Override
    public void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position) {

        FillContent.fillTitleBar(mContext,modelWeibo,((WeiboDetailHeadviewViewHolder) holder).profile_img,((WeiboDetailHeadviewViewHolder) holder).profile_name, ((WeiboDetailHeadviewViewHolder) holder).profile_time,((WeiboDetailHeadviewViewHolder) holder).weibo_comefrom);
        FillContent.fillWeiBoContent(mContext,modelWeibo.getText(),((WeiboDetailHeadviewViewHolder) holder).weibo_content);
        FillContent.fillWeiBoImgList(mContext,((WeiboDetailHeadviewViewHolder) holder).imageList,modelWeibo,0,imageClickListener);

        FillContent.showButtonBar(View.GONE, ((WeiboDetailHeadviewViewHolder) holder).bottombar_layout);
        FillContent.fillDetailBar(modelWeibo.getComments_count(), modelWeibo.getReposts_count(), modelWeibo.getAttitudes_count(), ((WeiboDetailHeadviewViewHolder) holder).commentView, ((WeiboDetailHeadviewViewHolder) holder).retweetView, ((WeiboDetailHeadviewViewHolder) holder).likeView);
        FillContent.refreshNoneView(mContext, StatusDetailModelImp.COMMENT_PAGE, modelWeibo.getReposts_count(), modelWeibo.getComments_count(), ((WeiboDetailHeadviewViewHolder) holder).mNoneView);

//        boolean isNightMode = (boolean) SharedPreferencesUtil.get(mContext, "setNightMode", false);

        ((WeiboDetailHeadviewViewHolder) holder).likeView.setTextColor(Color.parseColor("#828282"));
    }



    public class WeiboDetailHeadviewViewHolder extends RecyclerView.ViewHolder {
        //微博列表的控件
        public  View mView;
        public LinearLayout origin_weibo_layout;
        public ImageView profile_img;
        public ImageView profile_verified;
        public TextView profile_name;
        public TextView profile_time;
        public TextView weibo_comefrom;
        public EmojiTextView weibo_content;
        public LinearLayout bottombar_layout;
        public FlowLayout imageList;
        public TextView retweetView;
        public TextView commentView;
        public TextView likeView;
        public RelativeLayout mNoneView;
        public ImageView mCommentIndicator;
        public ImageView mRetweetIndicator;
        public ImageView mPopover_arrow;

        public WeiboDetailHeadviewViewHolder(View mView) {
            super(mView);
            origin_weibo_layout = (LinearLayout) mView.findViewById(R.id.weibo_layout);
            profile_img = (ImageView) mView.findViewById(R.id.profile_img);
            profile_verified = (ImageView) mView.findViewById(R.id.profile_verified);
            profile_name = (TextView) mView.findViewById(R.id.profile_name);
            profile_time = (TextView) mView.findViewById(R.id.profile_time);
            weibo_content = (EmojiTextView) mView.findViewById(R.id.weibo_Content);
            weibo_comefrom = (TextView) mView.findViewById(R.id.weiboComeFrom);
            bottombar_layout = (LinearLayout) mView.findViewById(R.id.bottombar_layout);
            imageList = (FlowLayout) mView.findViewById(R.id.fl_image);
            mPopover_arrow = (ImageView) mView.findViewById(R.id.popover_arrow);
            commentView = (TextView) mView.findViewById(R.id.commentBar_comment);
            retweetView = (TextView) mView.findViewById(R.id.commentBar_retweet);
            likeView = (TextView) mView.findViewById(R.id.commentBar_like);
            mNoneView = (RelativeLayout) mView.findViewById(R.id.noneLayout);
            mCommentIndicator = (ImageView) mView.findViewById(R.id.comment_indicator);
            mRetweetIndicator = (ImageView) mView.findViewById(R.id.retweet_indicator);

            AdapterCommentRecycler.this.commentView=commentView;
            AdapterCommentRecycler.this.retweetView=retweetView;
            AdapterCommentRecycler.this.mCommentIndicator=mCommentIndicator;
            AdapterCommentRecycler.this.mRetweetIndicator=mRetweetIndicator;


            //点击转发列表按钮
            retweetView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDetailButtonClickListener.OnRetweet();
                    //FillContent.refreshNoneView(mContext, mType, status.reposts_count, status.comments_count, mNoneView);
                }
            });

            //点击评论列表按钮
            commentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDetailButtonClickListener.OnComment();
                    //FillContent.refreshNoneView(mContext, mType, status.reposts_count, status.comments_count, mNoneView);
                }
            });

        }

    }


    public void commentHighlight() {
        commentView.setTextColor(Color.parseColor("#000000"));
        mCommentIndicator.setVisibility(View.VISIBLE);
        retweetView.setTextColor(Color.parseColor("#828282"));
        mRetweetIndicator.setVisibility(View.INVISIBLE);
    }


    public void repostHighlight() {
        retweetView.setTextColor(Color.parseColor("#000000"));
        mRetweetIndicator.setVisibility(View.VISIBLE);
        commentView.setTextColor(Color.parseColor("#828282"));
        mCommentIndicator.setVisibility(View.INVISIBLE);
    }


    public abstract class OnWeiboImageClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            onClick(v, Integer.parseInt(v.getTag(R.id.sinaweibo_image_position).toString()),
                    Integer.parseInt(v.getTag(R.id.sinaweibo_item_position).toString()));
        }

        public abstract void onClick(View v, int imagePostition, int itemPosition);
    }




}