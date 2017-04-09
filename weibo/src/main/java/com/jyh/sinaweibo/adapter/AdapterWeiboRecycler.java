package com.jyh.sinaweibo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyh.sinaweibo.R;
import com.jyh.sinaweibo.adapter.base.BaseRecyclerAdapter;
import com.jyh.sinaweibo.common.FillContent;
import com.jyh.sinaweibo.common.GetDataStatus;
import com.jyh.sinaweibo.model.ModelWeibo;
import com.jyh.sinaweibo.ui.ImageGalleryActivity;
import com.jyh.sinaweibo.ui.OriginPicTextCommentDetailActivity;
import com.jyh.sinaweibo.widget.EmojiTextView;
import com.jyh.sinaweibo.widget.FlowLayout;

/**
 *
 * 微博适配器
 */
public class AdapterWeiboRecycler extends BaseRecyclerAdapter<ModelWeibo>
{

    private OnWeiboImageClickListener imageClickListener;

    public AdapterWeiboRecycler(Context context) {
        super(context, ONLY_FOOTER);
        initListener();
    }

    private void initListener() {
        imageClickListener = new OnWeiboImageClickListener() {
            //imagePostition：图片序号
            //itemPosition：列表item的索引值
            @Override
            public void onClick(View v, int imagePostition, int itemPosition) {

                //获取原始图片数组
                if(getItemViewType(itemPosition)== GetDataStatus.TYPE_ORINGIN_ITEM)
                {
                    Log.i("test1","TYPE_ORINGIN_ITEM imagePostition="+imagePostition+"  itemPosition="+itemPosition);
                    //获取large型的原始图片
                    String[] images = ModelWeibo.Image.getLargeImagePath(getItem(itemPosition).getPic_urls());
                    ImageGalleryActivity.show(mContext, images, imagePostition);

                }else{
                    Log.i("test1","TYPE_RETWEET_ITEM imagePostition="+imagePostition+"  itemPosition="+itemPosition);
                    String[] images = ModelWeibo.Image.getLargeImagePath(getItem(itemPosition).getRetweeted_status().getPic_urls());
                    ImageGalleryActivity.show(mContext, images, imagePostition);
                }
            }
        };

    }


    /*
    * 用于构建明细的列表界面
    * */
    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {

        if(type==GetDataStatus.TYPE_ORINGIN_ITEM)
            return new OriginViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_weiboitem_original_pictext, parent, false));
        else if(type==GetDataStatus.TYPE_RETWEET_ITEM)
            return new RetweetViewHolder(LayoutInflater.from(mContext).inflate(R.layout.mainfragment_weiboitem_retweet_pictext, parent, false));
        else
            return null;
    }

    /*
    * 数据绑定界面组件
    * */
    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, final ModelWeibo item, int position) {


        if (holder instanceof OriginViewHolder) {
            //如果这条原创微博没有被删除
            if (item.getUser() != null) {
          /*      ((OriginViewHolder) holder).titlebar_layout.setVisibility(View.VISIBLE);
                ((OriginViewHolder) holder).bottombar_layout.setVisibility(View.VISIBLE);
                ((OriginViewHolder) holder).splitLine.setVisibility(View.GONE);
                ((OriginViewHolder) holder).favoritedelete.setVisibility(View.GONE);*/

                //填充发布人头像、发布人的呢称、时间、来源
                FillContent.fillTitleBar(mContext,item,((OriginViewHolder) holder)
                        .profile_img,((OriginViewHolder) holder).profile_name, ((OriginViewHolder) holder)
                        .profile_time,((OriginViewHolder) holder).weibo_comefrom);
                //填充微博内容
                FillContent.fillWeiBoContent(mContext,item.getText(),((OriginViewHolder) holder).weibo_content);
                //转发次数、评论次数、点赞次数
                FillContent.fillButtonBar(mContext,item,((OriginViewHolder) holder)
                        .comment, ((OriginViewHolder) holder).redirect, ((OriginViewHolder) holder).feedlike);
                //填充微博的图片
                FillContent.fillWeiBoImgList(mContext,((OriginViewHolder) holder).imageList,item,position,imageClickListener);

                //微博背景的点击事件
                ((OriginViewHolder) holder).origin_weibo_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, OriginPicTextCommentDetailActivity.class);
                        intent.putExtra("weiboitem", item);
                        mContext.startActivity(intent);
                    }
                });

            }
            //如果这条原创微博被删除
            else {

            }


        } else if (holder instanceof RetweetViewHolder) {
            FillContent.fillTitleBar(mContext,item,((RetweetViewHolder) holder).profile_img,((RetweetViewHolder) holder).profile_name, ((RetweetViewHolder) holder).profile_time,((RetweetViewHolder) holder).weibo_comefrom);
            FillContent.fillRetweetContent(mContext,item,((RetweetViewHolder) holder).origin_nameAndcontent);
            FillContent.fillWeiBoContent(mContext,item.getText(),((RetweetViewHolder) holder).weibo_Content);
            FillContent.fillButtonBar(mContext,item,((RetweetViewHolder) holder).comment, ((RetweetViewHolder) holder).redirect, ((RetweetViewHolder) holder).feedlike);
            FillContent.fillWeiBoImgList(mContext,((RetweetViewHolder) holder).retweet_imageList,item.getRetweeted_status(),position,imageClickListener);

            //微博背景的点击事件
            ((RetweetViewHolder) holder).retweet_weibo_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               /*     Intent intent = new Intent(mContext, RetweetPicTextCommentDetailActivity.class);
                    intent.putExtra("weiboitem", mDatas.get(position));
                    mContext.startActivity(intent);*/
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {

        int type=super.getItemViewType(position);

        //列表默认明细类型
        if(type==VIEW_TYPE_NORMAL)
        {
            ModelWeibo modelWeibo=getItem(position);
            //如果Retweeted_status不为空，它是属于转发微博类型，否则属于原创微博类型
            if (modelWeibo.getRetweeted_status() != null) {
                return GetDataStatus.TYPE_RETWEET_ITEM;
            } else {
                return GetDataStatus.TYPE_ORINGIN_ITEM;
            }
        }else
            return type;

    }


    public static class OriginViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout origin_weibo_layout;
        public LinearLayout titlebar_layout;
        public ImageView profile_img;
        public ImageView profile_verified;
        public ImageView popover_arrow;
        public TextView profile_name;
        public TextView profile_time;
        public TextView weibo_comefrom;
        public EmojiTextView weibo_content;
        public TextView redirect;
        public TextView comment;
        public TextView feedlike;
        public FlowLayout imageList;
        public TextView favoritedelete;
        public ImageView splitLine;
        public LinearLayout bottombar_layout;
        public LinearLayout bottombar_retweet;
        public LinearLayout bottombar_comment;
        public LinearLayout bottombar_attitude;

        public OriginViewHolder(View v) {
            super(v);
            origin_weibo_layout = (LinearLayout) v.findViewById(R.id.weibo_layout);
            titlebar_layout = (LinearLayout) v.findViewById(R.id.titlebar_layout);
            profile_img = (ImageView) v.findViewById(R.id.profile_img);
            profile_verified = (ImageView) v.findViewById(R.id.profile_verified);
            popover_arrow = (ImageView) v.findViewById(R.id.popover_arrow);
            profile_name = (TextView) v.findViewById(R.id.profile_name);
            profile_time = (TextView) v.findViewById(R.id.profile_time);
            weibo_content = (EmojiTextView) v.findViewById(R.id.weibo_Content);
            weibo_comefrom = (TextView) v.findViewById(R.id.weiboComeFrom);
            redirect = (TextView) v.findViewById(R.id.redirect);
            comment = (TextView) v.findViewById(R.id.comment);
            feedlike = (TextView) v.findViewById(R.id.feedlike);
            splitLine = (ImageView) v.findViewById(R.id.splitLine);
            imageList = (FlowLayout) v.findViewById(R.id.fl_image);
            favoritedelete = (TextView) v.findViewById(R.id.favorities_delete);
            bottombar_layout = (LinearLayout) v.findViewById(R.id.bottombar_layout);
            bottombar_retweet = (LinearLayout) v.findViewById(R.id.bottombar_retweet);
            bottombar_comment = (LinearLayout) v.findViewById(R.id.bottombar_comment);
            bottombar_attitude = (LinearLayout) v.findViewById(R.id.bottombar_attitude);
        }
    }

    public static class RetweetViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout retweet_weibo_layout;
        public ImageView profile_img;
        public ImageView profile_verified;
        public ImageView popover_arrow;
        public TextView profile_name;
        public TextView profile_time;
        public TextView weibo_comefrom;
        public EmojiTextView weibo_Content;
        public TextView redirect;
        public TextView comment;
        public TextView feedlike;
        public EmojiTextView origin_nameAndcontent;
        public FlowLayout retweet_imageList;
        public LinearLayout bottombar_layout;
        public LinearLayout bottombar_retweet;
        public LinearLayout bottombar_comment;
        public LinearLayout bottombar_attitude;
        public LinearLayout retweetStatus_layout;


        public RetweetViewHolder(View v) {
            super(v);
            retweet_weibo_layout = (LinearLayout) v.findViewById(R.id.retweet_weibo_layout);
            profile_img = (ImageView) v.findViewById(R.id.profile_img);
            profile_verified = (ImageView) v.findViewById(R.id.profile_verified);
            popover_arrow = (ImageView) v.findViewById(R.id.popover_arrow);
            profile_name = (TextView) v.findViewById(R.id.profile_name);
            profile_time = (TextView) v.findViewById(R.id.profile_time);
            weibo_Content = (EmojiTextView) v.findViewById(R.id.weibo_Content);
            weibo_comefrom = (TextView) v.findViewById(R.id.weiboComeFrom);
            redirect = (TextView) v.findViewById(R.id.redirect);
            comment = (TextView) v.findViewById(R.id.comment);
            feedlike = (TextView) v.findViewById(R.id.feedlike);
            origin_nameAndcontent = (EmojiTextView) v.findViewById(R.id.origin_nameAndcontent);
            retweet_imageList = (FlowLayout) v.findViewById(R.id.fl_image);
            bottombar_layout = (LinearLayout) v.findViewById(R.id.bottombar_layout);
            bottombar_retweet = (LinearLayout) v.findViewById(R.id.bottombar_retweet);
            bottombar_comment = (LinearLayout) v.findViewById(R.id.bottombar_comment);
            bottombar_attitude = (LinearLayout) v.findViewById(R.id.bottombar_attitude);
            retweetStatus_layout = (LinearLayout) v.findViewById(R.id.retweetStatus_layout);
        }
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
