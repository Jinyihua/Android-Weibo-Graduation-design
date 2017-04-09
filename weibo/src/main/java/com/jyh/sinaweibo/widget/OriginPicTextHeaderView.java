package com.jyh.sinaweibo.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyh.sinaweibo.R;
import com.jyh.sinaweibo.common.FillContent;
import com.jyh.sinaweibo.model.ModelWeibo;


/**
 * Created by cheng on 2016/12/23.
 */
public class OriginPicTextHeaderView extends LinearLayout {
    private View mView;
    private LinearLayout origin_weibo_layout;
    private ImageView profile_img;
    private ImageView profile_verified;
    private TextView profile_name;
    private TextView profile_time;
    private TextView weibo_comefrom;
    private EmojiTextView weibo_content;
    private LinearLayout bottombar_layout;
    private FlowLayout imageList;
    private TextView retweetView;
    private TextView commentView;
    private TextView likeView;
    private RelativeLayout mNoneView;
    private Context mContext;
    private ImageView mCommentIndicator;
    private ImageView mRetweetIndicator;
    private ImageView mPopover_arrow;

    private boolean isNightMode=false;
//    private OnDetailButtonClickListener onDetailButtonClickListener;
//    private int mType = StatusDetailModelImp.COMMENT_PAGE;

    public OriginPicTextHeaderView(Context context, ModelWeibo modelWeibo, int type) {
        super(context);
//        mType = type;
        init(context, modelWeibo);
        commentHighlight();

        Log.i("test","OriginPicTextHeaderView");
        /*switch (mType) {
            case StatusDetailModelImp.COMMENT_PAGE:
                commentHighlight();
                break;
            case StatusDetailModelImp.REPOST_PAGE:
                repostHighlight();
                break;
        }*/
    }

/*    public void setOnDetailButtonClickListener(OnDetailButtonClickListener onDetailButtonClickListener) {
        this.onDetailButtonClickListener = onDetailButtonClickListener;
    }*/

    public void init(Context context, ModelWeibo modelWeibo) {
        mContext = context;
        mView = inflate(context, R.layout.mainfragment_weiboitem_detail_commentbar_origin_pictext_headview, this);
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
        mCommentIndicator = (ImageView) findViewById(R.id.comment_indicator);
        mRetweetIndicator = (ImageView) findViewById(R.id.retweet_indicator);
        initWeiBoContent(context, modelWeibo);

        Log.i("test","initWeiBoContent  modelWeibo id="+modelWeibo.getId());
    }

    private void initWeiBoContent(Context context, final ModelWeibo modelWeibo) {

        FillContent.fillTitleBar(context,modelWeibo,profile_img,profile_name, profile_time,weibo_comefrom);
        FillContent.fillWeiBoContent(context,modelWeibo.getText(),weibo_content);
        FillContent.fillWeiBoImgList(context,imageList,modelWeibo,0,null);

        FillContent.showButtonBar(View.GONE, bottombar_layout);
        FillContent.fillDetailBar(modelWeibo.getComments_count(), modelWeibo.getReposts_count(), modelWeibo.getAttitudes_count(), commentView, retweetView, likeView);
//        FillContent.refreshNoneView(mContext, mType, status.reposts_count, status.comments_count, mNoneView);

//        boolean isNightMode = (boolean) SharedPreferencesUtil.get(mContext, "setNightMode", false);
        if (isNightMode) {
            likeView.setTextColor(Color.parseColor("#45484a"));
        }else {
            likeView.setTextColor(Color.parseColor("#828282"));
        }

        mPopover_arrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                DetailWeiBoArrowWindow detailWeiBoArrowWindow = new DetailWeiBoArrowWindow(mContext, status);
//                detailWeiBoArrowWindow.showAtLocation(mView, Gravity.CENTER, 0, 0);
/*
                ArrowDialog arrowDialog = new TimelineArrowWindow.Builder(mContext, status)
                        .setCanceledOnTouchOutside(true)
                        .setCancelable(true)
                        .create();
                int width = ScreenUtil.getScreenWidth(mContext) - DensityUtil.dp2px(mContext, 80);
                arrowDialog.show();
                arrowDialog.getWindow().setLayout(width, (ViewGroup.LayoutParams.WRAP_CONTENT));*/

            }
        });

        retweetView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                repostHighlight();
//                onDetailButtonClickListener.OnRetweet();
                //FillContent.refreshNoneView(mContext, mType, status.reposts_count, status.comments_count, mNoneView);
            }
        });

        commentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                commentHighlight();
//                onDetailButtonClickListener.OnComment();
                //FillContent.refreshNoneView(mContext, mType, status.reposts_count, status.comments_count, mNoneView);
            }
        });

    }

    public void refreshDetailBar(int comments_count, int reposts_count, int attitudes_count) {
/*        FillContent.fillDetailBar(comments_count, reposts_count, attitudes_count, commentView, retweetView, likeView);
        FillContent.refreshNoneView(mContext, mType, reposts_count, comments_count, mNoneView);*/
    }

    public void commentHighlight() {
//        boolean isNightMode = (boolean) SharedPreferencesUtil.get(mContext, "setNightMode", false);
        if (!isNightMode) {
            commentView.setTextColor(Color.parseColor("#000000"));
            mCommentIndicator.setVisibility(View.VISIBLE);
            retweetView.setTextColor(Color.parseColor("#828282"));
            mRetweetIndicator.setVisibility(View.INVISIBLE);
        } else {
            commentView.setTextColor(Color.parseColor("#888888"));
            mCommentIndicator.setVisibility(View.VISIBLE);
            retweetView.setTextColor(Color.parseColor("#45484a"));
            mRetweetIndicator.setVisibility(View.INVISIBLE);
        }


    }

    public void repostHighlight() {
//        boolean isNightMode = (boolean) SharedPreferencesUtil.get(mContext, "setNightMode", false);
        if (!isNightMode) {
            retweetView.setTextColor(Color.parseColor("#000000"));
            mRetweetIndicator.setVisibility(View.VISIBLE);
            commentView.setTextColor(Color.parseColor("#828282"));
            mCommentIndicator.setVisibility(View.INVISIBLE);
        } else {
            retweetView.setTextColor(Color.parseColor("#888888"));
            mRetweetIndicator.setVisibility(View.VISIBLE);
            commentView.setTextColor(Color.parseColor("#45484a"));
            mCommentIndicator.setVisibility(View.INVISIBLE);
        }
    }

}
