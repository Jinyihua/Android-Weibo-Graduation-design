package com.jyh.sinaweibo.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyh.sinaweibo.R;
import com.jyh.sinaweibo.adapter.base.BaseListAdapter;
import com.jyh.sinaweibo.adapter.base.ViewHolder;
import com.jyh.sinaweibo.common.FillContent;
import com.jyh.sinaweibo.common.GetDataStatus;
import com.jyh.sinaweibo.model.ModelWeibo;
import com.jyh.sinaweibo.ui.ImageGalleryActivity;
import com.jyh.sinaweibo.widget.FlowLayout;


public class AdapterWeibo extends BaseListAdapter<ModelWeibo> {


    private Callback callback;
    private OnWeiboImageClickListener imageClickListener;

    public AdapterWeibo(Callback callback) {
        super(callback);
        initListener();
    }

    private void initListener() {
        imageClickListener = new OnWeiboImageClickListener() {
            @Override
            public void onClick(View v, int imagePostition, int itemPosition) {

                //获取原始图片数组
                if (getItemViewType(itemPosition) == GetDataStatus.TYPE_ORINGIN_ITEM) {
                    String[] images = ModelWeibo.Image.getLargeImagePath(getItem(itemPosition).getPic_urls());
                    ImageGalleryActivity.show(mCallback.getContext(), images, imagePostition);

                } else {
                    String[] images = ModelWeibo.Image.getLargeImagePath(getItem(itemPosition).getRetweeted_status().getPic_urls());
                    ImageGalleryActivity.show(mCallback.getContext(), images, imagePostition);
                }
            }
        };

    }

    /*
    * 将数据绑定组件
    * */
    @SuppressWarnings("deprecation")
    @Override
    protected void convert(ViewHolder vh, ModelWeibo item, int position) {

        if (item.getRetweeted_status() == null) {
            FillContent.fillTitleBar(mCallback.getContext(), item, (ImageView) vh.getView(R.id.profile_img), (TextView) vh.getView(R.id.profile_name), (TextView) vh.getView(R.id.profile_time), (TextView) vh.getView(R.id.weiboComeFrom));
            FillContent.fillWeiBoContent(mCallback.getContext(), item.getText(), (TextView) vh.getView(R.id.weibo_Content));
            FillContent.fillButtonBar(mCallback.getContext(), item, (TextView) vh.getView(R.id.comment), (TextView) vh.getView(R.id.redirect), (TextView) vh.getView(R.id.feedlike));
            FillContent.fillWeiBoImgList(mCallback.getContext(), (FlowLayout) vh.getView(R.id.fl_image), item, position, imageClickListener);
        } else {
            FillContent.fillTitleBar(mCallback.getContext(), item, (ImageView) vh.getView(R.id.profile_img), (TextView) vh.getView(R.id.profile_name), (TextView) vh.getView(R.id.profile_time), (TextView) vh.getView(R.id.weiboComeFrom));
            FillContent.fillRetweetContent(mCallback.getContext(), item, (TextView) vh.getView(R.id.origin_nameAndcontent));
            FillContent.fillWeiBoContent(mCallback.getContext(), item.getText(), (TextView) vh.getView(R.id.weibo_Content));
            FillContent.fillButtonBar(mCallback.getContext(), item.getRetweeted_status(), (TextView) vh.getView(R.id.comment), (TextView) vh.getView(R.id.redirect), (TextView) vh.getView(R.id.feedlike));
            FillContent.fillWeiBoImgList(mCallback.getContext(), (FlowLayout) vh.getView(R.id.fl_image), item.getRetweeted_status(), position, imageClickListener);

        }
    }


    /*
    * 设置item的布局
    * */
    @Override
    protected int getLayoutId(int position, ModelWeibo item) {

        if (getItemViewType(position) == GetDataStatus.TYPE_ORINGIN_ITEM) {
            return R.layout.home_weiboitem_original_pictext;
        } else {
            return R.layout.mainfragment_weiboitem_retweet_pictext;
        }
    }


    @Override
    public int getItemViewType(int position) {

        ModelWeibo modelWeibo = getItem(position);

        if (modelWeibo.getRetweeted_status() != null)
            return GetDataStatus.TYPE_RETWEET_ITEM;
        else
            return GetDataStatus.TYPE_ORINGIN_ITEM;
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