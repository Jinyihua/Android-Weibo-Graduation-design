/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jyh.sinaweibo.emoji;


import com.jyh.sinaweibo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Emoji在手机上的显示规则
 *
 * @author kymjs (http://www.kymjs.com)
 */
public enum DisplayRules {
    // 注意：value不能从0开始，因为0会被库自动设置为删除按钮
    // int type, int value, int resId, String cls
    KJEMOJI0(0, 1, R.mipmap.smiley_0, "[微笑]", "[0]"),
    KJEMOJI1(0, 1, R.mipmap.smiley_1, "[撇嘴]", "[1]"),
    KJEMOJI2(0, 1, R.mipmap.smiley_2, "[色]", "[2]"),
    KJEMOJI3(0, 1, R.mipmap.smiley_3, "[发呆]", "[3]"),
    KJEMOJI4(0, 1, R.mipmap.smiley_4, "[得意]", "[4]"),
    KJEMOJI5(0, 1, R.mipmap.smiley_5, "[流泪]", "[5]"),
    KJEMOJI6(0, 1, R.mipmap.smiley_6, "[害羞]", "[6]"),
    KJEMOJI7(0, 1, R.mipmap.smiley_7, "[闭嘴]", "[7]"),
    KJEMOJI8(0, 1, R.mipmap.smiley_8, "[睡]", "[8]"),
    KJEMOJI9(0, 1, R.mipmap.smiley_9, "[大哭]", "[9]"),
    KJEMOJI10(0, 1, R.mipmap.smiley_10, "[尴尬]", "[10]"),
    KJEMOJI11(0, 1, R.mipmap.smiley_11, "[发怒]", "[11]"),
    KJEMOJI12(0, 1, R.mipmap.smiley_12, "[调皮]", "[12]"),
    KJEMOJI13(0, 1, R.mipmap.smiley_13, "[呲牙]", "[13]"),
    KJEMOJI14(0, 1, R.mipmap.smiley_14, "[惊讶]", "[14]"),
    KJEMOJI15(0, 1, R.mipmap.smiley_15, "[难过]", "[15]"),
    KJEMOJI16(0, 1, R.mipmap.smiley_16, "[酷]", "[16]"),
    KJEMOJI17(0, 1, R.mipmap.smiley_17, "[冷汗]", "[17]"),
    KJEMOJI18(0, 1, R.mipmap.smiley_18, "[抓狂]", "[18]"),
    KJEMOJI19(0, 1, R.mipmap.smiley_19, "[吐]", "[19]"),
    KJEMOJI20(0, 1, R.mipmap.smiley_20, "[偷笑]", "[20]"),
    KJEMOJI21(0, 1, R.mipmap.smiley_21, "[可爱]", "[21]"),
    KJEMOJI22(0, 1, R.mipmap.smiley_22, "[白眼]", "[22]"),
    KJEMOJI23(0, 1, R.mipmap.smiley_23, "[傲慢]", "[23]"),
    KJEMOJI24(0, 1, R.mipmap.smiley_24, "[饥饿]", "[24]"),
    KJEMOJI25(0, 1, R.mipmap.smiley_25, "[困]", "[25]"),
    KJEMOJI26(0, 1, R.mipmap.smiley_26, "[惊恐]", "[26]"),
    KJEMOJI27(0, 1, R.mipmap.smiley_27, "[流汗]", "[27]"),
    KJEMOJI28(0, 1, R.mipmap.smiley_28, "[憨笑]", "[28]"),
    KJEMOJI29(0, 1, R.mipmap.smiley_29, "[大兵]", "[29]"),
    KJEMOJI30(0, 1, R.mipmap.smiley_30, "[奋斗]", "[30]"),
    KJEMOJI31(0, 1, R.mipmap.smiley_31, "[咒骂]", "[31]"),
    KJEMOJI32(0, 1, R.mipmap.smiley_32, "[疑问]", "[32]"),
    KJEMOJI33(0, 1, R.mipmap.smiley_33, "[嘘]", "[33]"),
    KJEMOJI34(0, 1, R.mipmap.smiley_34, "[晕]", "[34]"),
    KJEMOJI35(0, 1, R.mipmap.smiley_35, "[折磨]", "[35]"),
    KJEMOJI36(0, 1, R.mipmap.smiley_36, "[衰]", "[36]"),
    KJEMOJI37(0, 1, R.mipmap.smiley_37, "[骷髅]", "[37]"),
    KJEMOJI38(0, 1, R.mipmap.smiley_38, "[敲打]", "[38]"),
    KJEMOJI39(0, 1, R.mipmap.smiley_39, "[再见]", "[39]"),
    KJEMOJI40(0, 1, R.mipmap.smiley_40, "[擦汗]", "[40]"),
    KJEMOJI41(0, 1, R.mipmap.smiley_41, "[抠鼻]", "[41]"),
    KJEMOJI42(0, 1, R.mipmap.smiley_42, "[鼓掌]", "[42]"),
    KJEMOJI43(0, 1, R.mipmap.smiley_43, "[糗大了]", "[43]"),
    KJEMOJI44(0, 1, R.mipmap.smiley_44, "[坏笑]", "[44]"),
    KJEMOJI45(0, 1, R.mipmap.smiley_45, "[左哼哼]", "[45]"),
    KJEMOJI46(0, 1, R.mipmap.smiley_46, "[右哼哼]", "[46]"),
    KJEMOJI47(0, 1, R.mipmap.smiley_47, "[哈欠]", "[47]"),
    KJEMOJI48(0, 1, R.mipmap.smiley_48, "[鄙视]", "[48]"),
    KJEMOJI49(0, 1, R.mipmap.smiley_49, "[委屈]", "[49]"),
    KJEMOJI50(0, 1, R.mipmap.smiley_50, "[快哭了]", "[50]"),
    KJEMOJI51(0, 1, R.mipmap.smiley_51, "[阴险]", "[51]"),
    KJEMOJI52(0, 1, R.mipmap.smiley_52, "[亲亲]", "[52]"),
    KJEMOJI53(0, 1, R.mipmap.smiley_53, "[吓]", "[53]"),
    KJEMOJI54(0, 1, R.mipmap.smiley_54, "[可怜]", "[54]"),
    KJEMOJI55(0, 1, R.mipmap.smiley_55, "[菜刀]", "[55]"),
    KJEMOJI56(0, 1, R.mipmap.smiley_56, "[西瓜]", "[56]"),
    KJEMOJI57(0, 1, R.mipmap.smiley_57, "[啤酒]", "[57]"),
    KJEMOJI58(0, 1, R.mipmap.smiley_58, "[篮球]", "[58]"),
    KJEMOJI59(0, 1, R.mipmap.smiley_59, "[乒乓]", "[59]"),
    KJEMOJI60(0, 1, R.mipmap.smiley_60, "[咖啡]", "[60]"),
    KJEMOJI61(0, 1, R.mipmap.smiley_61, "[饭]", "[61]"),
    KJEMOJI62(0, 1, R.mipmap.smiley_62, "[猪头]", "[62]"),
    KJEMOJI63(0, 1, R.mipmap.smiley_63, "[玫瑰]", "[63]"),
    KJEMOJI64(0, 1, R.mipmap.smiley_64, "[凋谢]", "[64]"),
    KJEMOJI65(0, 1, R.mipmap.smiley_65, "[嘴唇]", "[65]"),
    KJEMOJI66(0, 1, R.mipmap.smiley_66, "[爱心]", "[66]"),
    KJEMOJI67(0, 1, R.mipmap.smiley_67, "[心碎]", "[67]"),
    KJEMOJI68(0, 1, R.mipmap.smiley_68, "[蛋糕]", "[68]"),
    KJEMOJI69(0, 1, R.mipmap.smiley_69, "[闪电]", "[69]"),
    KJEMOJI70(0, 1, R.mipmap.smiley_70, "[炸弹]", "[70]"),
    KJEMOJI71(0, 1, R.mipmap.smiley_71, "[刀]", "[71]"),
    KJEMOJI72(0, 1, R.mipmap.smiley_72, "[足球]", "[72]"),
    KJEMOJI73(0, 1, R.mipmap.smiley_73, "[瓢虫]", "[73]"),
    KJEMOJI74(0, 1, R.mipmap.smiley_74, "[便便]", "[74]"),
    KJEMOJI75(0, 1, R.mipmap.smiley_75, "[月亮]", "[75]"),
    KJEMOJI76(0, 1, R.mipmap.smiley_76, "[太阳]", "[76]"),
    KJEMOJI77(0, 1, R.mipmap.smiley_77, "[礼物]", "[77]"),
    KJEMOJI78(0, 1, R.mipmap.smiley_78, "[拥抱]", "[78]"),
    KJEMOJI79(0, 1, R.mipmap.smiley_79, "[强]", "[79]"),
    KJEMOJI80(0, 1, R.mipmap.smiley_80, "[弱]", "[80]"),
    KJEMOJI81(0, 1, R.mipmap.smiley_81, "[握手]", "[81]"),
    KJEMOJI82(0, 1, R.mipmap.smiley_82, "[胜利]", "[82]"),
    KJEMOJI83(0, 1, R.mipmap.smiley_83, "[抱拳]", "[83]"),
    KJEMOJI84(0, 1, R.mipmap.smiley_84, "[勾引]", "[84]"),
    KJEMOJI85(0, 1, R.mipmap.smiley_85, "[拳头]", "[85]"),
    KJEMOJI86(0, 1, R.mipmap.smiley_86, "[差劲]", "[86]"),
    KJEMOJI87(0, 1, R.mipmap.smiley_87, "[爱你]", "[87]"),
    KJEMOJI88(0, 1, R.mipmap.smiley_88, "[NO]", "[88]"),
    KJEMOJI89(0, 1, R.mipmap.smiley_89, "[OK]", "[89]"),
    KJEMOJI90(0, 1, R.mipmap.smiley_90, "[爱情]", "[90]"),
    KJEMOJI91(0, 1, R.mipmap.smiley_91, "[飞吻]", "[91]"),
    KJEMOJI92(0, 1, R.mipmap.smiley_92, "[跳跳]", "[92]"),
    KJEMOJI93(0, 1, R.mipmap.smiley_93, "[发抖]", "[93]"),
    KJEMOJI94(0, 1, R.mipmap.smiley_94, "[怄火]", "[94]"),
    KJEMOJI95(0, 1, R.mipmap.smiley_95, "[转圈]", "[95]"),
    KJEMOJI96(0, 1, R.mipmap.smiley_96, "[磕头]", "[96]"),
    KJEMOJI97(0, 1, R.mipmap.smiley_97, "[回头]", "[97]"),
    KJEMOJI98(0, 1, R.mipmap.smiley_98, "[跳绳]", "[98]"),
    KJEMOJI99(0, 1, R.mipmap.smiley_99, "[投降]", "[99]"),
    KJEMOJI100(0, 1, R.mipmap.smiley_100, "[激动]", "[100]"),
    KJEMOJI101(0, 1, R.mipmap.smiley_101, "[乱舞]", "[101]"),
    KJEMOJI102(0, 1, R.mipmap.smiley_102, "[献吻]", "[102]"),
    KJEMOJI103(0, 1, R.mipmap.smiley_103, "[左太极]", "[103]"),
    KJEMOJI104(0, 1, R.mipmap.smiley_104, "[右太极]", "[104]");



    /*********************************
     * 操作
     **************************************/
    private String emojiStr;
    private String remote;
    private int value;
    private int resId;
    private int type;
    private static Map<String, Integer> sEmojiMap;

    private DisplayRules(int type, int value, int resId, String cls,
                         String remote) {
        this.type = type;
        this.emojiStr = cls;
        this.value = value;
        this.resId = resId;
        this.remote = remote;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public String getEmojiStr() {
        return emojiStr;
    }

    public int getValue() {
        return value;
    }

    public int getResId() {
        return resId;
    }

    public int getType() {
        return type;
    }

    private static Emojicon getEmojiFromEnum(DisplayRules data) {
        return new Emojicon(data.getResId(), data.getValue(),
                data.getEmojiStr(), data.getRemote());
    }

    public static Emojicon getEmojiFromRes(int resId) {
        for (DisplayRules data : values()) {
            if (data.getResId() == resId) {
                return getEmojiFromEnum(data);
            }
        }
        return null;
    }

    public static Emojicon getEmojiFromValue(int value) {
        for (DisplayRules data : values()) {
            if (data.getValue() == value) {
                return getEmojiFromEnum(data);
            }
        }
        return null;
    }

    public static Emojicon getEmojiFromName(String emojiStr) {
        for (DisplayRules data : values()) {
            if (data.getEmojiStr().equals(emojiStr)) {
                return getEmojiFromEnum(data);
            }
        }
        return null;
    }

    /**
     * 提高效率，忽略线程安全
     */
    public static Map<String, Integer> getMapAll() {
        if (sEmojiMap == null) {
            sEmojiMap = new HashMap<String, Integer>();
            for (DisplayRules data : values()) {
                sEmojiMap.put(data.getEmojiStr(), data.getResId());
                sEmojiMap.put(data.getRemote(), data.getResId());
            }
        }
        return sEmojiMap;
    }

    public static List<Emojicon> getAllByType(int type) {
        List<Emojicon> datas = new ArrayList<Emojicon>(values().length);
        for (DisplayRules data : values()) {
            if (data.getType() == type) {
                datas.add(getEmojiFromEnum(data));
            }
        }
        return datas;
    }
}
