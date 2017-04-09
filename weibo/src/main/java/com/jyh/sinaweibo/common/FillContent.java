package com.jyh.sinaweibo.common;

import android.content.Context;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyh.sinaweibo.R;
import com.jyh.sinaweibo.emoji.InputHelper;
import com.jyh.sinaweibo.model.ModelUser;
import com.jyh.sinaweibo.model.ModelWeibo;
import com.jyh.sinaweibo.model.PicUrls;
import com.jyh.sinaweibo.util.AssimilateUtils;
import com.jyh.sinaweibo.util.DateUtils;
import com.jyh.sinaweibo.util.GlideUtils;
import com.jyh.sinaweibo.util.TimeUtils;
import com.jyh.sinaweibo.widget.FlowLayout;

import net.qiujuer.genius.ui.Ui;

import java.util.ArrayList;
import java.util.Date;


public class FillContent {


    /**
     * 设置头像的认证icon，记住要手动刷新icon，不然icon会被recycleriview重用，导致显示出错
     *
     * @param user
     * @param profile_img
     */
    public static void fillProfileImg(final Context context, final ModelUser user, final ImageView profile_img) {

        GlideUtils.into(context, user.getProfile_image_url(), profile_img);
    }

    /**
     * 填充顶部微博用户信息数据
     *
     * @param context
     * @param profile_img
     * @param profile_name
     * @param profile_time
     * @param weibo_comefrom
     */
    public static void fillTitleBar(Context context, ModelWeibo modelWeibo, ImageView profile_img, TextView profile_name, TextView profile_time, TextView weibo_comefrom) {
        fillProfileImg(context, modelWeibo.getUser(), profile_img);
        setWeiBoName(profile_name, modelWeibo.getUser());
        setWeiBoTime(context, profile_time, modelWeibo.getCreated_at());
        setWeiBoComeFrom(context, weibo_comefrom, modelWeibo);
    }


    public static void setWeiBoName(TextView textView, ModelUser user) {
        textView.setText(user.getName());
    }


    public static void setWeiBoTime(Context context, TextView textView, String createAt) {
        Date data = DateUtils.parseDate(createAt, DateUtils.WeiBo_ITEM_DATE_FORMAT);
        TimeUtils timeUtils = TimeUtils.instance(context);
        textView.setText(timeUtils.buildTimeString(data.getTime()) + "   ");

    }


    public static void setWeiBoComeFrom(Context context, TextView textView, ModelWeibo modelWeibo) {
        if (modelWeibo.getSource() == null) {
            textView.setText("");
            return;
        }
        if (!TextUtils.isEmpty(modelWeibo.getSource())) {
            Spannable itemSourceSpannable = AssimilateUtils.assimilateTagAndAtUser(context, modelWeibo.getSource());
            textView.setText(itemSourceSpannable);

        } else {
            textView.setText("");
        }
    }


    /**
     * 填充原创微博文字内容
     */
    public static void fillWeiBoContent(Context context, String content, TextView tv_content) {

        if (!TextUtils.isEmpty(content)) {
            content = content.replaceAll("[\n\\s]+", " ");
        }

        tv_content.setText(content);
        //解释超链接
        Spannable spannable = AssimilateUtils.assimilateOnlyLink(context, content);
        //解释表情
        spannable = InputHelper.displayEmoji(context.getResources(), spannable);

        tv_content.setText(spannable);
        tv_content.setMovementMethod(LinkMovementMethod.getInstance());
        tv_content.setFocusable(false);
        tv_content.setLongClickable(false);
    }


    /**
     * 填充微博转发，评论，赞的数量
     */
    public static void fillButtonBar(final Context context, final ModelWeibo modelWeibo, TextView comment, TextView redirect, TextView feedlike) {

        if (modelWeibo.getComments_count() != 0) {
            comment.setText(modelWeibo.getComments_count() + "");
        } else {
            comment.setText("评论");
        }

        if (modelWeibo.getReposts_count() != 0) {
            redirect.setText(modelWeibo.getReposts_count() + "");
        } else {
            redirect.setText("转发");
        }


        if (modelWeibo.getAttitudes_count() != 0) {
            feedlike.setText(modelWeibo.getAttitudes_count() + "");
        } else {
            feedlike.setText("赞");
        }

//        fillButtonBar(context, status, bottombar_retweet, bottombar_comment, bottombar_attitude);
    }


    /**
     * 填充微博图片列表,包括原创微博和转发微博中的图片都可以使用
     */
    public static void fillWeiBoImgList(Context context, FlowLayout flowLayout, ModelWeibo modelWeibo, int position, View.OnClickListener imageClickListener) {

        ArrayList<PicUrls> picUrlses = modelWeibo.getPic_urls();
        flowLayout.removeAllViews();
        if (picUrlses != null && picUrlses.size() > 0) {
            flowLayout.setVisibility(View.VISIBLE);
            //设置图片显示的高宽
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) Ui.dipToPx(context.getResources(), 90)
                    , (int) Ui.dipToPx(context.getResources(), 90));
            for (int i = 0; i < picUrlses.size(); i++) {
                PicUrls picUrl = picUrlses.get(i);
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(layoutParams);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                //图片的序号
                imageView.setTag(R.id.sinaweibo_image_position, i);
                //item对应的索引值
                imageView.setTag(R.id.sinaweibo_item_position, position);
                //针对图片点击事件的监听
                imageView.setOnClickListener(imageClickListener);

                String thumbnailPath = picUrl.getThumbnail_pic();
                //获取中等图的url地址
                String bmiddlePath = thumbnailPath.replace("thumbnail", "bmiddle")
                        .toString();

                GlideUtils.initImageIcon(R.color.grey_200);
                GlideUtils.into(context, bmiddlePath, imageView);

                flowLayout.addView(imageView);
            }


        } else {
            flowLayout.setVisibility(View.GONE);
        }
    }


    /**
     * 填充转发微博文字内容
     */
    public static void fillRetweetContent(Context context, ModelWeibo modelWeibo, TextView origin_nameAndcontent) {


        if (modelWeibo.retweeted_status.getUser() != null) {
            StringBuffer retweetcontent_buffer = new StringBuffer();
            retweetcontent_buffer.setLength(0);
            retweetcontent_buffer.append("@");
            retweetcontent_buffer.append(modelWeibo.retweeted_status.getUser().getName() + " :  ");
            retweetcontent_buffer.append(modelWeibo.retweeted_status.getText());

            String content = retweetcontent_buffer.toString();
            //解释超链接
            Spannable spannable = AssimilateUtils.assimilateOnlyLink(context, content);
            //解释表情
            spannable = InputHelper.displayEmoji(context.getResources(), spannable);

            origin_nameAndcontent.setText(spannable);
            origin_nameAndcontent.setMovementMethod(LinkMovementMethod.getInstance());
            origin_nameAndcontent.setFocusable(false);
            origin_nameAndcontent.setLongClickable(false);


        } else {
            origin_nameAndcontent.setText("抱歉，此微博已被作者删除。查看帮助：#网页链接#");
        }
    }


    /**
     * 决定是否隐藏转发，评论，赞的底部的bar，进入weibodetail的时候隐藏他
     *
     * @param visible
     * @param layout
     */
    public static void showButtonBar(int visible, LinearLayout layout) {
        if (visible == View.VISIBLE) {
            layout.setVisibility(View.VISIBLE);
        } else if (visible == View.GONE) {
            layout.setVisibility(View.GONE);
        } else if (visible == View.INVISIBLE) {
            layout.setVisibility(View.INVISIBLE);
        }
    }


    public static void fillDetailBar(int comments_count, int reposts_count, int attitudes_count, TextView comment, TextView redirect, TextView feedlike) {
        comment.setText("评论 " + comments_count);
        redirect.setText("转发 " + reposts_count);
        feedlike.setText("赞 " + attitudes_count);

    }


    public static void refreshNoneView(Context context, int type, int repostss_count, int comments_count, View noneView) {
        TextView textView = (TextView) noneView.findViewById(R.id.tv_normal_refresh_footer_status);

        switch (type) {
            case StatusDetailModelImp.COMMENT_PAGE:
                if (comments_count > 0) {
                    noneView.setVisibility(View.GONE);
                } else if (comments_count == 0) {
                    noneView.setVisibility(View.VISIBLE);
                    textView.setText("还没有人评论");
                }
                break;

            case StatusDetailModelImp.REPOST_PAGE:
                if (repostss_count > 0) {
                    noneView.setVisibility(View.GONE);
                } else if (repostss_count == 0) {
                    noneView.setVisibility(View.VISIBLE);
                    textView.setText("还没有人转发");
                }
                break;
        }


    }


}
