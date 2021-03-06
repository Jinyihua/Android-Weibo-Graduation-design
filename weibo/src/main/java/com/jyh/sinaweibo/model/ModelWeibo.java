package com.jyh.sinaweibo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2016/9/27.
 */
public class ModelWeibo  extends Entity
{

    /** 微博创建时间 */
    private String created_at;
    /** 微博ID */
    private String id;
    /** 微博MID */
    private String mid;
    /** 字符串型的微博ID */
    private String idstr;
    /** 微博信息内容 */
    private String text;
    /** 微博来源 */
    private String source;
    /** 是否已收藏，true：是，false：否 */
    private boolean favorited;
    /** 是否被截断，true：是，false：否 */
    private boolean truncated;
    /** （暂未支持）回复ID */
    private String in_reply_to_status_id;
    /** （暂未支持）回复人UID */
    private String in_reply_to_user_id;
    /** （暂未支持）回复人昵称 */
    private String in_reply_to_screen_name;
    /** 缩略图片地址（小图），没有时不返回此字段 */
    private String thumbnail_pic;
    /** 中等尺寸图片地址（中图），没有时不返回此字段 */
    private String bmiddle_pic;
    /** 原始图片地址（原图），没有时不返回此字段 */
    private String original_pic;
    /** 地理信息字段 */
    // private Geo geo;
    /** 微博作者的用户信息字段 */
    private ModelUser user;
    /**
     * 被转发的原微博信息字段，当该微博为转发微博时返回
     */
    public ModelWeibo retweeted_status;
    /** 转发数 */
    private int reposts_count;
    /** 评论数 */
    private int comments_count;
    /** 表态数 */
    private int attitudes_count;
    /** 暂未支持 */
    private int mlevel;

    private ArrayList<String> pic_ids;

    /**
     * 微博的可见性及指定可见分组信息。该 object 中 type 取值， 0：普通微博，1：私密微博，3：指定分组微博，4：密友微博；
     * list_id为分组的组号
     */
    // private Visible visible;
    /** 微博配图地址。多图时返回多图链接。无配图返回"[]" */
    private ArrayList<PicUrls> pic_urls;
    private String error;
    //错误编码
    private int error_code;


    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public String getIn_reply_to_status_id() {
        return in_reply_to_status_id;
    }

    public void setIn_reply_to_status_id(String in_reply_to_status_id) {
        this.in_reply_to_status_id = in_reply_to_status_id;
    }

    public String getIn_reply_to_user_id() {
        return in_reply_to_user_id;
    }

    public void setIn_reply_to_user_id(String in_reply_to_user_id) {
        this.in_reply_to_user_id = in_reply_to_user_id;
    }

    public String getIn_reply_to_screen_name() {
        return in_reply_to_screen_name;
    }

    public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
        this.in_reply_to_screen_name = in_reply_to_screen_name;
    }

    public String getThumbnail_pic() {
        return thumbnail_pic;
    }

    public void setThumbnail_pic(String thumbnail_pic) {
        this.thumbnail_pic = thumbnail_pic;
    }

    public String getBmiddle_pic() {
        return bmiddle_pic;
    }

    public void setBmiddle_pic(String bmiddle_pic) {
        this.bmiddle_pic = bmiddle_pic;
    }

    public String getOriginal_pic() {
        return original_pic;
    }

    public void setOriginal_pic(String original_pic) {
        this.original_pic = original_pic;
    }

    public ModelUser getUser() {
        return user;
    }

    public void setUser(ModelUser user) {
        this.user = user;
    }

    public ModelWeibo getRetweeted_status() {
        return retweeted_status;
    }

    public void setRetweeted_status(ModelWeibo retweeted_status) {
        this.retweeted_status = retweeted_status;
    }

    public int getReposts_count() {
        return reposts_count;
    }

    public void setReposts_count(int reposts_count) {
        this.reposts_count = reposts_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getAttitudes_count() {
        return attitudes_count;
    }

    public void setAttitudes_count(int attitudes_count) {
        this.attitudes_count = attitudes_count;
    }

    public int getMlevel() {
        return mlevel;
    }
    public void setMlevel(int mlevel) {
        this.mlevel = mlevel;
    }


    public ArrayList<PicUrls> getPic_urls() {
        return pic_urls;
    }

    public void setPic_urls(ArrayList<PicUrls> pic_urls) {
        this.pic_urls = pic_urls;
    }


    public ArrayList<String> getPic_ids() {
        return pic_ids;
    }

    public void setPic_ids(ArrayList<String> pic_ids) {
        this.pic_ids = pic_ids;
    }


    public static class Image implements Serializable {

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        private String href;

        public static String[] getThumbnailImagePath(List<PicUrls> picUrlses) {
            if (picUrlses == null || picUrlses.size() == 0)
                return null;
            String[] paths = new String[picUrlses.size()];

            for (int i = 0; i < picUrlses.size(); i++) {
                paths[i] = picUrlses.get(i).getThumbnail_pic();
            }
            return paths;
        }


        public static String[] getLargeImagePath(List<PicUrls> picUrlses) {
            if (picUrlses == null || picUrlses.size() == 0)
                return null;
            String[] paths = new String[picUrlses.size()];

            for (int i = 0; i < picUrlses.size(); i++) {
                String originalPath = picUrlses.get(i).getThumbnail_pic().replace("thumbnail", "large")
                        .toString();
                paths[i] = originalPath;
            }
            return paths;
        }

    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
